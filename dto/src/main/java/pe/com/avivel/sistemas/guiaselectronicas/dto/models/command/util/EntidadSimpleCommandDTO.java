package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntidadSimpleCommandDTO {

    @Min(value = 0)
//    @NotNull
    private Integer id;
}
