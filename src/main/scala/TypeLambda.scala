/**
 * [X] =>> F[X] defines a function from types to types.
 * The parameter(s) may carry bounds.
 */

object TypeLambda {

  type T[X, Y] = Map[Y, X] // basic type lamdbda
  type Tuple = [X] =>> (X, X)


  type TL = [X] =>> [Y] =>> (X, Y) // The body of a type lambda can again be a type lambda
  
  def test : Unit = {
    val m: T[String, Int] = Map(1 -> "One")
    println(m)
    
    val tuple: Tuple[String] = ("a", "b")
    println(tuple)
  }
}
