package models.extensions

import models._

trait InjuryLevelExtension{ this: InjuryLevel =>

}



trait InjuryLevelQuery{
  this: DatabaseClient[InjuryLevel] =>
}