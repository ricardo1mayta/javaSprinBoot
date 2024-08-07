package pe.com.avivel.sistemas.guiaselectronicas.service.impl;

public class ConstansResponse {

    public static final String NO_ENCONTRADO = "Registro/Recurso no encontrado.";
    public static final String ENCONTRADO = "Registro/Recurso no encontrado exitosamente.";
    public static final String YA_EXISTE = "Ya existe un registro con los datos especificados.";

    public static class UsuarioResponse{
        public static final String NO_ENCONTRADO = "Usuario no encontrado.";
        public static final String INACTIVO = "Usuario inactivo.";
        public static final String BAD_CREDENTIALS = "Credenciales incorrectas.";
        public static final String DATOS_SESION_NO_ENCONTRADOS = "No se pudo encontrar el usuario actual especificado en el token.";
        public static final String EXISTE_EMAIL = "Ya existe un usuario asociado a este email.";
        public static final String EXISTE_USERNAME = "Ya existe un usuario asociado a este username.";
        public static final String REQUIERE_USUARIO_EXTERNO = "Es necesario seleccionar un usuario externo para realizar esta acción";
    }

    public static class RolResponse{
        public static final String NO_ENCONTRADO = "Rol no encontrado.";
        public static final String DEBE_SER_TIPO_MENU = "El rol especificado debe de ser tipo Menu.";
    }

    public static class SesionResponse{
        public static final String NO_ENCONTRADA = "Sesion no encontrada.";
        public static final String TOKEN_EXPIRADO = "Su sesión ha expirado, por favor vuelva a iniciar sesión.";
    }

    public static class SerieResponse{
        public static final String NO_ENCONTRADO = "Serie no encontrada.";
        public static final String EXISTE_CODIGO = "Ya existe una serie asociada a este codigo.";
    }

    public static class ArticuloResponse{
        public static final String NO_ENCONTRADO = "Articulo no encontrado.";
        public static final String EXISTE_CODIGO = "Ya existe una articulo asociada a este codigo.";
    }

    public static class UnidadMedidaResponse{
        public static final String NO_ENCONTRADO = "Unidad medida no encontrada.";
    }

    public static class ArticuloUnidadMedidaResponse{
        public static final String NO_ENCONTRADO = "Articulo Unidad Medida no encontrado.";
        public static final String EXISTE_ARTICULO_UNIDAD = "La unidad especificada ya se encuentra asociada al articulo.";
    }

    public static class EntidadResponse{
        public static final String NO_ENCONTRADA = "Entidad no encontrada.";
        public static final String EXISTE_TIPO_NRO_DOCUMENTO = "Ya existe una entidad con el tipo y nro de documento de identidad especificados.";
        public static final String ALMACEN_NO_ENCONTRADO = "Almacen no encontrado";
        public static final String CORREO_NO_ENCONTRADO = "Correo no encontrado";
        public static final String EXISTE_CORREO = "La entidad ya tiene registrado el correo especificado.";
    }

    public static class ConductorResponse {
        public static final String NO_ENCONTRADO = "Conductor no encontrado.";
        public static final String EXISTE_TIPO_NRO_DOCUMENTO = "Ya existe un conductor con el tipo y nro de documento de identidad especificados.";
    }

    public static class VehiculoResponse {
        public static final String NO_ENCONTRADO = "Vehiculo no encontrada.";
        public static final String EXISTE_PLACA = "Ya existe un vehiculo con la placa principal especificada.";
    }

    public static class AlmacenResponse {
        public static final String NO_ENCONTRADO = "Almacen no encontrado.";
        public static final String EXISTE_CODIGO = "Ya existe un almacen con el codigo sunat especificado.";
    }

    public static class EstadoGuiaResponse{
        public static final String NO_ENCONTRADO = "Estado de la guia no encontrado.";
    }

    public static class DistritoResponse{
        public static final String NO_ENCONTRADO = "Distrito no encontrado.";
    }

    public static class TipoDocumentoResponse{
        public static final String NO_ENCONTRADO = "Tipo de documento no encontrado.";
    }

    public static class TipoDocumentoIdentidadResponse{
        public static final String NO_ENCONTRADO = "Tipo de documento de identidad no encontrado.";
    }

    public static class TipoGuiaResponse{
        public static final String NO_ENCONTRADO = "Tipo de guia no encontrado.";
    }

    public static class MarcaResponse{
        public static final String NO_ENCONTRADO = "Marca no encontrada.";
    }

    public static class CategoriaResponse{
        public static final String NO_ENCONTRADO = "Categoria no encontrada.";
    }

    public static class GuiaRemisionRemitenteResponse{
        public static final String NO_ENCONTRADO = "La guia no pudo ser encontrada.";
        public static final String ACTUALIZACION_EMISION_DENEGADA = "La guia solo se puede modificar/emitir cuando se encuentre en los siguientes estados : No enviado, Error o Desconocido.";
        public static final String NO_ENVIADO_EFACT = "La guia especificada todavia no ha sido enviada a efact";
        public static final String DESCARGA_DENEGADA = "La desarga de xml/pdf solo se puede realizar en cuento la guia se encuentre en el estado de Aceptada.";
        public static final String DADO_BAJA = "La guia especificada ya ha sido dada de baja.";
        public static final String GUIA_ENVIADA_ERROR_CDR = "La guia fue enviada, pero no se pudo obtener el CDR : %s";
        public static final String SERIE_NO_DISPONIBLE = "No cuenta con los permisos para generar/acceder a una guia con la serie especificada.";
        public static final String ACTUALIZACION_SERIE_NUMERO_INVALIDA = "No es posible actualizar la serie o numero de una guia de remisió ya creada.";
        public static final String MODALIDAD_TRASLADO_INVALIDA = "La modalidad de traslado es inválida.";
        public static final String MOTIVO_TRASLADO_INVALIDO = "El motivo de traslado es inválida.";
        public static final String DESTINATARIO_DEBE_SER_DISTINTO_REMITENTE = "El destinatario y el remitente deben ser distintos.";
        public static final String DESTINATARIO_DEBE_SER_IGUAL_REMITENTE = "El destinatario y el remitente deben ser los mismos.";
        public static final String DOCUMENTO_RELACIONADO_RUC_DEBE_SER_DISTINTO_REMITENTE = "documentoRelacionados[%s].rucEmisor: Debe ser distinto al ruc del remitente.";
        public static final String DOCUMENTO_RELACIONADO_RUC_DEBE_SER_IGUAL_REMITENTE = "documentoRelacionados[%s].rucEmisor: Debe ser igual al ruc del remitente.";
        public static final String DOCUMENTO_RELACIONADO_DUPLICADO = "Existen documentos relacionados con valores duplicados.";
        public static final String CORREO_DESTINATARIO_DUPLICADO = "Existen correos destinatarios con valores duplicados.";
        public static final String CONDUCTOR_DUPLICADO = "Existen conductores con valores duplicados.";
        public static final String VEHICULO_DUPLICADO = "Existen vehiculos con valores duplicados.";
        public static final String ARTICULO_DUPLICADO = "Existen articulos con valores duplicados.";
        public static final String NRO_ORDEN_VEHICULOS_INVALIDO = "La secuencia de los nros de orden de los vehiculos es invalido.";
        public static final String NRO_ORDEN_CONDUCTORES_INVALIDO = "La secuencia de los nros de orden de los conductores es invalido.";
        public static final String NRO_ORDEN_ARTICULOS_INVALIDO = "La secuencia de los nros de orden de los articulos es invalido.";
    }
}
