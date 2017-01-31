package aep;

import net.sf.json.JSONObject;

import java.io.*;

/**
 * Created by yooyoung-mo on 2017. 1. 10..
 */
public class AppConfigFileLoader {
    private static final String CONFIG_ROOT_DEFAULT_FILE_PATH = "conf/app-config.json";
    private static AppConfigFileLoader instance = null;
    private static String configText = "";

    private AppConfigFileLoader() throws IOException {
        InputStream configStream = AppConfig.class.getClassLoader().getResourceAsStream(CONFIG_ROOT_DEFAULT_FILE_PATH);

        if(configStream == null){
            throw new FileNotFoundException("파일을 찾을 수 없습니다 - path : " + CONFIG_ROOT_DEFAULT_FILE_PATH);
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

        configText = configStringBuilder.toString();
    }

    public String getText() throws IOException {
        return configText;
    }


    public static AppConfigFileLoader getInstance() throws IOException {
        if (instance == null) {
            synchronized(AppConfigFileLoader.class) {
                if (instance == null) {
                    instance = new AppConfigFileLoader();
                }
            }
        }
        return instance;
    }

}
