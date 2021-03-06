package com.sna_deportivo.utils.bd;

/**
 * 
 * Clase que contiene el nombre de las entidades en la base de datos
 * 
 * @author Luis Felipe Gonzales Moreno
 * @version 1.0
 *
 */

public class Entidades {
	
	//*************************Generales****************************
	/**
	 * 
	 * Hace referencia a la entidad RecursoExterno en la base de datos
	 * 
	 */
	public static final String RECURSOEXTERNO = "E_RecursoExterno";
	/**
	 * 
	 * Hace referencia a la relacion entre Implemento y Genero
	 * 
	 */
	
	public static final String GENERO = "E_Genero";
	/**
	 * 
	 * Contiene el nombre de la entidad Ciudad en la base de datos
	 * 
	 */
	
	//*************************Usuarios*****************************
	/**
	 * 
	 * Contiene el nombre de la entidad Usuario en la base de datos
	 * 
	 */
	public static final String USUARIO = "E_Usuario";
	
	public static final String ROL = "E_Rol";
	
	public static final String PERMISO = "E_Permiso";
	
	//*************************Ubicaciones**************************
	/**
	 * 
	 * Contiene el nombre de la entidad Pais en la base de datos
	 * 
	 */
	public static final String PAIS = "E_Pais";
	/**
	 * 
	 * Contiene el nombre de la entidad Genero en la base de datos
	 * 
	 */
	public static final String CIUDAD = "E_Ciudad";
	/**
	 * 
	 * Hace referencia a la relacion entre Ciudad, Genero y LugarEntrenamiento
	 * con las posiciones exactas de otras entidades (eg. Deporte)
	 * 
	 */
	public static final String UBICACION = "E_Ubicacion";
	/**
	 * 
	 * Hace referencia a la entidad LugarPractica en la base de datos
	 * 
	 */
	public static final String LUGARPRACTICA = "E_LugarPractica";
	/**
	 * 
	 * Hace referencia a la relacion entre una Ubicacion y un Deporte
	 * 
	 */
	public static final String UBICACIONDEPORTE = "E_UbicacionDeporte";
	/**
	 * 
	 * Hace referencia a las ubicaciones registradas/visitadas por el usuario
	 * 
	 */
	public static final String UBICACIONUSUARIO = "E_UbicacionUsuario";
	
	//************************Eventos deportivos************************
	/**
	 * 
	 * Hace referencia a la relacion entre una Ubicacion, un EventoDeportivo,
	 * Usuario y Genero
	 * 
	 */
	public static final String DEPORTEEVENTO = "E_DeporteEvento";
	/**
	 * 
	 * Hace referencia a la entidad EventoDeportivo en la base de datos
	 * 
	 */
	public static final String EVENTODEPORTIVO = "E_EventoDeportivo";

	//***************************Deporte********************************
	/**
	 * 
	 * Contiene el nombre de la entidad Deporte en la base de datos
	 * 
	 */
	public static final String DEPORTE = "E_Deporte";
	/**
	 * 
	 * Hace referencia a la entidad ClasificacionDeporte en la base de datos
	 * 
	 */
	public static final String CLASIFICACIONDEPORTE = "E_ClasificacionDeporte";
	/**
	 * 
	 * Hace referencia a la entidad MovimientoTecnico en la base de datos
	 * 
	 */
	public static final String MOVIMIENTOTECNICO = "E_MovimientoTecnico";
	/**
	 * 
	 * Hace referencia a la entidad Tactica en la base de datos
	 * 
	 */
	public static final String TACTICA = "E_Tactica";
	/**
	 * 
	 * Hace referencia a la entidad Posicion en la base de datos
	 * 
	 */
	public static final String POSICION = "E_Posicion";
	/**
	 * 
	 * Hace referencia a la entidad EstiloJuego en la base de datos
	 * 
	 */
	public static final String ESTILOJUEGO = "E_EstiloJuego";
	/**
	 * 
	 * Hace referencia a la entidad RecursoExterno en la base de datos
	 * 
	 */
	public static final String IMPLEMENTOSEGUNGENERO = "E_ImplementoSegunGenero";
	/**
	 * 
	 * Hace referencia a la entidad Implemento en la base de datos
	 * 
	 */
	public static final String IMPLEMENTO = "E_Implemento";
	/**
	 * 
	 * Hace referencia a la relacion entre Posicion, Usuario y Deporte
	 * 
	 */
	public static final String POSICIONUSUARIODEPORTE = "E_PosicionUsuarioDeporte";
	
	private Entidades(){}
	
}
