import deriving._

val intPrettyStringX =
  new PrettyString[Int] {
    def prettyString(a: Int): String  = a.toString
  }

val stringPrettyStringX = new PrettyString[String] {
  def prettyString(a: String): String = "\"" + a + "\""
}

println(intPrettyStringX.prettyString(123)) // prints 123
println(stringPrettyStringX.prettyString("hello world")) // prints "hello world"


/*
to create the userPrettyString instance we used 3 kinds of information:

The label of the case class itself: “User”
The labels of the fields: “name” and “age”
The typeclass instances for the fields: stringPrettyString and intPrettyString
 */

given intPrettyString as PrettyString[Int] {
  def prettyString(a: Int): String = a.toString
}

given stringPrettyString as PrettyString[String] {
  def prettyString(a: String): String = "\"" + a + "\""
}

def prettyPrintln[A](a: A)(using prettyStringInstance: PrettyString[A]) =
  println(prettyStringInstance.prettyString(a))

prettyPrintln(123) // prints 123
prettyPrintln("hello world") // prints "hello world"

// we can also pass the PrettyString instance explicitly
prettyPrintln(123)(using intPrettyString)

/**
given
Scala 3 it was reworked to clarify intent:
given the instance intPrettyString we can call prettyPrintln
using the PrettyString instance for the type Int.

The compiler will then take care of passing the intPrettyString parameter to prettyPrintln.
 */

// You can derive typeclasse instances for complex types from simpler ones.

given userPrettyString as PrettyString[User] {
  def prettyString(a: User): String =
    s"User(name=${stringPrettyString.prettyString(a.name)}, age=${intPrettyString.prettyString(a.age)})"
}

prettyPrintln(User("bob", 25))