package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.entidad;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.dto.annotations.CustomTypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.util.EntidadSimpleCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.entidad.EntidadAlmacen;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class EntidadAlmacenCommandDTO {

    @Length(max = 255)
    @NotNull
    private String descripcion;

    @Valid
    @NotNull
    private EntidadSimpleCommandDTO distrito;

    @NotNull
    @Length(max = 500)
    private String direccion;

    @Positive
    @NotNull
    private Integer entidadId;

    @NotNull
    private boolean activo = true;

    @CustomTypeMap
    public static void registerTypeMap(ModelMapper modelMapper) {
        TypeMap<EntidadAlmacenCommandDTO, EntidadAlmacen> propertyMapper =
                modelMapper.createTypeMap(EntidadAlmacenCommandDTO.class, EntidadAlmacen.class);

        propertyMapper.setPreConverter(context -> {
            EntidadAlmacen destination = context.getDestination();
            if(destination != null){
                destination.setDistrito(null);
           }
            return destination;
        });

        propertyMapper.addMappings(mapper -> mapper.skip(EntidadAlmacen::setId));
    }
}
