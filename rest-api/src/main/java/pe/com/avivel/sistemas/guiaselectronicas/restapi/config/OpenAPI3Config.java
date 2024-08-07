package pe.com.avivel.sistemas.guiaselectronicas.restapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.Data;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springdoc.core.SwaggerUiOAuthProperties;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springdoc.webmvc.ui.SwaggerIndexTransformer;
import org.springdoc.webmvc.ui.SwaggerWelcomeCommon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "${app.server.nombre}", version = "${app.server.version}",
                contact = @Contact(name = "${app.contact.name}", email = "${app.contact.email}", url = "${app.contact.url}"),
                license = @License(name = "${app.license.name}", url = "${app.license.name}"), termsOfService = "${app.tos.uri}",
                description = "${app.server.description}"),
        security = { @SecurityRequirement(name = "Authorization") }
)
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.APIKEY,
        description = "Enter the token with the `Bearer: ` prefix, e.g. \"Bearer abcde12345\".",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPI3Config {

    @Value("${app.api.urlContextPath}")
    private String defaultServerUrl;

    @Bean
    @ConfigurationProperties(prefix = "springdoc.swagger-ui.servers")
    public List<ServerProperties> setValues() {
        return new ArrayList<>();
    }

    @Bean
    public OpenApiCustomiser openApiCustomiser(List<ServerProperties> serverProperties){
        List<ServerProperties> servers = new ArrayList<>(serverProperties);
        int index = IntStream.range(0, servers.size())
                .filter(i -> servers.get(i).getUrl().equals(defaultServerUrl))
                .findFirst()
                .orElse(-1);
        if(index > -1){
            ServerProperties defaultServer = servers.get(index);
            servers.remove(index);
            servers.add(0, defaultServer);
        }

        List<Server> serversOpenAPI = servers
                .stream()
                .map(s -> new Server().url(s.getUrl()).description(s.getDescription()))
                .collect(Collectors.toList());

        return openApi -> openApi.servers(serversOpenAPI);
    }

    @Profile("!prod")
    @Bean
    public SwaggerIndexTransformer swaggerIndexTransformer(
            SwaggerUiConfigProperties swaggerUiConfigProperties,
            SwaggerUiOAuthProperties swaggerUiOAuthProperties,
            SwaggerUiConfigParameters swaggerUiConfigParameters,
            SwaggerWelcomeCommon swaggerWelcomeCommon,
            ObjectMapperProvider objectMapperProvider) {
        return new SwaggerCssBlockTransformer(
                swaggerUiConfigProperties,
                swaggerUiOAuthProperties,
                swaggerUiConfigParameters,
                swaggerWelcomeCommon,
                objectMapperProvider);
    }

    @Data
    public static class ServerProperties {
        private String description;
        private String url;
    }
}
