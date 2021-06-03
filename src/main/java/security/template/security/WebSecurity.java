package security.template.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import security.template.constants.SecurityConstants;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            /**
             * api for registration user permit all
             */
            .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
            /**
             * access to DB permit all
             */
            .antMatchers(SecurityConstants.ACCESS_TO_DB).permitAll()
            /**
             * all others should have authenticated
             */
            .anyRequest().authenticated();
        /**
         * clean headers after all request
         */
        http.headers().frameOptions().disable();
    }
}
