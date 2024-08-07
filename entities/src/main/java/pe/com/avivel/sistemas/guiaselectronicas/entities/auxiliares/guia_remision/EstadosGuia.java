package pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum EstadosGuia {
    NO_ENVIADO(1,"No enviado", "La guia fue creada exitosamente."),
    ENVIADO(2,"Enviado", "La guia fue enviada a efact exitosamente, " +
            "por favor vuelva a descargar el CDR para ver el resultado de la validación."),
    VALIDANDO(3,"Validando", "La guia se esta validando en los servidores de EFACT," +
            " por favor descargue el CDR para ver el resultado de la validación."),
    ACEPTADO(4,"Aceptado", "La guia fue aceptada exitosamenteen EFACT."),
    ERROR(5,"Error", "Se han detectado errores al validar la guia en EFACT. " +
            "Por favor corrijalos antes de volver a enviar."),
    BAJA(6,"Baja", "La guia fue dada de baja exitosamente."),
    DESCONOCIDO(7,"Desconocido","Se ha producido un error desconocido al procesar" +
            " la guia en los servidores de EFACT. Vuelva a intentar.");

    private final Integer id;

    private final String descripcion;

    private final String mensaje;

    public static Optional<EstadosGuia> valueOf(Integer id) {
        return Arrays.stream(values())
            .filter(e -> e.id.equals(id))
            .findFirst();
    }
}
