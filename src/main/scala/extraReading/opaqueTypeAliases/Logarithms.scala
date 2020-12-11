package extraReading.opaqueTypeAliases

object Logarithms {

  opaque type Logarithm = Double

  object Logarithm {

    // These are the two ways to lift to the Logarithm type
    def apply(d: Double): Logarithm = math.log(d)

    def safe(d: Double): Option[Logarithm] =
      if d > 0.0 then Some(math.log(d)) else None

    // extension methods
    extension (x: Logarithm) {
      def toDouble: Double = math.exp(x)

      def +(y: Logarithm): Logarithm = Logarithm(math.exp(x) + math.exp(y))

      def *(y: Logarithm): Logarithm = x + y
    }
  }

}

/**
 *    The opaque keyword marks the type alias for Logarithm as special.
 *    Construction of instances is provided by the Logarithm object, analogous to a companion object for classes.
 *    You do have to define methods like apply yourself, unlike for case classes, where the compiler can generate them.
 *    Any methods you want on Logarithms are implemented as extension methods,
 *    like toDouble, +, and * here. You can’t just call Double methods on a Logarithm.
 *    
 *    You either have to define an extension method for each Double method you want
 *    or
 *    use toDouble, call the method, then convert back to a Logarithm.
 */

object LogarithmExample {
  
  def test: Unit = {
  
    import Logarithms._
  
    val l1: Logarithm = Logarithm(1.0)
    println(s"Logarithm(1.0) = ${l1}")
  
    val l2 = Logarithm(2.0)
    println(s"Logarithm(2.0) = ${l2}")
  
    val ls = (0 to 10).map(i => Logarithm(i))
    println(s"logs 0 to 10: $ls")
    
    val add = l1 + l2
    println(s"Logarithm(1.0) + Logarithm(2.0) = $add")
    
    val multiply = l1 * l2
    println(s"Logarithm(1.0) * Logarithm(2.0) = $multiply")
    
    val logToDouble = l1.toDouble
    println(s"Logarithm(1.0).toDouble")
  }
}

