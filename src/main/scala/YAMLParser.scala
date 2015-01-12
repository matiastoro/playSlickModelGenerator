package via56.slickGenerator
import scala.util.parsing.combinator._
import scala.util.matching.Regex
import collection.immutable.ListMap

object YAMLParser extends RegexParsers {
  override def skipWhitespace = false

  val mapSeparator = ": *\r?\n?"r

  val seqIndicator = "- *\r?\n?"r

  val mappingKey = """[^-:\r\n\}\{\[]+"""r

  val newLine = " *\r*\n+"r

  val inlineSeparator = " *, +"r

  val openBrace = """\{ *"""r;

  val closeBrace = """ *\}"""r;

  val openBracket = """\[ *"""r;

  val closeBracket = """ *]"""r;

  def leadingSpaces(numLeadingSpaces:Int):Parser[Int] = ("^[ ]{" + numLeadingSpaces + ",}"r) ^^ { _.length }

  //a mapping is either a indented map or an inline map (represented as a Scala ListMap)
  def mappings(numLeadingSpaces:Int):Parser[ListMap[String, Any]] =
    ( indentedMap(numLeadingSpaces) | inlineMap) ^^ { ListMap() ++ _ }

  //a indented map is a indented mapping (1 or more times)
  def indentedMap(numLeadingSpaces:Int):Parser[List[(String, Any)]] = {
    rep1sep(indentedMapping(numLeadingSpaces), newLine)
  }

  //An inline-map is a bracket surrounded inline-mapping  (0 or more times)
  def inlineMap:Parser[List[(String, Any)]] = (openBrace ~> repsep(inlineMapping, inlineSeparator) <~ closeBrace)

  //An indented mapping is a multi-line map indicated by key, a separator an a node(list, map or data).
  // All preceded, or not, by spaces (represented as a Scala Map)
  def indentedMapping(numLeadingSpaces:Int):Parser[(String, Any)] =
    leadingSpaces(numLeadingSpaces) ~> mappingKey ~ mapSeparator ~ (list(0) | mappings(numLeadingSpaces+2) | scalarData("""[^\r\n]+""")) ^^ { case key~_~value => (key, value) }

  def inlineMapping:Parser[(String, Any)] =
    mappingKey ~ mapSeparator ~ (inlineList | inlineMap | scalarData("""[^,\r\n\}]+""")) ^^ { case key~_~value => (key, value) }

  //A list is either a inline list or a indented list (represented as a Scala List)
  def list(numLeadingSpaces:Int):Parser[List[Any]] =
    (inlineList | indentedList(numLeadingSpaces)) ^^ { List() ++ _ }

  //An indented list is a multi-line list of nested list data indicated by  "-" (1 or more times)
  def indentedList(numLeadingSpaces:Int):Parser[List[Any]] =
    rep1sep( leadingSpaces(numLeadingSpaces) ~ seqIndicator ~>nestedListData(numLeadingSpaces), newLine)

  //An inline list is a list of nested list data (0 or more times) surrounded by brackets
  def inlineList:Parser[List[Any]] =
    (openBracket ~> repsep( nestedListData(0), inlineSeparator) <~ closeBracket)

  //A nested list data is either a data or a mapping (like a JSON object)
  def nestedListData(numLeadingSpaces:Int):Parser[Any] = (list(numLeadingSpaces+1) |
    mappings(numLeadingSpaces) |
    scalarData("""[^,\r\n\]]+"""))

  //A scalar data is any data that maps directly from a regular expression
  def scalarData(regexString:String):Parser[String] = regexString.r  ^^ { case value => value.trim }

  //A yaml is a list or a mapping preceded, or not, by a new line
  def yaml:Parser[Any] = ( opt(newLine) ~> ( list(0) | mappings(0) ))

  def parse(text : String):ParseResult[Any] = {
    parse(yaml, text)
  }
}