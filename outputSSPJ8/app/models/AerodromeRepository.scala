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
class AerodromeRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, aerodrome_statusRepo: AerodromeStatusRepository, aerodrome_typeRepo: AerodromeTypeRepository)(implicit ec: ExecutionContext) extends DatabaseClient[Aerodrome] with AerodromeQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class AerodromeTable(tag: Tag) extends Table[Aerodrome](tag, "aerodrome") {
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
}