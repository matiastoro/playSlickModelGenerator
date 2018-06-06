package models.extensions

import models._

trait TrafficTypeExtension{ this: TrafficType =>

}



trait TrafficTypeQuery{
  this: DatabaseClient[TrafficType] =>
}