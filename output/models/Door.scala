package models
import models.extensions.DoorExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Door(id: Option[Long],
                branchId: Long,
                door: String) extends DoorExtension{
  lazy val selectString = door
  def getBranch = BranchQuery.byId(branchId)
}



class DoorMapping(tag: Tag) extends Table[Door](tag, "door") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def branchId = column[Long]("branch_id")
  def branch = foreignKey("branch_id_fk", branchId, BranchQuery.all)(_.id, onDelete=ForeignKeyAction.Cascade)
  def door = column[String]("door")

  def * = (id.?, branchId, door).shaped <> (Door.tupled, Door.unapply)
}

class DoorQueryBase extends DatabaseClient[Door] {
  type DBTable = DoorMapping

  private[models] val all = {
    TableQuery[DBTable]
  }
  def byBranchId(id: Option[Long]) = database.withSession { implicit db: Session =>
    id.map{i =>
      all.filter(_.branchId===i).list
    }.getOrElse(List())
  }
                            
}
case class DoorFormData(obj: Door){
  def update(updatedObj: Door = obj) = {

    DoorQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Door) = {
    val id = DoorQuery.insert(insertedObj)

    id
  }
}

object DoorForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "branchId" -> optional(longNumber),
      "door" -> text
    )/*(Door.apply)(Door.unapply)*/
    ((id,branchId,door) => {
      DoorFormData(Door(id, branchId.getOrElse(0), door))
    })((formData: DoorFormData) => {
      Some(formData.obj.id, Some(formData.obj.branchId), formData.obj.door)
    })
  )
}