package models
import models.extensions.BranchExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Branch(id: Option[Long],
                  branch: String,
                  companyId: Long,
                  address: String,
                  visible: Boolean,
                  state: Int,
                  createdAt: Option[DateTime],
                  updatedAt: Option[DateTime]) extends BranchExtension{
  lazy val selectString = branch
  def getCompany = CompanyQuery.byId(companyId)
}



class BranchMapping(tag: Tag) extends Table[Branch](tag, "branch") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def branch = column[String]("branch")
  def companyId = column[Long]("company_id")
  def company = foreignKey("company_id_fk", companyId, CompanyQuery.all)(_.id, onDelete=ForeignKeyAction.Cascade)
  def address = column[String]("address")
  def visible = column[Boolean]("visible")
  def state = column[Int]("state")
  def createdAt = column[Option[DateTime]]("created_at", O.Default(None))
  def updatedAt = column[Option[DateTime]]("updated_at", O.Default(None))

  def * = (id.?, branch, companyId, address, visible, state, createdAt, updatedAt).shaped <> (Branch.tupled, Branch.unapply)
}

class BranchQueryBase extends DatabaseClient[Branch] {
  type DBTable = BranchMapping

  private[models] val all = {
    TableQuery[DBTable]
  }
  def byCompanyId(id: Option[Long]) = database.withSession { implicit db: Session =>
    id.map{i =>
      all.filter(_.companyId===i).list
    }.getOrElse(List())
  }
                            
}
case class BranchFormData(obj: Branch, doors: List[DoorFormData], windows: List[WindowFormData]){
  def update(updatedObj: Branch = obj) = {
    //Delete elements that are not part of the form but they do exists in the database.
    DoorQuery.byBranchId(obj.id).filterNot{o => doors.exists(_.obj.id == o.id)}.map{DoorQuery.delete(_)}
    doors.map{o => o.update(o.obj.copy(branchId = obj.id.get))}
    //Delete elements that are not part of the form but they do exists in the database.
    WindowQuery.byBranchId(obj.id).filterNot{o => windows.exists(_.obj.id == o.id)}.map{WindowQuery.delete(_)}
    windows.map{o => o.update(o.obj.copy(branchId = obj.id.get))}
    BranchQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Branch) = {
    val id = BranchQuery.insert(insertedObj)
    doors.map{o => o.insert(o.obj.copy(branchId = id))}
    windows.map{o => o.insert(o.obj.copy(branchId = id))}
    id
  }
}
object BranchFormData{
  def apply(obj: Branch) = {
    val doors = DoorQuery.byBranchId(obj.id).map(DoorFormData(_))
    val windows = WindowQuery.byBranchId(obj.id).map(WindowFormData(_))
    new BranchFormData(obj, doors, windows)
  }
}
object BranchForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "branch" -> text,
      "companyId" -> optional(longNumber),
      "address" -> text,
      "visible" -> boolean,
      "state" -> number,
      "doors" -> list(models.DoorForm.form.mapping),
      "windows" -> list(models.WindowForm.form.mapping)
    )/*(Branch.apply)(Branch.unapply)*/
    ((id,branch,companyId,address,visible,state,doors,windows) => {
      BranchFormData(Branch(id, branch, companyId.getOrElse(0), address, visible, state, Some(new DateTime()), Some(new DateTime())), doors, windows)
    })((formData: BranchFormData) => {
      Some(formData.obj.id, formData.obj.branch, Some(formData.obj.companyId), formData.obj.address, formData.obj.visible, formData.obj.state, formData.doors, formData.windows)
    })
  )
}