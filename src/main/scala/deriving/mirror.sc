/*
  sealed trait Mirror {
  type MirroredType // the type that was mirrored itself
  type MirroredLabel <: String // the label of the mirrored type
  type MirroredElemLabels <: Tuple // a tuple of the labels for each individual element
  type MirroredElemTypes <: Tuple // a tuple of types for each individual element
  }
 */

/**
 * MirroredType - is just the type we are deriving from, so our case class User
 * MirroredLabel - type-level representation of the label of the type we are mirroring, so the literal type "User"
 * MirroredElemLabels - A Tuple that contains the field names at type-level (literal types again!), so of type ("name", "age")
 * MirroredElemTypes - A tuple of all elements that User can be broken into: (String, Int) (for fields name and age)
 */

/**

// represents a case class
trait Product extends Mirror {
 def fromProduct(p: scala.Product): MirroredType
}

// represents a sealed trait
trait Sum extends Mirror { self =>
 def ordinal(x: MirroredType): Int
}

 */

import deriving._

import scala.deriving.Mirror

trait UserMirror extends Mirror.Product {
  type MirroredType = User
  type MirroredLabel = "User"
  type MirroredElemLabels = ("name", "age")
  type MirroredElemTypes = (String, Int)
}

// We need to bring it to the value-level to work with it at runtime

import scala.compiletime.constValue

inline def labelFromMirror[A](using m: Mirror.Of[A]): String = constValue[m.MirroredLabel]

println(labelFromMirror[User])

/*
keyword inline is needed here because the compiler needs to resolve constValue statically
and inline it at compile time which the keyword makes possible.
 */

import scala.compiletime.erasedValue

inline def getElemLabels[A <: Tuple]: List[String] = inline erasedValue[A] match {
  case _: EmptyTuple => Nil // stop condition - the tuple is empty
  case _: (head *: tail) =>  // yes, in scala 3 we can match on tuples head and tail to deconstruct them step by step
    val headElementLabel = constValue[head].toString // bring the head label to value space
    val tailElementLabels = getElemLabels[tail] // recursive call to get the labels from the tail
    headElementLabel :: tailElementLabels // concat head + tail
}

// helper method to get the mirror from compiler
inline def getElemLabelsHelper[A](using m: Mirror.Of[A]) =
  getElemLabels[m.MirroredElemLabels] // and call getElemLabels with the elemlabels type

getElemLabelsHelper[User]

/*
The most interesting thing here is the trick with the erasedValue method.
It allows us to create a virtual instance of the type A and match on it.
Virtual means that this is done at COMPILE TIME and there is no actual value to be matched on at runtime.
 */

// set up pretty string like in manual

given intPrettyString as PrettyString[Int] {
  def prettyString(a: Int): String = a.toString
}

given stringPrettyString as PrettyString[String] {
  def prettyString(a: String): String = "\"" + a + "\""
}

given userPrettyString as PrettyString[User] {
  def prettyString(a: User): String =
    s"User(name=${stringPrettyString.prettyString(a.name)}, age=${intPrettyString.prettyString(a.age)})"
}

import scala.compiletime.summonInline

inline def getTypeclassInstances[A <: Tuple]: List[PrettyString[Any]] = inline erasedValue[A] match {
  case _: EmptyTuple => Nil
  case _: (head *: tail) =>
    val headTypeClass = summonInline[PrettyString[head]] // summon was known as implicitly in scala 2
    val tailTypeClasses = getTypeclassInstances[tail] // recursive call to resolve also the tail
    headTypeClass.asInstanceOf[PrettyString[Any]] :: getTypeclassInstances[tail]
}

// helper method like before
inline def summonInstancesHelper[A](using m: Mirror.Of[A]) =
  getTypeclassInstances[m.MirroredElemTypes]

summonInstancesHelper[User]

// Right now we got all the information in place that we need for the generic derivation

inline def derivePrettyStringCaseClass[A](using m: Mirror.ProductOf[A]) =
  new PrettyString[A] {
    def prettyString(a: A): String = {
      val label = labelFromMirror[m.MirroredType]
      val elemLabels = getElemLabels[m.MirroredElemLabels]
      val elemInstances = getTypeclassInstances[m.MirroredElemTypes]
      val elems = a.asInstanceOf[Product].productIterator // every case class implements scala.Product, we can safely cast here
      val elemStrings = elems.zip(elemLabels).zip(elemInstances).map{
        case ((elem, label), instance) => s"$label=${instance.prettyString(elem)}"
      }
      s"$label(${elemStrings.mkString(", ")})"
    }
  }

val autoUserPrettyString = derivePrettyStringCaseClass[User]

println(autoUserPrettyString.prettyString(User("Bob", 25)))