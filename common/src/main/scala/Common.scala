import upickle.default.{ ReadWriter, macroRW }

object Common:
  val hostname = "localhost"
  val queueName = "hello"

  case class MyMessage(name: String, priority: Int, what: String)
  object MyMessage:
    implicit val vw: ReadWriter[MyMessage] = macroRW
