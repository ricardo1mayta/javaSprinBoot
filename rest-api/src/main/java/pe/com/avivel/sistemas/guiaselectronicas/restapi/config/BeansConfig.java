package pe.com.avivel.sistemas.guiaselectronicas.restapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import pe.com.avivel.sistemas.guiaselectronicas.dto.annotations.CustomTypeMap;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Set;

@Slf4j
@Configuration
public class BeansConfig {
    public static String API_NOMBRE;

    public static String API_VERSION;

    @Value("${app.server.nombre}")
    public void setApiNombre(String value){
        API_NOMBRE = value;
    }

    @Value("${app.server.version}")
    public void setApiVersion(String value){
        API_VERSION = value;
    }

    @Value("${app.dto.package}")
    public String API_DTO_PACKAGE;

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
 //       org.modelmapper.config.Configuration configuration =  modelMapper.getConfiguration();
//        configuration.setAmbiguityIgnored(true);
//        configuration.setMethodAccessLevel(AccessLevel.PRIVATE);
//        configuration.setFieldAccessLevel(AccessLevel.PRIVATE);
        registrarTypeMaps(modelMapper);
        return modelMapper;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    @Bean
    public LocaleResolver localeResolver() {
        FixedLocaleResolver flr = new FixedLocaleResolver(); //SessionLocaleResolver
        flr.setDefaultLocale(new Locale("es", "PE"));
        return flr;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public Validator validator(){
        javax.validation.Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        Validator validator = factory.getValidator();
        factory.close();
        return validator;
    }

    private void registrarTypeMaps(ModelMapper modelMapper){
        Object[] parameters = new Object[] { modelMapper };

        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage(API_DTO_PACKAGE))
                        .setScanners(Scanners.MethodsAnnotated)
        );
        Set<Method> customTypeMaps = reflections.getMethodsAnnotatedWith(CustomTypeMap.class);

        for(Method method: customTypeMaps){
            String methodName = method.getDeclaringClass().getSimpleName() + " : "  + method.getName();
            String methodUbication = method.getDeclaringClass().getCanonicalName();
            try {
                method.invoke(null, parameters);
                log.info("Registro exitoso de TypeMap " +  methodName);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("No se pudo registrar el TypeMap de " + methodName + ", ubicado en :" + methodUbication, e);
            }
        }
    }
}
