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
class EccairsRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, injury_levelRepo: InjuryLevelRepository, weather_conditionRepo: WeatherConditionRepository, aircraft_categoryRepo: AircraftCategoryRepository, operation_typeRepo: OperationTypeRepository, operator_typeRepo: OperatorTypeRepository, aircraft_mass_groupRepo: AircraftMassGroupRepository, ocurrence_classRepo: OcurrenceClassRepository, state_areaRepo: StateAreaRepository, operatorRepo: OperatorRepository)(implicit ec: ExecutionContext) extends DatabaseClient[Eccairs] with EccairsQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class EccairsTable(tag: Tag) extends Table[Eccairs](tag, "eccairs") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def number = column[String]("number", O.Default(""))
    def date = column[DateTime]("date", O.Default(new DateTime()))
    def stateAreaId = column[Long]("state_area_id", O.Default(0L))
    def ocurrenceClassId = column[Long]("ocurrence_class_id", O.Default(0L))
    def injuryLevelId = column[Option[Long]]("injury_level_id", O.Default(None))
    def massGroupId = column[Option[Long]]("mass_group_id", O.Default(None))
    def categoryId = column[Option[Long]]("category_id", O.Default(None))
    def registry = column[Option[String]]("registry", O.Default(None))
    def operationTypeId = column[Option[Long]]("operation_type_id", O.Default(None))
    def operatorTypeId = column[Option[Long]]("operator_type_id", O.Default(None))
    def operatorId = column[Option[Long]]("operator_id", O.Default(None))
    def weatherConditionId = column[Option[Long]]("weather_condition_id", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, number, date, stateAreaId, ocurrenceClassId, injuryLevelId, massGroupId, categoryId, registry, operationTypeId, operatorTypeId, operatorId, weatherConditionId).shaped <> (Eccairs.tupled, Eccairs.unapply)
  }

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = EccairsTable
  val all = TableQuery[EccairsTable]

  def byStateAreaId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.stateAreaId===id).result
  }}.getOrElse{Future(List())}
                            

  def byOcurrenceClassId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.ocurrenceClassId===id).result
  }}.getOrElse{Future(List())}
                            

  def byInjuryLevelId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.injuryLevelId===id).result
  }}.getOrElse{Future(List())}
                            

  def byMassGroupId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.massGroupId===id).result
  }}.getOrElse{Future(List())}
                            

  def byCategoryId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.categoryId===id).result
  }}.getOrElse{Future(List())}
                            

  def byOperationTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.operationTypeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byOperatorTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.operatorTypeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byOperatorId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.operatorId===id).result
  }}.getOrElse{Future(List())}
                            

  def byWeatherConditionId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.weatherConditionId===id).result
  }}.getOrElse{Future(List())}
                            


  def getStateArea(obj: Eccairs) = state_areaRepo.byId(obj.stateAreaId)
  def getOcurrenceClass(obj: Eccairs) = ocurrence_classRepo.byId(obj.ocurrenceClassId)
  def getInjuryLevel(obj: Eccairs) = if(obj.injuryLevelId.isDefined) injury_levelRepo.byId(obj.injuryLevelId.get) else None
  def getMassGroup(obj: Eccairs) = if(obj.massGroupId.isDefined) aircraft_mass_groupRepo.byId(obj.massGroupId.get) else None
  def getCategory(obj: Eccairs) = if(obj.categoryId.isDefined) aircraft_categoryRepo.byId(obj.categoryId.get) else None
  def getOperationType(obj: Eccairs) = if(obj.operationTypeId.isDefined) operation_typeRepo.byId(obj.operationTypeId.get) else None
  def getOperatorType(obj: Eccairs) = if(obj.operatorTypeId.isDefined) operator_typeRepo.byId(obj.operatorTypeId.get) else None
  def getOperator(obj: Eccairs) = if(obj.operatorId.isDefined) operatorRepo.byId(obj.operatorId.get) else None
  def getWeatherCondition(obj: Eccairs) = if(obj.weatherConditionId.isDefined) weather_conditionRepo.byId(obj.weatherConditionId.get) else None
}