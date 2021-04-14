package extraReading.newInScala

object WhatsNew {

  sealed trait Things
  case object Thing1 extends Things
  case object Thing2 extends Things

  // check the lack of braces!
  trait A:
    def f: Int

  class C(x: Int) extends A:
    def f = x

  enum Color:
    case Red, Green, Blue

  def ifElse(x: Int) = {
    if x < 0 then
      "negative"
    else if x == 0 then
      "zero"
    else
      "positive"
  }

  def optionalBraces(thing: Things) = {
    thing match {
      case _: extraReading.newInScala.WhatsNew.Thing1.type => "no braces round match"
      case _: extraReading.newInScala.WhatsNew.Thing2.type => ":)"
    }
  }

  def numberMatch(x: Int) = {
    x match
      case 1 => print("I")
      case 2 => print("II")
      case 3 => print("III")
      case 4 => print("IV")
      case 5 => print("V")
      case _ => print("can't help you sorry")

    println(".")
  }

}

object WhatsNewExample {
  def test: Unit = {

    import WhatsNew._

    val ifElseExample = WhatsNew.ifElse(10)
    println(ifElseExample)

    val optBracesExample = WhatsNew.optionalBraces(Thing1)
    println(optBracesExample)

    WhatsNew.numberMatch(3)
    WhatsNew.numberMatch(10)
  }

}
