# AEP(Application Environment Profile)
애플리케이션 개발 스테이지(Stage-계발계, 테스트계, 운영계 등)별로 달라 지는 환경 설정 정보를 관리 한다.

## 사용법
### 전제 조건
* config 파일 설정
  * app-config.json
```json
  {
    "profile": {
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
        }
      }
    },
    "googleMaps" : {
      "url" : "http://google.com/maps"
    }
  }
```
* Java Opt 으로 Profile 지정
  * -Dapp.env.profile.active = dev or prod

### Java 사용법
```java
AppConfigMap configMap = AppConfig.get("paymentGateway");
String domain = configMap.get("domain");
String propertyPath = configMap.get("propertyPath")
```
### JSP(Java Server Page) 사용법
```jsp
<script src="<incubator:appConfig id="paymentGateway" key="domain"/>/main/payment.js"></script>
```
## 참고 자료
### Resource Loading
```java
InputStream in = this.getClass().getClassLoader().getResourceAsStream("conf/app-config.json");
```
### JSONObject from InputStream
```java
BufferedReader bR = new BufferedReader(  new InputStreamReader(in));
String line = "";

StringBuilder responseStrBuilder = new StringBuilder();
while((line =  bR.readLine()) != null){

    responseStrBuilder.append(line);
}
in.close();
JSONObject jsonObject = JSONObject.fromObject(responseStrBuilder.toString());
```
