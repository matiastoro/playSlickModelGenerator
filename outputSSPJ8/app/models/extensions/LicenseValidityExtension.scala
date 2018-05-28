package models.extensions

import models._

trait LicenseValidityExtension{ this: LicenseValidity =>

}



trait LicenseValidityQuery{
  this: DatabaseClient[LicenseValidity] =>
}