package security.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }
    @Bean(name = "AppProperties")
    public AppProperties getAppProperties() {
        return new AppProperties();
    }
}
