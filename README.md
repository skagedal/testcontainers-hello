# testcontainers + colima replication case

This here is some simple code to test Testcontainers. 

If I run this with `colima` as my Docker engine, and configure it [straight out of documentation](https://www.testcontainers.org/supported_docker_environment/), I get errors:

```
❯ ./gradlew test

> Task :app:test

AppTest > testcontainers_mysql() STANDARD_ERROR
    [Test worker] INFO org.testcontainers.utility.ImageNameSubstitutor - Image name substitution will be performed by: DefaultImageNameSubstitutor (composite of 'ConfigurationFileImageNameSubstitutor' and 'PrefixingImageNameSubstitutor')
    [Test worker] INFO org.testcontainers.dockerclient.DockerClientProviderStrategy - Loaded org.testcontainers.dockerclient.UnixSocketClientProviderStrategy from ~/.testcontainers.properties, will try it first
    [Test worker] INFO org.testcontainers.dockerclient.DockerClientProviderStrategy - Found Docker environment with Environment variables, system properties and defaults. Resolved dockerHost=unix:///Users/simon/.colima/docker.sock
    [Test worker] INFO org.testcontainers.DockerClientFactory - Docker host IP address is localhost
    [Test worker] INFO org.testcontainers.DockerClientFactory - Connected to docker:
      Server Version: 20.10.18
      API Version: 1.41
      Operating System: Alpine Linux v3.16
      Total Memory: 1976 MB
    [Test worker] INFO 🐳 [testcontainers/ryuk:0.3.4] - Pulling docker image: testcontainers/ryuk:0.3.4. Please be patient; this may take some time but only needs to be done once.
    [docker-java-stream-257369379] INFO 🐳 [testcontainers/ryuk:0.3.4] - Starting to pull image
    [docker-java-stream-257369379] INFO 🐳 [testcontainers/ryuk:0.3.4] - Pulling image layers:  0 pending,  0 downloaded,  0 extracted, (0 bytes/0 bytes)
    [docker-java-stream-257369379] INFO 🐳 [testcontainers/ryuk:0.3.4] - Pulling image layers:  2 pending,  1 downloaded,  0 extracted, (595 KB/? MB)
    [docker-java-stream-257369379] INFO 🐳 [testcontainers/ryuk:0.3.4] - Pulling image layers:  1 pending,  2 downloaded,  0 extracted, (4 MB/? MB)
    [docker-java-stream-257369379] INFO 🐳 [testcontainers/ryuk:0.3.4] - Pulling image layers:  0 pending,  3 downloaded,  0 extracted, (4 MB/4 MB)
    [docker-java-stream-257369379] INFO 🐳 [testcontainers/ryuk:0.3.4] - Pulling image layers:  0 pending,  3 downloaded,  1 extracted, (4 MB/4 MB)
    [docker-java-stream-257369379] INFO 🐳 [testcontainers/ryuk:0.3.4] - Pulling image layers:  0 pending,  3 downloaded,  2 extracted, (4 MB/4 MB)
    [docker-java-stream-257369379] INFO 🐳 [testcontainers/ryuk:0.3.4] - Pulling image layers:  0 pending,  3 downloaded,  3 extracted, (4 MB/4 MB)
    [docker-java-stream-257369379] INFO 🐳 [testcontainers/ryuk:0.3.4] - Pull complete. 3 layers, pulled in 2s (downloaded 4 MB at 2 MB/s)
    [Test worker] INFO 🐳 [testcontainers/ryuk:0.3.4] - Creating container for image: testcontainers/ryuk:0.3.4
    [Test worker] INFO 🐳 [testcontainers/ryuk:0.3.4] - Container testcontainers/ryuk:0.3.4 is starting: 0e046e375fef91083628b06b9a46397811d65fa04511886ddfbd662fafa771b4
    [Test worker] INFO 🐳 [testcontainers/ryuk:0.3.4] - Container testcontainers/ryuk:0.3.4 started in PT4.847594S
    [testcontainers-ryuk] WARN org.testcontainers.utility.RyukResourceReaper - Can not connect to Ryuk at localhost:49153
    java.net.ConnectException: Connection refused
        at java.base/sun.nio.ch.Net.pollConnect(Native Method)
        at java.base/sun.nio.ch.Net.pollConnectNow(Net.java:672)
        at java.base/sun.nio.ch.NioSocketImpl.timedFinishConnect(NioSocketImpl.java:549)
        at java.base/sun.nio.ch.NioSocketImpl.connect(NioSocketImpl.java:597)
        at java.base/java.net.SocksSocketImpl.connect(SocksSocketImpl.java:327)
        at java.base/java.net.Socket.connect(Socket.java:633)
        at org.testcontainers.utility.RyukResourceReaper.lambda$null$0(RyukResourceReaper.java:92)
        at org.rnorth.ducttape.ratelimits.RateLimiter.doWhenReady(RateLimiter.java:27)
        at org.testcontainers.utility.RyukResourceReaper.lambda$maybeStart$1(RyukResourceReaper.java:88)
        at java.base/java.lang.Thread.run(Thread.java:833)
    [testcontainers-ryuk] WARN org.testcontainers.utility.RyukResourceReaper - Can not connect to Ryuk at localhost:49153
    java.net.ConnectException: Connection refused
        at java.base/sun.nio.ch.Net.pollConnect(Native Method)
        at java.base/sun.nio.ch.Net.pollConnectNow(Net.java:672)
        at java.base/sun.nio.ch.NioSocketImpl.timedFinishConnect(NioSocketImpl.java:542)
        at java.base/sun.nio.ch.NioSocketImpl.connect(NioSocketImpl.java:597)
        at java.base/java.net.SocksSocketImpl.connect(SocksSocketImpl.java:327)
        at java.base/java.net.Socket.connect(Socket.java:633)
        at org.testcontainers.utility.RyukResourceReaper.lambda$null$0(RyukResourceReaper.java:92)
        at org.rnorth.ducttape.ratelimits.RateLimiter.doWhenReady(RateLimiter.java:27)
        at org.testcontainers.utility.RyukResourceReaper.lambda$maybeStart$1(RyukResourceReaper.java:88)
        at java.base/java.lang.Thread.run(Thread.java:833)
    [testcontainers-ryuk] WARN org.testcontainers.utility.RyukResourceReaper - Can not connect to Ryuk at localhost:49153
    java.net.ConnectException: Connection refused
        at java.base/sun.nio.ch.Net.pollConnect(Native Method)
        at java.base/sun.nio.ch.Net.pollConnectNow(Net.java:672)
        at java.base/sun.nio.ch.NioSocketImpl.timedFinishConnect(NioSocketImpl.java:542)
        at java.base/sun.nio.ch.NioSocketImpl.connect(NioSocketImpl.java:597)
        at java.base/java.net.SocksSocketImpl.connect(SocksSocketImpl.java:327)
        at java.base/java.net.Socket.connect(Socket.java:633)
        at org.testcontainers.utility.RyukResourceReaper.lambda$null$0(RyukResourceReaper.java:92)
        at org.rnorth.ducttape.ratelimits.RateLimiter.doWhenReady(RateLimiter.java:27)
        at org.testcontainers.utility.RyukResourceReaper.lambda$maybeStart$1(RyukResourceReaper.java:88)
        at java.base/java.lang.Thread.run(Thread.java:833)
```

Or at least warnings regardings Ryuk. When I run my real app's test suite like this, I get similar errors from Localstack test, but I haven't been able to reproduce reliably in this test case.

If I set things up according to [this comment](https://github.com/testcontainers/testcontainers-java/issues/5034#issuecomment-1319812252), i.e. with colima running with `--network-address` and another environment variable for my test, everything works.

I run this on a M1 macBook Pro:
```
❯ uname -a
Darwin ip-10-0-100-241.eu-west-2.compute.internal 21.6.0 Darwin Kernel Version 21.6.0: Thu Sep 29 20:13:56 PDT 2022; root:xnu-8020.240.7~1/RELEASE_ARM64_T6000 arm64

❯ sw_vers
ProductName:	macOS
ProductVersion:	12.6.1
BuildVersion:	21G217

❯ java -version
openjdk version "17.0.5" 2022-10-18
OpenJDK Runtime Environment Temurin-17.0.5+8 (build 17.0.5+8)
OpenJDK 64-Bit Server VM Temurin-17.0.5+8 (build 17.0.5+8, mixed mode)

```
