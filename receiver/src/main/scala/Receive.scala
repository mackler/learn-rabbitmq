import com.rabbitmq.client.{
  Channel,
  Connection,
  ConnectionFactory,
  DefaultConsumer,
  Delivery,
  DeliverCallback,
  Envelope
}
import com.rabbitmq.client.AMQP.BasicProperties
import upickle.default.*
import scala.jdk.CollectionConverters.*
import java.nio.charset.StandardCharsets

object Receiver:
  class deliverCallback(channel: Channel) extends DefaultConsumer(channel):
    override def handleDelivery(
      consumerTag: String, envelope: Envelope, amqpProperties: BasicProperties, body: Array[Byte]
    ): Unit =
      val bodyString = new String(body, StandardCharsets.UTF_8)
      val message = read[Common.MyMessage](bodyString)
      println(s"consumer tag is is '$consumerTag'")
      println(s"envelope is '$envelope'")
      println(s"body is '$bodyString'")
      println(s"AMQP properties $amqpProperties")
      println(s"name is ${message.name}")
      println(s"priority is ${message.priority}")
      println(s"instruction is ${message.what}")

  @main def recvMain(args: String*): Unit =
    val factory = new ConnectionFactory
    factory.setHost("rabbitmq")
    val connection = factory.newConnection
    val channel = connection.createChannel

    channel.queueDeclare(Common.queueName, false, false, false, Map.empty[String, AnyRef].asJava)
    println("waiting for messages")
    channel.basicConsume(Common.queueName, true, deliverCallback(channel))
