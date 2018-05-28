package models
import models.extensions.PerroExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Perro(id: Option[Long] = None /*None*/,
                 nombre: String = "" /*None*/,
                 edad: Int = 0 /*None*/) extends PerroExtension{
  lazy val selectString = nombre

}



class PerroMapping(tag: Tag) extends Table[Perro](tag, "perro") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def nombre = column[String]("nombre", O.Default(""))
  def edad = column[Int]("edad", O.Default(0))

  def * = (id.?, nombre, edad).shaped <> (Perro.tupled, Perro.unapply)
}

class PerroQueryBase extends DatabaseClient[Perro] {
  type DBTable = PerroMapping

  private[models] val all = {
    TableQuery[DBTable]
  }

}
case class PerroFormData(obj: Perro){
  def update(updatedObj: Perro = obj) = {

    PerroQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Perro) = {
    val id = PerroQuery.insert(insertedObj)

    id
  }
}

object PerroForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "nombre" -> text,
      "edad" -> number
    )/*(Perro.apply)(Perro.unapply)*/
    ((id,nombre,edad) => {
      PerroFormData(Perro(id, nombre, edad))
    })((formData: PerroFormData) => {
      Some((formData.obj.id, formData.obj.nombre, formData.obj.edad))
    })
  )
}