package pe.com.avivel.sistemas.guiaselectronicas.commons.efact;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResultObtenerCDR {

    private String ticket;
    private String guiaSituacion;
    private String observacion;
    private Integer statusCode;
    private byte[] byteCDRFile;
    private boolean cdrObtenido=false;
    private String digestValue;
}
