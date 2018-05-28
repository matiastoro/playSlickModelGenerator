package models
import models.extensions.CharacterExtension
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

case class Character(id: Option[Long] = None /*None*/,
                     character: String = "" /*None*/,
                     background: String = "" /*None*/,
                     observations: String = "" /*None*/,
                     leader: Boolean = false /*None*/,
                     userId: Long = 0 /*None*/,
                     number: Int = 0 /*None*/,
                     q: Int = 0 /*None*/,
                     r: Int = 0 /*None*/,
                     s: Int = 0 /*None*/) extends CharacterExtension{
  lazy val selectString = character
}

object Character {
  implicit val format = Json.format[Character]
  val tupled = (this.apply _).tupled
}




case class CharacterFormData(obj: Character){
  def update(updatedObj: Character = obj)(implicit repo: CharacterRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Character)(implicit repo: CharacterRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}

object CharacterForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "character" -> text,
      "background" -> text,
      "observations" -> text,
      "leader" -> boolean,
      "userId" -> optional(longNumber),
      "number" -> number,
      "q" -> number,
      "r" -> number,
      "s" -> number
    )/*(Character.apply)(Character.unapply)*/
    ((id,character,background,observations,leader,userId,number,q,r,s) => {
      CharacterFormData(Character(id, character, background, observations, leader, userId.getOrElse(0), number, q, r, s))
    })((formData: CharacterFormData) => {
      Some((formData.obj.id, formData.obj.character, formData.obj.background, formData.obj.observations, formData.obj.leader, Some(formData.obj.userId), formData.obj.number, formData.obj.q, formData.obj.r, formData.obj.s))
    })
  )
}