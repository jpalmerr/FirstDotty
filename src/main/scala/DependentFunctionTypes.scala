/**
 * A dependent function type is a function type whose result depends on the function's parameters
 */

object DependentFunctionTypes {

  trait Entry { type Key; val key: Key }

  def extractKey(e: Entry): e.Key = e.key          // a dependent method

  val extractor: (e: Entry) => e.Key = extractKey  // a dependent function value
  //             ^^^^^^^^^^^^^^^^^^^
  //             a dependent function type



  def test = {
    type DF = (x: Entry) => x.Key
    val depFun: DF = (e: Entry) => e.key
    val entry = new Entry { type Key = Int; val key = 0 }
    val result: Int = depFun(entry)
    println(s"Result: ${result}")
  }
  
  
}
