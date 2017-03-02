package aep;

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

    private static final String filePath = "conf/test-app-config.json";
    private static final String filePathForTest = "target/classes/" + filePath;
    private static final String fileText = "{  \"default\" : {    \"googleMaps\" : {      \"url\" : \"http://google.com/maps\"    },    \"openApi\" : {      \"url\" :\"http://daum.net\"    }  },  \"profile\": {    \"validStage\" : [\"dev\", \"stag\", \"prod\", \"test\"],    \"stage\" : {      \"dev\": {        \"paymentGateway\": {          \"domain\": \"http://dev-pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        }      },      \"stag\": {        \"paymentGateway\": {          \"domain\": \"http://stg-pg.com\",          \"propertyPath\": \"classpath:conf/stg-pg.properties\"        }      },      \"prod\": {        \"paymentGateway\": {          \"domain\": \"http://pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        },        \"googleMaps\" : {          \"url\" : \"http://googletest.com/maps\"        }      }    }  }}";

    @Before
    public void setUp() throws Exception {
        // 테스트 파일 생성

        Closeable writerCloseable = null;

        try {
            //FileWriter fileWriter = new FileWriter("E:\\Program_Files\\Portable\\PortableGit\\repo\\AEP\\src\\main\\resources\\conf\\spike-app-config.json");
            FileWriter fileWriter = new FileWriter(filePathForTest);
            writerCloseable = fileWriter;

            BufferedWriter out = new BufferedWriter(fileWriter);
            writerCloseable = out;

            out.write(fileText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writerCloseable != null) {
                writerCloseable.close();
            }
        }

        AppConfigFileLoader appConfigFileLoader = AppConfigFileLoader.getInstance();

        Field field = appConfigFileLoader.getClass().getDeclaredField("CONFIG_ROOT_DEFAULT_FILE_PATH");
        field.setAccessible(true);
        field.set(appConfigFileLoader, filePath);

        // private 메소드 호출
        Method method = appConfigFileLoader.getClass().getDeclaredMethod("initConfigText");
        method.setAccessible(true);
        method.invoke(appConfigFileLoader);

        //AppConfigFileLoader.setConfigRootDefaultFilePath(filePath);
    }

    //@After
    public void tearDown() throws Exception {
        // 테스트 파일 삭제
        File file = new File(filePathForTest);
        if (!file.delete()) {
            System.out.println("파일 삭제 실패");
        }
    }

    @Test
    public void getText() throws IOException {
        // given
        AppConfigFileLoader appConfigFileLoader = AppConfigFileLoader.getInstance();

        // when
        String actual = appConfigFileLoader.getText();

        // then
        String expected = fileText;
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