package models.extensions

import models._

trait AerodromeStatusExtension{ this: AerodromeStatus =>

}



trait AerodromeStatusQuery{
  this: DatabaseClient[AerodromeStatus] =>
}