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
class AerodromeRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, aerodrome_statusRepo: AerodromeStatusRepository, aerodrome_typeRepo: AerodromeTypeRepository, runwayRepo: Provider[RunwayRepository], helicopter_landing_areaRepo: Provider[HelicopterLandingAreaRepository])(implicit ec: ExecutionContext) extends DatabaseClient[Aerodrome] with AerodromeQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class AerodromeTable(tag: Tag) extends Table[Aerodrome](tag, "aerodrome"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def icaoCode = column[String]("icaoCode", O.Default(""))
    def aerodromeStatusId = column[Long]("aerodrome_status_id", O.Default(0L))
    def aerodromeTypeId = column[Long]("aerodrome_type_id", O.Default(0L))
    def latitude = column[Option[String]]("latitude", O.Default(None))
    def longitude = column[Option[String]]("longitude", O.Default(None))
    def elevationAboveMsl = column[Option[Double]]("elevation_above_msl", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, icaoCode, aerodromeStatusId, aerodromeTypeId, latitude, longitude, elevationAboveMsl).shaped <> (Aerodrome.tupled, Aerodrome.unapply)
  }

  override val sortMap = Map[String, SortMapType](
    "id" -> {(q,s) => q.sortBy(_.id)(s)},
    "icaoCode" -> {(q,s) => q.sortBy(_.icaoCode)(s)},
    "aerodromeStatusId" -> {(q,s) => q.join(aerodrome_statusRepo.all).on(_.aerodromeStatusId === _.id).sortBy(_._2.status)(s).map{_._1}},
    "aerodromeTypeId" -> {(q,s) => q.join(aerodrome_typeRepo.all).on(_.aerodromeTypeId === _.id).sortBy(_._2.tpe)(s).map{_._1}},
    "latitude" -> {(q,s) => q.sortBy(_.latitude)(s)},
    "longitude" -> {(q,s) => q.sortBy(_.longitude)(s)},
    "elevationAboveMsl" -> {(q,s) => q.sortBy(_.elevationAboveMsl)(s)}
  )
      

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = AerodromeTable
  val all = TableQuery[AerodromeTable]

  def byAerodromeStatusId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.aerodromeStatusId===id).result
  }}.getOrElse{Future(List())}
                            

  def byAerodromeTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.aerodromeTypeId===id).result
  }}.getOrElse{Future(List())}
                            


  def getAerodromeStatus(obj: Aerodrome) = aerodrome_statusRepo.byId(obj.aerodromeStatusId)
  def getAerodromeType(obj: Aerodrome) = aerodrome_typeRepo.byId(obj.aerodromeTypeId)

  def filter(formData: AerodromeFilter) = {
    all
      .filteredBy( formData.id.flatMap{x => if(x>0L) Some(x) else None} )( _.id === _ )
      .filteredBy(formData.icaoCode)((x,y) => x.icaoCode.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy( formData.aerodromeStatusId.collect{case x if x>0L => x} )( _.aerodromeStatusId === _ )
      .filteredBy( formData.aerodromeTypeId.collect{case x if x>0L => x} )( _.aerodromeTypeId === _ )
      .filteredBy(formData.latitude)((x,y) => x.latitude.getOrElse("").toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.longitude)((x,y) => x.longitude.getOrElse("").toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.elevationAboveMsl)((x,y) => x.elevationAboveMsl.getOrElse(0.0) === y)
      .oneToManyFilterfilter(formData.runways)((x,o) => runwayRepo.get().filter(o).filter(y => y.aerodromeId === x.id).exists)
      .oneToManyFilterfilter(formData.helicopterLandingAreas)((x,o) => helicopter_landing_areaRepo.get().filter(o).filter(y => y.aerodromeId === x.id).exists)
  }
}