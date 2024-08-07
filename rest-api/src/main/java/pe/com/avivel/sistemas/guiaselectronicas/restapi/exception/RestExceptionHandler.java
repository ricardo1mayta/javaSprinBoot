package pe.com.avivel.sistemas.guiaselectronicas.restapi.exception;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.DeleteEntityException;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.FileException;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.ReportException;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.ValidationException;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        for (ObjectError error : exception.getBindingResult().getGlobalErrors()) {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        }
        errors = errors.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return buildErrorResponse(
                errors,
                "Error al validar los argumentos de la peticion",
                "ArgumentsNotValid",
                exception, HttpStatus.BAD_REQUEST, request
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        return buildErrorResponse(
                "Error Interno", "InternalException",
                exception, HttpStatus.CONFLICT, request
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception,
                                                                 WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> error : exception.getConstraintViolations()) {
            errors.put(error.getPropertyPath().toString(), error.getMessage());
        }

        errors = errors.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return buildErrorResponse(
                errors,
                "Error al validar los argumentos de la peticion",
                "ConstraintViolationException",
                exception, HttpStatus.BAD_REQUEST, request
        );
    }

    @ExceptionHandler(JwtException.class)
    private ResponseEntity<?> handleJwtException(JwtException exception,
                                                 WebRequest request) {
        return buildErrorResponse(
                exception.getMessage(),
                "Error al validar el JWT", "JwtException",
                exception, HttpStatus.UNAUTHORIZED, request
        );
    }

    @ExceptionHandler(DataAccessException.class)
    private ResponseEntity<?> handleDataAccessException(DataAccessException exception,
                                                        WebRequest request) {
        String error = (exception.getMessage() != null ? exception.getMessage() : "" ) + " : ";
        error += exception.getMostSpecificCause().toString();
        return buildErrorResponse(
                "Error al actualizar los datos en la BD.",
                error, "DataAccessException",
                exception, HttpStatus.INTERNAL_SERVER_ERROR, request
        );
    }

    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<?> handleValidationException(ValidationException exception,
                                                        WebRequest request) {
        return buildErrorResponse(
                exception.getMessage(),
                "Error al validar los datos.", "ValidationException",
                exception, HttpStatus.BAD_REQUEST, request
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<?> handleEntityNotFound(EntityNotFoundException exception,
                                                   WebRequest request) {
        return buildErrorResponse(
                "Entity no encontrado, consulte con soporte.", "EntityNotFound",
                exception, HttpStatus.NOT_FOUND, request
        );
    }

    @ExceptionHandler(PersistenceException.class)
    private ResponseEntity<?> handlePersistence(PersistenceException exception,
                                                WebRequest request) {
        return buildErrorResponse(
                "Error en Base de Datos, consulte con soporte.", "Persistence",
                exception, HttpStatus.INTERNAL_SERVER_ERROR, request
        );
    }

    @ExceptionHandler(DeleteEntityException.class)
    private ResponseEntity<?> handleDeleteEntity(DeleteEntityException exception,
                                                 WebRequest request) {
        return buildErrorResponse(
                exception.getMessage(),
          "Error al eliminar el registro.", "DeleteEntity",
                exception, HttpStatus.INTERNAL_SERVER_ERROR, request
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception,
                                                          WebRequest request) {
        return buildErrorResponse(
                "No se cuenta con los permisos necesarios.", "AccessDeniedException",
                exception, HttpStatus.UNAUTHORIZED, request
        );
    }

    @ExceptionHandler(FileException.class)
    private ResponseEntity<?> handleFileException(FileException exception,
                                                  WebRequest request) {
        return buildErrorResponse(
                exception.getMessage(),
                "Error al procesar un archivo.", "FileException",
                exception, HttpStatus.INTERNAL_SERVER_ERROR, request
        );
    }

    @ExceptionHandler(ReportException.class)
    private ResponseEntity<?> handleReportException(ReportException exception,
                                                    WebRequest request) {
        return buildErrorResponse(
                exception.getMessage(),
                "Error al generar el reporte de Jasper Report.", "ReportException",
                exception, HttpStatus.INTERNAL_SERVER_ERROR, request
        );
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<?> handleGenericException(Exception exception,
                                                     WebRequest request) {
        return buildErrorResponse(
                "Error no controlado.", "GenericException",
                exception, HttpStatus.INTERNAL_SERVER_ERROR, request
        );
    }

    private ResponseEntity<Object> buildErrorResponse(
            String message,
            String typeException,
            Exception exception,
            HttpStatus httpStatus,
            @SuppressWarnings("unused") WebRequest request
    ) {
        return buildErrorResponse(null, message, typeException, exception, httpStatus, request);
    }

    private ResponseEntity<Object> buildErrorResponse(
            Object data,
            String message,
            String typeException,
            Exception exception,
            HttpStatus httpStatus,
            WebRequest request
    ) {
        log.info("requestDescription : " + request.getDescription(true));
        log.error("### handle " + typeException + " <- {0}", exception);

        return new ResponseEntity<>(
                new ResponseData<>(
                        data,
                        message,
                        typeException
                ), httpStatus);
    }
}