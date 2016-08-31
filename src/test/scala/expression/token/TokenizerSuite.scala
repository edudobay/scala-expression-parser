package expression.token

import org.scalatest._
import expression.UnitSuite

class TokenizerSuite extends UnitSuite {

  test("tokenize number literals") {
    assert(Token.tokenize("1") == List(Number(1)))
  }

  test("tokenize variable literals") {
    assert(Token.tokenize("x") == List(Var("x")))
  }

  test("tokenize addition of numbers") {
    assert(Token.tokenize("1+3") == List(Number(1), Operator("+"), Number(3)))
  }

  test("tokenize one number") {
    for ((regex, m) <- Token.findMatcher("1")) {
      assert(regex == Token.NumberRegex)
      assert(m.matched == "1")
      assert(Token.getToken((regex, m.matched)) == Some(Number(1)))
    }
  }

}
