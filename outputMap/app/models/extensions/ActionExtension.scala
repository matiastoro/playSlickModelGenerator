package models.extensions

import models._

trait ActionExtension{ this: Action =>

}

trait ActionQuery{
  this: DatabaseClient[Action] =>
}