# AEP(Application Environment Profile)
애플리케이션 개발 스테이지(Stage-계발계, 테스트계, 운영계 등)별로 달라 지는 환경 설정 정보를 관리 한다.

## 작성 원칙
- Master branch는 Pair Programming 결과만 반영 한다.
- 테스트 코드 먼저 작성 한다.
- JUnit 테스트 케이스를 통과하지 못하는 코드는 Master branch에 반영하지 않는다.

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
