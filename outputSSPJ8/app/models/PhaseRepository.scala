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
class PhaseRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends DatabaseClient[Phase] with PhaseQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class PhaseTable(tag: Tag) extends Table[Phase](tag, "phase"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def phase = column[String]("phase", O.Default(""))
    def phaseId = column[Option[Long]]("phase_id", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, phase, phaseId).shaped <> (Phase.tupled, Phase.unapply)
  }

  override val sortMap = Map[String, SortMapType](
    "id" -> {(q,s) => q.sortBy(_.id)(s)},
    "phase" -> {(q,s) => q.sortBy(_.phase)(s)},
    //displayField for field phase is not defined in YML
  )
      

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = PhaseTable
  val all = TableQuery[PhaseTable]

  def byPhaseId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.phaseId===id).result
  }}.getOrElse{Future(List())}
                            


  def getPhase(obj: Phase) = if(obj.phaseId.isDefined) this.byId(obj.phaseId.get) else None

  def filter(formData: PhaseFilter) = {
    all
      .filteredBy( formData.id.flatMap{x => if(x>0L) Some(x) else None} )( _.id === _ )
      .filteredBy(formData.phase)((x,y) => x.phase.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy( formData.phaseId.flatMap{x => if(x>0L) Some(x) else None} )( _.phaseId.getOrElse(0L) === _ )
  }
}