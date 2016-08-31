package expression

abstract class Expr

case class Number(n: Int) extends Expr
case class Var(name: String) extends Expr

case class Add(l: Expr, r: Expr) extends Expr
case class Subtract(l: Expr, r: Expr) extends Expr
case class Multiply(l: Expr, r: Expr) extends Expr
case class Divide(l: Expr, r: Expr) extends Expr
