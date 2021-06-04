package security.template.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class
AppProperties {

    @Autowired
    private Environment env;

    public String getTokenSecret() {
        return env.getProperty("tokenSecret");}

    public String getAdminCode() {return env.getProperty("admin.code");}

    public String getPort() {return env.getProperty("server.port");}
}
