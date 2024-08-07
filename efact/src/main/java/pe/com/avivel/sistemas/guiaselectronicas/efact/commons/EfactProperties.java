package pe.com.avivel.sistemas.guiaselectronicas.efact.commons;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:efact.properties")
@Data
public class EfactProperties {

    @Value("${guias.efact.baseurl.api}")
    private String guiasEfactBaseUrlApi;

    @Value("${guias.efact.platform.username}")
    private String guiasEfactPlatformUsername;

    @Value("${guias.efact.platform.password}")
    private String guiasEfactPlatformPassword;
}
