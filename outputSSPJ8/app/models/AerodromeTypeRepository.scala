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
class AerodromeTypeRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends DatabaseClient[AerodromeType] with AerodromeTypeQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class AerodromeTypeTable(tag: Tag) extends Table[AerodromeType](tag, "aerodrome_type"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def tpe = column[String]("tpe", O.Default(""))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, tpe).shaped <> (AerodromeType.tupled, AerodromeType.unapply)
  }

  override val sortMap = Map[String, SortMapType](
    "id" -> {(q,s) => q.sortBy(_.id)(s)},
    "tpe" -> {(q,s) => q.sortBy(_.tpe)(s)}
  )
      

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = AerodromeTypeTable
  val all = TableQuery[AerodromeTypeTable]






  def filter(formData: AerodromeTypeFilter) = {
    all
      .filteredBy( formData.id.flatMap{x => if(x>0L) Some(x) else None} )( _.id === _ )
      .filteredBy(formData.tpe)((x,y) => x.tpe.toUpperCase like ('%'+y.toUpperCase+'%'))
  }
}