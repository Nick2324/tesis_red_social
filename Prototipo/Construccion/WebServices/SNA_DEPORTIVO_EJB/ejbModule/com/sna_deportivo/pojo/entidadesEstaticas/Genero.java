package com.sna_deportivo.pojo.entidadesEstaticas;

import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.excepciones.AtributoInexistenteException;
import com.sna_deportivo.utils.json.JsonUtils;

public class Genero extends ObjectSNSDeportivo{

	private Integer id;
	private String nombre;
	private String descripcion;
	
	public Integer getId(){
		return this.id;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public String getDescripcion(){
		return this.descripcion;
	}
	
	@Override
	public String stringJson() {
		Object[] propiedades = new Object[]{
				this.id,
				this.nombre,
				this.descripcion
		};
		return "{"+
				JsonUtils.propiedadNulaTDPrimitivo("id",this.id,propiedades,0)+
				JsonUtils.propiedadNula("nombre",this.nombre,propiedades,1)+
				JsonUtils.propiedadNula("descripcion",this.descripcion,propiedades,2)
				+"}";
	}

	@Override
	public ObjectSNSDeportivo setNullObject() {
		this.id = null;
		this.nombre = null;
		this.descripcion = null;
		return this;
	}

	@Override
	protected String retornarToString() {
		return this.toString();
	}

	@Override
	protected boolean esAtributo(String atributo) {
		if(atributo != null &&
		   atributo.equals("id") ||
		   atributo.equals("nombre") ||
		   atributo.equals("descripcion"))
			return true;
		return false;
	}

	@Override
	protected boolean setAtributo(String atributo, Object[] valor) {
		boolean asignado = false;
		if(atributo != null){
			if(atributo.equals("id")){
				this.id = (String)valor[0]==null || "null".equals((String)valor[0])?
							null:Integer.parseInt((String)valor[0]);
				asignado = true;
			}else if(atributo.equals("nombre")){
				this.nombre = JsonUtils.propiedadNulaBackwards((String)valor[0]);
				asignado = true;
			}else if(atributo.equals("descripcion")){
				this.descripcion = JsonUtils.propiedadNulaBackwards((String)valor[0]);
				asignado = true;
			}
		}
		return asignado;
	}

	@Override
	protected String get(String atributo) throws AtributoInexistenteException {
		if(atributo != null){
			if(atributo.equals("id")){
				return this.id.toString();
			}else if(atributo.equals("nombre")){
				return this.nombre;
			}else if(atributo.equals("descripcion")){
				return this.descripcion;
			}
		}
		return null;
	}
	
	@Override
	public boolean equals(Object obj){
		boolean igual = true;
		if(obj instanceof Genero){
			Genero g = (Genero)obj;
			if(igual && this.id != g.getId()){
				igual = false;
			}
			if(igual && !this.nombre.equals(g.getNombre())){
				igual = false;
			}
			if(igual && !this.descripcion.equals(g.getDescripcion())){
				igual = false;
			}
		}else{
			igual = false;
		}
		return igual;
	}

	@Override
	public Class<?> getTipoDatoPropiedad(String propiedad) {
		// TODO Auto-generated method stub
		return null;
	}

}
