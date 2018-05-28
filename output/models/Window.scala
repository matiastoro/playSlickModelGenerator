package models
import models.extensions.WindowExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Window(id: Option[Long],
                  branchId: Long,
                  window: String) extends WindowExtension{
  lazy val selectString = window
  def getBranch = BranchQuery.byId(branchId)
}



class WindowMapping(tag: Tag) extends Table[Window](tag, "window") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def branchId = column[Long]("branch_id")
  def branch = foreignKey("branch_id_fk", branchId, BranchQuery.all)(_.id, onDelete=ForeignKeyAction.Cascade)
  def window = column[String]("window")

  def * = (id.?, branchId, window).shaped <> (Window.tupled, Window.unapply)
}

class WindowQueryBase extends DatabaseClient[Window] {
  type DBTable = WindowMapping

  private[models] val all = {
    TableQuery[DBTable]
  }
  def byBranchId(id: Option[Long]) = database.withSession { implicit db: Session =>
    id.map{i =>
      all.filter(_.branchId===i).list
    }.getOrElse(List())
  }
                            
}
case class WindowFormData(obj: Window){
  def update(updatedObj: Window = obj) = {

    WindowQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Window) = {
    val id = WindowQuery.insert(insertedObj)

    id
  }
}

object WindowForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "branchId" -> optional(longNumber),
      "window" -> text
    )/*(Window.apply)(Window.unapply)*/
    ((id,branchId,window) => {
      WindowFormData(Window(id, branchId.getOrElse(0), window))
    })((formData: WindowFormData) => {
      Some(formData.obj.id, Some(formData.obj.branchId), formData.obj.window)
    })
  )
}