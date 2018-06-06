package models.extensions

import models._

trait AerodromeLocationExtension{ this: AerodromeLocation =>

}



trait AerodromeLocationQuery{
  this: DatabaseClient[AerodromeLocation] =>
}