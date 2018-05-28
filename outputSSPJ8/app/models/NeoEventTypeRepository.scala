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
class NeoEventTypeRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends DatabaseClient[NeoEventType] with NeoEventTypeQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class NeoEventTypeTable(tag: Tag) extends Table[NeoEventType](tag, "neo_event_type") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def eventType = column[String]("event_type", O.Default(""))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, eventType).shaped <> (NeoEventType.tupled, NeoEventType.unapply)
  }

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = NeoEventTypeTable
  val all = TableQuery[NeoEventTypeTable]





}