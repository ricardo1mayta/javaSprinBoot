package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.dto.annotations.CustomTypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.util.EntidadSimpleCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Conductor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ConductorCommandDTO {

    @Valid
    @NotNull
    private EntidadSimpleCommandDTO tipoDocumentoIdentidad;

    @Size(max = 15)
    @NotNull
    private String nroDocumentoIdentidad;

    @Size(max = 250)
    @NotNull
    private String apellidos;

    @Size(max = 250)
    @NotNull
    private String nombres;

    @Size(max = 10)
    @NotBlank
    private String nroLicenciaBrevete;

    @NotNull
    private boolean activo = true;

    @CustomTypeMap
    public static void registerTypeMap(ModelMapper modelMapper) {
        TypeMap<ConductorCommandDTO, Conductor> propertyMapper =
                modelMapper.createTypeMap(ConductorCommandDTO.class, Conductor.class);

        propertyMapper.setPreConverter(context -> {
            Conductor destination = context.getDestination();
            if(destination != null){
                destination.setTipoDocumentoIdentidad(null);
           }
            return destination;
        });
    }
}
