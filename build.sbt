lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(
    "com.rabbitmq" % "amqp-client" % "5.21.0",
    "org.slf4j" % "slf4j-api" % "2.0.16",
    "org.slf4j" % "slf4j-simple" % "2.0.16"
  ),
  scalaVersion := "3.5.0"
)

lazy val common = (project in file("common"))
  .settings(
    commonSettings
  )

lazy val send = (project in file("send"))
  .settings(
    commonSettings
  ).dependsOn(common)

lazy val receiver = (project in file("receiver"))
  .enablePlugins(DockerPlugin, JavaServerAppPackaging)
  .settings(
    commonSettings
  ).dependsOn(common)
