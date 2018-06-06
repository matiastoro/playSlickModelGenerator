package models.extensions

import models._

trait ReportingEntityExtension{ this: ReportingEntity =>

}



trait ReportingEntityQuery{
  this: DatabaseClient[ReportingEntity] =>
}