package aep;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by langve on 2017. 3. 3..
 */
public class AppConfigFileLoader {
    public static String getText() throws IOException {
        InputStream configStream = AppConfig.class.getClassLoader().getResourceAsStream("conf/app-config.json");
        Closeable streamCloser = configStream;
        StringBuilder configStringBuilder = new StringBuilder();

        try {
            BufferedReader configBufferReader = new BufferedReader(new InputStreamReader(configStream, Charset.forName("UTF-8")));
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
}
