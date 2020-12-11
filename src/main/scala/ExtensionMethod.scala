object ExtensionMethod {
  
  case class Circle(x: Double, y: Double, radius: Double)
  
  extension (c: Circle) {
    def circumference: Double = c.radius * math.Pi * 2
  }
  
  def test: Unit = {
    val circle = Circle(0, 0 , 1)
    println(s"circle: ${circle}")
    val circumference = circle.circumference
    println(s"circumference: ${circumference}")
  }
}

/**
 * Translation:
 *  An extension method translates to a specially labelled method that takes the leading parameter section as its first argument list. 
 */
