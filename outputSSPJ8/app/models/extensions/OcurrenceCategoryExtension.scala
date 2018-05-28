package models.extensions

import models._

trait OcurrenceCategoryExtension{ this: OcurrenceCategory =>

}



trait OcurrenceCategoryQuery{
  this: DatabaseClient[OcurrenceCategory] =>
}