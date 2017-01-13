package aep;

import net.sf.json.JSONObject;

import java.io.*;

/**
 * Created by yooyoung-mo on 2017. 1. 10..
 */
public class AppConfigFileLoader {
    private final String filePath;

    public AppConfigFileLoader(String filePath) {
        this.filePath = filePath;
    }

    public String getText() throws IOException {
        InputStream configStream = AppConfig.class.getClassLoader().getResourceAsStream(filePath);

        if(configStream == null){
            throw new FileNotFoundException("파일을 찾을 수 없습니다 - path : " + filePath);
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
}
