package models

import javax.inject.{Inject, Singleton, Provider}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.libs.json._

import scala.concurrent.{ Future, ExecutionContext }
import models.extensions._
import org.joda.time.{DateTime, LocalDate}
import com.github.tototoshi.slick.H2JodaSupport._

@Singleton
class LicenseIssuedByRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends DatabaseClient[LicenseIssuedBy] with LicenseIssuedByQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class LicenseIssuedByTable(tag: Tag) extends Table[LicenseIssuedBy](tag, "license_issued_by"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def licenseIssuedBy = column[String]("license_issued_by", O.Default(""))
    def licenseIssuedById = column[Option[Long]]("license_issued_by_id", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, licenseIssuedBy, licenseIssuedById).shaped <> (LicenseIssuedBy.tupled, LicenseIssuedBy.unapply)
  }

  override val sortMap = Map[String, SortMapType](
    "id" -> {(q,s) => q.sortBy(_.id)(s)},
    "licenseIssuedBy" -> {(q,s) => q.sortBy(_.licenseIssuedBy)(s)},
    //displayField for field licenseIssuedBy is not defined in YML
  )
      

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = LicenseIssuedByTable
  val all = TableQuery[LicenseIssuedByTable]

  def byLicenseIssuedById(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.licenseIssuedById===id).result
  }}.getOrElse{Future(List())}
                            


  def getLicenseIssuedBy(obj: LicenseIssuedBy) = if(obj.licenseIssuedById.isDefined) this.byId(obj.licenseIssuedById.get) else None

  def filter(formData: LicenseIssuedByFilter) = {
    all
      .filteredBy( formData.id.flatMap{x => if(x>0L) Some(x) else None} )( _.id === _ )
      .filteredBy(formData.licenseIssuedBy)((x,y) => x.licenseIssuedBy.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy( formData.licenseIssuedById.flatMap{x => if(x>0L) Some(x) else None} )( _.licenseIssuedById.getOrElse(0L) === _ )
  }
}