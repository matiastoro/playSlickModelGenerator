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
class LicenseTypeRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends DatabaseClient[LicenseType] with LicenseTypeQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class LicenseTypeTable(tag: Tag) extends Table[LicenseType](tag, "license_type"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def licenseType = column[String]("license_type", O.Default(""))
    def licenseTypeId = column[Option[Long]]("license_type_id", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, licenseType, licenseTypeId).shaped <> (LicenseType.tupled, LicenseType.unapply)
  }

  override val sortMap = Map[String, SortMapType](
    "id" -> {(q,s) => q.sortBy(_.id)(s)},
    "licenseType" -> {(q,s) => q.sortBy(_.licenseType)(s)},
    //displayField for field licenseType is not defined in YML
  )
      

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = LicenseTypeTable
  val all = TableQuery[LicenseTypeTable]

  def byLicenseTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.licenseTypeId===id).result
  }}.getOrElse{Future(List())}
                            


  def getLicenseType(obj: LicenseType) = if(obj.licenseTypeId.isDefined) this.byId(obj.licenseTypeId.get) else None

  def filter(formData: LicenseTypeFilter) = {
    all
      .filteredBy( formData.id.flatMap{x => if(x>0L) Some(x) else None} )( _.id === _ )
      .filteredBy(formData.licenseType)((x,y) => x.licenseType.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy( formData.licenseTypeId.flatMap{x => if(x>0L) Some(x) else None} )( _.licenseTypeId.getOrElse(0L) === _ )
  }
}