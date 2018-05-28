package models
import models.extensions.ObjExtension
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

case class Obj(id: Option[Long] = None /*None*/,
               tpe: Int = 0 /*None*/,
               q: Int = 0 /*None*/,
               r: Int = 0 /*None*/,
               s: Int = 0 /*None*/) extends ObjExtension{
  lazy val selectString = id
}

object Obj {
  implicit val format = Json.format[Obj]
  val tupled = (this.apply _).tupled
}




case class ObjFormData(obj: Obj){
  def update(updatedObj: Obj = obj)(implicit repo: ObjRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Obj)(implicit repo: ObjRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}

object ObjForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "tpe" -> number,
      "q" -> number,
      "r" -> number,
      "s" -> number
    )/*(Obj.apply)(Obj.unapply)*/
    ((id,tpe,q,r,s) => {
      ObjFormData(Obj(id, tpe, q, r, s))
    })((formData: ObjFormData) => {
      Some((formData.obj.id, formData.obj.tpe, formData.obj.q, formData.obj.r, formData.obj.s))
    })
  )
}