package pe.com.avivel.sistemas.guiaselectronicas.dto.annotations;

import java.lang.annotation.*;

/**
 * Anotacion usada para registrar manualmente
 * los TypeMap utilizados en los mapeos del modelmapper
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CustomTypeMap {
}