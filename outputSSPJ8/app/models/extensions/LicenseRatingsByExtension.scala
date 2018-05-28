package models.extensions

import models._

trait LicenseRatingsByExtension{ this: LicenseRatingsBy =>

}

trait LicenseRatingsByQuery{
  this: DatabaseClient[LicenseRatingsBy] =>
}