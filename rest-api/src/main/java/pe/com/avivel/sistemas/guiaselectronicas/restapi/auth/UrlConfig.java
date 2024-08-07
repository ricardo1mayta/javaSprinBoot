package pe.com.avivel.sistemas.guiaselectronicas.restapi.auth;

import java.util.Arrays;
import java.util.List;

public class UrlConfig {
    public static final String URL_ALL = "/**";
    public static final String URL_PREFIX = "/api";
    public static final String URL_PUBLIC_PREFIX = URL_PREFIX + "/public";
    public static final String URL_PUBLIC = URL_PUBLIC_PREFIX + "/**";
    public static final String URL_LOGIN = UsuarioUrl.CommandPublic.PREFIX + UsuarioUrl.CommandPublic.LOGIN;
    public static final String URL_LOGOUT = UsuarioUrl.CommandPublic.PREFIX + UsuarioUrl.CommandPublic.LOGOUT;
    public static final String  URL_AUTH_PREFIX = URL_PREFIX + "/auth";
    public static final String URL_AUTH = URL_AUTH_PREFIX + "/**";
    public static final String URL_TEST_PREFIX = URL_PREFIX + "/test";
    public static final String URL_TEST = URL_TEST_PREFIX + "/**";
    public static final List<String> URL_PREFIX_WITHOUT_SECURITY = Arrays.asList(URL_PUBLIC_PREFIX, URL_TEST_PREFIX);
    public static final List<String> URL_SWAGGER_WITHOUT_SECURITY= Arrays.asList(
            "/v3/api-docs",
            "/v3/api-docs/swagger-config",
            "/swagger-ui/.+\\.html",
            "/swagger-ui/.+\\.css",
            "/swagger-ui/.+\\.js",
            "/swagger-ui/.+\\.png"
    );
    public static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**.html",
            "/swagger-ui/**.css",
            "/swagger-ui/**.js",
            "/swagger-ui/**.png"
    };

    public static class TestingUrl{
        public static final String TAG = "Test"; //TAG SWAGGUER

        public static class Query {
            public static final String PREFIX = URL_TEST_PREFIX + "/test-query";
            public static final String PRUEBA = "/prueba";
        }
    }

    public static class UsuarioUrl{
        public static final String TAG = "Usuario"; //TAG SWAGGUER

        public static class Query {
            public static final String PREFIX = URL_AUTH_PREFIX + "/usuario-query";
            public static final String FIND_BY_ID = "/{id}";
            public static final String FIND_BY_USERNAME = "/by-username/{username}";
            public static final String FIND_DATOS_TRANSPORTISTA = "/datos-transportista";
            public static final String LIST_ALL_BY_FILTROS_PAGINADO = "/list-all-by-filtros-paginado";
            public static final String LIST_SERIES_DISPONIBLES = "/list-series-disponibles";
            public static final String LIST_ALL_SERIES_ASIGNADAS = "/list-all-series-asignadas/{usuario-id}";
            public static final String LIST_ALL_ARTICULOS_ASIGNADOS = "/list-all-articulos-asignados/{usuario-id}";
            public static final String LIST_ALL_ENTIDADES_ASIGNADAS = "/list-all-entidades-asignadas/{usuario-id}";
        }

        public static class Command {
            public static final String PREFIX = URL_AUTH_PREFIX + "/usuario-command";
            public static final String CREATE = "/create";
            public static final String UPDATE = "/update/{id}";
            public static final String ADD_USUARIO_ROL = "/add-usuario-rol/{usuario-id}/{rol-id}";
            public static final String DELETE_USUARIO_ROL = "/delete-usuario-rol/{usuario-id}/{rol-id}";
            public static final String SET_PASSWORD = "/set-password/{id}";
            public static final String UPDATE_PASSWORD = "/update-password";
            public static final String UPDATE_ESTADO = "/update-estado/{id}";
            public static final String ADD_USUARIO_SERIE = "/add-usuario-serie/{usuario-id}/{serie-id}";
            public static final String DELETE_USUARIO_SERIE = "/delete-usuario-serie/{usuario-serie-id}";
            public static final String ADD_USUARIO_ARTICULO = "/add-usuario-articulo/{usuario-id}/{articulo-id}";
            public static final String DELETE_USUARIO_ARTICULO = "/delete-usuario-articulo/{usuario-articulo-id}";
            public static final String ADD_USUARIO_ENTIDAD = "/add-usuario-entidad/{usuario-id}/{entidad-id}";
            public static final String DELETE_USUARIO_ENTIDAD = "/delete-usuario-entidad/{usuario-entidad-id}";
            public static final String UPDATE_DATOS_TRANSPORTISTA = "/update-datos-transportista/{usuario-id}";

        }

        public static class CommandPublic {
            public static final String PREFIX = URL_PUBLIC_PREFIX + "/usuario-command";
            public static final String LOGIN = "/login";
            public static final String LOGOUT = "/logout";

        }
    }

    public static class RolUrl {
        public static final String TAG = "Rol";

        public static class Query {
            public static final String PREFIX = URL_AUTH_PREFIX + "/rol-query";
            public static final String FIND_BY_ID = "/{id}";
            public static final String LIST_BY_ROL_TIPO = "/list-by-rol-tipo/{tipo}";
            public static final String LIST_BY_USUARIO_ID = "/list-by-usuario_id/{usuario-id}";
            public static final String LIST_ALL_BY_FILTROS_PAGINADO = "/list-all-by-filtros-paginado";
        }

        public static class Command {
            public static final String PREFIX = URL_AUTH_PREFIX + "/rol-command";
            public static final String CREATE_ROL_MENUS = "/rol-menus/{rol-id}";
        }
    }

    public static class MenuUrl {
        public static final String TAG = "Menu"; //TAG SWAGGUER

        public static class Query {
            public static final String PREFIX = URL_AUTH_PREFIX + "/menu-query";
            public static final String FIND_BY_ID = "/{id}";
            public static final String LIST = "/list";
            public static final String LIST_BY_USUARIO_ID = "/list-by-usuario-id/{usuario-id}";
            public static final String LIST_BY_USUARIO_ID_SIN_JERARQUIA = "/list-by-usuario-id-sin-jerarquia/{usuario-id}";
            public static final String LIST_BY_ROL_ID = "/list-by-rol-id/{rol-id}";
            public static final String LIST_BY_ROL_ID_SIN_JERARQUIA = "/list-by-rol-id-sin-jerarquia/{rol-id}";
        }
    }

    public static class SesionUrl {
        public static final String TAG = "Sesion"; //TAG SWAGGUER

        public static class Query {
            public static final String PREFIX = URL_AUTH_PREFIX + "/sesion-query";
            public static final String LIST_ALL_BY_FILTROS_PAGINADO = "/list-all-by-filtros-paginado";
        }
    }

    public static class GuiaRemisionRemitenteUrl {
        public static final String TAG = "Guia Remision Remitente"; //TAG SWAGGUER

        public static class Query {
            public static final String PREFIX = URL_AUTH_PREFIX + "/guia-remision-remitente-query";
            public static final String FIND_BY_ID = "/{id}";
            public static final String LIST_BY_FILTROS_PAGINADO = "/list-by-filtros-paginado";
            public static final String LIST_REPORT_GUIAS_ELABORADAS_BY_FILTROS_PAGINADO = "/list-reporte-guias-elaboradas-by-filtros-paginado";
        }

        public static class Command {
            public static final String PREFIX = URL_AUTH_PREFIX + "/guia-remision-remitente-command";
            public static final String CREATE = "/create";
            public static final String UPDATE = "/update/{id}";
            public static final String ANULAR = "/anular/{id}";
            public static final String EMITIR = "/emitir/{id}";
            public static final String OBTENER_CDR = "/obtener-cdr/{id}";
            public static final String OBTENER_XML = "/obtener-xml/{id}";
            public static final String OBTENER_PDF = "/obtener-pdf/{id}";
            public static final String OBTENER_PDF_NUEVO = "/obtener-pdf-nuevo/{id}";
            public static final String OBTENER_PDF_MASIVO = "/obtener-pdf-masivo";
            public static final String ENVIAR_CORREO = "/enviar_correo/{id}";
        }
    }

    public class GuiaRemisionRemitenteJVentasUrl{
        public static final String TAG = "Guia Remision Remitente JVentas"; //TAG SWAGGUER

        public class Command{
            public static final String PREFIX = URL_AUTH_PREFIX + "/guia-remision-remitente-jventas-command";
            public static final String CREATE = "/create";
            public static final String UPDATE = "/update/{id}";
            public static final String ANULAR = "/anular/{id}";
            public static final String EMITIR = "/emitir/{id}";
        }
    }

    public static class MotivoTrasladoUrl {
        public static final String TAG = "Motivo Traslado"; // TAG SWAGGER

        public static class Query {
            public static final String PREFIX = URL_AUTH_PREFIX + "/motivo-traslado-query";
            public static final String LIST = "/list";
        }
    }

    public static class ModalidadTrasladoUrl {
        public static final String TAG = "Modalidad Traslado"; // TAG SWAGGER

        public static class Query {
            public static final String PREFIX = URL_AUTH_PREFIX + "/modalidad-traslado-query";
            public static final String LIST = "/list";
        }
    }

    public static class SerieUrl {
        public static final String TAG = "Serie"; // TAG SWAGGER

        public static class Query {
            public static final String PREFIX = URL_AUTH_PREFIX + "/serie-query";
            public static final String LIST = "/list";
            public static final String LIST_ALL_BY_FILTROS_PAGINADO = "/list-all-by-filtros-paginado";
            public static final String FIND_CORRELATIVO_ACTUAL = "/correlativo-actual/{id}";
        }

        public static class Command {
            public static final String PREFIX = URL_AUTH_PREFIX + "/serie-command";
            public static final String CREATE = "/create";
            public static final String UPDATE = "/update/{id}";
            public static final String UPDATE_ESTADO = "/update-estado/{id}";
        }
    }

    public static class EntidadUrl {
        public static final String TAG = "Entidad"; // TAG SWAGGER

        public static class Query {
            public static final String PREFIX = URL_AUTH_PREFIX + "/entidad-query";
            public static final String LIST_BY_FILTROS_PAGINADO = "/list-by-filtros-paginado";
            public static final String LIST_ALL_BY_FILTROS_PAGINADO = "/list-all-by-filtros-paginado";
            public static final String LIST_ALMACENES_BY_ID = "/list-almacenes/{id}";
            public static final String LIST_ALL_ALMACENES_BY_FILTROS_PAGINADO = "/list-all-almacenes-by-filtros-paginado";
            public static final String LIST_CORREOS_BY_ID = "/list-correos/{id}";
            public static final String LIST_ALL_CORREOS_BY_FILTROS_PAGINADO = "/list-all-correos-by-filtros-paginado";
        }

        public static class Command {
            public static final String PREFIX = URL_AUTH_PREFIX + "/entidad-command";
            public static final String CREATE = "/create";
            public static final String UPDATE = "/update/{id}";
            public static final String UPDATE_ESTADO = "/update-estado/{id}";
            public static final String CREATE_ALMACEN = "/create-almacen";
            public static final String UPDATE_ALMACEN = "/update-almacen/{id}";
            public static final String UPDATE_ESTADO_ALMACEN = "/update-estado-almacen/{id}";
            public static final String CREATE_CORREO = "/create-correo";
            public static final String UPDATE_CORREO = "/update-correo/{id}";
            public static final String UPDATE_ESTADO_CORREO = "/update-estado-correo/{id}";
        }
    }

    public static class ArticuloUrl {
        public static final String TAG = "Articulo"; // TAG SWAGGER

        public static class Query {
            public static final String PREFIX = URL_AUTH_PREFIX + "/articulo-query";
            public static final String LIST_BY_FILTROS_PAGINADO = "/list-by-filtros-paginado";
            public static final String LIST_ALL_BY_FILTROS_PAGINADO = "/list-all-by-filtros-paginado";
            public static final String LIST_AUTOCOMPLETE_BY_CODIGO = "/list-autocomplete-by-codigo/{codigo}";
            public static final String LIST_AUTOCOMPLETE_BY_DESCRIPCION = "/list-autocomplete-by-descripcion/{descripcion}";
            public static final String LIST_ALL_UNIDADES_MEDIDA_BY_ARTICULO = "/list-all-unidades-medida/{articuloId}";
        }

        public static class Command {
            public static final String PREFIX = URL_AUTH_PREFIX + "/articulo-command";
            public static final String CREATE = "/create";
            public static final String UPDATE = "/update/{id}";
            public static final String UPDATE_ESTADO = "/update-estado/{id}";
            public static final String CREATE_UNIDAD_MEDIDA = "/create-unidad-medida/{articulo-id}/{unidad-medida-id}";
            public static final String DELETE_UNIDAD_MEDIDA = "/delete-unidad-medida/{articulo-unidad-medida-id}";
        }
    }

    public static class VehiculoUrl {
        public static final String TAG = "Vehiculo"; // TAG SWAGGER

        public static class Query {
            public static final String PREFIX = URL_AUTH_PREFIX + "/vehiculo-query";
            public static final String LIST_BY_FILTROS_PAGINADO = "/list-by-filtros-paginado";
            public static final String LIST_ALL_BY_FILTROS_PAGINADO = "/list-all-by-filtros-paginado";
        }

        public static class Command {
            public static final String PREFIX = URL_AUTH_PREFIX + "/vehiculo-command";
            public static final String CREATE = "/create";
            public static final String UPDATE = "/update/{id}";
            public static final String UPDATE_ESTADO = "/update-estado/{id}";
        }
    }

    public static class ConductorUrl {
        public static final String TAG = "Conductor"; // TAG SWAGGER
        public static class Query {

            public static final String PREFIX = URL_AUTH_PREFIX + "/conductor-query";
            public static final String LIST_BY_FILTROS_PAGINADO = "/list-by-filtros-paginado";
            public static final String LIST_ALL_BY_FILTROS_PAGINADO = "/list-all-by-filtros-paginado";
        }

        public static class Command {
            public static final String PREFIX = URL_AUTH_PREFIX + "/conductor-command";
            public static final String CREATE = "/create";
            public static final String UPDATE = "/update/{id}";
            public static final String UPDATE_ESTADO = "/update-estado/{id}";
        }
    }

    public static class AlmacenUrl {
        public static final String TAG = "Almacen"; // TAG SWAGGER
        public static class Query {

            public static final String PREFIX = URL_AUTH_PREFIX + "/almacen-query";
            public static final String LIST = "/list";
            public static final String LIST_ALL_BY_FILTROS_PAGINADO = "/list-all-by-filtros-paginado";
        }

        public static class Command {
            public static final String PREFIX = URL_AUTH_PREFIX + "/almacen-command";
            public static final String CREATE = "/create";
            public static final String UPDATE = "/update/{id}";
            public static final String UPDATE_ESTADO = "/update-estado/{id}";
        }
    }

    public static class UnidadMedidaUrl {
        public static final String TAG = "Unidad Medida"; // TAG SWAGGER
        public static class Query {

            public static final String PREFIX = URL_AUTH_PREFIX + "/unidad-medida-query";
            public static final String LIST = "/list";
        }
    }

    public static class UnidadMedidaPesoBrutoUrl {
        public static final String TAG = "Unidad Medida Peso Bruto"; // TAG SWAGGER
        public static class Query {

            public static final String PREFIX = URL_AUTH_PREFIX + "/unidad-medida-peso-bruto-query";
            public static final String LIST = "/list";
        }
    }

    public static class TipoDocumentoUrl {
        public static final String TAG = "Tipo Documento"; // TAG SWAGGER
        public static class Query {

            public static final String PREFIX = URL_AUTH_PREFIX + "/tipo-documento-query";
            public static final String LIST = "/list";
        }
    }

    public static class TipoDocumentoIdentidadUrl {
        public static final String TAG = "Tipo Documento Identidad"; // TAG SWAGGER
        public static class Query {

            public static final String PREFIX = URL_AUTH_PREFIX + "/tipo-documento-identidad-query";
            public static final String LIST = "/list";
        }
    }

    public static class DepartamentoUrl {
        public static final String TAG = "Ubigeos"; // TAG SWAGGER
        public static class Query {

            public static final String PREFIX = URL_AUTH_PREFIX + "/departamento-query";
            public static final String LIST_BY_PAIS_ID = "/list-by-pais-id/{pais-id}";
        }
    }

    public static class ProvinciaUrl {
        public static final String TAG = "Ubigeos"; // TAG SWAGGER
        public static class Query {

            public static final String PREFIX = URL_AUTH_PREFIX + "/provincia-query";
            public static final String LIST_BY_DEPARTAMENTO_ID = "/list-by-departamento-id/{departamento-id}";
        }
    }

    public static class DistritoUrl {
        public static final String TAG = "Ubigeos"; // TAG SWAGGER
        public static class Query {

            public static final String PREFIX = URL_AUTH_PREFIX + "/distrito-query";
            public static final String LIST_BY_PROVINCIA_ID = "/list-by-provincia-id/{provincia-id}";
        }
    }
    public static class MarcaUrl {
        public static final String TAG = "Marca"; // TAG SWAGGER
        public static class Query {
            public static final String PREFIX = URL_AUTH_PREFIX + "/marca-query";

            public static final String LIST_MARCAS = "/list-marcas";
        }
    }
    public static class CategoriaUrl {
        public static final String TAG = "Categoria"; // TAG SWAGGER
        public static class Query {
            public static final String PREFIX = URL_AUTH_PREFIX + "/categoria-query";

            public static final String LIST_CATEGORIAS= "/list-categorias";
        }
    }
}
