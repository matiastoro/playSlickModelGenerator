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
class OperationTypeRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends DatabaseClient[OperationType] with OperationTypeQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class OperationTypeTable(tag: Tag) extends Table[OperationType](tag, "operation_type"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def operationType = column[String]("operation_type", O.Default(""))
    def operationTypeId = column[Option[Long]]("operation_type_id", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, operationType, operationTypeId).shaped <> (OperationType.tupled, OperationType.unapply)
  }

  override val sortMap = Map[String, SortMapType](
    "id" -> {(q,s) => q.sortBy(_.id)(s)},
    "operationType" -> {(q,s) => q.sortBy(_.operationType)(s)},
    //displayField for field operationType is not defined in YML
  )
      

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = OperationTypeTable
  val all = TableQuery[OperationTypeTable]

  def byOperationTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.operationTypeId===id).result
  }}.getOrElse{Future(List())}
                            


  def getOperationType(obj: OperationType) = if(obj.operationTypeId.isDefined) this.byId(obj.operationTypeId.get) else None

  def filter(formData: OperationTypeFilter) = {
    all
      .filteredBy( formData.id.flatMap{x => if(x>0L) Some(x) else None} )( _.id === _ )
      .filteredBy(formData.operationType)((x,y) => x.operationType.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy( formData.operationTypeId.flatMap{x => if(x>0L) Some(x) else None} )( _.operationTypeId.getOrElse(0L) === _ )
  }
}