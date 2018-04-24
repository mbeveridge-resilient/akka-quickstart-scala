package com.resilient.akka.sample

import akka.actor.ActorSystem
import akka.dispatch.{PriorityGenerator, UnboundedStablePriorityMailbox}
import com.resilient.akka.sample.FraudInvoker.Evaluate
import com.typesafe.config.Config

class PriorityMailbox(settings: ActorSystem.Settings, config: Config)
  extends UnboundedStablePriorityMailbox(
    // Create a new PriorityGenerator, lower priority means more important
    PriorityGenerator {

      // low priority should be treated last
      case Evaluate(_, 'low_priority) => 10

      // We default to 0, which is high priority and should be treated first
      case _                          => 0
    })
