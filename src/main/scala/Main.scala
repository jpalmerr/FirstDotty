object Main {

  def main(args: Array[String]): Unit = {
    println("Hello world!")
    println(msg)
    
    runExample("Intersection Types")(IntersectionTypes.test)
    runExample("Union Types")(UnionTypes.test)
    runExample("Enums")(Enums.test)
  }

  def msg = "I was compiled by dotty :)"

  private def runExample(name: String)(f: => Unit) = {
    println(s"$name example:")
    f
    println()
  }

}
