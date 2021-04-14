trait Ord[T]:
  def compare(x: T, y: T): Int
  extension (x: T) def < (y: T) = compare(x, y) < 0
  extension (x: T) def > (y: T) = compare(x, y) > 0

/*
given intOrd as Ord[Int] {
  def compare(x: Int, y: Int) =
    if x < y then -1 else if x > y then +1 else 0
}
 */

// dont need to provide names for these implicits..
given Ord[Int] {
  def compare(x: Int, y: Int) =
    if x < y then -1 else if x > y then +1 else 0
}


object NewImplicitsWorld {
  def max[T](x: T, y: T)(using ord: Ord[T]): T =
    if ord.compare(x, y) < 0 then y else x

  def maximum[T](xs: List[T])(using Ord[T]): T =
    xs.reduceLeft(max)

  def descending[T](using asc: Ord[T]): Ord[T] = new Ord[T]:
    def compare(x: T, y: T) = asc.compare(y, x)

  def minimum[T](xs: List[T])(using Ord[T]) =
    maximum(xs)(using descending)
}

object NewImplicitsWorldExample {

  import NewImplicitsWorld._

  def test: Unit = {

    println("max...")
//    val max = NewImplicitsWorld.max(2, 3)(using intOrd)
//    println(max)
    val max2 = NewImplicitsWorld.max(2, 3)
    println(max2)


    // the following calls are all well-formed, and they all normalize to the last one
    val xs: List[Int] = List(3, 2, 5)

    println("min ...")
    val min = minimum(xs)
    println(min)
    val min2 = maximum(xs)(using descending)
    println(min2)
  }
}
