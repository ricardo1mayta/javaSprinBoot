package pe.com.avivel.sistemas.guiaselectronicas.restapi.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.List;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.*;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailsService userDetailstService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthenticationExceptionHandler authenticationExceptionHandler;

    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    @Value("${spring.profiles.active}")
    private List<String> profiles;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailstService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .cors(Customizer.withDefaults())
                .addFilterBefore(jwtAuthorizationFilter, BasicAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider())
                .headers().frameOptions().disable().and()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationExceptionHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests(authorize -> {
                    authorize.antMatchers(HttpMethod.GET, URL_PUBLIC, URL_TEST).permitAll()
                            .antMatchers(HttpMethod.POST,URL_LOGIN, URL_LOGOUT).permitAll()
                            .antMatchers(HttpMethod.GET, URL_AUTH).authenticated()
                            .antMatchers(HttpMethod.POST, URL_AUTH).authenticated()
                            .antMatchers(HttpMethod.PUT, URL_AUTH).authenticated()
                            .antMatchers(HttpMethod.DELETE, URL_AUTH).authenticated()
                            .antMatchers(HttpMethod.PATCH, URL_AUTH).authenticated();
                    if(!profiles.contains("prod")){
                        authorize.antMatchers(HttpMethod.GET, SWAGGER_WHITELIST).permitAll();
                    }
                    authorize.anyRequest().denyAll();
                })
                .build();
    }

}
