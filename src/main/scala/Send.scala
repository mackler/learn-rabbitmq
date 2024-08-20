import com.rabbitmq.client.{ Channel, Connection, ConnectionFactory }
import scala.util.{ Try, Success, Failure, Using }
import scala.jdk.CollectionConverters.*

object Send:
  val QUEUE_NAME = "hello"
  @main def sendMain(args: String*): Unit =
    val factory = new ConnectionFactory
    factory.setHost("localhost")

    val result: Try[String] = Using.Manager: use =>
      val connection = use(factory.newConnection)
      val channel = use(connection.createChannel)
      channel.queueDeclare(QUEUE_NAME, false, false, false, Map.empty[String, AnyRef].asJava)
      channel.basicPublish(
        "", QUEUE_NAME, new com.rabbitmq.client.AMQP.BasicProperties, args(0).getBytes
      )
      "success"

    result match
      case Success(message) => println(message)
      case Failure(e) => println(e.toString)
