package models.extensions

import models._

trait UserExtension{ this: User =>

}

trait UserQuery{
  this: DatabaseClient[User] =>
}