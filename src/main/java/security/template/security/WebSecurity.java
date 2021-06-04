package security.template.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import security.template.constants.SecurityConstants;
import security.template.repo.UserRepository;
import security.template.service.UserService;

import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

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
            .anyRequest().authenticated()
            .and()
            .addFilter(getAuthenticationFilter())
            /**
             * only authonticated user can do something in app
             */
            .addFilter(new AuthorizationFilter(authenticationManager(), userRepository));
        /**
         * clean headers after all request
         */
        http.headers().frameOptions().disable();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * login user now by link .../users/login
     */
    protected AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager(), userRepository);
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }

    /**
     * add cors
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
