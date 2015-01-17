package via56.slickGenerator

import scala.collection.immutable.ListMap

/**
 * Created by matias on 1/15/15.
 */
trait CodeGenerator {
  def underscoreToCamel(name: String) = "_([a-z\\d])".r.replaceAllIn(name, {m =>
    m.group(1).toUpperCase()
  })

  def isSubClass(props: Any): Boolean = {
    props match{
      case ps: ListMap[String, Any] =>
        ps.exists(pair =>
          pair match {
            case (prop, value) =>  value.isInstanceOf[Map[_,_]]
          }
        )
      case _ => false
    }
  }
}
