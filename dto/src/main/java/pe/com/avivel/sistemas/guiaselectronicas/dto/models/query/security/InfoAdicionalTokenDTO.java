package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoAdicionalTokenDTO {

    public final static String USUARIO_ID =  "usuario_id";
    public final static String TOKEN_SESION =  "token_session";
    public final static String ROLES =  "authorities";

    @JsonProperty("token_session")
    private String tokenSession;

    @JsonProperty(USUARIO_ID)
    private Integer usuarioId;

    @JsonProperty("usuario_nombre")
    private String usuarioNombre;

    @JsonProperty("usuario_email")
    private String usuarioEmail;

    @JsonProperty("usuario_externo")
    private boolean usuarioExterno;

    @JsonProperty(ROLES)
    private List<String> roles;

    private List<MenuQueryDTO> menus;
}
