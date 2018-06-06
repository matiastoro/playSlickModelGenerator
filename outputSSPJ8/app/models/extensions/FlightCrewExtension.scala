package models.extensions

import models._

trait FlightCrewExtension{ this: FlightCrew =>

}



trait FlightCrewQuery{
  this: DatabaseClient[FlightCrew] =>
}