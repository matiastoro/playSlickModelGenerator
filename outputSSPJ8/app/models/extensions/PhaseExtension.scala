package models.extensions

import models._

trait PhaseExtension{ this: Phase =>

}



trait PhaseQuery{
  this: DatabaseClient[Phase] =>
}