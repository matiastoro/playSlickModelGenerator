package models.extensions

import models._

trait WeatherConditionExtension{ this: WeatherCondition =>

}



trait WeatherConditionQuery{
  this: DatabaseClient[WeatherCondition] =>
}