package pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.guia_remision_remitente;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum MotivosTraslado {
    VENTA(10),
    COMPRA(11),
    VENTA_ENTREGA_TERCEROS(19),
    TRASLADO_ENTRE_ESTABLECIMIENTOS(12),
    CONSIGNACION(21),
    DEVOLUCION(20),
    RECOJO_BIENES_TRANSFORMADOS(22),
    OTROS(15),
    VENTA_SUJETA_CONFIRMACION(16),
    TRASLADO_BIENES_PARA_TRANSFORMACION(23),
    TRASLADO_EMISOR_ITINERANTE_CP(17);

    private final Integer id;

    public static Optional<MotivosTraslado> valueOf(Integer id) {
        return Arrays.stream(values())
            .filter(mt -> mt.id.equals(id))
            .findFirst();
    }
}
