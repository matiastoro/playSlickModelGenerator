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
class AircraftManufacturerModelRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends DatabaseClient[AircraftManufacturerModel] with AircraftManufacturerModelQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class AircraftManufacturerModelTable(tag: Tag) extends Table[AircraftManufacturerModel](tag, "aircraft_manufacturer_model"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def manufacturerModel = column[String]("manufacturer_model", O.Default(""))
    def aircraftManufacturerModelId = column[Option[Long]]("aircraft_manufacturer_model_id", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, manufacturerModel, aircraftManufacturerModelId).shaped <> (AircraftManufacturerModel.tupled, AircraftManufacturerModel.unapply)
  }

  override val sortMap = Map[String, SortMapType](
    "id" -> {(q,s) => q.sortBy(_.id)(s)},
    "manufacturerModel" -> {(q,s) => q.sortBy(_.manufacturerModel)(s)},
    "aircraftManufacturerModelId" -> {(q,s) => q.join(all).on(_.aircraftManufacturerModelId === _.id).sortBy(_._2.manufacturerModel)(s).map{_._1}}
  )
      

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = AircraftManufacturerModelTable
  val all = TableQuery[AircraftManufacturerModelTable]

  def byAircraftManufacturerModelId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.aircraftManufacturerModelId===id).result
  }}.getOrElse{Future(List())}
                            


  def getAircraftManufacturerModel(obj: AircraftManufacturerModel) = if(obj.aircraftManufacturerModelId.isDefined) this.byId(obj.aircraftManufacturerModelId.get) else None

  def filter(formData: AircraftManufacturerModelFilter) = {
    all
      .filteredBy( formData.id.flatMap{x => if(x>0L) Some(x) else None} )( _.id === _ )
      .filteredBy(formData.manufacturerModel)((x,y) => x.manufacturerModel.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy( formData.aircraftManufacturerModelId.flatMap{x => if(x>0L) Some(x) else None} )( _.aircraftManufacturerModelId.getOrElse(0L) === _ )
  }
}