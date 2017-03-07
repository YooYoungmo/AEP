package aep;

import org.junit.*;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by yooyoung-mo on 2017. 1. 10..
 */
public class AppConfigFileLoaderTest {
    final static String testConfigJson = "{\"default\" : { \"googleMaps\" : { \"url\" : \"http://google.com/maps\" }, \"openApi\" : { \"url\" :\"http://daum.net\" } } }";
    final static String testConfigFilePath = "target/test-classes/";
    final static String testConfigFileName = "test-app-config.json";
    final static String testConfigFilePathFullPath = testConfigFilePath + testConfigFileName;
    static String originPath = "";

    @BeforeClass
    public static void beforeClass() throws Exception {
        // 테스트 파일 생성
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(testConfigFilePathFullPath), "utf-8"))) {
            writer.write(testConfigJson);
        }

        // Java Reflect 를 이용하여 파일 경로로 바꾸고 이를 다시 읽어 드리게 함.
        AppConfigFileLoader appConfigFileLoader = AppConfigFileLoader.getInstance();

        // private 필드 변경
        Field field = appConfigFileLoader.getClass().getDeclaredField("CONFIG_ROOT_DEFAULT_FILE_PATH");
        field.setAccessible(true);
        originPath = (String)field.get(appConfigFileLoader);

        field.set(appConfigFileLoader, testConfigFileName);


        // private 메소드 호출
        Method method = appConfigFileLoader.getClass().getDeclaredMethod("initConfigText");
        method.setAccessible(true);
        method.invoke(appConfigFileLoader);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        // 테스트 파일 삭제
        File f = new File(testConfigFilePathFullPath);
        if (f.delete()) {
        } else {
            System.err.println("파일 또는 디렉토리 지우기 실패: " + testConfigFilePath);
        }

        // Java Reflect 를 이용하여 파일 경로로 롤백
        AppConfigFileLoader appConfigFileLoader = AppConfigFileLoader.getInstance();

        // private 필드 변경
        Field field = appConfigFileLoader.getClass().getDeclaredField("CONFIG_ROOT_DEFAULT_FILE_PATH");
        field.setAccessible(true);
        field.set(appConfigFileLoader, originPath);

        // private 메소드 호출
        Method method = appConfigFileLoader.getClass().getDeclaredMethod("initConfigText");
        method.setAccessible(true);
        method.invoke(appConfigFileLoader);
    }

    @Test
    public void getText() throws IOException {
        // given
        AppConfigFileLoader appConfigFileLoader = AppConfigFileLoader.getInstance();

        // when
        String actual = appConfigFileLoader.getText();

        // then
        Assert.assertEquals(testConfigJson, actual);
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