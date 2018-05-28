package models.extensions

import models._

trait FlightCrewCategoryExtension{ this: FlightCrewCategory =>

}



trait FlightCrewCategoryQuery{
  this: DatabaseClient[FlightCrewCategory] =>
}