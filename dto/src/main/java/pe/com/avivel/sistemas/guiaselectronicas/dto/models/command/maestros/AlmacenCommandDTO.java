package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.dto.annotations.CustomTypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.util.EntidadSimpleCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Almacen;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlmacenCommandDTO {

    @Size(max = 6)
    @NotNull
    private String codigoSunat;

    @NotBlank
    private String descripcion;

    @Valid
    @NotNull
    private EntidadSimpleCommandDTO distrito;

    @Size(max = 500)
    @NotNull
    private String direccion;

    @Size(max = 500)
    @NotNull
    private String observacion;

    @NotNull
    private boolean activo = true;

    @CustomTypeMap
    public static void registerTypeMap(ModelMapper modelMapper) {
        TypeMap<AlmacenCommandDTO, Almacen> propertyMapper =
                modelMapper.createTypeMap(AlmacenCommandDTO.class, Almacen.class);

        propertyMapper.setPreConverter(context -> {
            Almacen destination = context.getDestination();
            if(destination != null){
                destination.setDistrito(null);
           }
            return destination;
        });
    }
}
