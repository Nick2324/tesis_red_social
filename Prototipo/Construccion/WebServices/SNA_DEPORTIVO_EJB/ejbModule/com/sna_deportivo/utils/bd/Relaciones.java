/**
 * 
 */
package com.sna_deportivo.utils.bd;

/**
 * 
 * Clase que contiene las constantes relacionadas a las relaciones en la
 * base de datos
 * 
 * @author Nicolas Mauricio Garcia Garzon
 * @version 1.0
 *
 */
public class Relaciones {
	//*****************************Usuarios*******************************
	/**
	 * Relacion entre {@link Entidades#USUARIO} y {@link Entidades#DEPORTE}
	 */
	public static final String PRACTICADEPORTE = "R_PracticaDeporte";
	
	/**
	 * Relacion entre {@link Entidades#USUARIO} y {@link Entidades#ROL}
	 */
	public static final String ASUMEROL = "R_AsumeRol";
	
	//*****************************Deportes*******************************
	/**
	 * Relacion entre {@link Entidades#DEPORTE} y {@link Entidades#GENERO}
	 */
	public static final String CATEGORIAGENERO = "R_CategoriaGenero";
	
	/**
	 * Relacion entre {@link Entidades#DEPORTE}, {@link Entidades#IMPLEMENTO} y 
	 * {@link Entidades#GENERO}
	 */
	public static final String IMPLEMENTOGENERODEPORTE = "R_ImplementoGeneroDeporte";
	
	/**
	 * Relacion entre {@link Entidades#DEPORTE} y {@link Entidades#TACTICA}
	 */
	public static final String UTILIZATACTICA = "R_UtilizaTactica";
	
	/**
	 * Relacion entre {@link Entidades#DEPORTE} y {@link Entidades#MOVIMIENTOTECNICO}
	 */
	public static final String UTILIZATECNICA = "R_UtilizaTecnica";
	
	/**
	 * Relacion entre {@link Entidades#MOVIMIENTOTECNICO} y {@link Entidades#MOVIMIENTOTECNICO},
	 * más especializado
	 */
	public static final String TECNICAESPECIALIZADA = "R_TecnicaEspecializada";
	
	/**
	 * Relacion entre {@link Entidades#DEPORTE} y {@link Entidades#ESTILOJUEGO}
	 */
	public static final String TIENEESTILODEPORTE = "R_TieneEstiloDeporte";
	
	/**
	 * Relacion entre {@link Entidades#DEPORTE} y {@link Entidades#POSICION}
	 */
	public static final String POSICIONDEPORTE = "R_PosicionDeporte";
	
	/**
	 * Relacion entre {@link Entidades#DEPORTE} y {@link Entidades#POSICIONUSUARIODEPORTE}
	 */
	public static final String USUARIOPOSICIONDEPORTE = "R_UsuarioPosicionDeporte";
	
	/**
	 * Relacion entre {@link Entidades#DEPORTE} y {@link Entidades#CLASIFICACIONDEPORTE}
	 */
	public static final String CATEGORIACLASIFICACION = "R_CategoriaClasificacion";
	
	//************************Eventos deportivos**************************
	/**
	 * 
	 * Relacion entre un {@link Entidades#EVENTODEPORTIVO} y su 
	 * {@link Entidades#USUARIO} creador
	 * 
	 */
	public static final String CREAEVENTO = "R_CreaEvento";	
	/**
	 * 
	 * Mapea la relacion n-aria entre {@link Entidades#DEPORTEEVENTO} y las siguientes:
	 * 
	 * - {@link Entidades#DEPORTE}
	 * - {@link Entidades#GENERO}
	 * - {@link Entidades#DEPORTE}
	 * - {@link Entidades#EVENTODEPORTIVO}
	 * - {@link Entidades#UBICACION}
	 * 
	 */
	public static final String DESCRIPCIONEVENTO = "R_DescripcionEvento";
	/**
	 * 
	 * Relacion entre {@link Entidades#USUARIO} y {@link Entidades#DEPORTEEVENTO}
	 * 
	 */
	public static final String PARTICIPANTEEVENTO = "R_ParticipaEvento";
	/**
	 * Relacion existente entre dos {@link Entidades#EVENTODEPORTIVO} donde
	 * uno de los eventos contiene otros
	 */
	public static final String CONTIENEEVENTO = "R_ContieneEvento";
	/**
	 * Relacion que existe entre un usuario y un evento con concepto de
	 * solicitud de participar en el evento. Relacion entre {@link Entidades#DEPORTEEVENTO}
	 * y {@link Entidades#USUARIO}
	 */
	public static final String SOLICITAPARTICIPAR = "R_SolicitaParticipar";
	/**
	 * Relacion que existe entre un usuario y un evento con concepto de
	 * invitacion a participar en el evento. Relacion entre {@link Entidades#DEPORTEEVENTO}
	 * y {@link Entidades#USUARIO}
	 */
	public static final String INVITADOAPARTICIPAR = "R_InvitadoAParticipar";
	//****************************Ubicaciones******************************
	/**
	 * 
	 * Relacion n-aria entre {@link Entidades#LUGARPRACTICA}, {@link Entidades#PAIS}
	 *  y {@link Entidades#CIUDAD}
	 * 
	 */
	public static final String  COMPLETAUBICACION = "R_CompletaUbicacion";
	/**
	 * 
	 * Une a un Pais con su respectiva Ciudad
	 * 
	 */
	public static final String PAISCIUDAD = "R_PaisCiudad";
	/**
	 * 
	 * Relacion n-aria entre {@link Entidades#UBICACION}, {@link Entidades#DEPORTE} y 
	 * {@link Entidades#GENERO}
	 * 
	 */
	public static final String PRACTICADOEN = "R_PracticadoEn";
	/**
	 * 
	 * Une una {@link Entidades#UBICACIONUSUARIO} con un {@link Entidades#USUARIO}
	 * 
	 */
	public static final String UBICACIONUSUARIO = "R_UbicacionUsuario";
	
	private Relaciones(){}
	
}
