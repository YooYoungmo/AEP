package aep;

import java.io.*;

/**
 * <pre>
 * 정해진 경로(conf/app-config.json)의 파일을 읽어 들여서 텍스트로 변환한다.
 *
 * 해당 클래스는 Singleton Pattern(하위 문서 참조)으로 설계하였으며,
 * 이유는 파일을 두번 읽어들이지 않도록 하기 위함.
 *
 * 따라서 객체가 생성 된 이후에 파일이 변경되어도 파일의 변경 분은 반영되지 않음.
 * </pre>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Singleton_pattern">Singleton Pattern</a>
 */
public class AppConfigFileLoader {
    private static final String CONFIG_ROOT_DEFAULT_FILE_PATH = "conf/app-config.json";
    private static volatile AppConfigFileLoader instance = null;
    private final String configText;

    /**
     * Singleton Pattern 이기 때문에 외부에서 {@link AppConfigFileLoader#getInstance()}를 사용할 것.
     *
     * @throws IOException 정해진 경로(conf/app-config.json)에 파일을 찾을 수 없거나 오류가 발생하는 경우 발생
     */
    private AppConfigFileLoader() throws IOException {
        configText = initConfigText();
    }

    /**
     * 정해진 경로의 파일 읽어 들여 이를 문자열로 반환 한다.
     *
     * @return 정햐진 경로의 파일의 텍스트
     * @throws IOException 정해진 경로(conf/app-config.json)에 파일을 찾을 수 없거나 오류가 발생하는 경우 발생
     */
    private String initConfigText() throws IOException {
        String configText;
        InputStream configStream = AppConfig.class.getClassLoader().getResourceAsStream(CONFIG_ROOT_DEFAULT_FILE_PATH);

        if(configStream == null) {
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
        return configText;
    }

    /**
     * AppConfig 파일을 텍스트로 반환한다.
     *
     * @return AppConfig 텍스트
     * @throws IOException
     */
    public String getText() throws IOException {
        return configText;
    }

    /**
     * {@link AppConfigFileLoader} 인스턴스(instance)를 반환 한다.
     *
     * @return {@link AppConfigFileLoader} 인스턴스
     * @throws IOException 정해진 경로(conf/app-config.json)에 파일을 찾을 수 없거나 오류가 발생하는 경우 발생
     */
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
