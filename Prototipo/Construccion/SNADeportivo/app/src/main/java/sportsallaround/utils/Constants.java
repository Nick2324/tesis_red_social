package sportsallaround.utils;

/**
 * Created by LuisFelipe on 12/04/2015.
 */
public class Constants {

    public static final String ROOT_URL = "http://192.168.0.8:8080/SNA_DEPORTIVO_WEB/services/";
    //public static final String ROOT_URL = "http://10.42.0.1:8080/SNA_DEPORTIVO_WEB/services/";
    /*
    *
    * SERVICIOS GESTION DE USUARIO
    *
    * */
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

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String EMAIL_PATTERN ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";



}
