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
class LicenseRatingsByRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends DatabaseClient[LicenseRatingsBy] with LicenseRatingsByQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._


  class LicenseRatingsByTable(tag: Tag) extends Table[LicenseRatingsBy](tag, "license_ratings_by") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def licenseRatings = column[String]("license_ratings", O.Default(""))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, licenseRatings).shaped <> (LicenseRatingsBy.tupled, LicenseRatingsBy.unapply)
  }

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = LicenseRatingsByTable
  val all = TableQuery[LicenseRatingsByTable]





}