package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.dto.annotations.CustomTypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.UnidadMedidaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Articulo;

import java.util.*;

@Slf4j
@Data
@NoArgsConstructor
public class ArticuloQueryDTO {

    private Integer id;

    private String codigo;

    private String descripcion;

    private List<UnidadMedidaQueryDTO> unidadMedidas;

    @JsonProperty("fechaRegistro")
    private Date creationDate;

    private boolean activo;

    public static final String TYPE_MAP_ARTICULOS_SIN_UNIDADES = "articulos_sin_unidades";

    @CustomTypeMap
    public static void registerTypeMapSinArticulos(ModelMapper modelMapper) {
        TypeMap<Articulo, ArticuloQueryDTO> propertyMapper =
                modelMapper.createTypeMap(Articulo.class, ArticuloQueryDTO.class, TYPE_MAP_ARTICULOS_SIN_UNIDADES);
        propertyMapper.addMappings(mapper -> mapper.skip(ArticuloQueryDTO::setUnidadMedidas));
    }
}
