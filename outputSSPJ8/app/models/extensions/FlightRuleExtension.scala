package models.extensions

import models._

trait FlightRuleExtension{ this: FlightRule =>

}



trait FlightRuleQuery{
  this: DatabaseClient[FlightRule] =>
}