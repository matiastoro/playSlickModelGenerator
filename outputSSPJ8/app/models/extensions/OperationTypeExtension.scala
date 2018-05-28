package models.extensions

import models._

trait OperationTypeExtension{ this: OperationType =>

}



trait OperationTypeQuery{
  this: DatabaseClient[OperationType] =>
}