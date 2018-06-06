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
class EccairsOcurrenceCategoryRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, eccairsRepo: EccairsRepository, ocurrence_categoryRepo: OcurrenceCategoryRepository)(implicit ec: ExecutionContext) extends DatabaseClient[EccairsOcurrenceCategory] with EccairsOcurrenceCategoryQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class EccairsOcurrenceCategoryTable(tag: Tag) extends Table[EccairsOcurrenceCategory](tag, "eccairs_ocurrence_category"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def eccairsId = column[Long]("eccairs_id", O.Default(0L))
    def ocurrenceCategoryId = column[Long]("ocurrence_category_id", O.Default(0L))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, eccairsId, ocurrenceCategoryId).shaped <> (EccairsOcurrenceCategory.tupled, EccairsOcurrenceCategory.unapply)
  }

  override val sortMap = Map[String, SortMapType](
    "id" -> {(q,s) => q.sortBy(_.id)(s)},
    //displayField for field eccairs is not defined in YML,
    //displayField for field ocurrenceCategory is not defined in YML
  )
      

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = EccairsOcurrenceCategoryTable
  val all = TableQuery[EccairsOcurrenceCategoryTable]

  def byEccairsId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.eccairsId===id).result
  }}.getOrElse{Future(List())}
                            

  def byOcurrenceCategoryId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.ocurrenceCategoryId===id).result
  }}.getOrElse{Future(List())}
                            


  def getEccairs(obj: EccairsOcurrenceCategory) = eccairsRepo.byId(obj.eccairsId)
  def getOcurrenceCategory(obj: EccairsOcurrenceCategory) = ocurrence_categoryRepo.byId(obj.ocurrenceCategoryId)

  def filter(formData: EccairsOcurrenceCategoryFilter) = {
    all
      .filteredBy( formData.id.flatMap{x => if(x>0L) Some(x) else None} )( _.id === _ )
      .filteredBy( formData.eccairsId.collect{case x if x>0L => x} )( _.eccairsId === _ )
      .filteredBy( formData.ocurrenceCategoryId.collect{case x if x>0L => x} )( _.ocurrenceCategoryId === _ )
  }
}