package models
import models.extensions.ActionExtension
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import org.joda.time.{DateTime, LocalDate}
//import com.github.tototoshi.slick.H2JodaSupport._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._


/*import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._


import play.api.libs.json._*/

case class Action(id: Option[Long] = None /*None*/,
                  userId: Long = 0 /*None*/,
                  turnId: Long = 0 /*None*/,
                  characterId: Long = 0 /*None*/,
                  fromQ: Int = 0 /*None*/,
                  fromR: Int = 0 /*None*/,
                  fromS: Int = 0 /*None*/,
                  toQ: Int = 0 /*None*/,
                  toR: Int = 0 /*None*/,
                  toS: Int = 0 /*None*/,
                  createdAt: Option[DateTime] = None /*Hidden*/,
                  updatedAt: Option[DateTime] = None /*Hidden*/) extends ActionExtension{
  lazy val selectString = id
}

object Action {
  implicit val format = Json.format[Action]
  val tupled = (this.apply _).tupled
}




case class ActionFormData(obj: Action){
  def update(updatedObj: Action = obj)(implicit repo: ActionRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Action)(implicit repo: ActionRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}

object ActionForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "userId" -> longNumber,
      "turnId" -> longNumber,
      "characterId" -> longNumber,
      "fromQ" -> number,
      "fromR" -> number,
      "fromS" -> number,
      "toQ" -> number,
      "toR" -> number,
      "toS" -> number
    )/*(Action.apply)(Action.unapply)*/
    ((id,userId,turnId,characterId,fromQ,fromR,fromS,toQ,toR,toS) => {
      ActionFormData(Action(id, userId, turnId, characterId, fromQ, fromR, fromS, toQ, toR, toS, Some(new DateTime()), Some(new DateTime())))
    })((formData: ActionFormData) => {
      Some((formData.obj.id, formData.obj.userId, formData.obj.turnId, formData.obj.characterId, formData.obj.fromQ, formData.obj.fromR, formData.obj.fromS, formData.obj.toQ, formData.obj.toR, formData.obj.toS))
    })
  )
}