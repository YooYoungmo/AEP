package aep;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by yooyoung-mo on 2017. 1. 10..
 */
public class AppConfigFileLoaderTest {

    @Before
    public void setUp() throws Exception {
        // 테스트 파일 생성
    }

    @After
    public void tearDown() throws Exception {
        // 테스트 파일 삭제

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