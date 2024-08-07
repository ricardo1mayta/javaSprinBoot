package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
public class MenuQueryDTO {

    private Integer id;

    private String path;

    private String title;

    private String icon;

    @JsonProperty("class")
    private String clazz;

    private String badge;

    private String badgeClass;

    private boolean externalLink;

    private Integer orden;

    private Integer menuId;

    private List<MenuQueryDTO> subMenus;

    private boolean activo;

    private boolean visible;
}
