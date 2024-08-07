package pe.com.avivel.sistemas.guiaselectronicas.filesadapter.service.impl;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.BorderRadius;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.commons.empresa.EmpresaProperties;
import pe.com.avivel.sistemas.guiaselectronicas.commons.files.FileRoutes;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.DocumentoRelacionado;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.GuiaRemisionRemitente;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle.Articulo;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle.Conductor;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle.Vehiculo;

import javax.xml.xpath.XPathConstants;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class PdfGeneratorImp {
    public void pdfguia(GuiaRemisionRemitente guiaRemisionRemitente,String dest) throws Exception {

       dest= FileRoutes.PDF_FOLDER+dest;
        PdfWriter writer = new PdfWriter(dest);


        float marginLeft = 10;   // 0.5 inch
        float marginRight = 10;  // 0.5 inch
        float marginTop = 10;    // 1 inch
        float marginBottom = 10; // 1 inch
        // Creating a PdfDocument
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4.rotate());
        document.setMargins(marginLeft, marginRight, marginTop, marginBottom);
        document.setFontSize(8);


        // Primer bloque
        Table table = new Table(new float[]{80f, 280f,120f, 300f}); // 3 columnas con proporciones personalizadas
        table.setWidth(UnitValue.createPercentValue(100));// La tabla ocupa el 100% del ancho de la página

        // Añadir logo
        Image logo = new Image(ImageDataFactory.create( FileRoutes.LOGO_FOLDER+"logo.png"));


        // Ajustar el tamaño del logo
        float anchoMax = 100; // Ancho máximo en puntos
        float altoMax = 50; // Alto máximo en puntos
        logo.scaleToFit(anchoMax, altoMax);
        Cell cellLogo = new Cell().add(logo).setBorder(Border.NO_BORDER);
        table.addCell(cellLogo);

        // Añadir dirección
        Cell cellDireccion = new Cell().add(new Paragraph(  EmpresaProperties.getInstance().getRasonSocial() +"\n"+
                EmpresaProperties.getInstance().getDireccion() +"\n"+
                EmpresaProperties.getInstance().getDepartamento() +" - "+
                EmpresaProperties.getInstance().getProvincia() +" - "+
                EmpresaProperties.getInstance().getDistrito() )).setFontSize(8).setBorder(Border.NO_BORDER);
        table.addCell(cellDireccion);
        Table table_qr= new Table(new float[]{100, 100});
        String qrCodeFilePath =  FileRoutes.LOGO_FOLDER+"qrCode.png";
        this.generateQRCodeImage("https://e-factura.sunat.gob.pe/v1/contribuyente/gre/comprobantes/descargaqr?hashqr=PB9GHszJi9h3WYsVW00fVZdT5v1GYqWLfoQmeOsBx0ZPg+SVfiYcBnoeuNBAdX3nMaNfBntt+X4zjivr026pMLSFNibw1AKiTUMEcaFwb5cTTE5i0FEg/H/XmkvUyCbG", 70, 70, qrCodeFilePath);

        ImageData imageData = ImageDataFactory.create(qrCodeFilePath);
        Image qrCodeImage = new Image(imageData);
        Cell qr =new Cell().add(qrCodeImage).setBorder(Border.NO_BORDER);
        table_qr.addCell(qr).setBorder(Border.NO_BORDER);

        Image logo_osee = new Image(ImageDataFactory.create( FileRoutes.LOGO_FOLDER+"logo_ose.png"));
        float anchoMaxl = 100; // Ancho máximo en puntos
        float altoMaxl = 50; // Alto máximo en puntos
        logo_osee.scaleToFit(anchoMaxl, altoMaxl);
        Cell logo_ose= new Cell().add(logo_osee).setBorder(Border.NO_BORDER);

        table_qr.addCell(logo_ose).setBorder(Border.NO_BORDER);
        Cell cellDireccion2 = new Cell().add(table_qr).setBorder(Border.NO_BORDER);
        table.addCell(cellDireccion2);


        Div roundedRectDiv = new Div();
       // roundedRectDiv.setBackgroundColor(ColorConstants.LIGHT_GRAY); // Color de fondo
        roundedRectDiv.setBorder(new SolidBorder(ColorConstants.GRAY, 1));
        roundedRectDiv.setBorderRadius(new BorderRadius(12)); // Esquinas redondeadas
        roundedRectDiv.add(new Paragraph( "RUC:"+EmpresaProperties.getInstance().getRuc()).setFontSize(10));
        roundedRectDiv.add(new Paragraph("GUÍA DE REMISIÓN REMITENTE\n" +
                " ELECTRÓNICA\n").setTextAlignment(TextAlignment.CENTER).setFontSize(12));
        roundedRectDiv.add(new Paragraph("Nro:"+guiaRemisionRemitente.getNumeroSerie()+"\n").setTextAlignment(TextAlignment.CENTER).setFontSize(10));
        // Añadir rectángulo con número de guía
        Cell cellNumeroGuia = new Cell().add(roundedRectDiv).setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER); // Borde sólido para el rectángulo
        table.addCell(cellNumeroGuia);

        document.add(table);
       // document.add(new Paragraph("\n"));


        // Crear una tabla con 3 columnas
        Table table2 = new Table(new float[]{120, 280,120,280,350});

        // Añadir celdas a la tabla

        table2.addCell(new Cell().add(new Paragraph("Cliente:\nRUC: \nDirección:\nCuidad:")).setBorder(Border.NO_BORDER));
        table2.addCell(new Cell().add(new Paragraph(guiaRemisionRemitente.getDestinatario().getRazonSocial()+"\n"+
                guiaRemisionRemitente.getDestinatario().getNroDocumentoIdentidad()+"\n"+
                guiaRemisionRemitente.getDestinatario().getDireccion()+"\n"+
                guiaRemisionRemitente.getDestinatario().getDistrito().getDescripcion())).setBorder(Border.NO_BORDER));

        table2.addCell(new Cell().add(new Paragraph("Fecha Emisión:\nOrden pedido:\nOrden compra:")).setBorder(Border.NO_BORDER));
        table2.addCell(new Cell().add(new Paragraph(guiaRemisionRemitente.getFechaEmision()+"\n"+
                guiaRemisionRemitente.getOrdenPedido()+"\n"+
                guiaRemisionRemitente.getOrdenCompra())).setBorder(Border.NO_BORDER));
        String doc_relacion="";

        for (DocumentoRelacionado item : guiaRemisionRemitente.getDocumentoRelacionados()) {
           doc_relacion=doc_relacion+"\n"+item.getNroDocumento();
        }

        table2.addCell(new Cell().add(new Paragraph("Documentos Relacionados:"+doc_relacion)).setBorder(Border.NO_BORDER));


        // Crear un Div y añadir la tabla al Div
        Div divWithTable = new Div();
        divWithTable.add(table2).setWidth(UnitValue.createPercentValue(100)).setBorder(new SolidBorder(ColorConstants.GRAY, 1));

        // Añadir el Div al documento
        document.add(divWithTable);


        // Crear una tabla con 3 columnas
        Table table3 = new Table(new float[]{400, 280,280});

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        table3.addCell(new Cell().add(new Paragraph("DETALLE DE LA GUÍA:").setFont(font)).setBorder(Border.NO_BORDER));
        table3.addCell(new Cell().add(new Paragraph("PUNTO DE PARTIDA:").setFont(font)).setBorder(Border.NO_BORDER));
        table3.addCell(new Cell().add(new Paragraph("PUNTO DE LLEGADA:").setFont(font)).setBorder(Border.NO_BORDER));



        Table table4 = new Table(new float[]{400,400});
        table4.addCell(new Cell().add(new Paragraph("Modalidad de traslado:"+guiaRemisionRemitente.getEnvio().getMotivoTraslado().getDescripcion()+
                "\nUnidad Medida Peso Bruto:"+guiaRemisionRemitente.getEnvio().getUnidadMedidaPesoBruto().getDescripcion()+"\n"+
                "Peso bruto total de carga:"+guiaRemisionRemitente.getEnvio().getPesoBruto()+
                "\nMotivo del traslado:"+guiaRemisionRemitente.getEnvio().getMotivoTraslado().getDescripcion()).setFontSize(6)).setBorder(Border.NO_BORDER));

        table4.addCell(new Cell().add(new Paragraph("Fecha inicio de traslado:"+guiaRemisionRemitente.getEnvio().getFechaInicioTraslado()+
                "\nIndicador de trasnporte programado:NO\n"+
                "Indicador de retorno de vehiculo vacio:NO\n"+
                "Indicador de traslado en vehiculos de categoria M1 o L:NO\n"+
                "Indicador de retorno de vehiculo con envases o embalajes vacios:SI").setFontSize(6)).setBorder(Border.NO_BORDER));

        Div divDetalleGuia = new Div();
        divDetalleGuia.add(table4).setWidth(UnitValue.createPercentValue(100)).setBorder(new SolidBorder(ColorConstants.GRAY, 1));

        table3.addCell(new Cell().add(divDetalleGuia).setBorder(Border.NO_BORDER));

        Div divDetalleGuia2 = new Div();
        divDetalleGuia2.add(new Paragraph(":" + guiaRemisionRemitente.getPuntoPartida().getDireccion() + "\n" + guiaRemisionRemitente.getPuntoPartida().getDistrito().getDescripcion())
                        )
                .setWidth(UnitValue.createPercentValue(100))
                .setBorder(new SolidBorder(ColorConstants.GRAY, 1))
                .setPadding(5); // Agrega un espaciado interno de 10 unidades

        table3.addCell(new Cell().add(divDetalleGuia2).setBorder(Border.NO_BORDER));

        Div divDetalleGuia3 = new Div();
        if (guiaRemisionRemitente != null && guiaRemisionRemitente.getPuntoPartida() != null) {

            try {
                divDetalleGuia3.add(new Paragraph(":" + guiaRemisionRemitente.getPuntoLlegada().getDireccion() + "\n" + guiaRemisionRemitente.getPuntoLlegada().getDistrito().getDescripcion())
                                .setMarginBottom(5))
                        .setWidth(UnitValue.createPercentValue(100))
                        .setBorder(new SolidBorder(ColorConstants.GRAY, 1))
                        .setPadding(5); // Agrega un espaciado interno de 10 unidades
            }catch (Exception e){

            }

        }
        table3.addCell(new Cell().add(divDetalleGuia3).setBorder(Border.NO_BORDER));
        document.add(table3);

        // detalleproductos

        Table tableArticulos = new Table(new float[]{20, 40,50, 40,400, 40,40,40, 40,40});
            tableArticulos.setWidth(UnitValue.createPercentValue(100)).setFontSize(7);

            PdfFont font2 = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            tableArticulos.addCell(new Cell().add(new Paragraph("ITEM").setFont(font2)).setBackgroundColor(ColorConstants.LIGHT_GRAY).setFontColor(ColorConstants.WHITE).setBorder(new SolidBorder(ColorConstants.GRAY,1)).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
            tableArticulos.addCell(new Cell().add(new Paragraph("CANTIDAD").setFont(font2)).setBackgroundColor(ColorConstants.LIGHT_GRAY).setFontColor(ColorConstants.WHITE).setBorder(new SolidBorder(ColorConstants.GRAY,1)).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
            tableArticulos.addCell(new Cell().add(new Paragraph("UNIDAD DE MEDIDA").setFont(font2)).setBackgroundColor(ColorConstants.LIGHT_GRAY).setFontColor(ColorConstants.WHITE).setBorder(new SolidBorder(ColorConstants.GRAY,1)).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
            tableArticulos.addCell(new Cell().add(new Paragraph("CODIGO DEL BIEN").setFont(font2)).setBackgroundColor(ColorConstants.LIGHT_GRAY).setFontColor(ColorConstants.WHITE).setBorder(new SolidBorder(ColorConstants.GRAY,1)).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
            tableArticulos.addCell(new Cell().add(new Paragraph("DESCRIPCION DETALLADA").setFont(font2)).setBackgroundColor(ColorConstants.LIGHT_GRAY).setFontColor(ColorConstants.WHITE).setBorder(new SolidBorder(ColorConstants.GRAY,1)).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
            tableArticulos.addCell(new Cell().add(new Paragraph("BIEN NORMALIZADO").setFont(font2)).setBackgroundColor(ColorConstants.LIGHT_GRAY).setFontColor(ColorConstants.WHITE).setBorder(new SolidBorder(ColorConstants.GRAY,1)).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
            tableArticulos.addCell(new Cell().add(new Paragraph("PESO").setFont(font2)).setBackgroundColor(ColorConstants.LIGHT_GRAY).setFontColor(ColorConstants.WHITE).setBorder(new SolidBorder(ColorConstants.GRAY,1)).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
         tableArticulos.addCell(new Cell().add(new Paragraph("CODIGO PRODUCTO SUNAT").setFont(font2)).setBackgroundColor(ColorConstants.LIGHT_GRAY).setFontColor(ColorConstants.WHITE).setBorder(new SolidBorder(ColorConstants.GRAY,1)).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
            tableArticulos.addCell(new Cell().add(new Paragraph("PARTIDA ARANCELARIA").setFont(font2)).setBackgroundColor(ColorConstants.LIGHT_GRAY).setFontColor(ColorConstants.WHITE).setBorder(new SolidBorder(ColorConstants.GRAY,1)).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
            tableArticulos.addCell(new Cell().add(new Paragraph("CODIGO GTIN").setFont(font2)).setBackgroundColor(ColorConstants.LIGHT_GRAY).setFontColor(ColorConstants.WHITE).setBorder(new SolidBorder(ColorConstants.GRAY,1)).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
            Integer i=1;
            for (Articulo item: guiaRemisionRemitente.getArticulos()){
                tableArticulos.addCell(new Cell().add(new Paragraph(""+i)));
                tableArticulos.addCell(new Cell().add(new Paragraph(""+item.getCantidad())));
                tableArticulos.addCell(new Cell().add(new Paragraph(""+item.getUnidadMedida().getDescripcion())));
                tableArticulos.addCell(new Cell().add(new Paragraph(""+item.getCodigo())));
                tableArticulos.addCell(new Cell().add(new Paragraph(""+item.getDescripcion())));
                tableArticulos.addCell(new Cell().add(new Paragraph("No")));
                tableArticulos.addCell(new Cell().add(new Paragraph(""+item.getPeso())));
                tableArticulos.addCell(new Cell().add(new Paragraph("")));
                tableArticulos.addCell(new Cell().add(new Paragraph("")));
                tableArticulos.addCell(new Cell().add(new Paragraph("")));
                i+=i;
            }

        document.add(tableArticulos);

            //Observaciones
        document.add(new Paragraph("OBSERVACIONES").setFont(font2));
        document.add(new Paragraph(guiaRemisionRemitente.getObservacion()));
        document.add(new Paragraph("DATOS DEL CONDUCTOR").setFont(font2));
        Div datosconductor=new Div();
        datosconductor.setBorder(new SolidBorder(ColorConstants.GRAY, 1)).setWidth(UnitValue.createPercentValue(100));
        datosconductor.add(new Paragraph("  Pincipal:").setMultipliedLeading(0.5f).setPaddingLeft(10));
        for (Conductor con: guiaRemisionRemitente.getConductores() ){
            Paragraph parrafoDatosConductor = new Paragraph();

            // Añadir texto y estilos al párrafo
            parrafoDatosConductor.add(new Text("  Nro de documento: ").setFont(font2));
            parrafoDatosConductor.add(new Text(con.getNroDocumentoIdentidad()));
            parrafoDatosConductor.add(new Text("\t\t\tNro de Licencia: ").setFont(font2));
            parrafoDatosConductor.add(new Text(con.getNroLicenciaBrevete()));
            parrafoDatosConductor.add(new Text("\t\t\tNombres y Apellidos: ").setFont(font2));
            parrafoDatosConductor.add(new Text(con.getNombres() + " " + con.getApellidos()));

            // Añadir el párrafo a la lista datosconductor
            datosconductor.add(parrafoDatosConductor.setMultipliedLeading(0.2f).setPaddingLeft(10));
        }

        document.add(datosconductor);

        document.add(new Paragraph("DATOS DEL VEHÍCULO").setFont(font2));
        Div datosvehiculo=new Div();
        datosvehiculo.setBorder(new SolidBorder(ColorConstants.GRAY, 1)).setWidth(UnitValue.createPercentValue(100));
        datosvehiculo.add(new Paragraph("  Pincipal:").setMultipliedLeading(0.5f).setPaddingLeft(10));
        for (Vehiculo con: guiaRemisionRemitente.getVehiculos() ){
            Paragraph parrafoDatosConductor = new Paragraph();

            // Añadir texto y estilos al párrafo
            parrafoDatosConductor.add(new Text("  Placa: ").setFont(font2));
            parrafoDatosConductor.add(new Text(con.getNumeroPlaca()));


            // Añadir el párrafo a la lista datosconductor
            datosvehiculo.add(parrafoDatosConductor.setMultipliedLeading(0.2f).setPaddingLeft(10));
        }
        document.add(datosvehiculo);

document.add(new Paragraph(" Operador de Servicios Electrónicos\n" +
        " según Resolución N° 034-005-0008776").setTextAlignment(TextAlignment.CENTER));

document.add(new Paragraph("Representación impresa de la guía de remisión remitente, consulte en www.efact.pe\n" +
        " Autorizado mediante la Resolución de intendencia N° 0340050004177/SUNAT").setTextAlignment(TextAlignment.CENTER));

        // Close document
        document.close();
    }

    public static Path generateQRCodeImage(String text, int width, int height, String filePath) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        Path path = Path.of(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        return path;
    }
}
