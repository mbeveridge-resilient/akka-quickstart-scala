# akka-quickstart-scala

Example of using Akka Http to provide Rest endpoint to invoke AWS step function with re-queuing of failed requests in a priority queue. 

Put the ARN of the State Machine to start in `StepFunctionInvoker.scala`.

Invoke it using the following HTTP request:

```
curl -X POST "http://localhost:8080/call/<callId>" -d '{"callId": "<callId>", "date": "<ISO Date>", "networkCLI": "<CLI>", "withheld": <true/false>, "customerId": "<customerId"}'
```

For example:

```
curl -X POST "http://localhost:8080/call/12398739417" -d '{"callId": "f971a122-12c9-4a95-9f98-d0c4f73e1f5e", "date": "2008-09-15T15:53:00+00:00", "networkCLI": "+441488123456", "withheld": false, "customerId": "a0940c96-4a29-4180-8415-eb929d1c8f64"}'
```
