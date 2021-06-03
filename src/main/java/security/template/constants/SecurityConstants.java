package security.template.constants;


import security.template.config.AppProperties;
import security.template.config.SpringApplicationContext;

public class SecurityConstants {
    public static final String SIGN_UP_URL = "/users/v1";
    public static final String ACCESS_TO_DB = "/h2-console/**";
    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * @return tokenSecret from application. properties file
     */
    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
