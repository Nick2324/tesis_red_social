package com.sna_deportivo.pojo.evento;

import java.util.ArrayList;

import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

/**
 * 
 * Composite para eventos. Puede alojar diferentes tipos de eventos deportivos
 * 
 * @author Nicolas Mauricio Garcia Garzon
 * @version 1.0
 * @see Evento
 * @see PracticaDeportiva
 *
 */

public class EventoDeportivo extends Evento{

	private ArrayList<Evento> eventosInternos;
	
	/**
	 * 
	 * Constructor por defecto
	 * 
	 */
	
	public EventoDeportivo(){
		super();
		eventosInternos = new ArrayList<Evento>();
	}
	
	/**
	 * 
	 * Retorna un evento interno segun el estado pasado sobre el parametro
	 * eventoBuscado
	 * 
	 * @param eventoBuscado Evento con estado a buscar
	 * @return Evento buscado
	 */
	
	public Evento getEvento(Evento eventoBuscado){
		
		for(Evento e:eventosInternos){
			if(e.equals(eventoBuscado))
				return e;
		}
		
		return null;
		
	}
	
	/**
	 * 
	 * Adiciona un evento interno
	 * 
	 * @param eventoAdicion Evento a adicionar
	 */
	
	public void setEvento(Evento eventoAdicion){
		eventosInternos.add(eventoAdicion);
	}
	
	@Override
	protected boolean setAtributo(String atributo,Object[] valor){
		if(!super.setAtributo(atributo, valor))
			if(atributo.equals("eventosInternos")){
				eventosInternos.clear();
				for(Object eventoInterno:valor){
					//Cambiar por Factory, no se me ocurre otro modo
					Evento e = new PracticaDeportiva();
					try {
						e.deserializarJson((JsonObject)eventoInterno);
					} catch (ExcepcionJsonDeserializacion e1) {
						e1.printStackTrace();
						//Que hacer con esta?
					}
					eventosInternos.add(e);
				}
				return true;
			}
		return false;
	}
	
	@Override
	public String toString(){
		return this.stringJson();
	}
	
	@Override 
	public String stringJson(){
		String retorno = super.toString();
		retorno = retorno.substring(0, retorno.length() - 1) + ",eventosInternos:[";
		for(Evento e:eventosInternos)
			retorno += e.toString() + ",";
		return retorno.substring(0, retorno.length() - 1) + "]}";
	}
	
	/*@Override
	public JsonObject serializarJson() {
		StringBuilder stringSerializacion = new StringBuilder(
				super.toString().substring(0,super.toString().length() - 1));
		stringSerializacion.append(",eventosInternos:[");
		for(Evento e:this.eventosInternos){
			stringSerializacion.append(e.toString());
			stringSerializacion.append(",");
		}
		
		return JsonUtils.JsonStringToObject(stringSerializacion.substring(
				0,stringSerializacion.length() - 1) + "]");
	}*/
	
}
