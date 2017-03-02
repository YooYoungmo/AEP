package aep;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by yooyoung-mo on 2017. 1. 10..
 */
public class AppConfigFileLoaderTest {
    final String testCinfigJson = "{\"default\" : { \"googleMaps\" : { \"url\" : \"http://google.com/maps\" }, \"openApi\" : { \"url\" :\"http://daum.net\" } } }";
    final String testConfigFilePath = "src/main/resources/test-app-config.json";
    final String testConfigFileName = "test-app-config.json";

    @Before
    public void setUp() throws Exception {
        // 테스트 파일 생성
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("src/main/resources/test-app-config.json"), "utf-8"))) {
            writer.write(testCinfigJson);
        }

        // Java Reflect 를 이용하여 파일 경로로 바꾸고 이를 다시 읽어 드리게 함.
        AppConfigFileLoader appConfigFileLoader = AppConfigFileLoader.getInstance();

        // private 필드 변경
        Field field = appConfigFileLoader.getClass().getDeclaredField("CONFIG_ROOT_DEFAULT_FILE_PATH");
        field.setAccessible(true);
        field.set(appConfigFileLoader, "/sssss.json");

        // private 메소드 호출
        Method method = appConfigFileLoader.getClass().getDeclaredMethod("initConfigText");
        method.setAccessible(true);
        method.invoke(appConfigFileLoader);
    }

    @After
    public void tearDown() throws Exception {
        // 테스트 파일 삭제
        File f = new File(testConfigFilePath);
        if (f.delete()) {
        } else {
            System.err.println("파일 또는 디렉토리 지우기 실패: " + testConfigFilePath);
        }
    }

    @Test
    public void getText() throws IOException {
        // given
        AppConfigFileLoader appConfigFileLoader = AppConfigFileLoader.getInstance();

        // when
        String actual = appConfigFileLoader.getText();

        // then
        String expected = "{  \"default\" : {    \"googleMaps\" : {      \"url\" : \"http://google.com/maps\"    },    \"openApi\" : {      \"url\" :\"http://daum.net\"    }  },  \"profile\": {    \"validStage\" : [\"dev\", \"stag\", \"prod\"],    \"stage\" : {      \"dev\": {        \"paymentGateway\": {          \"domain\": \"http://dev-pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        }      },      \"stag\": {        \"paymentGateway\": {          \"domain\": \"http://stg-pg.com\",          \"propertyPath\": \"classpath:conf/stg-pg.properties\"        }      },      \"prod\": {        \"paymentGateway\": {          \"domain\": \"http://pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        },        \"googleMaps\" : {          \"url\" : \"http://googletest.com/maps\"        }      }    }  }}";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getInstance() throws IOException {
        // given

        // when
        AppConfigFileLoader appConfigFileLoaderFirst = AppConfigFileLoader.getInstance();
        AppConfigFileLoader appConfigFileLoaderSecond = AppConfigFileLoader.getInstance();

        // then
        Assert.assertEquals(appConfigFileLoaderFirst, appConfigFileLoaderSecond);
    }
}