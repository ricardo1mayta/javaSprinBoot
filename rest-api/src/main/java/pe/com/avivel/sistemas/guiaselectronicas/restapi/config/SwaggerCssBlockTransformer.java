package pe.com.avivel.sistemas.guiaselectronicas.restapi.config;

import org.springdoc.core.SwaggerUiConfigParameters;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springdoc.core.SwaggerUiOAuthProperties;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springdoc.webmvc.ui.SwaggerIndexPageTransformer;
import org.springdoc.webmvc.ui.SwaggerWelcomeCommon;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceTransformerChain;
import org.springframework.web.servlet.resource.TransformedResource;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class SwaggerCssBlockTransformer extends SwaggerIndexPageTransformer {

    public SwaggerCssBlockTransformer(SwaggerUiConfigProperties swaggerUiConfig, SwaggerUiOAuthProperties swaggerUiOAuthProperties, SwaggerUiConfigParameters swaggerUiConfigParameters, SwaggerWelcomeCommon swaggerWelcomeCommon, ObjectMapperProvider objectMapperProvider) {
        super(swaggerUiConfig, swaggerUiOAuthProperties, swaggerUiConfigParameters, swaggerWelcomeCommon, objectMapperProvider);
    }

    @SuppressWarnings("ExtractMethodRecommender")
    @Override
    public Resource transform(HttpServletRequest request,
                              Resource resource,
                              ResourceTransformerChain transformer)
            throws IOException {
        if (resource.toString().contains("swagger-ui.css")) {
            final InputStream is = resource.getInputStream();
            final InputStreamReader isr = new InputStreamReader(is);
            try (BufferedReader br = new BufferedReader(isr)) {
                final String css = br.lines().collect(Collectors.joining());
                final String cssNew = css
                        .replace(".swagger-ui img{max-width:100%}",
                        ".swagger-ui img{max-width:400px}")
                        .replace(".swagger-ui textarea{background:#fff;",
                                ".swagger-ui textarea{resize:vertical;background:#fff;")
                        .replace(".swagger-ui .opblock-body pre.microlight{",
                                ".swagger-ui .opblock-body pre.microlight{max-height: 400px;");
                final byte[] transformedContent = cssNew.getBytes();
                return  new TransformedResource(resource, transformedContent);
            } // AutoCloseable br > isr > is
        }
        return super.transform(request, resource, transformer);
    }
}
