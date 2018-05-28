package models.extensions

import models._

trait ProfileUserExtension{ this: ProfileUser =>

}

trait ProfileUserQuery{
  this: DatabaseClient[ProfileUser] =>
}