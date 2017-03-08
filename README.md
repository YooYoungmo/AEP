# AEP(Application Environment Profile)  

## 목적
응용소프트웨어(Application)를 개발 하다보면 많은 config값들을 필요로 한다.
이 값들은 실행 환경(stage)에 따라 별도의 값을 가지는 경우가 많다.

현재 우리가 개발하는 응용소프트웨어의 코드를 들여다 보면
많은 config값들이 하드코딩 되어있다.
실행 환경에 대한 고려 없이 주로 운영 환경에서 사용하는 값들로 채워져있으며
테스트 환경에서 개발을 하는 우리는 이 값들을 주석 처리를 하고
테스트 용 config값들로 변경하여 개발과 테스트를 하고 있다.

이 와중에 개발자의 실수로 인해
테스트 용 config값을 가진 코드들이
운영 환경에 배포되어 서비스 장애를 발생시키거나
테스트 환경에서 운영 용 config값들을 이용하여 잘못된 처리를 일으키고 있다.
이는 고스란히 우리의 잘못과 책임이 되고 있다.

또 현재 framework 에서 사용하는 config값 관리 방식은
운영 환경 배포 과정에 배포 담당자와의 연계를 필요로하며
자동화된 시스템에 의해 처리되는 것이 아니라 사람에 의존하여 배포가 되고 있다.
이는 개발 이 외의 커뮤니케이션 비용 발생, 운영업무 증가, 인적요소에 의한 장애의 위험 등을 안고있는 상황이다.

우리는 이러한 문제점들을 고치기 위해 고민을 하고 있으며
이 프로젝트가 시작되었다.

## 목표
애플리케이션 개발 스테이지(Stage-계발계, 테스트계, 운영계 등)별로 달라 지는 환경 설정 정보를 관리 한다.

## 프로젝트 진행 원칙
- Master branch는 Pair Programming 결과만 반영 한다.
- 자동화 테스트 케이스 코드를 먼저 작성 한다.
- 자동화 테스트 케이스를 통과하지 못하는 코드는 Master branch에 반영하지 않는다.
- 각 이슈 별로 branch를 생성하며 이슈 해결 후 Master branch와 merge를 한다.

## 사용법
### 전제 조건
* config 파일 설정
  * app-config.json
```json
  {
  "default" : {
    "googleMaps" : {
      "url" : "http://google.com/maps"
    }
  },
  "profile": {
    "validStage" : ["dev", "prod"],
    "stage" : {
      "dev": {
        "paymentGateway": {
          "domain": "http://dev-pg.com",
          "propertyPath": "classpath:conf/dev-pg.properties"
        }
      },
      "prod": {
        "paymentGateway": {
          "domain": "http://pg.com",
          "propertyPath": "classpath:conf/dev-pg.properties"
        },
        "googleMaps" : {
          "url" : "http://googletest.com/maps"
        }
      }
    }
  }
}
```
* Java Opt 으로 Profile 지정
  * -Dapp.env.profile.active = dev or prod

### Java 사용법
```java
Map<String, String> configValue = AppConfig.getConfigValue("paymentGateway");
String domain = configValue.get("domain");
String propertyPath = configValue.get("propertyPath")
```
### JSP(Java Server Page) 사용법
```jsp
<script src="<incubator:appConfig id="paymentGateway" key="domain"/>/main/payment.js"></script>
```
