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
        InputStream inputStream = AppConfig.class.getClassLoader().getResourceAsStream(filePath);

        if(inputStream == null){
            throw new FileNotFoundException("파일을 찾을 수 없습니다 - path : " + filePath);
        }

        BufferedReader bR = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";

        StringBuilder responseStrBuilder = new StringBuilder();
        while ((line = bR.readLine()) != null) {

            responseStrBuilder.append(line);
        }
        inputStream.close();

        return responseStrBuilder.toString();
    }
}
