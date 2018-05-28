package models.extensions

import models._

trait CharacterExtension{ this: Character =>

}

trait CharacterQuery{
  this: DatabaseClient[Character] =>
}