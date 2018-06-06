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
class ReportingEntityRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends DatabaseClient[ReportingEntity] with ReportingEntityQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class ReportingEntityTable(tag: Tag) extends Table[ReportingEntity](tag, "reporting_entity"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def reportingEntity = column[String]("reporting_entity", O.Default(""))
    def reportingEntityId = column[Option[Long]]("reporting_entity_id", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, reportingEntity, reportingEntityId).shaped <> (ReportingEntity.tupled, ReportingEntity.unapply)
  }

  override val sortMap = Map[String, SortMapType](
    "id" -> {(q,s) => q.sortBy(_.id)(s)},
    "reportingEntity" -> {(q,s) => q.sortBy(_.reportingEntity)(s)},
    //displayField for field reportingEntity is not defined in YML
  )
      

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = ReportingEntityTable
  val all = TableQuery[ReportingEntityTable]

  def byReportingEntityId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.reportingEntityId===id).result
  }}.getOrElse{Future(List())}
                            


  def getReportingEntity(obj: ReportingEntity) = if(obj.reportingEntityId.isDefined) this.byId(obj.reportingEntityId.get) else None

  def filter(formData: ReportingEntityFilter) = {
    all
      .filteredBy( formData.id.flatMap{x => if(x>0L) Some(x) else None} )( _.id === _ )
      .filteredBy(formData.reportingEntity)((x,y) => x.reportingEntity.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy( formData.reportingEntityId.flatMap{x => if(x>0L) Some(x) else None} )( _.reportingEntityId.getOrElse(0L) === _ )
  }
}