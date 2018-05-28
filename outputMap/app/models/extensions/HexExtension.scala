package models.extensions

import models._

trait HexExtension{ this: Hex =>

}

trait HexQuery{
  this: DatabaseClient[Hex] =>
}