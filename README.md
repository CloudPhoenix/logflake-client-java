# LogFlake Client Java

> This repository contains the sources for the client-side components of the LogFlake product suite for applications logs and performance collection for Java applications with a 5 thread pool.

### 🏠 [LogFlake Website](https://logflake.io) |  🔥 [CloudPhoenix Website](https://cloudphoenix.it)



## Installation

Install the package:

```mvn
<dependency>
    <groupId>io.logflake</groupId>
    <artifactId>client</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Usage

### Initialization

Import the necessary functions and initialize the client with your application ID and server URL:

```java
import io.logflake.LogFlakeClient;


...

LogFlakeClient logFlakeClient = LogFlakeClient.builder()
        .server("YOUR_SERVER_URL") // Optional, default is "https://app.logflake.io"
        .appKey("YOUR_APP_ID")
        .hostname("HOSTNAME") // Optional 
        .correlation("correlation-id") // Optional correlation function or string
        .enableCompression(true) // Optional, default is true
        .verbose(true) // Optional, default is true
        .build();


...
```

### Sending Logs

Send a log message with optional parameters:

```java
import io.logflake.enums.LogLevel;
import io.logflake.LogFlakeClient;

...

LogFlakeClient logFlakeClient = LogFlakeClient.builder()
        .server("YOUR_SERVER_URL") // Optional, default is "https://app.logflake.io"
        .appKey("YOUR_APP_ID")
        .hostname("HOSTNAME") // Optional 
        .correlation("correlation-id") // Optional correlation function or string
        .enableCompression(true) // Optional, default is true
        .verbose(true) // Optional, default is true
        .build();

logFlakeClient.sendLog("Hello, LogFlake!", LogLevel.INFO,"correlation-id", "HOSTNAME", HashMap<String, String> params);



...

```
- `correlation`: Optional by overloading, default from client or null
- `hostname`: Optional by overloading, default from client or null
- `param`: Optional by overloading


### Sending Exceptions

Capture and send exceptions to LogFlake:

```java
import io.logflake.enums.LogLevel;
import io.logflake.LogFlakeClient;

import java.util.HashMap;

...

LogFlakeClient logFlakeClient = LogFlakeClient.builder()
        .server("YOUR_SERVER_URL") // Optional, default is "https://app.logflake.io"
        .appKey("YOUR_APP_ID")
        .hostname("HOSTNAME") // Optional 
        .correlation("correlation-id") // Optional correlation function or string
        .enableCompression(true) // Optional, default is true
        .verbose(true) // Optional, default is true
        .build();

Exception e = new Exception("Something went wrong");

HashMap<String,String> params = new HashMap<>();
params.put("key", "value");

logFlakeClient.sendsendException(e,params,"correlation-id","HOSTNAME");


...
```

- `param`: Optional by overloading
- `correlation`: Optional by overloading, default from client or null
- `hostname`: Optional by overloading, default from client or null


### Measuring Performance

Measure the performance of a code block:

```java
import io.logflake.enums.LogLevel;
import io.logflake.LogFlakeClient;

import java.util.HashMap;

...

LogFlakeClient logFlakeClient = LogFlakeClient.builder()
        .server("YOUR_SERVER_URL") // Optional, default is "https://app.logflake.io"
        .appKey("YOUR_APP_ID")
        .hostname("HOSTNAME") // Optional 
        .correlation("correlation-id") // Optional correlation function or string
        .enableCompression(true) // Optional, default is true
        .verbose(true) // Optional, default is true
        .build();


// Your operation code here

logFlakeClient.measurePerformance("YOUR_LABEL");


...


```


### Destroy the client

You must manually destroy the client when done using it

```java
import io.logflake.enums.LogLevel;
import io.logflake.LogFlakeClient;

import java.util.HashMap;

...

LogFlakeClient logFlakeClient = LogFlakeClient.builder()
        .server("YOUR_SERVER_URL") // Optional, default is "https://app.logflake.io"
        .appKey("YOUR_APP_ID")
        .hostname("HOSTNAME") // Optional 
        .correlation("correlation-id") // Optional correlation function or string
        .enableCompression(true) // Optional, default is true
        .verbose(true) // Optional, default is true
        .build();


// Your operation code here

logFlakeClient.close();


...


```

### Resilience mechanism

The client use a 5 thread pool to send logs to the server, if it fails to send log, it starts an Exponential Back Off mechanism with 10s retry time and do 5 attempts.







## Configuration Options

When initializing the client, you can provide the following options:

- `correlation`: A function or value used to generate a correlation ID. Default is `null`.
- `enableCompression`: Enables compression of logs. Default is `true`.
- `verbose`: Enables verbose logging. Default is `true`.
- `hostname`: The hostname associated with the logs.

## Log Levels

The following log levels are available:

- `DEBUG`
- `INFO`
- `WARNING`
- `ERROR`
- `FATAL`
- `EXCEPTION`

Use these levels to categorize your log messages appropriately.




## Log4J Wrapper

The LogFlake client includes a Log4J wrapper that allows you to send logs directly from your Log4J configuration. To use the wrapper see the following example:

```java
import io.logflake.LogFlakeClient;
Log4jWrapper.getLogger(YourClass .class, (LogFlakeClient) logFlakeClient);


```
What does it do?:

- It wraps the `Apache` Log4J logger with the LogFlake client.
- You can use the logger as you would do with Log4J by adding the client as the last parameter.
- We maintain the same log levels as Log4J, so you can use them as you would do with Log4J.
- At the moment we support only the message to be wrapped, we are working on the other parameters.
- The correlation and hostname are automatically added to the log message from client config.





## License

This project is licensed under the MIT License.