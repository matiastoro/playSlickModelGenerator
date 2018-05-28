package models
import models.extensions.ArmyExtension
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

case class Army(id: Option[Long] = None /*None*/,
                army: String = "" /*None*/) extends ArmyExtension{
  lazy val selectString = army
}

object Army {
  implicit val format = Json.format[Army]
  val tupled = (this.apply _).tupled
}




case class ArmyFormData(obj: Army){
  def update(updatedObj: Army = obj)(implicit repo: ArmyRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Army)(implicit repo: ArmyRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}

object ArmyForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "army" -> text
    )/*(Army.apply)(Army.unapply)*/
    ((id,army) => {
      ArmyFormData(Army(id, army))
    })((formData: ArmyFormData) => {
      Some((formData.obj.id, formData.obj.army))
    })
  )
}