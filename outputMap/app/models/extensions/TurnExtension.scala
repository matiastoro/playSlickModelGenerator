package models.extensions

import models._

trait TurnExtension{ this: Turn =>

}

trait TurnQuery{
  this: DatabaseClient[Turn] =>
}