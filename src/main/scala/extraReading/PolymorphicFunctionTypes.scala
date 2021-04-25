package extraReading

object PolymorphicFunctionTypes {
  // A polymorphic method: a function type which accepts type parameters
  def foo[A](xs: List[A]): List[A] = xs.reverse

  // now we can set vals
  val bar: [A] => List[A] => List[A] = [A] => (xs: List[A]) => foo[A](xs)
  // the type of bar is: [A] => List[A] => List[A]
}
