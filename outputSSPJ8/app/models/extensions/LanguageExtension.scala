package models.extensions

import models._

trait LanguageExtension{ this: Language =>

}



trait LanguageQuery{
  this: DatabaseClient[Language] =>
}