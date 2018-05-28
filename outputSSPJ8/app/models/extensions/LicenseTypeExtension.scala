package models.extensions

import models._

trait LicenseTypeExtension{ this: LicenseType =>

}



trait LicenseTypeQuery{
  this: DatabaseClient[LicenseType] =>
}