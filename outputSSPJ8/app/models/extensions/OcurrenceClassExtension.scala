package models.extensions

import models._

trait OcurrenceClassExtension{ this: OcurrenceClass =>

}



trait OcurrenceClassQuery{
  this: DatabaseClient[OcurrenceClass] =>
}