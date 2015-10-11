package com.sna_deportivo.utils.bd;

import java.util.ArrayList;

import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;

public class RelacionSNS {

	//SE MANEJA TOMANDO COMO REFERENCIA EL OBJETO
	//QUE MANEJA EL DAO
	public static String DIRECCION_SALIDA = ">";
	public static String DIRECCION_ENTRADA = "<";
	
	private String relacion;
	private String identificadorRelacion;
	private ArrayList<ObjectSNSDeportivo> objetosRelacion;
	private ObjectSNSDeportivo propiedadesRelacion;
	private String direccion;
	
	public RelacionSNS(String relacion,
					   String identificadorRelacion,
					   String direccion){
		this.relacion = relacion;
		this.identificadorRelacion = identificadorRelacion;
		this.objetosRelacion = new ArrayList<ObjectSNSDeportivo>();
		this.direccion = direccion;
	}
	
	public RelacionSNS(String relacion,
			   String identificadorRelacion,
			   String direccion,
			   ArrayList<ObjectSNSDeportivo> objetosRelacion){
		this.relacion = relacion;
		this.objetosRelacion = objetosRelacion;
		this.identificadorRelacion = identificadorRelacion;
		this.direccion = direccion;
	}
	
	public RelacionSNS(String relacion,
					   String identificadorRelacion,
					   String direccion,
					   ObjectSNSDeportivo objetosRelacion){
		this.relacion = relacion;
		this.objetosRelacion = new ArrayList<ObjectSNSDeportivo>();
		this.objetosRelacion.add(objetosRelacion);
		this.identificadorRelacion = identificadorRelacion;
		this.direccion = direccion;
	}
	
	public RelacionSNS(String relacion,
			   		   String identificadorRelacion,
			   		   String direccion,
			   		   ArrayList<ObjectSNSDeportivo> objetosRelacion,
			   		   ObjectSNSDeportivo propiedadesRelacion){
		this.relacion = relacion;
		this.objetosRelacion = objetosRelacion;
		this.identificadorRelacion = identificadorRelacion;
		this.propiedadesRelacion = propiedadesRelacion;
	}
	
	public void setRelacion(String relacion) {
		this.relacion = relacion;
	}
	
	public void setObjetoRelacion(ObjectSNSDeportivo objeto){
		this.objetosRelacion.clear();
		this.objetosRelacion.add(objeto);
	}
	
	public void setObjetosRelacion(ArrayList<ObjectSNSDeportivo> objetosRelacion) {
		this.objetosRelacion = objetosRelacion;
	}
	
	public void setPropiedadesRelacion(ObjectSNSDeportivo propiedadesRelacion) {
		this.propiedadesRelacion = propiedadesRelacion;
	}
	
	public void setIdentificadorRelacion(String identificadorRelacion) {
		this.identificadorRelacion = identificadorRelacion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getRelacion() {
		return relacion;
	}
	
	public ObjectSNSDeportivo getObjetoRelacion(){
		if(this.objetosRelacion.size() == 1){
			return this.objetosRelacion.get(0);
		}else{
			return null;
		}
	}
	
	public ArrayList<ObjectSNSDeportivo> getObjetosRelacion() {
		return objetosRelacion;
	}
	
	public ObjectSNSDeportivo getPropiedadesRelacion() {
		return propiedadesRelacion;
	}
	
	public String getIdentificadorRelacion() {
		return identificadorRelacion;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public String stringJson(){
		StringBuilder json = new StringBuilder();
		if(RelacionSNS.DIRECCION_ENTRADA.equals(this.direccion)){
			json.append(RelacionSNS.DIRECCION_ENTRADA);
		}
		json.append("-");
		json.append("[");
		json.append(this.identificadorRelacion);
		json.append(":");
		json.append(this.relacion);
		if(this.propiedadesRelacion != null){
			json.append(this.propiedadesRelacion.stringJson());
		}
		json.append("]");
		json.append("-");
		if(RelacionSNS.DIRECCION_SALIDA.equals(this.direccion)){
			json.append(RelacionSNS.DIRECCION_SALIDA);
		}
		return json.toString();
	}
	
	public ArrayList<String> stringJsonPatrones(ObjectSNSDeportivoDAO dao){
		ArrayList<String> patrones = new ArrayList<String>();
		if(this.objetosRelacion != null &&
		   this.objetosRelacion.size() > 0 &&
		   dao != null){
			int i = 0;
			for(ObjectSNSDeportivo obj:this.objetosRelacion){
				dao.setObjetcSNSDeportivo(obj);
				patrones.add(this.stringJson() + 
						dao.producirNodoMathIndice(i));
			}
		}
		return patrones;
	}
	
}
