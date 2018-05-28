package models.extensions

import models._

trait AerodromeExtension{ this: Aerodrome =>

}



trait AerodromeQuery{
  this: DatabaseClient[Aerodrome] =>
}