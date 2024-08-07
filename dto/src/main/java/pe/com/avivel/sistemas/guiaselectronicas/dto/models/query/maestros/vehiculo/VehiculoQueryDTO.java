package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehiculoQueryDTO {

    private Integer id;

    private String descripcion;

    private String placaPrincipal;

    private String placaSecundaria;

    private MarcaQueryDTO marca;

    private String modelo;

    private CategoriaQueryDTO categoria;

    private String entidadAutorizadora;

    private String nroAutorizacion;

    private boolean activo;
}
