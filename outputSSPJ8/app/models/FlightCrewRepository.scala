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
class FlightCrewRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, neo_flight_operationRepo: NeoFlightOperationRepository, flight_crew_categoryRepo: FlightCrewCategoryRepository)(implicit ec: ExecutionContext) extends DatabaseClient[FlightCrew] with FlightCrewQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class FlightCrewTable(tag: Tag) extends Table[FlightCrew](tag, "flight_crew") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def neoFlightOperationId = column[Long]("neo_flight_operation_id", O.Default(0L))
    def categoryId = column[Long]("category_id", O.Default(0L))
    def experienceThis = column[Option[Double]]("experience_this", O.Default(None))
    def experienceAll = column[Option[Double]]("experience_all", O.Default(None))
    def dutyLast24Hours = column[Option[Double]]("duty_last_24_hours", O.Default(None))
    def restBeforeDuty = column[Option[Double]]("rest_before_duty", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, neoFlightOperationId, categoryId, experienceThis, experienceAll, dutyLast24Hours, restBeforeDuty).shaped <> (FlightCrew.tupled, FlightCrew.unapply)
  }

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = FlightCrewTable
  val all = TableQuery[FlightCrewTable]

  def byNeoFlightOperationId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.neoFlightOperationId===id).result
  }}.getOrElse{Future(List())}
                            

  def byCategoryId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.categoryId===id).result
  }}.getOrElse{Future(List())}
                            


  def getNeoFlightOperation(obj: FlightCrew) = neo_flight_operationRepo.byId(obj.neoFlightOperationId)
  def getCategory(obj: FlightCrew) = flight_crew_categoryRepo.byId(obj.categoryId)
}