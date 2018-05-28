package models.extensions

import models._

trait ProfileExtension{ this: Profile =>

}

trait ProfileQuery{
  this: DatabaseClient[Profile] =>
}