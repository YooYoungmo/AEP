package aep;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

/**
 * Created by yooyoung-mo on 2017. 1. 10..
 */
public class AppConfigFileLoaderTest {
    @Test
    public void getText() throws IOException {
        // given
        final String expectedConfigFileText = loadFileToText();

        AppConfigFileLoader appConfigFileLoader = AppConfigFileLoader.getInstance();

        // when
        String actual = appConfigFileLoader.getText();

        // then
        Assert.assertEquals(expectedConfigFileText, actual);
    }

    private String loadFileToText() throws IOException {
        InputStream configStream = AppConfigFileLoaderTest.class.getClassLoader().getResourceAsStream("conf/app-config.json");

        if(configStream == null) {
            throw new FileNotFoundException("파일을 찾을 수 없습니다 - path : conf/app-config.json");
        }

        Closeable streamCloser = configStream;

        StringBuilder configStringBuilder = new StringBuilder();
        try {
            BufferedReader configBufferReader = new BufferedReader(new InputStreamReader(configStream));
            streamCloser = configBufferReader;

            String line = "";
            while ((line = configBufferReader.readLine()) != null) {
                configStringBuilder.append(line);
            }
        } finally {
            streamCloser.close();
        }

        return configStringBuilder.toString();
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