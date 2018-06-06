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
class FlightCrewLicenseRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, license_issued_byRepo: LicenseIssuedByRepository, license_ratingsRepo: LicenseRatingsRepository, flight_crewRepo: FlightCrewRepository, license_validityRepo: LicenseValidityRepository, license_typeRepo: LicenseTypeRepository)(implicit ec: ExecutionContext) extends DatabaseClient[FlightCrewLicense] with FlightCrewLicenseQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ()



  class FlightCrewLicenseTable(tag: Tag) extends Table[FlightCrewLicense](tag, "flight_crew_license"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def flightCrewId = column[Long]("flight_crew_id", O.Default(0L))
    def licenseTypeId = column[Long]("license_type_id", O.Default(0L))
    def licenseIssuedById = column[Option[Long]]("license_issued_by_id", O.Default(None))
    def dateOfLicense = column[Option[DateTime]]("date_of_license", O.Default(None))
    def licenseValidityId = column[Option[Long]]("license_validity_id", O.Default(None))
    def licenseRatingsId = column[Option[Long]]("license_ratings_id", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, flightCrewId, licenseTypeId, licenseIssuedById, dateOfLicense, licenseValidityId, licenseRatingsId).shaped <> (FlightCrewLicense.tupled, FlightCrewLicense.unapply)
  }

  override val sortMap = Map[String, SortMapType](
    "id" -> {(q,s) => q.sortBy(_.id)(s)},
    //displayField for field flightCrew is not defined in YML,
    //displayField for field licenseType is not defined in YML,
    //displayField for field licenseIssuedBy is not defined in YML,
    "dateOfLicense" -> {(q,s) => q.sortBy(_.dateOfLicense)(s)},
    //displayField for field licenseValidity is not defined in YML,
    //displayField for field licenseRatings is not defined in YML
  )
      

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = FlightCrewLicenseTable
  val all = TableQuery[FlightCrewLicenseTable]

  def byFlightCrewId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.flightCrewId===id).result
  }}.getOrElse{Future(List())}
                            

  def byLicenseTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.licenseTypeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byLicenseIssuedById(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.licenseIssuedById===id).result
  }}.getOrElse{Future(List())}
                            

  def byLicenseValidityId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.licenseValidityId===id).result
  }}.getOrElse{Future(List())}
                            

  def byLicenseRatingsId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.licenseRatingsId===id).result
  }}.getOrElse{Future(List())}
                            


  def getFlightCrew(obj: FlightCrewLicense) = flight_crewRepo.byId(obj.flightCrewId)
  def getLicenseType(obj: FlightCrewLicense) = license_typeRepo.byId(obj.licenseTypeId)
  def getLicenseIssuedBy(obj: FlightCrewLicense) = if(obj.licenseIssuedById.isDefined) license_issued_byRepo.byId(obj.licenseIssuedById.get) else None
  def getLicenseValidity(obj: FlightCrewLicense) = if(obj.licenseValidityId.isDefined) license_validityRepo.byId(obj.licenseValidityId.get) else None
  def getLicenseRatings(obj: FlightCrewLicense) = if(obj.licenseRatingsId.isDefined) license_ratingsRepo.byId(obj.licenseRatingsId.get) else None

  def filter(formData: FlightCrewLicenseFilter) = {
    all
      .filteredBy( formData.id.flatMap{x => if(x>0L) Some(x) else None} )( _.id === _ )
      .filteredBy( formData.flightCrewId.collect{case x if x>0L => x} )( _.flightCrewId === _ )
      .filteredBy( formData.licenseTypeId.collect{case x if x>0L => x} )( _.licenseTypeId === _ )
      .filteredBy( formData.licenseIssuedById.flatMap{x => if(x>0L) Some(x) else None} )( _.licenseIssuedById.getOrElse(0L) === _ )
      .filteredBy( formData.dateOfLicenseFrom )((x,y) => x.dateOfLicense.map{d => d >= y}.getOrElse(false) )
      .filteredBy( formData.dateOfLicenseTo )((x,y) => x.dateOfLicense.map{d => d <= y}.getOrElse(false) )
      .filteredBy( formData.licenseValidityId.flatMap{x => if(x>0L) Some(x) else None} )( _.licenseValidityId.getOrElse(0L) === _ )
      .filteredBy( formData.licenseRatingsId.flatMap{x => if(x>0L) Some(x) else None} )( _.licenseRatingsId.getOrElse(0L) === _ )
  }
}