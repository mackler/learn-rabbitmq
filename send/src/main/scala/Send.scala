import com.rabbitmq.client.{ Channel, Connection, ConnectionFactory }
import scala.util.{ Try, Success, Failure, Using }
import scala.jdk.CollectionConverters.*

object Send:
  @main def main(args: String*): Unit =
    val factory = new ConnectionFactory
    factory.setHost(Common.hostname)

    val result: Try[String] = Using.Manager: use =>
      val connection = use(factory.newConnection)
      val channel = use(connection.createChannel)
      channel.queueDeclare(Common.queueName, false, false, false, Map.empty[String, AnyRef].asJava)
      channel.basicPublish(
        "", Common.queueName, new com.rabbitmq.client.AMQP.BasicProperties, args(0).getBytes
      )
      "success"

    result match
      case Success(message) => println(message)
      case Failure(e) => println(e.toString)
