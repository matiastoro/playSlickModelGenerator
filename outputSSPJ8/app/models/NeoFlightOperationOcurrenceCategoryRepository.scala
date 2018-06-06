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
class NeoFlightOperationOcurrenceCategoryRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, neo_flight_operationRepo: NeoFlightOperationRepository, ocurrence_categoryRepo: OcurrenceCategoryRepository)(implicit ec: ExecutionContext) extends DatabaseClient[NeoFlightOperationOcurrenceCategory] with NeoFlightOperationOcurrenceCategoryQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class NeoFlightOperationOcurrenceCategoryTable(tag: Tag) extends Table[NeoFlightOperationOcurrenceCategory](tag, "neo_flight_operation_ocurrence_category"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def neoFlightOperationId = column[Long]("neo_flight_operation_id", O.Default(0L))
    def ocurrenceCategoryId = column[Long]("ocurrence_category_id", O.Default(0L))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, neoFlightOperationId, ocurrenceCategoryId).shaped <> (NeoFlightOperationOcurrenceCategory.tupled, NeoFlightOperationOcurrenceCategory.unapply)
  }

  override val sortMap = Map[String, SortMapType](
    "id" -> {(q,s) => q.sortBy(_.id)(s)},
    //displayField for field neoFlightOperation is not defined in YML,
    //displayField for field ocurrenceCategory is not defined in YML
  )
      

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = NeoFlightOperationOcurrenceCategoryTable
  val all = TableQuery[NeoFlightOperationOcurrenceCategoryTable]

  def byNeoFlightOperationId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.neoFlightOperationId===id).result
  }}.getOrElse{Future(List())}
                            

  def byOcurrenceCategoryId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.ocurrenceCategoryId===id).result
  }}.getOrElse{Future(List())}
                            


  def getNeoFlightOperation(obj: NeoFlightOperationOcurrenceCategory) = neo_flight_operationRepo.byId(obj.neoFlightOperationId)
  def getOcurrenceCategory(obj: NeoFlightOperationOcurrenceCategory) = ocurrence_categoryRepo.byId(obj.ocurrenceCategoryId)

  def filter(formData: NeoFlightOperationOcurrenceCategoryFilter) = {
    all
      .filteredBy( formData.id.flatMap{x => if(x>0L) Some(x) else None} )( _.id === _ )
      .filteredBy( formData.neoFlightOperationId.collect{case x if x>0L => x} )( _.neoFlightOperationId === _ )
      .filteredBy( formData.ocurrenceCategoryId.collect{case x if x>0L => x} )( _.ocurrenceCategoryId === _ )
  }
}