package pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TiposGuia {
    REMITENTE(1),
    TRANSPORTISTA(2);

    private final Integer id;
}
