package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.dto.annotations.CustomTypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.Usuario;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class UsuarioUpdateDTO implements Serializable {

    private static final long serialVersionUID = -2149191583467596896L;

    @NotBlank
    private String username;

    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String apellidos;

    @NotBlank
    private String nombres;

    @NotNull
    private boolean usuarioExterno;

    @CustomTypeMap
    public static void registerTypeMap(ModelMapper modelMapper) {
        TypeMap<UsuarioUpdateDTO, Usuario> propertyMapper = modelMapper.createTypeMap(UsuarioUpdateDTO.class, Usuario.class);
        propertyMapper.addMappings(mapper -> mapper.skip(Usuario::setPassword));
    }
}
