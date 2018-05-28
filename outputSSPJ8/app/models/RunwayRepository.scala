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
class RunwayRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, runway_surface_typeRepo: RunwaySurfaceTypeRepository, aerodromeRepo: AerodromeRepository)(implicit ec: ExecutionContext) extends DatabaseClient[Runway] with RunwayQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class RunwayTable(tag: Tag) extends Table[Runway](tag, "runway") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def runway = column[String]("runway", O.Default(""))
    def runwaySurfaceTypeId = column[Long]("runway_surface_type_id", O.Default(0L))
    def aerodromeId = column[Long]("aerodrome_id", O.Default(0L))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, runway, runwaySurfaceTypeId, aerodromeId).shaped <> (Runway.tupled, Runway.unapply)
  }

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = RunwayTable
  val all = TableQuery[RunwayTable]

  def byRunwaySurfaceTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.runwaySurfaceTypeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byAerodromeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.aerodromeId===id).result
  }}.getOrElse{Future(List())}
                            


  def getRunwaySurfaceType(obj: Runway) = runway_surface_typeRepo.byId(obj.runwaySurfaceTypeId)
  def getAerodrome(obj: Runway) = aerodromeRepo.byId(obj.aerodromeId)
}