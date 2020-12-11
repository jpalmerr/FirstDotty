import extraReading.opaqueTypeAliases.LogarithmExample

object Main {

  def main(args: Array[String]): Unit = {
    println("Hello world!")
    println(msg)
    
    runExample("Intersection Types")(IntersectionTypes.test)
    runExample("Union Types")(UnionTypes.test)
    runExample("Enums")(Enums.test)
    runExample("Type Lambdas")(TypeLambda.test)
    runExample("Dependent Functions")(DependentFunctionTypes.test)
    runExample("Extension Method")(ExtensionMethod.test)
    
    
    
    
    println("Extra reading")
    runExample("OpaqueTypes")(LogarithmExample.test)
  }

  def msg = "I was compiled by dotty :)"

  private def runExample(name: String)(f: => Unit) = {
    println(s"$name example:")
    f
    println()
  }

}
