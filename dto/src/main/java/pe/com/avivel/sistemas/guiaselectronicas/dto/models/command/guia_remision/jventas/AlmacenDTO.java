package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.jventas;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AlmacenDTO {

    private String nombre;
    private String codigoSunat;
    private String direccion;

    @NotBlank
    private String codigoUbigeo;
}
