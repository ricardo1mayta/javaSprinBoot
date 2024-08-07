package pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.guia_remision_remitente;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum ModalidadesTraslado {
    PUBLICO(1),
    PRIVADO(2);

    private final Integer id;

    public static Optional<ModalidadesTraslado> valueOf(Integer id) {
        return Arrays.stream(values())
            .filter(mt -> mt.id.equals(id))
            .findFirst();
    }
}
