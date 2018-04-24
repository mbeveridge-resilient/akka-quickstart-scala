package com.resilient.akka.sample

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.http.scaladsl.server.{HttpApp, Route}
import akka.stream.ActorMaterializer
import com.resilient.akka.sample.FraudInvoker.Evaluate

import scala.concurrent.ExecutionContextExecutor

object WebService extends HttpApp {

  private val happyPathSmartblock = "FORWARD TO HAPPY PATH"

  // implicits...
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  // Create the invoker actor
  val fraudInvoker: ActorRef = system.actorOf(FraudInvoker.props, "fraudInvokerActor")

  // define routes
  override def routes: Route =
    path("call" / ".+".r) { (callId) =>
      post {
        entity(as[String]) { json =>
          fraudInvoker ! Evaluate(callId, json, 'high_priority)
          complete(happyPathSmartblock)
        }
      }
    }


  // start service
  def main(args: Array[String]): Unit = {
    WebService.startServer("localhost", 8080)
  }
}

// actor to invoke AWS step function
class FraudInvoker extends Actor with ActorLogging {
  import FraudInvoker._

  def receive: PartialFunction[Any, Unit] = {
    case Evaluate(callId, json, priority) =>
      log.info(s"Invocation received for call $callId with priority $priority")
      try {
        StepFunctionInvoker.startExecution(callId, json)
      } catch {
        // re-send as low priority message
        case t: Throwable => {
          log.warning(s"invocation failed with error: ${t.getMessage}. Re-queuing...")
          self ! Evaluate(callId, json, 'low_priority)
        }
      }
  }

}

object FraudInvoker {
  def props: Props = Props[FraudInvoker].withDispatcher("akka.priority-dispatcher")

  final case class Evaluate(name: String, input: String, priority: Symbol)
}
