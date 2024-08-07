package pe.com.avivel.sistemas.guiaselectronicas.restapi.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.*;

@Component
@RequiredArgsConstructor
public class CorsConfig {

    private final CorsProperties corsProperties;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        final CorsConfiguration configurationAuth = new CorsConfiguration();

        if(corsProperties.isOriginPattern()){
            configurationAuth.setAllowedOriginPatterns(corsProperties.getAllowedOrigins());
        } else {
            configurationAuth.setAllowedOrigins(corsProperties.getAllowedOrigins());
        }

        configurationAuth.setAllowedMethods(corsProperties.getAllowedMethods());
        configurationAuth.setAllowedHeaders(corsProperties.getAllowedHeaders());
        configurationAuth.setAllowCredentials(corsProperties.isAllowedCredentials());
        configurationAuth.setMaxAge(corsProperties.getMaxAge());
        source.registerCorsConfiguration(URL_ALL, configurationAuth);

        return source;
    }

    @Bean
    public WebMvcConfigurer corsMappingConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                if(corsProperties.isOriginPattern()){
                    registry.addMapping(URL_ALL)
                            .allowedOriginPatterns(corsProperties.getAllowedOrigins().toArray(String[]::new))
                            .allowedMethods(corsProperties.getAllowedMethods().toArray(String[]::new))
                            .allowedHeaders(corsProperties.getAllowedHeaders().toArray(String[]::new))
                            .allowCredentials(corsProperties.isAllowedCredentials())
                            .maxAge(corsProperties.getMaxAge());
                } else {
                    registry.addMapping(URL_ALL)
                            .allowedOrigins(corsProperties.getAllowedOrigins().toArray(String[]::new))
                            .allowedMethods(corsProperties.getAllowedMethods().toArray(String[]::new))
                            .allowedHeaders(corsProperties.getAllowedHeaders().toArray(String[]::new))
                            .allowCredentials(corsProperties.isAllowedCredentials())
                            .maxAge(corsProperties.getMaxAge());
                }
            }
        };
    }
}
