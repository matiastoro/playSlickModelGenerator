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
class OperatorTypeRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends DatabaseClient[OperatorType] with OperatorTypeQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class OperatorTypeTable(tag: Tag) extends Table[OperatorType](tag, "operator_type"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def operatorType = column[String]("operator_type", O.Default(""))
    def operatorTypeId = column[Option[Long]]("operator_type_id", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, operatorType, operatorTypeId).shaped <> (OperatorType.tupled, OperatorType.unapply)
  }

  override val sortMap = Map[String, SortMapType](
    "id" -> {(q,s) => q.sortBy(_.id)(s)},
    "operatorType" -> {(q,s) => q.sortBy(_.operatorType)(s)},
    //displayField for field operatorType is not defined in YML
  )
      

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = OperatorTypeTable
  val all = TableQuery[OperatorTypeTable]

  def byOperatorTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.operatorTypeId===id).result
  }}.getOrElse{Future(List())}
                            


  def getOperatorType(obj: OperatorType) = if(obj.operatorTypeId.isDefined) this.byId(obj.operatorTypeId.get) else None

  def filter(formData: OperatorTypeFilter) = {
    all
      .filteredBy( formData.id.flatMap{x => if(x>0L) Some(x) else None} )( _.id === _ )
      .filteredBy(formData.operatorType)((x,y) => x.operatorType.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy( formData.operatorTypeId.flatMap{x => if(x>0L) Some(x) else None} )( _.operatorTypeId.getOrElse(0L) === _ )
  }
}