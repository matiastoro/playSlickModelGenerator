package models.extensions

import models._

trait AircraftCategoryExtension{ this: AircraftCategory =>

}



trait AircraftCategoryQuery{
  this: DatabaseClient[AircraftCategory] =>
}