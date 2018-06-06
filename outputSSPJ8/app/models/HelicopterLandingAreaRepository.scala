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
class HelicopterLandingAreaRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, aerodromeRepo: AerodromeRepository, helicopter_landing_area_typeRepo: HelicopterLandingAreaTypeRepository, helicopter_landing_area_area_configurationRepo: HelicopterLandingAreaAreaConfigurationRepository, helicopter_landing_area_surface_typeRepo: HelicopterLandingAreaSurfaceTypeRepository)(implicit ec: ExecutionContext) extends DatabaseClient[HelicopterLandingArea] with HelicopterLandingAreaQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class HelicopterLandingAreaTable(tag: Tag) extends Table[HelicopterLandingArea](tag, "helicopter_landing_area"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def helicopterLandingArea = column[String]("helicopter_landing_area", O.Default(""))
    def aerodromeId = column[Long]("aerodrome_id", O.Default(0L))
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
    def * = (id.?, helicopterLandingArea, aerodromeId, helicopterLandingAreaTypeId, helicopterLandingAreaAreaConfigurationId, helicopterLandingAreaSurfaceTypeId).shaped <> (HelicopterLandingArea.tupled, HelicopterLandingArea.unapply)
  }

  override val sortMap = Map[String, SortMapType](
    "id" -> {(q,s) => q.sortBy(_.id)(s)},
    "helicopterLandingArea" -> {(q,s) => q.sortBy(_.helicopterLandingArea)(s)},
    //displayField for field aerodrome is not defined in YML,
    "helicopterLandingAreaTypeId" -> {(q,s) => q.join(helicopter_landing_area_typeRepo.all).on(_.helicopterLandingAreaTypeId === _.id).sortBy(_._2.tpe)(s).map{_._1}},
    "helicopterLandingAreaAreaConfigurationId" -> {(q,s) => q.join(helicopter_landing_area_area_configurationRepo.all).on(_.helicopterLandingAreaAreaConfigurationId === _.id).sortBy(_._2.areaConfiguration)(s).map{_._1}},
    "helicopterLandingAreaSurfaceTypeId" -> {(q,s) => q.join(helicopter_landing_area_surface_typeRepo.all).on(_.helicopterLandingAreaSurfaceTypeId === _.id).sortBy(_._2.surfaceType)(s).map{_._1}}
  )
      

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = HelicopterLandingAreaTable
  val all = TableQuery[HelicopterLandingAreaTable]

  def byAerodromeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.aerodromeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byHelicopterLandingAreaTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.helicopterLandingAreaTypeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byHelicopterLandingAreaAreaConfigurationId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.helicopterLandingAreaAreaConfigurationId===id).result
  }}.getOrElse{Future(List())}
                            

  def byHelicopterLandingAreaSurfaceTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.helicopterLandingAreaSurfaceTypeId===id).result
  }}.getOrElse{Future(List())}
                            


  def getAerodrome(obj: HelicopterLandingArea) = aerodromeRepo.byId(obj.aerodromeId)
  def getHelicopterLandingAreaType(obj: HelicopterLandingArea) = helicopter_landing_area_typeRepo.byId(obj.helicopterLandingAreaTypeId)
  def getHelicopterLandingAreaAreaConfiguration(obj: HelicopterLandingArea) = helicopter_landing_area_area_configurationRepo.byId(obj.helicopterLandingAreaAreaConfigurationId)
  def getHelicopterLandingAreaSurfaceType(obj: HelicopterLandingArea) = helicopter_landing_area_surface_typeRepo.byId(obj.helicopterLandingAreaSurfaceTypeId)

  def filter(formData: HelicopterLandingAreaFilter) = {
    all
      .filteredBy( formData.id.flatMap{x => if(x>0L) Some(x) else None} )( _.id === _ )
      .filteredBy(formData.helicopterLandingArea)((x,y) => x.helicopterLandingArea.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy( formData.aerodromeId.collect{case x if x>0L => x} )( _.aerodromeId === _ )
      .filteredBy( formData.helicopterLandingAreaTypeId.collect{case x if x>0L => x} )( _.helicopterLandingAreaTypeId === _ )
      .filteredBy( formData.helicopterLandingAreaAreaConfigurationId.collect{case x if x>0L => x} )( _.helicopterLandingAreaAreaConfigurationId === _ )
      .filteredBy( formData.helicopterLandingAreaSurfaceTypeId.collect{case x if x>0L => x} )( _.helicopterLandingAreaSurfaceTypeId === _ )
  }
}