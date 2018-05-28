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
class HelicopterLandingAreaRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, aerodromeRepo: AerodromeRepository)(implicit ec: ExecutionContext) extends DatabaseClient[HelicopterLandingArea] with HelicopterLandingAreaQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class HelicopterLandingAreaTable(tag: Tag) extends Table[HelicopterLandingArea](tag, "helicopter_landing_area") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def helicopterLandingArea = column[String]("helicopter_landing_area", O.Default(""))
    def aerodromeId = column[Long]("aerodrome_id", O.Default(0L))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, helicopterLandingArea, aerodromeId).shaped <> (HelicopterLandingArea.tupled, HelicopterLandingArea.unapply)
  }

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = HelicopterLandingAreaTable
  val all = TableQuery[HelicopterLandingAreaTable]

  def byAerodromeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.aerodromeId===id).result
  }}.getOrElse{Future(List())}
                            


  def getAerodrome(obj: HelicopterLandingArea) = aerodromeRepo.byId(obj.aerodromeId)
}