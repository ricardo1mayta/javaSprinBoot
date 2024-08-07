package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.entidad;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.dto.annotations.CustomTypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.util.EntidadSimpleCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.entidad.Entidad;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class EntidadCommandDTO {

    @Valid
    @NotNull
    private EntidadSimpleCommandDTO tipoDocumentoIdentidad;

    @NotEmpty
    @Length(max = 15)
    private String nroDocumentoIdentidad;

    @NotEmpty
    @Length(max = 250)
    private String razonSocial;

    @NotNull
    @Length(max = 250)
    private String apellidos;

    @NotNull
    @Length(max = 250)
    private String nombres;

    @Valid
    @NotNull
    private EntidadSimpleCommandDTO distrito;

    @NotNull
    @Length(max = 500)
    private String direccion;

    @NotNull
    private boolean activo = true;

    @CustomTypeMap
    public static void registerTypeMap(ModelMapper modelMapper) {
        TypeMap<EntidadCommandDTO, Entidad> propertyMapper =
                modelMapper.createTypeMap(EntidadCommandDTO.class, Entidad.class);

        propertyMapper.setPreConverter(context -> {
            Entidad destination = context.getDestination();
            if(destination != null){
                destination.setTipoDocumentoIdentidad(null);
                destination.setDistrito(null);
           }
            return destination;
        });
    }
}
