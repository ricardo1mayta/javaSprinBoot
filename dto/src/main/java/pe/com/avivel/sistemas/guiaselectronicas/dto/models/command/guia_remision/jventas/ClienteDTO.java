package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.jventas;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteDTO {

    private String tipoDocumentoCodigoSunat;
    private String numeroDocumentoIdentidad;
    private String razonSocial;
    private String direccion;
    private String clienteDistrito;
}
