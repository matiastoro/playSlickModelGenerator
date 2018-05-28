package models.extensions

import models._

trait ObjExtension{ this: Obj =>

}

trait ObjQuery{
  this: DatabaseClient[Obj] =>
}