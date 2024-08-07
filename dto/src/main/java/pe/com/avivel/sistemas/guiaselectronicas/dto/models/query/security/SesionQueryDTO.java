package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class SesionQueryDTO {

    private Integer id;

    private String cliente;

    private String ip;

    private String pais;

    private String token;

    @JsonProperty("username")
    private String usuarioUsername;

    @JsonProperty("fechaCreacion")
    private Date tokenCreacion;

    @JsonProperty("fechaExpiracion")
    private Date tokenExpiracion;

    private boolean activo;
}
