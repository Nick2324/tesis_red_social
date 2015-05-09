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
	 * Relación entre un {@link Entidades.#EVENTO} y su 
	 * {@link Entidades.#USUARIO} creador
	 * 
	 */
	public static final String CREAEVENTO = "";	
	/**
	 * 
	 * Mapea la relación n-aria entre {@link Entidades.#DEPORTEEVENTO} y las siguientes:
	 * 
	 * - {@link Entidades.#DEPORTE}
	 * - {@link Entidades.#GENERO}
	 * - {@link Entidades.#DEPORTE}
	 * - {@link Entidades.#EVENTODEPORTIVO}
	 * - {@link Entidades.#UBICACION}
	 * 
	 */
	public static final String EVENTODEDEPORTE = "";
	/**
	 * 
	 * Relación entre {@link Entidades.#USUARIO} y {@link Entidades.#DEPORTEEVENTO}
	 * 
	 */
	public static final String PARTICIPANTEEVENTO = "";
	
	
	private Relaciones(){}
	
}
