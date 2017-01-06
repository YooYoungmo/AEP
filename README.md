# AEP(Application Environment Profile)
애플리케이션 개발 스테이지(Stage-계발계, 테스트계, 운영계 등)별로 달라 지는 환경 설정 정보를 관리 한다.

## 사용법
### config 파일 설정
* app-config.json
```json
{
	"dev" : {
		"paymentGateway" : {
			"domain" : "http://dev-pg.com",
			"propertyPath" : "classpath:conf/dev-pg.properties"
		}
	},
	"prod" : {
		"paymentGateway" : {
			"domain" : "http://pg.com",
			"propertyPath" : "classpath:conf/pg.properties"
		}
	}
}
```
### Java
```java
AppConfigMap configMap = AppConfig.get("paymentGateway");
String domain = configMap.get("domain");
String propertyPath = configMap.get("propertyPath")
```
### JSP(Java Server Page)
```jsp
<script src="<incubator:appConfig id="paymentGateway" key="domain"/>/main/payment.js"></script>
```
