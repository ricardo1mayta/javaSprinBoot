package pe.com.avivel.sistemas.guiaselectronicas.service.impl.guia_remision.guia_remision_remitente;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.guiaselectronicas.commons.efact.ResultEnvioGuiaOSE;
import pe.com.avivel.sistemas.guiaselectronicas.commons.efact.ResultObtenerCDR;
import pe.com.avivel.sistemas.guiaselectronicas.commons.empresa.EmpresaProperties;
import pe.com.avivel.sistemas.guiaselectronicas.commons.files.FileNameAwareByteArrayResource;
import pe.com.avivel.sistemas.guiaselectronicas.commons.files.TypeFile;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.CorreoDestinatarioCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.DocumentoRelacionadoCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.categoria_vehiculo.GroupCategoriaDefault;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.categoria_vehiculo.GroupCategoriaM1L;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.modalidad_traslado.GroupTrasladoPrivado;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.modalidad_traslado.GroupTrasladoPrivadoCategoriaDefault;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.modalidad_traslado.GroupTrasladoPublico;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.modalidad_traslado.GroupTrasladoPublicoCategoriaDefault;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.motivo_traslado.*;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.guia_remision_remitente.detalle.DetalleArticuloCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.guia_remision_remitente.detalle.DetalleConductorCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.guia_remision_remitente.detalle.DetalleVehiculoCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteDetalladoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.ResultEmisionQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.ResultObtenerCdrQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.efact.exceptions.EfactException;
import pe.com.avivel.sistemas.guiaselectronicas.efact.service.GuiasElectronicasEfact;
import pe.com.avivel.sistemas.guiaselectronicas.efact.service.LectorXML;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.EstadoGuia;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.EstadosGuia;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.TipoGuia;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.guia_remision_remitente.ModalidadesTraslado;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.guia_remision_remitente.MotivosTraslado;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.CorreoDestinatario;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.DocumentoRelacionado;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.LogEstado;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.DetalleGuiaRemisionRemitente;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.GuiaRemisionRemitente;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle.Articulo;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle.Conductor;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle.Vehiculo;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Serie;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.Usuario;
import pe.com.avivel.sistemas.guiaselectronicas.filesadapter.exceptions.FilesAdapterException;
import pe.com.avivel.sistemas.guiaselectronicas.filesadapter.exceptions.XMLGuiaRemisionException;
import pe.com.avivel.sistemas.guiaselectronicas.filesadapter.service.FileManager;
import pe.com.avivel.sistemas.guiaselectronicas.filesadapter.service.XMLGuiaRemision;
import pe.com.avivel.sistemas.guiaselectronicas.filesadapter.service.impl.PdfGeneratorImp;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision.EstadoGuiaRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision.TipoGuiaRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.GuiaRemisionCorreoDestinatarioRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.GuiaRemisionDocumentoRelacionadoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.GuiaRemisionRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.guia_remision_remitente.detalle.GuiaRemisionRemitenteArticuloRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.guia_remision_remitente.detalle.GuiaRemisionRemitenteConductorRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.guia_remision_remitente.detalle.GuiaRemisionRemitenteVehiculoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.SerieRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.FileException;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.ValidationException;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteCommandService;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.SerieQueryService;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.UsuarioQueryService;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.util.EmailService;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static pe.com.avivel.sistemas.guiaselectronicas.service.impl.ConstansResponse.*;
import static pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.EstadosGuia.*;
import static pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.TiposGuia.*;
import static pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.guia_remision_remitente.ModalidadesTraslado.*;
import static pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.guia_remision_remitente.MotivosTraslado.*;

@SuppressWarnings("ImplicitSubclassInspection")
@Slf4j
@Service
@RequiredArgsConstructor
public class GuiaRemisionRemitenteCommandServiceImpl implements GuiaRemisionRemitenteCommandService {

    private final GuiaRemisionRepository guiaRemisionRepository;

    private final GuiaRemisionRemitenteRepository guiaRemisionRemitenteRepository;

    private final GuiaRemisionDocumentoRelacionadoRepository guiaRemisionDocumentoRelacionadoRepository;

    private final GuiaRemisionCorreoDestinatarioRepository guiaRemisionCorreoDestinatarioRepository;

    private final GuiaRemisionRemitenteConductorRepository guiaRemisionRemitenteConductorRepository;

    private final GuiaRemisionRemitenteVehiculoRepository guiaRemisionRemitenteVehiculoRepository;

    private final GuiaRemisionRemitenteArticuloRepository guiaRemisionRemitenteArticuloRepository;

    private final TipoGuiaRepository tipoGuiaRepository;

    private final SerieRepository serieRepository;

    private final EstadoGuiaRepository estadoGuiaRepository;

    private final UsuarioQueryService usuarioQueryService;

    private final SerieQueryService serieQueryService;

    private final Validator validator;

    private final ModelMapper modelMapper;

    private final XMLGuiaRemision xmlGuiaRemision;

    private final GuiasElectronicasEfact guiasElectronicasEfact;

    private final FileManager fileManager;

    private final EmailService emailService;
private final PdfGeneratorImp pdfGenerator;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GuiaRemisionRemitenteDetalladoQueryDTO create(GuiaRemisionRemitenteCommandDTO dto) {
        this.validarGuiaRemisionRemitente(null, dto);

        GuiaRemisionRemitente guiaRemisionRemitente = modelMapper.map(dto, GuiaRemisionRemitente.class);

        TipoGuia tipoGuia = tipoGuiaRepository.findById(REMITENTE.getId())
                .orElseThrow(() -> new ValidationException(TipoGuiaResponse.NO_ENCONTRADO));

        guiaRemisionRemitente.setTipoGuia(tipoGuia);

        this.actualizarEstadoGuia(guiaRemisionRemitente, NO_ENVIADO.getId());

        guiaRemisionRemitente = guiaRemisionRemitenteRepository.save(guiaRemisionRemitente);
        this.guardarListasAnidadas(guiaRemisionRemitente, dto);

        return modelMapper.map(guiaRemisionRemitente, GuiaRemisionRemitenteDetalladoQueryDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GuiaRemisionRemitenteDetalladoQueryDTO update(Integer id, GuiaRemisionRemitenteCommandDTO dto) {
        this.validarGuiaRemisionRemitente(id, dto);

        GuiaRemisionRemitente guiaRemisionRemitente = guiaRemisionRemitenteRepository.findById(id)
                .orElseThrow(() -> new ValidationException(GuiaRemisionRemitenteResponse.NO_ENCONTRADO));

        modelMapper.map(dto, guiaRemisionRemitente);

        GuiaRemisionRemitente guiaRemisionRemitenteUpdate = guiaRemisionRemitenteRepository.save(guiaRemisionRemitente);
        this.guardarListasAnidadas(guiaRemisionRemitenteUpdate, dto);

        return modelMapper.map(guiaRemisionRemitenteUpdate, GuiaRemisionRemitenteDetalladoQueryDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void anular(Integer id) {
        GuiaRemisionRemitente guiaRemisionRemitente = guiaRemisionRemitenteRepository.findById(id)
                .orElseThrow(() -> new ValidationException(GuiaRemisionRemitenteResponse.NO_ENCONTRADO));

        Integer estadoId = guiaRemisionRemitente.getEstado().getId();

        if(estadoId.equals(BAJA.getId())){
            throw new ValidationException(GuiaRemisionRemitenteResponse.DADO_BAJA);
        }

        this.actualizarEstadoGuia(guiaRemisionRemitente, BAJA.getId());

        guiaRemisionRemitenteRepository.save(guiaRemisionRemitente);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultEmisionQueryDTO emitir(Integer id) {
        GuiaRemisionRemitente guiaRemisionRemitente = guiaRemisionRemitenteRepository.findById(id)
                .orElseThrow(() -> new ValidationException(GuiaRemisionRemitenteResponse.NO_ENCONTRADO));

        //Validar estado de guia
        Integer estadoId = guiaRemisionRemitente.getEstado().getId();
        if(!Stream.of(NO_ENVIADO, ERROR, DESCONOCIDO).map(EstadosGuia::getId)
                .collect(Collectors.toList()).contains(estadoId) ){
            throw new ValidationException(GuiaRemisionRemitenteResponse.ACTUALIZACION_EMISION_DENEGADA);
        }

        this.validarPermisosSobreSerie(guiaRemisionRemitente.getSerie());

        //Generar XML
        UsuarioQueryDTO usuario = this.usuarioQueryService.findUsuarioQueryActual();
        guiaRemisionRemitente.setFechaEmision(new Date());
        guiaRemisionRemitente.setUsuarioEmision(usuario.getNombreCompleto());

        try{
            xmlGuiaRemision.generarXML(guiaRemisionRemitente);
        } catch (XMLGuiaRemisionException e){
            throw new FileException(e.getMessage(),e);
        }

       // this.actualizarEstadoGuia(guiaRemisionRemitente, ENVIADO.getId());

        //Enviar XML a EFACT
        ResultEnvioGuiaOSE resultEnvioGuiaOSE;

        try{
            String xmlFileName = guiaRemisionRemitente.getNombreGuiaElectronica(TypeFile.XML_GUIA_UNSIGNED);

            resultEnvioGuiaOSE = guiasElectronicasEfact.enviarGuiaRemision(xmlFileName);

            if(resultEnvioGuiaOSE.getTicket() == null){
                throw new EfactException(resultEnvioGuiaOSE.getMessage());
            }

        } catch (EfactException e){
            throw new FileException(e.getMessage(), e);
        }

        //Actualizar estado a Enviado y guardar ticket
        guiaRemisionRemitente.setEfactTicket(resultEnvioGuiaOSE.getTicket());
        this.actualizarEstadoGuia(guiaRemisionRemitente, ENVIADO.getId());
        guiaRemisionRemitenteRepository.save(guiaRemisionRemitente);

        //Obtener CDR
        ResultObtenerCdrQueryDTO resultObtenerCdrQueryDTO;

        try{
            resultObtenerCdrQueryDTO = this.obtenerCdr(guiaRemisionRemitente);
            //log.info("ok_aquiIIIIIIi______________------------------------------------");

        } catch (Exception e){
            return ResultEmisionQueryDTO
                    .builder()
                    .estadoId(guiaRemisionRemitente.getEstado().getId())
                    .mensaje(String.format(GuiaRemisionRemitenteResponse.GUIA_ENVIADA_ERROR_CDR, e.getMessage()))
                    .build();
        }

        if(resultObtenerCdrQueryDTO.getResource() == null){
            EstadosGuia estado = EstadosGuia.valueOf(resultObtenerCdrQueryDTO.getEstadoId()).orElseThrow();

            return ResultEmisionQueryDTO
                    .builder()
                    .estadoId(guiaRemisionRemitente.getEstado().getId())
                    .mensaje(estado.getMensaje())
                    .build();
        }

        //Enviar correos asociados
        if(!guiaRemisionRemitente.getCorreoDestinatarios().isEmpty()){
            String[] correos = guiaRemisionRemitente.getCorreoDestinatarios()
                    .stream()
                    .map(CorreoDestinatario::getCorreo)
                    .toArray(String[]::new);

            ExecutorService executorService = Executors.newFixedThreadPool(2);
            executorService.submit(() -> {
                try {
                    this.enviarCorreo(guiaRemisionRemitente, correos);
                } catch (MessagingException | UnsupportedEncodingException e) {
                    //throw new RuntimeException(e);
                    log.error("Error al enviar correo de guia " + guiaRemisionRemitente.getNumeroSerie(), e);
                }
            });
        }

        return ResultEmisionQueryDTO
                .builder()
                .estadoId(guiaRemisionRemitente.getEstado().getId())
                .mensaje(ACEPTADO.getMensaje())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultObtenerCdrQueryDTO obtenerCdr(Integer id) {
        GuiaRemisionRemitente guiaRemisionRemitente = guiaRemisionRemitenteRepository.findById(id)
                .orElseThrow(() -> new ValidationException(GuiaRemisionRemitenteResponse.NO_ENCONTRADO));
        return this.obtenerCdr(guiaRemisionRemitente);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileNameAwareByteArrayResource obtenerXml(Integer id) {
        GuiaRemisionRemitente guiaRemisionRemitente = guiaRemisionRemitenteRepository.findById(id)
                .orElseThrow(() -> new ValidationException(GuiaRemisionRemitenteResponse.NO_ENCONTRADO));
        return this.obtenerXml(guiaRemisionRemitente);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileNameAwareByteArrayResource obtenerPdf(Integer id) {
        GuiaRemisionRemitente guiaRemisionRemitente = guiaRemisionRemitenteRepository.findById(id)
                .orElseThrow(() -> new ValidationException(GuiaRemisionRemitenteResponse.NO_ENCONTRADO));
        return this.obtenerPdf(guiaRemisionRemitente);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileNameAwareByteArrayResource obtenerPdfNuevo(Integer id) throws Exception {
        GuiaRemisionRemitente guiaRemisionRemitente = guiaRemisionRemitenteRepository.findById(id)
                .orElseThrow(() -> new ValidationException(GuiaRemisionRemitenteResponse.NO_ENCONTRADO));
        return this.obtenerPdfNuevo(guiaRemisionRemitente);
    }

    @Override
    public FileNameAwareByteArrayResource obtenerPdfMasivo(List<Integer> ids) {
        List<FileNameAwareByteArrayResource> pdfs = new ArrayList<>();

        for (Integer id : ids){
            GuiaRemisionRemitente guiaRemisionRemitente = guiaRemisionRemitenteRepository.findById(id)
                    .orElseThrow(() -> new ValidationException(GuiaRemisionRemitenteResponse.NO_ENCONTRADO));
            FileNameAwareByteArrayResource pdf = this.obtenerPdf(guiaRemisionRemitente);
            pdfs.add(pdf);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (FileNameAwareByteArrayResource pdf : pdfs) {
                ZipEntry entry = new ZipEntry(pdf.getFilename());
                zos.putNextEntry(entry);
                zos.write(pdf.getByteArray());
                zos.closeEntry();
            }
        } catch(IOException ex) {
            throw new FileException(ex.getMessage(), ex);
        }

        return new FileNameAwareByteArrayResource("pdfs.zip", baos.toByteArray());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enviarCorreo(Integer id, String[] correos) throws MessagingException, UnsupportedEncodingException {
        GuiaRemisionRemitente guiaRemisionRemitente = guiaRemisionRemitenteRepository.findById(id)
                .orElseThrow(() -> new ValidationException(GuiaRemisionRemitenteResponse.NO_ENCONTRADO));
        this.enviarCorreo(guiaRemisionRemitente, correos);
    }

    @Transactional(rollbackFor = Exception.class)
    private ResultObtenerCdrQueryDTO obtenerCdr(GuiaRemisionRemitente guiaRemisionRemitente){


        if(guiaRemisionRemitente.getEstado().getId().equals(BAJA.getId())){
            throw new ValidationException(GuiaRemisionRemitenteResponse.DADO_BAJA);
        }

        if(guiaRemisionRemitente.getEfactTicket() == null ){
            throw new ValidationException(GuiaRemisionRemitenteResponse.NO_ENVIADO_EFACT);
        }

        String fileName = guiaRemisionRemitente.getNombreGuiaElectronica(TypeFile.CDR_GUIA);
        if(guiaRemisionRemitente.isEfactCdrDescargado()){
            try {
                byte[] bytes = fileManager.downloadFile(fileName, TypeFile.CDR_GUIA);
                return ResultObtenerCdrQueryDTO
                        .builder()
                        .mensaje(ENCONTRADO)
                        .estadoId(guiaRemisionRemitente.getEstado().getId())
                        .resource(new FileNameAwareByteArrayResource(fileName, bytes))
                        .build();
            } catch (FilesAdapterException e) {
                log.error("Error al obtener el cdr de carpeta local", e);
                return ResultObtenerCdrQueryDTO
                        .builder()
                        .estadoId(guiaRemisionRemitente.getEstado().getId())
                        .mensaje(e.getMessage())
                        .build();
            }
        }

        this.validarPermisosSobreSerie(guiaRemisionRemitente.getSerie());

        ResultObtenerCDR resultCdr;

        try {
            resultCdr = guiasElectronicasEfact.obtenerCDR(guiaRemisionRemitente.getEfactTicket(), guiaRemisionRemitente);

        } catch (EfactException ex){
            log.error("Error al obtener cdr de EFACT", ex);
            return ResultObtenerCdrQueryDTO
                    .builder()
                    .estadoId(guiaRemisionRemitente.getEstado().getId())
                    .mensaje(ex.getMessage())
                    .build();
        }

        ResultObtenerCdrQueryDTO result;
        log.info("STATUS CDR:-----------------------------------:::::"+resultCdr.getStatusCode() );
        log.info("MENSAGE CDR:-----------------------------------:::::"+resultCdr.getObservacion() );

        switch (resultCdr.getStatusCode()){
            case 200:
                try {
                    fileManager.save(resultCdr.getByteCDRFile(), fileName, TypeFile.CDR_GUIA);
                    LectorXML.leerCDR(guiaRemisionRemitente);
                } catch (FilesAdapterException | EfactException ex) {
                    result = ResultObtenerCdrQueryDTO
                            .builder()
                            .estadoId(guiaRemisionRemitente.getEstado().getId())
                            .mensaje(ex.getMessage())
                            .build();
                    break;
                }

                guiaRemisionRemitente.setEfactCdrDescargado(true);
                guiaRemisionRemitente.setObservacion(resultCdr.getObservacion());
                this.actualizarEstadoGuia(guiaRemisionRemitente, ACEPTADO.getId());
                guiaRemisionRemitenteRepository.save(guiaRemisionRemitente);

                result = ResultObtenerCdrQueryDTO
                        .builder()
                        .estadoId(guiaRemisionRemitente.getEstado().getId())
                        .mensaje(resultCdr.getObservacion())
                        .resource(new FileNameAwareByteArrayResource(fileName, resultCdr.getByteCDRFile()))
                        .build();
                break;
            case 202:
                guiaRemisionRemitente.setEfactObservacion(resultCdr.getObservacion());
                this.actualizarEstadoGuia(guiaRemisionRemitente, VALIDANDO.getId());
                guiaRemisionRemitenteRepository.save(guiaRemisionRemitente);

                result = ResultObtenerCdrQueryDTO
                        .builder()
                        .estadoId(guiaRemisionRemitente.getEstado().getId())
                        .mensaje(resultCdr.getObservacion())
                        .build();
                break;
            case 412:
                guiaRemisionRemitente.setEfactObservacion(resultCdr.getObservacion());
                this.actualizarEstadoGuia(guiaRemisionRemitente, ERROR.getId());
                guiaRemisionRemitenteRepository.save(guiaRemisionRemitente);

                result = ResultObtenerCdrQueryDTO
                        .builder()
                        .estadoId(guiaRemisionRemitente.getEstado().getId())
                        .mensaje(resultCdr.getObservacion())
                        .build();
                break;
            default:
                guiaRemisionRemitente.setEfactObservacion(resultCdr.getObservacion());
                this.actualizarEstadoGuia(guiaRemisionRemitente, DESCONOCIDO.getId());
                guiaRemisionRemitenteRepository.save(guiaRemisionRemitente);

                result = ResultObtenerCdrQueryDTO
                        .builder()
                        .estadoId(guiaRemisionRemitente.getEstado().getId())
                        .mensaje(resultCdr.getObservacion())
                        .build();
                break;
        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    private FileNameAwareByteArrayResource obtenerXml(GuiaRemisionRemitente guiaRemisionRemitente){

        this.validarGuiaAceptada(guiaRemisionRemitente);

        String fileName = guiaRemisionRemitente.getNombreGuiaElectronica(TypeFile.XML_GUIA_SIGNED);

        if(guiaRemisionRemitente.isEfactXmlDescargado()){
            try {
                byte[] bytes = fileManager.downloadFile(fileName, TypeFile.XML_GUIA_SIGNED);
                return new FileNameAwareByteArrayResource(fileName, bytes);
            } catch (FilesAdapterException e) {
                throw new FileException(e.getMessage(), e);
            }
        }

        this.validarPermisosSobreSerie(guiaRemisionRemitente.getSerie());

        byte[] bytes;

        try {
            bytes = guiasElectronicasEfact.obtenerXML(guiaRemisionRemitente.getEfactTicket());
            this.fileManager.save(bytes, fileName, TypeFile.XML_GUIA_SIGNED);
        } catch (EfactException | FilesAdapterException ex){
            throw new FileException(ex.getMessage(), ex);
        }

        guiaRemisionRemitente.setEfactXmlDescargado(true);
        guiaRemisionRemitenteRepository.save(guiaRemisionRemitente);

        return new FileNameAwareByteArrayResource(fileName, bytes);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    private FileNameAwareByteArrayResource obtenerPdfNuevo(GuiaRemisionRemitente guiaRemisionRemitente) throws Exception {

        this.validarGuiaAceptada(guiaRemisionRemitente);

        String fileName =  guiaRemisionRemitente.getNombreGuiaElectronica(TypeFile.PDF_GUIA);






        if(guiaRemisionRemitente.isEfactPdfDescargado()){
            try {

                    byte[] bytes = fileManager.downloadFile(fileName, TypeFile.PDF_GUIA);
                    return new FileNameAwareByteArrayResource(fileName, bytes);

            } catch (FilesAdapterException e) {
                throw new FileException(e.getMessage(), e);
            }
        }

        this.validarPermisosSobreSerie(guiaRemisionRemitente.getSerie());

        byte[] bytes;

        try {
            pdfGenerator.pdfguia(guiaRemisionRemitente,fileName);
             bytes = fileManager.downloadFile(fileName, TypeFile.PDF_GUIA);
        } catch (EfactException | FilesAdapterException ex){
            throw new FileException(ex.getMessage(), ex);
        }

        guiaRemisionRemitente.setEfactPdfDescargado(true);
        guiaRemisionRemitenteRepository.save(guiaRemisionRemitente);



        return new FileNameAwareByteArrayResource(fileName, bytes);
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    private FileNameAwareByteArrayResource obtenerPdf(GuiaRemisionRemitente guiaRemisionRemitente){

        this.validarGuiaAceptada(guiaRemisionRemitente);

        String fileName = guiaRemisionRemitente.getNombreGuiaElectronica(TypeFile.PDF_GUIA);

        if(guiaRemisionRemitente.isEfactPdfDescargado()){
            try {
                byte[] bytes = fileManager.downloadFile(fileName, TypeFile.PDF_GUIA);
                return new FileNameAwareByteArrayResource(fileName, bytes);
            } catch (FilesAdapterException e) {
                throw new FileException(e.getMessage(), e);
            }
        }

        this.validarPermisosSobreSerie(guiaRemisionRemitente.getSerie());

        byte[] bytes;

        try {
            bytes = guiasElectronicasEfact.obtenerPDF(guiaRemisionRemitente.getEfactTicket());
            this.fileManager.save(bytes, fileName, TypeFile.PDF_GUIA);
        } catch (EfactException | FilesAdapterException ex){
            throw new FileException(ex.getMessage(), ex);
        }

        guiaRemisionRemitente.setEfactPdfDescargado(true);
        guiaRemisionRemitenteRepository.save(guiaRemisionRemitente);

        return new FileNameAwareByteArrayResource(fileName, bytes);
    }

    @Transactional(rollbackFor = Exception.class)
    private void enviarCorreo(GuiaRemisionRemitente guiaRemisionRemitente, String[] correos) throws MessagingException, UnsupportedEncodingException {

        this.validarGuiaAceptada(guiaRemisionRemitente);
        this.validarPermisosSobreSerie(guiaRemisionRemitente.getSerie());

        ResultObtenerCdrQueryDTO result = this.obtenerCdr(guiaRemisionRemitente);

        if(result.getResource() == null){
            throw new FileException(result.getMensaje());
        }

        FileNameAwareByteArrayResource cdr = result.getResource();

        FileNameAwareByteArrayResource xml = this.obtenerXml(guiaRemisionRemitente);

        FileNameAwareByteArrayResource pdf = this.obtenerPdf(guiaRemisionRemitente);

        this.emailService.sendMail(
                correos,
                "Guia Remision " + guiaRemisionRemitente.getNumeroSerie(),
                "Se envia los documentos electronicos asociados a la guia de remision remitente.",
                new Resource[]{cdr, xml, pdf}
        );
    }

    private void validarGuiaAceptada(GuiaRemisionRemitente guiaRemisionRemitente){
        if(guiaRemisionRemitente.getEstado().getId().equals(BAJA.getId())){
            throw new ValidationException(GuiaRemisionRemitenteResponse.DADO_BAJA);
        }

        if(guiaRemisionRemitente.getEfactTicket() == null ){
            throw new ValidationException(GuiaRemisionRemitenteResponse.NO_ENVIADO_EFACT);
        }

        if(!guiaRemisionRemitente.getEstado().getId().equals(ACEPTADO.getId())){
            throw new ValidationException(GuiaRemisionRemitenteResponse.DESCARGA_DENEGADA);
        }
    }

    private void validarPermisosSobreSerie(String serie){
        this.usuarioQueryService.validarPermisosUsuarioSobreSerie(serie);
    }

    @Transactional(rollbackFor = Exception.class)
    private void guardarListasAnidadas(
            GuiaRemisionRemitente guiaRemisionRemitente, GuiaRemisionRemitenteCommandDTO dto){

        //Documentos relacionados
        List<DocumentoRelacionado> documentoRelacionados = this.guardarListaAnidadas(
                guiaRemisionRemitente,
                guiaRemisionDocumentoRelacionadoRepository,
                guiaRemisionRemitente.getDocumentoRelacionados(),
                dto.getDocumentoRelacionados(),
                DocumentoRelacionado.class
        );
        guiaRemisionRemitente.setDocumentoRelacionados(documentoRelacionados);

        //Correo destinatarios
        List<CorreoDestinatario> correoDestinatarios = this.guardarListaAnidadas(
                guiaRemisionRemitente,
                guiaRemisionCorreoDestinatarioRepository,
                guiaRemisionRemitente.getCorreoDestinatarios(),
                dto.getCorreoDestinatarios(),
                CorreoDestinatario.class
        );
        guiaRemisionRemitente.setCorreoDestinatarios(correoDestinatarios);

        //Conductores
        List<Conductor> conductores = this.guardarListaAnidadas(
                guiaRemisionRemitente,
                guiaRemisionRemitenteConductorRepository,
                guiaRemisionRemitente.getConductores(),
                dto.getConductores(),
                Conductor.class
        );
        guiaRemisionRemitente.setConductores(conductores);

        //Vehiculos
        List<Vehiculo> vehiculos = this.guardarListaAnidadas(
                guiaRemisionRemitente,
                guiaRemisionRemitenteVehiculoRepository,
                guiaRemisionRemitente.getVehiculos(),
                dto.getVehiculos(),
                Vehiculo.class
        );
        guiaRemisionRemitente.setVehiculos(vehiculos);

        //Articulos
        List<Articulo> articulos = this.guardarListaAnidadas(
                guiaRemisionRemitente,
                guiaRemisionRemitenteArticuloRepository,
                guiaRemisionRemitente.getArticulos(),
                dto.getArticulos(),
                Articulo.class
        );
        guiaRemisionRemitente.setArticulos(articulos);
    }

    @SuppressWarnings({"unchecked"})
    @Transactional(rollbackFor = Exception.class)
    private <E extends DetalleGuiaRemisionRemitente,
             R extends JpaRepository<E, Integer>,C extends Class<?>,DTO> List<E> guardarListaAnidadas(
            GuiaRemisionRemitente guiaRemisionRemitente, R jpaRepository, List<E> listaActual,
            List<DTO> listaDtos, C clazzEntity){

        List<E> listaGuardar = Objects.requireNonNullElse(
                        listaDtos, new ArrayList<DTO>()
                )
                .stream()
                .map(d -> (E) modelMapper.map(d, clazzEntity))
                .peek(d -> d.setGuiaRemisionRemitente(guiaRemisionRemitente))
                .collect(Collectors.toList());

        List<Integer> listaIdsGuardar = listaGuardar
                .stream()
                .map(DetalleGuiaRemisionRemitente::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Integer> listaIdsEliminar = Objects.requireNonNullElse(
                    listaActual, new ArrayList<E>()
                )
                .stream()
                .map(DetalleGuiaRemisionRemitente::getId)
                .filter(id -> !listaIdsGuardar.contains(id))
                .collect(Collectors.toList());

        jpaRepository.deleteAllById(listaIdsEliminar);
        return jpaRepository.saveAll(listaGuardar);
    }

    private void validarGuiaRemisionRemitente(Integer id, GuiaRemisionRemitenteCommandDTO guiaRemisionRemitente){

        //Validando exitencia de guia y estado
        if(id != null){
            if(!guiaRemisionRemitenteRepository.existsById(id)){
                throw new ValidationException(GuiaRemisionRemitenteResponse.NO_ENCONTRADO);
            }

            Integer estadoId = guiaRemisionRepository.findEstadoById(id);

            if(!Stream.of(NO_ENVIADO, ERROR, DESCONOCIDO).map(EstadosGuia::getId)
                    .collect(Collectors.toList()).contains(estadoId) ){
                throw new ValidationException(GuiaRemisionRemitenteResponse.ACTUALIZACION_EMISION_DENEGADA);
            }
        }

        //Validando serie y correlativo unicos
        Serie serie = serieRepository.findByCodigo(guiaRemisionRemitente.getSerie())
                .orElseThrow(() -> new ValidationException(SerieResponse.NO_ENCONTRADO));

        this.validarPermisosSobreSerie(guiaRemisionRemitente.getSerie());

        Optional<Integer> guiaBySerieAndNumeroId = guiaRemisionRepository.findBySerieAndNumero(
                serie.getCodigo(), guiaRemisionRemitente.getNumero());

        if(id == null){
            if(guiaBySerieAndNumeroId.isPresent()){
                String newNumero = serieQueryService.findCorrelativoActual(serie.getId());
                guiaRemisionRemitente.setNumero(newNumero);
            }
        } else {
            if(guiaBySerieAndNumeroId.isEmpty() || !guiaBySerieAndNumeroId.get().equals(id)){
                throw  new ValidationException(GuiaRemisionRemitenteResponse.ACTUALIZACION_SERIE_NUMERO_INVALIDA);
            }
        }

        //Grupo de validaciones adicionales by @Valid
        this.validarGuiaRemisionRemitenteByGroups(guiaRemisionRemitente);

        //Validacion documentos relacionados unicos
        if(guiaRemisionRemitente.getDocumentoRelacionados() != null){
            List<String> values =  guiaRemisionRemitente
                    .getDocumentoRelacionados()
                    .stream()
                    .map(dr -> dr.getTipoDocumento().getId() + "-" + dr.getNroDocumento())
                    .collect(Collectors.toList());
            if(values.size() != new HashSet<>(values).size()){
                throw new ValidationException(GuiaRemisionRemitenteResponse.DOCUMENTO_RELACIONADO_DUPLICADO);
            }
        }

        //Validacion correos destinatarios unicos
        if(guiaRemisionRemitente.getCorreoDestinatarios() != null){
            List<String> values =  guiaRemisionRemitente
                    .getCorreoDestinatarios()
                    .stream()
                    .map(CorreoDestinatarioCommandDTO::getCorreo)
                    .collect(Collectors.toList());
            if(values.size() != new HashSet<>(values).size()){
                throw new ValidationException(GuiaRemisionRemitenteResponse.CORREO_DESTINATARIO_DUPLICADO);
            }
        }

        //Validacion conductores unicos
        if(guiaRemisionRemitente.getConductores() != null){
            List<String> values =  guiaRemisionRemitente
                    .getConductores()
                    .stream()
                    .map(c -> c.getTipoDocumentoIdentidad().getId() + "-" + c.getNroDocumentoIdentidad())
                    .collect(Collectors.toList());
            if(values.size() != new HashSet<>(values).size()){
                throw new ValidationException(GuiaRemisionRemitenteResponse.CONDUCTOR_DUPLICADO);
            }
        }

        //Validacion vehiculo unicos
        if(guiaRemisionRemitente.getVehiculos() != null){
            List<String> values =  guiaRemisionRemitente
                    .getVehiculos()
                    .stream()
                    .map(DetalleVehiculoCommandDTO::getNumeroPlaca)
                    .collect(Collectors.toList());
            if(values.size() != new HashSet<>(values).size()){
                throw new ValidationException(GuiaRemisionRemitenteResponse.VEHICULO_DUPLICADO);
            }
        }

        //Validacion articulos unicos
        if(guiaRemisionRemitente.getArticulos() != null){
            List<String> values =  guiaRemisionRemitente
                    .getArticulos()
                    .stream()
                    .map(DetalleArticuloCommandDTO::getCodigo)
                    .collect(Collectors.toList());
            if(values.size() != new HashSet<>(values).size()){
                throw new ValidationException(GuiaRemisionRemitenteResponse.ARTICULO_DUPLICADO);
            }
        }

        //Validar numeración de vehiculos
        if(guiaRemisionRemitente.getVehiculos() != null && !guiaRemisionRemitente.getVehiculos().isEmpty()){
            List<Integer> nroOrdenVehiculos = guiaRemisionRemitente.getVehiculos()
                    .stream().map(DetalleVehiculoCommandDTO::getNroOrden)
                    .collect(Collectors.toList());

            Set<Integer> rangeNroOrdenVehiculos = IntStream.rangeClosed(1, guiaRemisionRemitente.getVehiculos().size())
                    .boxed().collect(Collectors.toSet());

            if(!rangeNroOrdenVehiculos.containsAll(nroOrdenVehiculos)){
                throw new ValidationException(GuiaRemisionRemitenteResponse.NRO_ORDEN_VEHICULOS_INVALIDO);
            }
        }

        //Validar numeración de conductores
        if(guiaRemisionRemitente.getConductores() != null && !guiaRemisionRemitente.getConductores().isEmpty()){
            List<Integer> nroOrdenConductores = guiaRemisionRemitente.getConductores()
                    .stream().map(DetalleConductorCommandDTO::getNroOrden)
                    .collect(Collectors.toList());

            Set<Integer> rangeNroOrdenConductores = IntStream.rangeClosed(1, guiaRemisionRemitente.getConductores().size())
                    .boxed().collect(Collectors.toSet());

            if(!rangeNroOrdenConductores.containsAll(nroOrdenConductores)){
                throw new ValidationException(GuiaRemisionRemitenteResponse.NRO_ORDEN_CONDUCTORES_INVALIDO);
            }
        }

        //Validar numeración de articulos
        if(guiaRemisionRemitente.getArticulos() != null && !guiaRemisionRemitente.getArticulos().isEmpty()){
            List<Integer> nroOrdenArticulos = guiaRemisionRemitente.getArticulos()
                    .stream().map(DetalleArticuloCommandDTO::getNroOrden)
                    .collect(Collectors.toList());

            Set<Integer> rangeNroOrdenArticulos = IntStream.rangeClosed(1, guiaRemisionRemitente.getArticulos().size())
                    .boxed().collect(Collectors.toSet());

            if(!rangeNroOrdenArticulos.containsAll(nroOrdenArticulos)){
                throw new ValidationException(GuiaRemisionRemitenteResponse.NRO_ORDEN_ARTICULOS_INVALIDO);
            }
        }

        //Obtener motivo de traslado
        MotivosTraslado motivoTraslado = MotivosTraslado
                .valueOf(guiaRemisionRemitente.getEnvio().getMotivoTraslado().getId())
                .orElseThrow(() -> new ValidationException(GuiaRemisionRemitenteResponse.MOTIVO_TRASLADO_INVALIDO));

        //Validar destinatario segun motivo de traslado
        if(Arrays.asList(VENTA,CONSIGNACION,DEVOLUCION,VENTA_SUJETA_CONFIRMACION,
                 VENTA_ENTREGA_TERCEROS).contains(motivoTraslado)){
            if(this.isRemitenteIgualDestinatario(guiaRemisionRemitente)){
                throw new ValidationException(GuiaRemisionRemitenteResponse.DESTINATARIO_DEBE_SER_DISTINTO_REMITENTE);
            }
        }

        if(Arrays.asList(COMPRA, RECOJO_BIENES_TRANSFORMADOS, TRASLADO_ENTRE_ESTABLECIMIENTOS,TRASLADO_BIENES_PARA_TRANSFORMACION).contains(motivoTraslado)){
            if(!this.isRemitenteIgualDestinatario(guiaRemisionRemitente)){
                throw new ValidationException(GuiaRemisionRemitenteResponse.DESTINATARIO_DEBE_SER_IGUAL_REMITENTE);
            }
        }

        //Validar ind isEmitidoInterno en documentos relacionados
        if(guiaRemisionRemitente.getDocumentoRelacionados() != null){
            for (int i = 0; i < guiaRemisionRemitente.getDocumentoRelacionados().size(); i++) {
                DocumentoRelacionadoCommandDTO documentoRelacionado = guiaRemisionRemitente
                        .getDocumentoRelacionados().get(i);

                if(documentoRelacionado.isEmitidoInterno() &&
                        !documentoRelacionado.getRucEmisor().equals(EmpresaProperties.getInstance().getRuc())){
                    throw new ValidationException(
                            String.format(GuiaRemisionRemitenteResponse.DOCUMENTO_RELACIONADO_RUC_DEBE_SER_IGUAL_REMITENTE, i));
                }

                if(!documentoRelacionado.isEmitidoInterno() &&
                        documentoRelacionado.getRucEmisor().equals(EmpresaProperties.getInstance().getRuc())){
                    throw new ValidationException(
                            String.format(GuiaRemisionRemitenteResponse.DOCUMENTO_RELACIONADO_RUC_DEBE_SER_DISTINTO_REMITENTE, i));
                }
            }
        }
    }

    private void validarGuiaRemisionRemitenteByGroups(GuiaRemisionRemitenteCommandDTO guiaRemisionRemitente){
        List<Class<?>> groups = new ArrayList<>();

        groups.add(guiaRemisionRemitente.getEnvio().isTrasladoVehiculosM1L() ?
                GroupCategoriaM1L.class: GroupCategoriaDefault.class);

        ModalidadesTraslado modalidadTraslado = ModalidadesTraslado
                .valueOf(guiaRemisionRemitente.getEnvio().getModalidadTraslado().getId())
                .orElseThrow(() -> new ValidationException(GuiaRemisionRemitenteResponse.MODALIDAD_TRASLADO_INVALIDA));

        if(modalidadTraslado == PUBLICO){
            groups.add(GroupTrasladoPublico.class);
            if(!guiaRemisionRemitente.getEnvio().isTrasladoVehiculosM1L()){
                groups.add(GroupTrasladoPublicoCategoriaDefault.class);
            }
        }

        if(modalidadTraslado == PRIVADO){
            groups.add(GroupTrasladoPrivado.class);
            if(!guiaRemisionRemitente.getEnvio().isTrasladoVehiculosM1L()){
                groups.add(GroupTrasladoPrivadoCategoriaDefault.class);
            }
        }

        MotivosTraslado motivoTraslado = MotivosTraslado
                .valueOf(guiaRemisionRemitente.getEnvio().getMotivoTraslado().getId())
                .orElseThrow(() -> new ValidationException(GuiaRemisionRemitenteResponse.MOTIVO_TRASLADO_INVALIDO));

        switch (motivoTraslado){
            case VENTA:

                break;
            case CONSIGNACION:
            case DEVOLUCION:
            case VENTA_SUJETA_CONFIRMACION:
                groups.add(GroupVentaEntregaTerceros.class);
                break;
            case TRASLADO_BIENES_PARA_TRANSFORMACION:
                groups.add(GroupTrasladoEntreEstablecimientos.class);
                break;
            case COMPRA:
            case RECOJO_BIENES_TRANSFORMADOS:
                groups.add(GroupTrasladoEntreEstablecimientos.class);
                break;
            case VENTA_ENTREGA_TERCEROS:
                groups.add(GroupVentaEntregaTerceros.class);
                break;
            case TRASLADO_EMISOR_ITINERANTE_CP:
                groups.add(GroupTrasladoEmisorItinerante.class);
                break;
            case OTROS:
                groups.add(GroupOtros.class);
                break;
            case TRASLADO_ENTRE_ESTABLECIMIENTOS:
                groups.add(GroupTrasladoEntreEstablecimientos.class);
                break;
        }

        log.info("Validando integridad guia remision remitente groups: " +
                groups.stream().map(Class::getSimpleName).collect(Collectors.joining(" , ")));

        Set<ConstraintViolation<GuiaRemisionRemitenteCommandDTO>> violations =
                validator.validate(guiaRemisionRemitente, groups.toArray(Class[]::new));

        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
    }

    private boolean isRemitenteIgualDestinatario(GuiaRemisionRemitenteCommandDTO guiaRemisionRemitente){
        return guiaRemisionRemitente.getDestinatario().getTipoDocumentoIdentidad().getId()
                    .equals(EmpresaProperties.getInstance().getTipoDocumentoIdentidadId()) &&
                guiaRemisionRemitente.getDestinatario().getNroDocumentoIdentidad()
                        .equals(EmpresaProperties.getInstance().getRuc());
    }

    private void actualizarEstadoGuia(GuiaRemisionRemitente guiaRemisionRemitente,Integer estadoId){
        EstadoGuia estado = estadoGuiaRepository.findById(estadoId)
                .orElseThrow(() -> new ValidationException(EstadoGuiaResponse.NO_ENCONTRADO));

        Usuario usuario = usuarioQueryService.findUsuarioActual();

        if(guiaRemisionRemitente.getEstado() != null &&
                estado.getId().equals(guiaRemisionRemitente.getEstado().getId())){
            return;
        }

        guiaRemisionRemitente.setEstado(estado);

        if(guiaRemisionRemitente.getLogEstados() == null){
            guiaRemisionRemitente.setLogEstados(new ArrayList<>());
        }

        LogEstado logEstado = LogEstado
                .builder()
                .fecha(new Date())
                .estado(estado)
                .usuario(usuario)
                .guiaRemision(guiaRemisionRemitente)
                .build();

        guiaRemisionRemitente.getLogEstados().add(logEstado);
    }
}
