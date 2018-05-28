package models.extensions

import models._

trait LicenseIssuedByExtension{ this: LicenseIssuedBy =>

}



trait LicenseIssuedByQuery{
  this: DatabaseClient[LicenseIssuedBy] =>
}