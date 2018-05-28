package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.libs.json._

import scala.concurrent.{ Future, ExecutionContext }
import models.extensions._
import org.joda.time.{DateTime, LocalDate}
import com.github.tototoshi.slick.H2JodaSupport._

@Singleton
class HelicopterLandingAreaAreaTypeRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, helicopter_landing_area_typeRepo: HelicopterLandingAreaTypeRepository, helicopter_landing_area_area_configurationRepo: HelicopterLandingAreaAreaConfigurationRepository, helicopter_landing_area_surface_typeRepo: HelicopterLandingAreaSurfaceTypeRepository)(implicit ec: ExecutionContext) extends DatabaseClient[HelicopterLandingAreaAreaType] with HelicopterLandingAreaAreaTypeQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class HelicopterLandingAreaAreaTypeTable(tag: Tag) extends Table[HelicopterLandingAreaAreaType](tag, "helicopter_landing_area_area_type") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def helicopterLandingArea = column[String]("helicopter_landing_area", O.Default(""))
    def helicopterLandingAreaTypeId = column[Long]("helicopter_landing_area_type_id", O.Default(0L))
    def helicopterLandingAreaAreaConfigurationId = column[Long]("helicopter_landing_area_area_configuration_id", O.Default(0L))
    def helicopterLandingAreaSurfaceTypeId = column[Long]("helicopter_landing_area_surface_type_id", O.Default(0L))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, helicopterLandingArea, helicopterLandingAreaTypeId, helicopterLandingAreaAreaConfigurationId, helicopterLandingAreaSurfaceTypeId).shaped <> (HelicopterLandingAreaAreaType.tupled, HelicopterLandingAreaAreaType.unapply)
  }

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = HelicopterLandingAreaAreaTypeTable
  val all = TableQuery[HelicopterLandingAreaAreaTypeTable]

  def byHelicopterLandingAreaTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.helicopterLandingAreaTypeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byHelicopterLandingAreaAreaConfigurationId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.helicopterLandingAreaAreaConfigurationId===id).result
  }}.getOrElse{Future(List())}
                            

  def byHelicopterLandingAreaSurfaceTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.helicopterLandingAreaSurfaceTypeId===id).result
  }}.getOrElse{Future(List())}
                            


  def getHelicopterLandingAreaType(obj: HelicopterLandingAreaAreaType) = helicopter_landing_area_typeRepo.byId(obj.helicopterLandingAreaTypeId)
  def getHelicopterLandingAreaAreaConfiguration(obj: HelicopterLandingAreaAreaType) = helicopter_landing_area_area_configurationRepo.byId(obj.helicopterLandingAreaAreaConfigurationId)
  def getHelicopterLandingAreaSurfaceType(obj: HelicopterLandingAreaAreaType) = helicopter_landing_area_surface_typeRepo.byId(obj.helicopterLandingAreaSurfaceTypeId)
}