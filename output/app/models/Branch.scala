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

case class Branch(id: Option[Long] = None /*None*/,
                  branch: String = "" /*None*/,
                  companyId: Long = 0 /*None*/,
                  address: String = "" /*None*/,
                  visible: Boolean = false /*None*/,
                  state: Int = 0 /*None*/,
                  createdAt: Option[DateTime] = None /*Hidden*/,
                  updatedAt: Option[DateTime] = None /*Hidden*/) extends BranchExtension{
  lazy val selectString = branch
  def getCompany = CompanyQuery.byId(companyId)
}



class BranchMapping(tag: Tag) extends Table[Branch](tag, "branch") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def branch = column[String]("branch", O.Default(""))
  def companyId = column[Long]("company_id", O.Default(0))
  def company = foreignKey("company_id_fk", companyId, CompanyQuery.all)(_.id, onDelete=ForeignKeyAction.Cascade)
  def address = column[String]("address", O.Default(""))
  def visible = column[Boolean]("visible", O.Default(false))
  def state = column[Int]("state", O.Default(0))
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
case class BranchFormData(obj: Branch, Doors: List[DoorFormData], Windows: List[WindowFormData]){
  def update(updatedObj: Branch = obj) = {
    //Delete elements that are not part of the form but they do exists in the database.
    DoorQuery.byBranchId(obj.id).filterNot{o => Doors.exists(_.obj.id == o.id)}.map{DoorQuery.delete(_)}
    Doors.map{o => o.update(o.obj.copy(branchId = obj.id.get))}
    //Delete elements that are not part of the form but they do exists in the database.
    WindowQuery.byBranchId(obj.id).filterNot{o => Windows.exists(_.obj.id == o.id)}.map{WindowQuery.delete(_)}
    Windows.map{o => o.update(o.obj.copy(branchId = obj.id.get))}
    BranchQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Branch) = {
    val id = BranchQuery.insert(insertedObj)
    Doors.map{o => o.insert(o.obj.copy(branchId = id))}
    Windows.map{o => o.insert(o.obj.copy(branchId = id))}
    id
  }
}
object BranchFormData{
  def apply(obj: Branch) = {
    val Doors = DoorQuery.byBranchId(obj.id).map(DoorFormData(_))
    val Windows = WindowQuery.byBranchId(obj.id).map(WindowFormData(_))
    new BranchFormData(obj, Doors, Windows)
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
      "Doors" -> list(models.DoorForm.form.mapping),
      "Windows" -> list(models.WindowForm.form.mapping)
    )/*(Branch.apply)(Branch.unapply)*/
    ((id,branch,companyId,address,visible,state,Doors,Windows) => {
      BranchFormData(Branch(id, branch, companyId.getOrElse(0), address, visible, state, Some(new DateTime()), Some(new DateTime())), Doors, Windows)
    })((formData: BranchFormData) => {
      Some((formData.obj.id, formData.obj.branch, Some(formData.obj.companyId), formData.obj.address, formData.obj.visible, formData.obj.state, formData.Doors, formData.Windows))
    })
  )
}