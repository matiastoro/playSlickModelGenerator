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
class FlightCrewCategoryRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends DatabaseClient[FlightCrewCategory] with FlightCrewCategoryQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class FlightCrewCategoryTable(tag: Tag) extends Table[FlightCrewCategory](tag, "flight_crew_category"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def category = column[String]("category", O.Default(""))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, category).shaped <> (FlightCrewCategory.tupled, FlightCrewCategory.unapply)
  }

  override val sortMap = Map[String, SortMapType](
    "id" -> {(q,s) => q.sortBy(_.id)(s)},
    "category" -> {(q,s) => q.sortBy(_.category)(s)}
  )
      

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = FlightCrewCategoryTable
  val all = TableQuery[FlightCrewCategoryTable]






  def filter(formData: FlightCrewCategoryFilter) = {
    all
      .filteredBy( formData.id.flatMap{x => if(x>0L) Some(x) else None} )( _.id === _ )
      .filteredBy(formData.category)((x,y) => x.category.toUpperCase like ('%'+y.toUpperCase+'%'))
  }
}