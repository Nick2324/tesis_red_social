package com.sna_deportivo.utils.gr;

import java.util.ArrayList;

import com.sna_deportivo.utils.gr.excepciones.AtributoInexistenteException;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonSerializable;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public abstract class ObjectSNSDeportivo implements JsonSerializable{

	protected String[] aRetornar;
	protected String[] separadores;
	
	public abstract String stringJson();
	
	public abstract ObjectSNSDeportivo setNullObject();
	
	public void retornoToString(String[] aRetornar){
		this.aRetornar = aRetornar;
	}
	
	public void retornoToStringSeparadores(String[] aRetornar,
										   String[] separadores) throws Exception{
		if((aRetornar == null && separadores == null) ||
		   (aRetornar != null && separadores != null &&
		    aRetornar.length - 1 == separadores.length)){
			this.separadores = separadores;
		}
		this.aRetornar = aRetornar;
	}
	
	public String toString(){
		if(this.retornoToStringCondicionado()){
			StringBuilder componerRetorno = new StringBuilder("");
			for(int i = 0; i < this.aRetornar.length; i++){
				try {
					componerRetorno.append(this.get(this.aRetornar[i]));
				} catch (AtributoInexistenteException e) {
					e.printStackTrace();
					componerRetorno = new StringBuilder("");
					break;
				}
				if(i < this.aRetornar.length - 1){
					if(this.separadores != null && this.separadores.length > 0){
						componerRetorno.append(this.separadores[i]);
					}else{
						componerRetorno.append(" ");
					}
				}
			}
			return componerRetorno.toString();
		}
		
		return this.retornarToString();
		
	}
	
	@Override
	public final JsonObject serializarJson() {
		return JsonUtils.JsonStringToObject(this.stringJson());
	}
	
	@Override
	public final void deserializarJson(JsonObject json) throws ExcepcionJsonDeserializacion {
		this.setNullObject();
		for(String propiedad:json.getPropiedades().keySet()){
			if(!(this.esAtributo(propiedad) &&
				 this.setAtributo(propiedad,json.getPropiedades().get(propiedad)))){
				throw new ExcepcionJsonDeserializacion();
			}
		}
	}
	
	protected final String stringJsonArray(String identificador,
									 ArrayList<ObjectSNSDeportivo> array){
		//HAY QUE BUSCAR UN MECANISMO PARA GUARDAR EL TIPO DE DATO
		StringBuilder stringSerializacion = new StringBuilder(identificador);
		stringSerializacion.append(":[");
		if(array != null && array.size() > 0){
			for(ObjectSNSDeportivo obj:array){
				stringSerializacion.append(obj.stringJson());
				stringSerializacion.append(",");
			}
			return stringSerializacion.substring(0,stringSerializacion.length() - 1) + "]";
		}else{
			return stringSerializacion.toString() + "]";
		}
	}
	
	protected abstract String retornarToString();
	
	protected abstract boolean esAtributo(String atributo);
	
	protected abstract boolean setAtributo(String atributo,Object[] valor);
	
	protected abstract String get(String atributo) throws AtributoInexistenteException;
	
	private boolean retornoToStringCondicionado(){
		if(this.aRetornar != null && this.aRetornar.length > 0){
			return true;
		}else{
			return false;
		}
	}
}
