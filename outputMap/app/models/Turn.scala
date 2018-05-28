package models
import models.extensions.TurnExtension
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

case class Turn(id: Option[Long] = None /*None*/,
                turn: Int = 0 /*None*/,
                observations: String = "" /*None*/,
                createdAt: Option[DateTime] = None /*Hidden*/,
                updatedAt: Option[DateTime] = None /*Hidden*/) extends TurnExtension{
  lazy val selectString = turn
}

object Turn {
  implicit val format = Json.format[Turn]
  val tupled = (this.apply _).tupled
}




case class TurnFormData(obj: Turn){
  def update(updatedObj: Turn = obj)(implicit repo: TurnRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Turn)(implicit repo: TurnRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}

object TurnForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "turn" -> number,
      "observations" -> text
    )/*(Turn.apply)(Turn.unapply)*/
    ((id,turn,observations) => {
      TurnFormData(Turn(id, turn, observations, Some(new DateTime()), Some(new DateTime())))
    })((formData: TurnFormData) => {
      Some((formData.obj.id, formData.obj.turn, formData.obj.observations))
    })
  )
}