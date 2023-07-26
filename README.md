<div align="center">
    <img src='./resources/logo.png' width=300>
</div>

## Introduce

&nbsp; 2022년에 개발했던 같이 하는 포모도로 서비스 `포동포동` 서버를 Kotlin 기반 Spring Boot로 리팩토링하는 레포지토리입니다.

## Skill Stack

- Kotlin 1.8.22
- JVM 17.0.2
- Gradle 8.1.1
  - checkstyle (`google`)
  - jacoco
- Spring Boot 3.1.2
  - Spring Data Jpa, QueryDsl
  - Spring Security, OAuth2.0
  - Spring Rest Docs
  - WebSocket, Netty
  - JUnit, Mockito
- MySql 8.0.33

## Build and Run

```shell
$ git clone https://github.com/lcomment/podong-podong-spring.git
$ ./gradlew build
$ docker build --build-arg DEPENDENCY=build/dependency -t podongpodong .
$ docker run --restart=always --name=podongpodong -d -p 8080:8080 podongpodong
```
