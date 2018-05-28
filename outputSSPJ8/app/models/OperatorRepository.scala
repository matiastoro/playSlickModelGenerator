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
class OperatorRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, operator_typeRepo: OperatorTypeRepository)(implicit ec: ExecutionContext) extends DatabaseClient[Operator] with OperatorQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class OperatorTable(tag: Tag) extends Table[Operator](tag, "operator") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def operator = column[String]("operator", O.Default(""))
    def operatorTypeId = column[Option[Long]]("operator_type_id", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, operator, operatorTypeId).shaped <> (Operator.tupled, Operator.unapply)
  }

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = OperatorTable
  val all = TableQuery[OperatorTable]

  def byOperatorTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.operatorTypeId===id).result
  }}.getOrElse{Future(List())}
                            


  def getOperatorType(obj: Operator) = if(obj.operatorTypeId.isDefined) operator_typeRepo.byId(obj.operatorTypeId.get) else None
}