/**
 * 
 */
package com.sna_deportivo.utils;

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
	public static final String EVENTODEDEPORTE = "R_EventoDeporte";
	/**
	 * 
	 * Relacion entre {@link Entidades#USUARIO} y {@link Entidades#DEPORTEEVENTO}
	 * 
	 */
	public static final String PARTICIPANTEEVENTO = "R_ParticipaEvento";
	/**
	 * 
	 * Relacion n-aria entre {@link Entidades#LUGARPRACTICA}, {@link Entidades#PAIS}
	 *  y {@link Entidades#CIUDAD}
	 * 
	 */
	
	//****************************Ubicaciones******************************
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
	 * Une una {@link Entidades#UBICACIONDEPORTE} con un {@link Entidades#USUARIO}
	 * 
	 */
	public static final String UBICACIONUSUARIO = "R_UbicacionUsuario";
	
	private Relaciones(){}
	
}
