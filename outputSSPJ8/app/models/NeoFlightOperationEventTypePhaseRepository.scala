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
class NeoFlightOperationEventTypePhaseRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, neo_flight_operationRepo: NeoFlightOperationRepository, neo_event_typeRepo: NeoEventTypeRepository, phaseRepo: PhaseRepository)(implicit ec: ExecutionContext) extends DatabaseClient[NeoFlightOperationEventTypePhase] with NeoFlightOperationEventTypePhaseQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class NeoFlightOperationEventTypePhaseTable(tag: Tag) extends Table[NeoFlightOperationEventTypePhase](tag, "neo_flight_operation_event_type_phase") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def neoFlightOperationId = column[Long]("neo_flight_operation_id", O.Default(0L))
    def neoEventTypeId = column[Long]("neo_event_type_id", O.Default(0L))
    def phaseId = column[Long]("phase_id", O.Default(0L))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, neoFlightOperationId, neoEventTypeId, phaseId).shaped <> (NeoFlightOperationEventTypePhase.tupled, NeoFlightOperationEventTypePhase.unapply)
  }

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = NeoFlightOperationEventTypePhaseTable
  val all = TableQuery[NeoFlightOperationEventTypePhaseTable]

  def byNeoFlightOperationId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.neoFlightOperationId===id).result
  }}.getOrElse{Future(List())}
                            

  def byNeoEventTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.neoEventTypeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byPhaseId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.phaseId===id).result
  }}.getOrElse{Future(List())}
                            


  def getNeoFlightOperation(obj: NeoFlightOperationEventTypePhase) = neo_flight_operationRepo.byId(obj.neoFlightOperationId)
  def getNeoEventType(obj: NeoFlightOperationEventTypePhase) = neo_event_typeRepo.byId(obj.neoEventTypeId)
  def getPhase(obj: NeoFlightOperationEventTypePhase) = phaseRepo.byId(obj.phaseId)
}