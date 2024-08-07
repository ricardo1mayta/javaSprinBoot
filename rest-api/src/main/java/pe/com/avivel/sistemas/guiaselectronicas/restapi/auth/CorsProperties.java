package pe.com.avivel.sistemas.guiaselectronicas.restapi.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.cors")
@Data
public class CorsProperties {

    private List<String> allowedOrigins;

    private boolean originPattern;

    private List<String> allowedMethods;

    private List<String> allowedHeaders;

    private boolean allowedCredentials;

    private long maxAge;
}
