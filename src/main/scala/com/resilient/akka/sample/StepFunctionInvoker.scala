package com.resilient.akka.sample

import java.util.concurrent.Executors.newFixedThreadPool

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.regions.Regions
import com.amazonaws.services.stepfunctions.model.{DescribeExecutionRequest, DescribeExecutionResult, StartExecutionRequest, StartExecutionResult}
import com.amazonaws.services.stepfunctions.{AWSStepFunctions, AWSStepFunctionsClientBuilder}

import scala.concurrent.ExecutionContext

object StepFunctionInvoker {

  implicit val executionContext: ExecutionContext = ExecutionContext.fromExecutorService(newFixedThreadPool(10))

  val client: AWSStepFunctions = AWSStepFunctionsClientBuilder.standard()
    .withCredentials(new DefaultAWSCredentialsProviderChain())
    .withRegion(Regions.EU_WEST_1)
    .build();

  def startExecution(name: String, input: String): String = {
    val request = new StartExecutionRequest()
      .withName(name)
      .withInput(input)
      .withStateMachineArn("")

    val result: StartExecutionResult = client.startExecution(request)

    result.getExecutionArn
  }

}
