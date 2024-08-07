package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.entidad;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.dto.annotations.CustomTypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.entidad.EntidadCorreo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class EntidadCorreoCommandDTO {

    @Email
    @Length(max = 255)
    @NotNull
    private String correo;

    @Positive
    @NotNull
    private Integer entidadId;

    @NotNull
    private boolean activo = true;

    @CustomTypeMap
    public static void registerTypeMap(ModelMapper modelMapper) {
        TypeMap<EntidadCorreoCommandDTO, EntidadCorreo> propertyMapper =
                modelMapper.createTypeMap(EntidadCorreoCommandDTO.class, EntidadCorreo.class);
        propertyMapper.addMappings(mapper -> mapper.skip(EntidadCorreo::setId));
    }
}
