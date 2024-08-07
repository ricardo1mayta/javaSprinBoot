package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.SerieSimpleQueryDTO;

import java.util.Date;

@Data
@NoArgsConstructor
public class UsuarioSerieQueryDTO {

    private Integer id;

    private SerieSimpleQueryDTO serie;

    private Date fechaIngreso;
}