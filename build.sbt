name := "akka-quickstart-scala"

version := "1.0"

scalaVersion := "2.12.5"

lazy val akkaVersion = "2.5.12"

resolvers += "spray" at "http://repo.spray.io/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-http"   % "10.1.1",
  "com.typesafe.akka" %% "akka-stream" % "2.5.12",
  "com.amazonaws" % "aws-java-sdk" % "1.11.301",
  "com.amazonaws" % "aws-java-sdk-stepfunctions" % "1.11.313",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)
