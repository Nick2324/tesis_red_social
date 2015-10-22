package com.sna_deportivo.pojo.ubicacion;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.excepciones.AtributoInexistenteException;
import com.sna_deportivo.utils.json.JsonUtils;

@XmlRootElement
public class Ciudad extends ObjectSNSDeportivo{
	
	private Integer id;
	private String nombre;
	private ArrayList<LugarPractica> ubicaciones;
	private Float latitud;
	private Float longitud;
	private Integer zipCode;
	
	public Ciudad(Map<String, Object[]> objeto) {
		id = Integer.valueOf((String) objeto.get("id")[0]);
		nombre = (String) objeto.get("nombre")[0];
		String[] coordenadas = ((String) objeto.get("coordenada")[0]).split(",");
		latitud = Float.valueOf(coordenadas[0]);
		longitud  = Float.valueOf(coordenadas[1]);
	}
	public Ciudad() {
	}
	public Integer getZipCode() {
		return zipCode;
	}
	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<LugarPractica> getUbicaciones() {
		return ubicaciones;
	}
	public void setUbicaciones(ArrayList<LugarPractica> ubicaciones) {
		this.ubicaciones = ubicaciones;
	}
	public Float getLatitud() {
		return latitud;
	}
	public void setLatitud(Float latitud) {
		this.latitud = latitud;
	}
	public Float getLongitud() {
		return longitud;
	}
	public void setLongitud(Float longitud) {
		this.longitud = longitud;
	}
	@Override
	public String stringJson() {
		if(!super.switchConvierteAtributosBD){
			Object[] propiedades = new Object[]{
					this.id,
					this.nombre,
					this.latitud,
					this.longitud,
					this.zipCode
			};
			return "{"+
					JsonUtils.propiedadNulaTDPrimitivo("id", this.id, propiedades,0)+
					JsonUtils.propiedadNula("nombre",this.nombre,propiedades,1)+
					JsonUtils.propiedadNulaTDPrimitivo("latitud",this.latitud,propiedades,2)+
					JsonUtils.propiedadNulaTDPrimitivo("longitud",this.longitud,propiedades,3)+
					JsonUtils.propiedadNulaTDPrimitivo("zipCode",this.zipCode,propiedades,4)
					+"}";
		}else{
			String coordenada = null;
			if(this.latitud != null && this.longitud != null){ 
				coordenada = this.latitud+","+this.longitud;
			}
			Object[] propiedades = new Object[]{
					this.id,
					this.nombre,
					coordenada,
					this.zipCode
			};
			return "{"+
					JsonUtils.propiedadNulaTDPrimitivo("id", this.id, propiedades, 0) +
					JsonUtils.propiedadNula("nombre",this.nombre,propiedades,1)+
					JsonUtils.propiedadNula("coordenada",coordenada,propiedades,2)+
					JsonUtils.propiedadNulaTDPrimitivo("zipCode",this.zipCode,propiedades,3)+
				   "}";
		}
	}
	@Override
	public ObjectSNSDeportivo setNullObject() {
		this.id = null;
		this.nombre = null;
		this.ubicaciones = null;
		this.latitud = null;
		this.longitud = null;
		return this;
	}
	@Override
	public Class<?> getTipoDatoPropiedad(String propiedad) {
		Class<?> retorno = null;
		if(propiedad.equals("id")){
			retorno = this.id.getClass();
		}else if(propiedad.equals("nombre")){
			retorno = this.nombre.getClass();
		}else if(propiedad.equals("latitud")){
			retorno = this.latitud.getClass();
		}else if(propiedad.equals("longitud")){
			retorno = this.longitud.getClass();
		}else if(propiedad.equals("zipCode")){
			retorno = this.zipCode.getClass();
		}
		return retorno;
	}
	@Override
	protected String retornarToString() {
		return this.stringJson();
	}
	@Override
	protected boolean esAtributo(String atributo) {
		if(atributo != null &&
		  (atributo.equals("id")||
		   atributo.equals("nombre")||
		   atributo.equals("latitud")||
		   atributo.equals("longitud")||
		   atributo.equals("zipCode"))){
			return true;
		}
		return false;
	}
	@Override
	protected boolean setAtributo(String atributo, Object[] valor) {
		boolean retorno = false;
		if(atributo.equals("id")){
			this.id = (Integer)JsonUtils.propiedadNulaBackwardsTDPrimi(
					(String)valor[0], Integer.class);
			retorno = true;
		}else if(atributo.equals("nombre")){
			this.nombre = JsonUtils.propiedadNulaBackwards((String)valor[0]);
			retorno = true;
		}else if(atributo.equals("latitud")){
			this.latitud = (Float)JsonUtils.propiedadNulaBackwardsTDPrimi(
					(String)valor[0], Float.class);
			retorno = true;
		}else if(atributo.equals("longitud")){
			this.longitud = (Float)JsonUtils.propiedadNulaBackwardsTDPrimi(
					(String)valor[0], Float.class);
			retorno = true;
		}else if(atributo.equals("zipCode")){
			this.zipCode = (Integer)JsonUtils.propiedadNulaBackwardsTDPrimi(
					(String)valor[0], Integer.class);
			retorno = true;
		}
		return retorno;
	}
	@Override
	protected String get(String atributo) throws AtributoInexistenteException {
		String retorno = null;
		if(atributo.equals("id")){
			retorno = this.id.toString();
		}else if(atributo.equals("nombre")){
			retorno = this.nombre;
		}else if(atributo.equals("latitud")){
			retorno = latitud.toString();
		}else if(atributo.equals("longitud")){
			retorno = longitud.toString();
		}else if(atributo.equals("zipCode")){
			retorno = zipCode.toString();
		}
		return retorno;
	}
	
}
