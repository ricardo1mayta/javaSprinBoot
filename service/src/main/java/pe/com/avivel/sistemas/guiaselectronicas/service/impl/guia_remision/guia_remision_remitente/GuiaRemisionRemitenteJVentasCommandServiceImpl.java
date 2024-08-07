package pe.com.avivel.sistemas.guiaselectronicas.service.impl.guia_remision.guia_remision_remitente;

import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.commons.constans.GuiasConstans;
import pe.com.avivel.sistemas.guiaselectronicas.commons.empresa.EmpresaProperties;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.jventas.*;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.CorreoDestinatario;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.DocumentoRelacionado;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.DetalleGuiaRemisionRemitente;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.GuiaRemisionRemitente;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle.Articulo;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle.Conductor;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle.Vehiculo;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.seccion.Destinatario;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.seccion.Direccion;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.seccion.Envio;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.seccion.Transportista;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.TipoDocumentoIdentidadRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.TipoDocumentoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision.UnidadMedidaPesoBrutoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision.UnidadMedidaRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision.guia_remision_remitente.ModalidadTrasladoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision.guia_remision_remitente.MotivoTrasladoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.GuiaRemisionCorreoDestinatarioRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.GuiaRemisionDocumentoRelacionadoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.GuiaRemisionRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.guia_remision_remitente.detalle.GuiaRemisionRemitenteArticuloRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.guia_remision_remitente.detalle.GuiaRemisionRemitenteConductorRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.guia_remision_remitente.detalle.GuiaRemisionRemitenteVehiculoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.AlmacenRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.ubigeos.DistritoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteJVentasCommandService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pe.com.avivel.sistemas.guiaselectronicas.commons.constans.GuiasConstans.*;

@Service
public class GuiaRemisionRemitenteJVentasCommandServiceImpl implements GuiaRemisionRemitenteJVentasCommandService {


    private final Boolean ALMACEN_INTERNO=true;
    private final Boolean NO_ALMACEN_INTERNO=false;
    private final DistritoRepository distritoRepository;
    private final AlmacenRepository almacenRepository;
    private final TipoDocumentoIdentidadRepository tipoDocumentoIdentidadRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final UnidadMedidaRepository unidadMedidaRepository;
    private final MotivoTrasladoRepository motivoTrasladoRepository;
    private final UnidadMedidaPesoBrutoRepository unidadMedidaPesoBrutoRepository;
    private final ModalidadTrasladoRepository modalidadTrasladoRepository;
    private final GuiaRemisionRepository guiaRemisionRepository;
    private final GuiaRemisionRemitenteConductorRepository conductorRepository;
    private final GuiaRemisionRemitenteVehiculoRepository vehiculoRepository;
    private final GuiaRemisionDocumentoRelacionadoRepository documentoRelacionadoRepository;
    private final GuiaRemisionCorreoDestinatarioRepository correoDestinatarioRepository;
    private final GuiaRemisionRemitenteArticuloRepository articuloRepository;



    public GuiaRemisionRemitenteJVentasCommandServiceImpl(DistritoRepository distritoRepository,
                                                          AlmacenRepository almacenRepository,
                                                          TipoDocumentoIdentidadRepository tipoDocumentoIdentidadRepository,
                                                          TipoDocumentoRepository tipoDocumentoRepository,
                                                          UnidadMedidaRepository unidadMedidaRepository,
                                                          MotivoTrasladoRepository motivoTrasladoRepository,
                                                          UnidadMedidaPesoBrutoRepository unidadMedidaPesoBrutoRepository,
                                                          ModalidadTrasladoRepository modalidadTrasladoRepository,
                                                          GuiaRemisionRepository guiaRemisionRepository,
                                                          GuiaRemisionRemitenteConductorRepository conductorRepository,
                                                          GuiaRemisionRemitenteVehiculoRepository vehiculoRepository,
                                                          GuiaRemisionDocumentoRelacionadoRepository documentoRelacionadoRepository,
                                                          GuiaRemisionCorreoDestinatarioRepository correoDestinatarioRepository,
                                                          GuiaRemisionRemitenteArticuloRepository articuloRepository) {

        this.distritoRepository = distritoRepository;
        this.almacenRepository = almacenRepository;
        this.tipoDocumentoIdentidadRepository = tipoDocumentoIdentidadRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        this.unidadMedidaRepository = unidadMedidaRepository;
        this.motivoTrasladoRepository = motivoTrasladoRepository;
        this.unidadMedidaPesoBrutoRepository = unidadMedidaPesoBrutoRepository;
        this.modalidadTrasladoRepository = modalidadTrasladoRepository;
        this.guiaRemisionRepository = guiaRemisionRepository;
        this.conductorRepository = conductorRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.documentoRelacionadoRepository = documentoRelacionadoRepository;
        this.correoDestinatarioRepository = correoDestinatarioRepository;
        this.articuloRepository = articuloRepository;
    }

    @Override
    public GuiaRemisionRemitente create(GuiaRemisionBodyDTO guia) {

        var guiaRemision = new GuiaRemisionRemitente();
        guiaRemision.setNumero(guia.getGuiaRemisionNumero());
        guiaRemision.setSerie(guia.getGuiaRemisionSerie());
        guiaRemision.setFechaEmision(guia.getFecha());

        if(guia.getOrdenCompra()!=null){
            guiaRemision.setOrdenCompra(guia.getOrdenCompra());
        }

        if(guia.getOrdenPedidoNumeroSerie()!=null){
            guiaRemision.setOrdenPedido(guia.getOrdenPedidoNumeroSerie());
        }


        if(guia.getMotivoTraslado().getMotivoTrasladoCodigo().equals(GuiasConstans.CODIGO_TRASLADO_INTERNO)){
            guiaRemision.setPuntoPartida(this.fromAlmacenDTO(guia.getPuntoPartidaAlmacen()));
            guiaRemision.setPuntoLlegada(this.fromAlmacenDTO(guia.getPuntoLLegadaAlmacen()));
        }

        if(guia.getPuntoPartidaAlmacen()==null || guia.getPuntoLLegadaAlmacen()==null){
            guiaRemision.setPuntoPartida(this.fromPuntoPartidaDTO(guia.getPuntoPartida()));
            guiaRemision.setPuntoLlegada(this.fromPuntoDeLlegadaDTO(guia.getPuntoDeLlegada()));
        }

        guiaRemision.setDestinatario(this.fromClienteDTO(guia.getCliente()));
        guiaRemision.setTransportista(this.fromEmpresaTransportistaDTO(guia.getEmpresaTransportista()));
        guiaRemision.setEnvio(this.fromGuiaRemisionBodyDTO(guia));


        var guiaSave = guiaRemisionRepository.save(guiaRemision);




        if(!guia.getConductores().isEmpty()){
            var conductores = guia.getConductores()
                    .stream()
                    .map(this::fromConductorDTO)
                    .peek(c->c.setGuiaRemisionRemitente(guiaSave))
                    .collect(Collectors.toList());
            //guiaRemision.setConductores(conductores);
            conductorRepository.saveAll(conductores);
        }

        if(!guia.getVehiculos().isEmpty()){
            var vehiculos= guia.getVehiculos()
                    .stream()
                    .map(this::fromVehiculoDTO)
                    .peek(v->v.setGuiaRemisionRemitente(guiaSave))
                    .collect(Collectors.toList());
            //guiaRemision.setVehiculos(vehiculos);

            vehiculoRepository.saveAll(vehiculos);

        }

        if(guia.getDocumentoRelacionado()!=null){
            var documentosRelacionados = Stream.of(this.fromDocumentoRelacionadoDTO(guia.getDocumentoRelacionado()))
                    .peek(d->d.setGuiaRemisionRemitente(guiaSave))
                    .collect(Collectors.toList());

            documentoRelacionadoRepository.saveAll(documentosRelacionados);
        }

        if(!guia.getCorreoDestinatarios().isEmpty()){

            var correos = this.fromCorreoDestinatarios(guia.getCorreoDestinatarios())
                    .stream()
                    .peek(cd->cd.setGuiaRemision(guiaRemision))
                    .collect(Collectors.toList());

            correoDestinatarioRepository.saveAll(correos);
        }

        if(!guia.getDetalles().isEmpty()){

            var detalles = guia.getDetalles()
                    .stream()
                    .map(this::fromDetalleGuiaRemisionDTO)
                    .peek(d->d.setGuiaRemisionRemitente(guiaRemision))
                    .collect(Collectors.toList());
            articuloRepository.saveAll(detalles);
        }


        return guiaRemision;
    }

    private Direccion fromAlmacenDTO(AlmacenDTO almacenDTO){

        var direccion = new Direccion();
        direccion.setDireccion(almacenDTO.getDireccion());
        direccion.setAlmacenInterno(ALMACEN_INTERNO);
        direccion.setEntidadAlmacen(null);
        var almacen = almacenRepository.findByCodigoSunat(almacenDTO.getCodigoSunat()).orElse(null);
        direccion.setAlmacen(almacen);
        var distrito = distritoRepository.findByCodigoUbigeo(almacenDTO.getCodigoUbigeo()).orElse(null);
        direccion.setDistrito(distrito);
        return direccion;
    }

    private Direccion fromPuntoPartidaDTO(PuntoPartidaDTO puntoPartidaDTO){
        var direccion = new Direccion();
        direccion.setDireccion(puntoPartidaDTO.getDireccion());
        direccion.setAlmacenInterno(NO_ALMACEN_INTERNO);
        direccion.setAlmacen(null);
        var distrito = distritoRepository.findByDescripcion(puntoPartidaDTO.getDistrito().trim()).orElse(null);
        direccion.setDistrito(distrito);
        return direccion;
    }

    private Direccion fromPuntoDeLlegadaDTO(PuntoDeLlegadaDTO puntoDeLlegadaDTO){
        var direccion = new Direccion();
        direccion.setDireccion(puntoDeLlegadaDTO.getDireccion());
        direccion.setAlmacenInterno(NO_ALMACEN_INTERNO);
        direccion.setAlmacen(null);
        var distrito = distritoRepository.findByDescripcion(puntoDeLlegadaDTO.getDistrito().trim()).orElse(null);
        direccion.setDistrito(distrito);
        return direccion;
    }

    private Destinatario fromClienteDTO(ClienteDTO clienteDTO) {
        var destinatario = new Destinatario();
        destinatario.setDireccion(clienteDTO.getDireccion());
        destinatario.setRazonSocial(clienteDTO.getRazonSocial());
        destinatario.setNroDocumentoIdentidad(clienteDTO.getNumeroDocumentoIdentidad());
        var tipoDocumento = tipoDocumentoIdentidadRepository.findByTipoDocumentoCodigoSunat(clienteDTO.getTipoDocumentoCodigoSunat()).orElse(null);
        destinatario.setTipoDocumentoIdentidad(tipoDocumento);
        var distrito = distritoRepository.findByDescripcion(clienteDTO.getClienteDistrito().trim()).orElse(null);
        destinatario.setDistrito(distrito);
        return destinatario;
    }

    private Conductor fromConductorDTO(ConductorDTO conductorDTO){
        var conductor = new Conductor();
        conductor.setApellidos(conductor.getApellidos());
        conductor.setNombres(conductor.getNombres());
        var tipoDoc = tipoDocumentoIdentidadRepository.findByTipoDocumentoCodigoSunat(conductorDTO.getTipoDocumentoIdentidad()).orElse(null);
        conductor.setTipoDocumentoIdentidad(tipoDoc);
        conductor.setNroDocumentoIdentidad(conductorDTO.getNumeroDocumentoIdentidad());
        conductor.setNroLicenciaBrevete(conductorDTO.getNumeroLicencia());
        conductor.setNroOrden(conductorDTO.getNroOrden());
        return conductor;
    }

    private Vehiculo fromVehiculoDTO(VehiculoDTO vehiculoDTO){
        var vehiculo = new Vehiculo();
        vehiculo.setDescripcion(vehiculoDTO.getDescripcion());
        vehiculo.setNumeroPlaca(vehiculoDTO.getNumeroPlaca());
        vehiculo.setNroAutorizacion(vehiculo.getNroAutorizacion());
        vehiculo.setEntidadAutorizadora(null);
        vehiculo.setNroOrden(vehiculoDTO.getNroOrden());
        return vehiculo;
    }

    private Transportista fromEmpresaTransportistaDTO(EmpresaTransportistaDTO empresaTransportistaDTO){
        var transportista = new Transportista();
        var tipoDocumento = tipoDocumentoIdentidadRepository.findByTipoDocumentoCodigoSunat(RUC_CODIGO_SUNAT).orElse(null);
        if(empresaTransportistaDTO.getRuc().equals(EmpresaProperties.getInstance().getRuc())){
            transportista.setNroDocumentoIdentidad(EmpresaProperties.getInstance().getRuc());
            transportista.setTipoDocumentoIdentidad(tipoDocumento);
            transportista.setRazonSocial(EmpresaProperties.getInstance().getRasonSocial());
        }else {
            transportista.setTipoDocumentoIdentidad(tipoDocumento);
            transportista.setNroDocumentoIdentidad(empresaTransportistaDTO.getRuc());
            transportista.setRazonSocial(empresaTransportistaDTO.getRazonSocial());
            transportista.setNroRegistroMTC(null);
        }
        return transportista;
    }

    private DocumentoRelacionado fromDocumentoRelacionadoDTO(DocumentoRelacionadoDTO docRel){
        var documento=new DocumentoRelacionado();
        var tipoDocumento = tipoDocumentoRepository.findByAbreviatura(docRel.getTipoDocumentoCodigo()).orElse(null);
        documento.setNroDocumento(docRel.getNumeroSerieDocumento());
        documento.setNroDocumento(documento.getNroDocumento());
        documento.setRucEmisor(documento.getRucEmisor());
        documento.setTipoDocumento(tipoDocumento);
        return documento;
    }

    private List<CorreoDestinatario> fromCorreoDestinatarios(List<String> correoDestinatarios){
        if(!correoDestinatarios.isEmpty()){
            List<CorreoDestinatario> listaCorreos = new ArrayList<>();
            correoDestinatarios.forEach(correo->{
                var correoDestinatario = new CorreoDestinatario();
                correoDestinatario.setCorreo(correo);
                listaCorreos.add(correoDestinatario);
            });

            return listaCorreos;
        }else {
            return null;
        }

    }

    private Articulo fromDetalleGuiaRemisionDTO(DetalleGuiaRemisionDTO detalle){
        var articulo = new Articulo();
        articulo.setDescripcion(detalle.getDescripcion());
        articulo.setObservacion(detalle.getObservacion());
        articulo.setNroOrden(detalle.getNroOrden());
        articulo.setCodigo(detalle.getCodigo());
        articulo.setPeso(detalle.getPeso());
        articulo.setCantidad(detalle.getCantidad());
        unidadMedidaRepository.findByCodigoSunat(detalle.getUnidadCodigoSunat()).ifPresent(articulo::setUnidadMedida);

        return articulo;
    }

    private Envio fromGuiaRemisionBodyDTO(GuiaRemisionBodyDTO guia){

        var envio = new Envio();
        var motivoTraslado = motivoTrasladoRepository.findByCodigo(guia.getMotivoTraslado().getMotivoTrasladoCodigo()).orElse(null);

        if(motivoTraslado!=null){
            envio.setMotivoTraslado(motivoTraslado);
            envio.setDescripcionMotivoTraslado(motivoTraslado.getDescripcion());
        }
        envio.setFechaInicioTraslado(new Date());
        unidadMedidaPesoBrutoRepository.findByCodigoSunat(PESO_BRUTO_CODIGO_KGM).ifPresent(envio::setUnidadMedidaPesoBruto);
        guia.getDetalles().stream().map(DetalleGuiaRemisionDTO::getPeso).reduce(Double::sum).ifPresent(envio::setPesoBruto);

        if(EmpresaProperties.getInstance().getRuc().equals(guia.getEmpresaTransportista().getRuc())){
            modalidadTrasladoRepository.findByCodigoSunat(MODALIDAD_TRASLADO_PRIVADO).ifPresent(envio::setModalidadTraslado);
        }else {
            modalidadTrasladoRepository.findByCodigoSunat(MODALIDAD_TRASLADO_PUBLICO).ifPresent(envio::setModalidadTraslado);
        }

        envio.setTrasladoVehiculosM1L(false);

        return envio;
    }
}
