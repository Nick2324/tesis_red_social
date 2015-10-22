package com.sna_deportivo.pojo.ubicacion;

import javax.xml.bind.annotation.XmlRootElement;

import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.excepciones.AtributoInexistenteException;
import com.sna_deportivo.utils.json.JsonUtils;

@XmlRootElement
public class Ubicacion  extends ObjectSNSDeportivo{
	
	private Integer id;
	private Pais pais;
	private Ciudad ciudad;
	private LugarPractica lugar;
	
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	public Ciudad getCiudad() {
		return ciudad;
	}
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}
	public LugarPractica getLugar() {
		return lugar;
	}
	public void setLugar(LugarPractica lugar) {
		this.lugar = lugar;
	}
	@Override
	public String stringJson() {
		Object[] propiedades = new Object[]{
				this.id
		};
		return "{"+
				JsonUtils.propiedadNulaTDPrimitivo("id", this.id, propiedades,0)
			    +"}";
	}
	@Override
	public ObjectSNSDeportivo setNullObject() {
		this.pais = null;
		this.ciudad = null;
		this.lugar = null;
		return this;
	}
	@Override
	public Class<?> getTipoDatoPropiedad(String propiedad) {
		Class<?> retorno = null;
		if(propiedad.equals("id")){
			retorno = this.id.getClass();
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
		  (atributo.equals("pais") || 
		   atributo.equals("ciudad") || 
		   atributo.equals("lugar")||
		   atributo.equals("id"))){
			return true;
		}
		return false;
	}
	@Override
	protected boolean setAtributo(String atributo, Object[] valor) {
		boolean retorno = false;
		if(atributo.equals("pais") || 
		   atributo.equals("ciudad") || 
		   atributo.equals("lugar")){
			retorno = true;
		}else if(atributo.equals("id")){
			this.id = (Integer)JsonUtils.
				propiedadNulaBackwardsTDPrimi((String)valor[0],Integer.class);
			retorno = true;
		}
		return retorno;
	}
	@Override
	protected String get(String atributo) throws AtributoInexistenteException {
		String retorno = null;
		if(atributo.equals("id")){
		   retorno = this.id.toString();
		}
		return retorno;
	}
	
	

}
