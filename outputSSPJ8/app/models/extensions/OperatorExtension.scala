package models.extensions

import models._

trait OperatorExtension{ this: Operator =>

}



trait OperatorQuery{
  this: DatabaseClient[Operator] =>
}