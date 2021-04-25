object UnionTypes {
  
  // pretty cool, will remove need for heirachy adts

  case class UserName(name: String)
  case class UserNumber(number: Int)
  
  def help(userId: UserName | UserNumber) = {
    val user = userId match {
      case UserName(name) => println(s"name: ${name}")
      case UserNumber(number) => println(s"number: ${number}")
    }
  }
  
  def test: Unit = {
    val userName = UserName("name")
    val userNumber = UserNumber(12345)
    help(userName)
    help(userNumber) 
  }
}
