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
class ObjRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends DatabaseClient[Obj] with ObjQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._


  class ObjTable(tag: Tag) extends Table[Obj](tag, "obj") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def tpe = column[Int]("tpe", O.Default(0))
    def q = column[Int]("q", O.Default(0))
    def r = column[Int]("r", O.Default(0))
    def s = column[Int]("s", O.Default(0))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, tpe, q, r, s).shaped <> (Obj.tupled, Obj.unapply)
  }

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = ObjTable
  val all = TableQuery[ObjTable]





}