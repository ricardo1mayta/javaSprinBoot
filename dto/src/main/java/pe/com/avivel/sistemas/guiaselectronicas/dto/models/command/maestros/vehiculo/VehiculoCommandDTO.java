package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.vehiculo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.dto.annotations.CustomTypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.util.EntidadSimpleCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.vehiculo.Vehiculo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehiculoCommandDTO {

    @NotNull
    @Size(max = 255)
    private String descripcion;

    @NotNull
    @Size(max = 8)
    private String placaPrincipal;

    @Size(max = 8)
    private String placaSecundaria;

    @Valid
    private EntidadSimpleCommandDTO marca;

    @NotNull
    @Size(max = 25)
    private String modelo;

    @Valid
    private EntidadSimpleCommandDTO categoria;

    @NotNull
    @Size(max = 50)
    private String entidadAutorizadora;

    @NotNull
    @Size(max = 50)
    private String nroAutorizacion;

    @NotNull
    private boolean activo = true;

    @CustomTypeMap
    public static void registerTypeMap(ModelMapper modelMapper) {
        TypeMap<VehiculoCommandDTO, Vehiculo> propertyMapper =
                modelMapper.createTypeMap(VehiculoCommandDTO.class, Vehiculo.class);

        propertyMapper.setPreConverter(context -> {
            Vehiculo destination = context.getDestination();
            if(destination != null){
                destination.setMarca(null);
                destination.setCategoria(null);
           }
            return destination;
        });
    }
}
