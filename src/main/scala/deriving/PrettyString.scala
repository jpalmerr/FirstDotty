package deriving

trait PrettyString[A] {
  def prettyString(a: A): String
}
