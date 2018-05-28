package models
import models.extensions.HexExtension
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

case class Hex(id: Option[Long] = None /*None*/,
               q: Int = 0 /*None*/,
               r: Int = 0 /*None*/,
               s: Int = 0 /*None*/,
               userId: Long = 0 /*None*/) extends HexExtension{
  lazy val selectString = id
}

object Hex {
  implicit val format = Json.format[Hex]
  val tupled = (this.apply _).tupled
}




case class HexFormData(obj: Hex){
  def update(updatedObj: Hex = obj)(implicit repo: HexRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Hex)(implicit repo: HexRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}

object HexForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "q" -> number,
      "r" -> number,
      "s" -> number,
      "userId" -> longNumber
    )/*(Hex.apply)(Hex.unapply)*/
    ((id,q,r,s,userId) => {
      HexFormData(Hex(id, q, r, s, userId))
    })((formData: HexFormData) => {
      Some((formData.obj.id, formData.obj.q, formData.obj.r, formData.obj.s, formData.obj.userId))
    })
  )
}