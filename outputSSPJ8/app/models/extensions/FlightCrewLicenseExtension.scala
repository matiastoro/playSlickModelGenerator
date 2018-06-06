package models.extensions

import models._

trait FlightCrewLicenseExtension{ this: FlightCrewLicense =>

}



trait FlightCrewLicenseQuery{
  this: DatabaseClient[FlightCrewLicense] =>
}