package sportsallaround.utils.generales;

import sportsallaround.snadeportivo.ubicaciones.pojos.TipoUbicacion;

/**
 * Created by LuisFelipe on 12/04/2015.
 */
public class Constants {

    //public static final String SERVICES_VERIFICAR_USUARIO = "GestionUsuarioService/verificarUsuario";
    //public static final String SERVICES_CREAR_USUARIO = "GestionUsuarioService/crearUsuario";
    //public static final String SERVICES_OBTENER_ROLES = "GestionUsuarioService/obtenerRoles";

    //**************************************Servicios Eventos**************************************
    public static final String SERVICES_PATH_EVENTOS = "eventos/";
    public static final String SERVICES_PATH_EVE_PARTICIPANTES = "participantes/";
    public static final String SERVICES_PATH_EVE_SOLICITUDES = "participantes/solicitudes/";
    public static final String SERVICES_PATH_EVE_INVITACIONES = "invitaciones/";
    //*********************************************************************************************
    //*************************************Servicios Deportes**************************************
    public static final String SERVICES_PATH_DEPORTES = "deportes/";
    //*********************************************************************************************
    //***********************************Servicios Entidades Generales*****************************
    public static final String SERVICES_PATH_GENERALES = "generales/";
    public static final String SERVICES_PATH_GENERO = "generos/";
    //***********************************Servicios Ubicaciones*************************************
    public static final String SERVICES_PATH_UBICACIONES = "ubicaciones/";
    //*********************************************************************************************
    //******************************Strings de formato y patrones**********************************
    public static final String ROOT_URL = "http://192.168.0.15:8080/SNA_DEPORTIVO_WEB/services/";
    //public static final String ROOT_URL = "http://10.42.0.1:8080/SNA_DEPORTIVO_WEB/services/";
    /*
    *
    * SERVICIOS GESTION DE USUARIO
    *
    * */
    public static final String SERVICES_PATH_USUARIOS = "GestionUsuarioService/";
    public static final String SERVICES_VERIFICAR_USUARIO = "GestionUsuarioService/verificarUsuario";
    public static final String SERVICES_CREAR_USUARIO = "GestionUsuarioService/crearUsuario";
    public static final String SERVICES_ACTUALIZAR_USUARIO = "GestionUsuarioService/actualizarDatosUsuario";
    public static final String SERVICES_OBTENER_ROLES = "GestionUsuarioService/obtenerRoles";
    public static final String SERVICES_OBTENER_PERMISOS_ROL = "GestionUsuarioService/obtenerPermisosRol";
    public static final String SERVICES_OBTENER_PERMISOS = "GestionUsuarioService/obtenerPermisosAsociados";
    public static final String SERVICES_OBTENER_ROL_USUARIO = "GestionUsuarioService/obtenerRolUsuario";
    public static final String SERVICES_OBTENER_DEPORTES_PRACTICADOS = "GestionUsuarioService/obtenerDeportesPracticadosFull";
    public static final String SERVICES_OBTENER_INFO_USUARIO = "GestionUsuarioService/{user}";
    public static final String SERVICES_OBTENER_DEPORTES = "GestionUsuarioService/obtenerDeportes";
    public static final String SERVICES_OBTENER_POSICIONES_DEPORTE = "GestionUsuarioService/obtenerPosicionesDeporte";
    public static final String SERVICES_ADICIONAR_DEPORTE_PRACTICADO = "GestionUsuarioService/adicionarDeportePracticado";
    public static final String SERVICES_ELIMINAR_DEPORTE_PRACTICADO = "GestionUsuarioService/eliminarDeportePracticado";
    public static final String SERVICES_ACTUALIZAR_DEPORTE_PRACTICADO = "GestionUsuarioService/actualizarDeportePracticado";


    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //*********************************************************************************************

    //*********CONSTANTES PARA EL PASO DE MENSAJES ENTRE ACTIVIDADES SEGUN FUNCIONALIDADES*********
    public static final String DATOS_FUNCIONALIDAD = "datos_funcionalidad";
    public static final String FUNCIONALIDAD = "funcionalidad";
    public static final String USUARIO = "user";

    public static final String SERVICES_CREAR_UBICACION ="GestionUbicacionService/crearLugarPractica";
    public static final String SERVICES_OBTENER_UBICACIONES = "GestionUbicacionService/obtenerLugaresPractica";
    public static final String SERVICES_ASIGNAR_DEPORTE_UBICACION = "GestionUbicacionService/asignarDeportesPracticadosLugar";

    public static final TipoUbicacion[] TIPOS_UBICACION =
            {new TipoUbicacion("Parque", true),
             new TipoUbicacion("Complejo", true),
             new TipoUbicacion("Campo", true),
             new TipoUbicacion("Coliseo", true),
             new TipoUbicacion("Estadio", true),
             new TipoUbicacion("Centro de convenciones", false)};

}
