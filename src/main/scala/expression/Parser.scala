package expression

object Parser {
  def parse(expr: String): Expr = {
    try Number(expr.toInt)
    catch {
      case e: java.lang.NumberFormatException =>
        Var(expr)
    }
  }
}

