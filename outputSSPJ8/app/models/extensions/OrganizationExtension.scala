package models.extensions

import models._

trait OrganizationExtension{ this: Organization =>

}



trait OrganizationQuery{
  this: DatabaseClient[Organization] =>
}