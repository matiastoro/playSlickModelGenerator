package models.extensions

import models._

trait CountryExtension{ this: Country =>

}

trait CountryQuery{
  this: DatabaseClient[Country] =>
}