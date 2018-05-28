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
class AircraftCategoryRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends DatabaseClient[AircraftCategory] with AircraftCategoryQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class AircraftCategoryTable(tag: Tag) extends Table[AircraftCategory](tag, "aircraft_category") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def category = column[String]("category", O.Default(""))
    def aircraftCategoryId = column[Option[Long]]("aircraft_category_id", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, category, aircraftCategoryId).shaped <> (AircraftCategory.tupled, AircraftCategory.unapply)
  }

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = AircraftCategoryTable
  val all = TableQuery[AircraftCategoryTable]

  def byAircraftCategoryId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.aircraftCategoryId===id).result
  }}.getOrElse{Future(List())}
                            


  def getAircraftCategory(obj: AircraftCategory) = if(obj.aircraftCategoryId.isDefined) this.byId(obj.aircraftCategoryId.get) else None
}