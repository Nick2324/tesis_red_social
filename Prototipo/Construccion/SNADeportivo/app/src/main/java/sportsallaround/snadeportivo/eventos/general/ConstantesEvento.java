package sportsallaround.snadeportivo.eventos.general;

/**
 * Created by nicolas on 13/09/15.
 */
public class ConstantesEvento {

    //FUNCIONALIDADES DE EVENTOS DE ADMINISTRADOR
    public static final String CREAR_EVENTO = "CE";
    public static final String ACTUALIZAR_EVENTO = "AE";
    public static final String OWNER = "OW";

    //FUNCIONALIDADES DE EVENTOS DE PARTICIPANTES
    public static final String POSIBLE_PARTICIPANTE_EVENTO = "PPE";
    public static final String PARTICIPANTE_EVENTO ="PE";
    public static final String MIS_INVITACIONES_EVENTO = "MIE";
    public static final String NO_OWNER = "NOW";

    //TIPOS DE MENU DE EVENTOS
    public static final String MENU_ADMIN_EVENTOS = "MAE";
    public static final String MENU_PART_EVENTOS = "MPE";

    //TAGS COMUNICACION JSON POR INTENT EVENTOS
    public static final String UBICACION_MANEJADA = "ubicacion";
    public static final String EVENTO_MANEJADO = "evento";
    public static final String DEPORTE_MANEJADO = "deporte";
    public static final String GENERO_MANEJADO = "genero";
    public static final String TIPO_EVENTO = "tipoEvento";
    public static final String TIPO_MENU = "menu";
    public static final String SERVICIO_EVENTO = "servicioEvento";
    public static final String OWNER_EVENTO = "duenoEvento";

    //TAGS DE COMUNICACION DE CALLBACKS
    public static final String CALLBACK_TRAE_UBICACION_EVENTO = "cbTraeUbicacionEvento";

    //TAGS PARA MUESTRA DE DIFERENTES ERRORES
    public static final String ERROR_TRAER_UBICACION_EVENTO = "traerUbicacionEvento";
}
