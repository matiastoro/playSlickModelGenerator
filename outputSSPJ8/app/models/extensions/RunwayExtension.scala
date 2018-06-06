package models.extensions

import models._

trait RunwayExtension{ this: Runway =>

}



trait RunwayQuery{
  this: DatabaseClient[Runway] =>
}