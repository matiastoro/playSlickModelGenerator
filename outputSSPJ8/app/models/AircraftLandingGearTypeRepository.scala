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
class AircraftLandingGearTypeRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends DatabaseClient[AircraftLandingGearType] with AircraftLandingGearTypeQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class AircraftLandingGearTypeTable(tag: Tag) extends Table[AircraftLandingGearType](tag, "aircraft_landing_gear_type"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def landingGearType = column[String]("landing_gear_type", O.Default(""))
    def aircraftLandingGearTypeId = column[Option[Long]]("aircraft_landing_gear_type_id", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, landingGearType, aircraftLandingGearTypeId).shaped <> (AircraftLandingGearType.tupled, AircraftLandingGearType.unapply)
  }

  override val sortMap = Map[String, SortMapType](
    "id" -> {(q,s) => q.sortBy(_.id)(s)},
    "landingGearType" -> {(q,s) => q.sortBy(_.landingGearType)(s)},
    "aircraftLandingGearTypeId" -> {(q,s) => q.join(all).on(_.aircraftLandingGearTypeId === _.id).sortBy(_._2.landingGearType)(s).map{_._1}}
  )
      

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = AircraftLandingGearTypeTable
  val all = TableQuery[AircraftLandingGearTypeTable]

  def byAircraftLandingGearTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.aircraftLandingGearTypeId===id).result
  }}.getOrElse{Future(List())}
                            


  def getAircraftLandingGearType(obj: AircraftLandingGearType) = if(obj.aircraftLandingGearTypeId.isDefined) this.byId(obj.aircraftLandingGearTypeId.get) else None

  def filter(formData: AircraftLandingGearTypeFilter) = {
    all
      .filteredBy( formData.id.flatMap{x => if(x>0L) Some(x) else None} )( _.id === _ )
      .filteredBy(formData.landingGearType)((x,y) => x.landingGearType.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy( formData.aircraftLandingGearTypeId.flatMap{x => if(x>0L) Some(x) else None} )( _.aircraftLandingGearTypeId.getOrElse(0L) === _ )
  }
}