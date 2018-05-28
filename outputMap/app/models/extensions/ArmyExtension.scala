package models.extensions

import models._

trait ArmyExtension{ this: Army =>

}

trait ArmyQuery{
  this: DatabaseClient[Army] =>
}