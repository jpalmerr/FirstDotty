
Given instances define  "canonical" values of certain types
that serve for synthesizing arguments to context parameters

## Pattern Bound

```scala
pair match
   case (ctx @ given Context, y) => ???
```

Remember ambigious implicit compiler errors...

```scala
trait Tagged[A]

case class Foo[A](value: Boolean)
object Foo:
  given fooTagged[A](using Tagged[A]): Foo[A] = Foo(true)
  given fooNotTagged[A](using NotGiven[Tagged[A]]): Foo[A] = Foo(false)

@main def test(): Unit =
  given Tagged[Int] with {}
  assert(summon[Foo[Int]].value) // fooTagged is found
  assert(!summon[Foo[String]].value) // fooNotTagged is found
```
