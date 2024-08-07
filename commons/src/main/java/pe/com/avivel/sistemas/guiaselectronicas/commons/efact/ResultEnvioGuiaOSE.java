package pe.com.avivel.sistemas.guiaselectronicas.commons.efact;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResultEnvioGuiaOSE {

    private String ticket;
    private String message;
    private String status;
}
