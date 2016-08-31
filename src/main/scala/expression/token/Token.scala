package expression.token

import scala.util.matching.Regex

abstract class Token
case class Number(n: Int) extends Token
case class Var(name: String) extends Token
case class Operator(name: String) extends Token

object Token {
  val ValidOperators = Set("+", "-", "*", "/")
  val OperatorRegex = """[+*/-]""".r
  val NumberRegex = """\b-?\d+\b""".r
  val IdentifierRegex = """\b[a-zA-Z_][a-zA-Z_0-9]*\b""".r
  val WhitespaceRegex = """\s+""".r

  private def tryMatch(expr: String, regexs: List[Regex]): Option[(Regex, Regex.Match)] = {
    regexs match {
      case regex :: regexs_ => (regex findPrefixMatchOf expr) match {
        case Some(m) => Some((regex, m))
        case _ => tryMatch(expr, regexs_)
      }
      case _ => None
    }
  }

  def tryApply[T, U](f: T => Option[U])(targets: List[T]): Option[U] =
    targets match {
      case t :: ts => f(t) orElse tryApply(f)(ts)
      case _ => None
    }

  private val Regexs = List(NumberRegex, OperatorRegex, IdentifierRegex, WhitespaceRegex)

  def findMatcher(expr: String): Option[(Regex, Regex.Match)] =
    tryApply((regex: Regex) => for (m <- regex findPrefixMatchOf expr) yield (regex, m))(Regexs)

  def getToken(result: (Regex, String)): Option[Token] = result match {
      case (NumberRegex, s) => Some(Number(s.toInt))
      case (OperatorRegex, s) => Some(Operator(s))
      case (IdentifierRegex, s) => Some(Var(s))
      case (WhitespaceRegex, s) => None
  }

  def maybePrepend[T](x: Option[T], xs: List[T]): List[T] =
    x match {
      case Some(x_) => x_ :: xs
      case None => xs
    }

  def tokenize(expr: String): List[Token] = {
    (for ((regex, m) <- findMatcher(expr)) yield {
      maybePrepend(
        getToken(regex, m.matched),
        tokenize(m.after.toString))}) getOrElse List()

  }
}
