package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioQueryDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SesionCommandDTO {

    @NotNull
    @Size(max = 255)
    private String cliente;

    @NotNull
    @Size(max = 255)
    private String ip;

    @NotNull
    @Size(max = 255)
    private String pais;

    @NotNull
    private UsuarioQueryDTO usuario;
}
