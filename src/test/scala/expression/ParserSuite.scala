package expression

import org.scalatest._

class ParserSuite extends UnitSuite {

  test("parse number literals") {
    assert(Parser.parse("1") == Number(1))
  }

  test("parse variable literals") {
    assert(Parser.parse("x") == Var("x"))
  }

  test("parse addition of numbers") {
    assert(Parser.parse("1+3") == Add(Number(1), Number(3)))
  }

}
