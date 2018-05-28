package models.extensions

import models._

trait NeoEventTypeExtension{ this: NeoEventType =>

}



trait NeoEventTypeQuery{
  this: DatabaseClient[NeoEventType] =>
}