package expression

import org.scalatest._

class ParserSuite extends UnitSuite {

  test("parse number literals") {
    assert(Parser.parse("1") == Number(1))
  }

}
