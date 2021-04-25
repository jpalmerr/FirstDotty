object MatchTypes {

  // A match type reduces to one of its right-hand sides

  type LeafElem[X] = X match
    case String => Char
    case Array[t] => LeafElem[t]
    case Iterable[t] => LeafElem[t]
    case AnyVal => X


  def leafElem[X](x: X): LeafElem[X] = x match
    case x: String      => x.charAt(0)
    case x: Array[t]    => leafElem(x(9))
    case x: Iterable[t] => leafElem(x.head)
    case x: AnyVal      => x
  
  
  def test: Unit = {
    val printMe = leafElem("hello")
    println(printMe)
    val bigList = leafElem(Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    println(bigList)
  }

}
