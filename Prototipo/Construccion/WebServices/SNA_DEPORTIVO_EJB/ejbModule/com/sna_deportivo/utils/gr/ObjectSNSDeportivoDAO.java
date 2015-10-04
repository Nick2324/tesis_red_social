package com.sna_deportivo.utils.gr;

import java.util.ArrayList;

import com.sna_deportivo.utils.bd.BDUtils;
import com.sna_deportivo.utils.bd.RelacionSNS;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public abstract class ObjectSNSDeportivoDAO {

	protected ObjectSNSDeportivo objectSNSDeportivo;
	protected FactoryObjectSNSDeportivo factoryOSNS;
	protected String tipoObjetoSNS;
	protected String identificadorQueries;
	
	public ObjectSNSDeportivoDAO(){
		this.setUpDAOGeneral();
	}
	
	public ObjectSNSDeportivo[] getObjetoSNSDeportivoDB() throws BDException{
		ObjectSNSDeportivo[] objetos = null;
		String where = BDUtils.condicionWhere(this.objectSNSDeportivo, identificadorQueries);
		if(where != null || this.objectSNSDeportivo == null){
			StringBuilder query = new StringBuilder("MATCH ("+identificadorQueries+":");
			query.append(tipoObjetoSNS);
			query.append(")");
			if(where != null)
				query.append(where);
			query.append("RETURN "+identificadorQueries);
			try{
				System.out.println(query.toString());
				Object[] resultadoQuery = BDUtils.
						ejecutarQueryREST(query.toString());
				objetos = new ObjectSNSDeportivo[resultadoQuery.length];
				for(int i = 0; i < resultadoQuery.length; i++){
					JsonObject datos =
							(JsonObject)BDUtils.obtenerRestRegistro(resultadoQuery[i]).
										getPropiedades().
										get("data")[0];
					objetos[i] = this.factoryOSNS.getObjetoSNS();
					objetos[i].deserializarJson(datos);
				}
			}catch(BDException e){
				throw e;
			}catch(ExcepcionJsonDeserializacion e){
				e.printStackTrace();
			}
		}
		
		return objetos;
	}
	
	public String producirNodoMatch(){
		StringBuilder nodo = new StringBuilder();
		nodo.append("(");
		nodo.append(this.identificadorQueries);
		nodo.append(":");
		nodo.append(this.tipoObjetoSNS);
		nodo.append(this.objectSNSDeportivo.stringJson());
		nodo.append(")");
		return nodo.toString();
	}
	
	public boolean crearRelacion(RelacionSNS relacionACrear,
							     ProductorSNSDeportivo productor,
							     String direccion){
		if(this.objectSNSDeportivo != null &&
		   relacionACrear != null &&
		   productor != null &&
		   direccion != null){
			String queryNodoPrincipal = this.producirNodoMatch();
			StringBuilder queryIndividual = new StringBuilder();
			for(ObjectSNSDeportivo obj:relacionACrear.getObjetosRelacion()){
				ObjectSNSDeportivoDAO dao = 
						productor.
						producirFacObjetoSNS(obj.getClass().getSimpleName()).
						getObjetoSNSDAO();
				dao.setObjetcSNSDeportivo(obj);
				queryIndividual.append("MATCH ");
				queryIndividual.append(queryNodoPrincipal);
				queryIndividual.append(",");
				queryIndividual.append(dao.producirNodoMatch());
				queryIndividual.append(" CREATE ");
				queryIndividual.append("(");
				queryIndividual.append(this.identificadorQueries);
				queryIndividual.append(")");
				if(RelacionSNS.DIRECCION_ENTRADA.equals(direccion)){
					queryIndividual.append(RelacionSNS.DIRECCION_ENTRADA);
				}
				queryIndividual.append("-");
				queryIndividual.append(relacionACrear.stringJson());
				queryIndividual.append("-");
				if(RelacionSNS.DIRECCION_SALIDA.equals(direccion)){
					queryIndividual.append(RelacionSNS.DIRECCION_SALIDA);
				}
				queryIndividual.append("(");
				queryIndividual.append(dao.getIdentificadorQueries());
				queryIndividual.append(")");
				try{
					BDUtils.ejecutarQueryREST(queryIndividual.toString());
				}catch(BDException e){
					return false;
				}
			}
			
			return true;
			
		}
		
		return false;
		
	}
	/**
	 * Retorna los nodos unidos al objeto de éste DAO en la BD
	 * @param relacionACrear Lista de relaciones que se desean
	 * @param productor Lista de productores de nodos
	 * @param direccion Lista de direcciones de las relaciones del primer parametro
	 * @return Retorna los objetos nodo unidos al nodo objeto manejado por éste DAO.
	 * El objeto manejado por éste DAO es asignado al valor que se ha obtenido después
	 * de la búsqueda
	 */
	protected ArrayList<ArrayList<ObjectSNSDeportivo>> 
				obtenerRelacionesObjSNS(ArrayList<RelacionSNS> relacionAObtener,
		     							ArrayList<ProductorSNSDeportivo> productor,
		     							ArrayList<String> direccion){
		StringBuilder query = new StringBuilder();
		StringBuilder clausulaReturn = new StringBuilder("RETURN ");
		String nodoManejado = this.producirNodoMatch();
		query.append("MATCH ");
		RelacionSNS relacionTratada;
		ObjectSNSDeportivoDAO dao;
		String nodoManejado2;
		for(int i = 0; i < relacionAObtener.size(); i++){
			int j = 0;
			for(ObjectSNSDeportivo obj:relacionAObtener.get(i).getObjetosRelacion()){
				dao = productor.get(i).producirFacObjetoSNS(
						obj.getClass().getSimpleName()).
						getObjetoSNSDAO();
				dao.setObjetcSNSDeportivo(obj);
				nodoManejado2 = dao.producirNodoMatch();
				if(RelacionSNS.DIRECCION_ENTRADA.equals(direccion.get(i))){
					query.append(RelacionSNS.DIRECCION_ENTRADA);
				}
				//query.append(c)
				query.append("-");
				//query.append(relacionACrear.stringJson());
				query.append("-");
				if(RelacionSNS.DIRECCION_SALIDA.equals(direccion.get(i))){
					query.append(RelacionSNS.DIRECCION_SALIDA);
				}
				query.append(",");
			}
		}
		return null;
	}
	
	protected abstract void setUpDAOGeneral();
	
	public abstract ObjectSNSDeportivo crearObjetoSNS();
	
	public void setObjetcSNSDeportivo(ObjectSNSDeportivo objectSNSDeportivo){
		this.objectSNSDeportivo = objectSNSDeportivo;
	}
	
	public void setFactoryOSNS(FactoryObjectSNSDeportivo factoryOSNS){
		this.factoryOSNS = factoryOSNS;
	}
	
	public ObjectSNSDeportivo getObjetcSNSDeportivo(){
		return this.objectSNSDeportivo;
	}
	
	public FactoryObjectSNSDeportivo getFactoryOSNS(){
		return this.factoryOSNS;
	}
	
	public String getIdentificadorQueries() {
		return identificadorQueries;
	}
	
	public static void main(String[] args){
		BDUtils.ejecutarQueryREST("MATCH (evento:E_DeporteEvento),(evento)-[r]-(ev:E_EventoDeportivo{id:1}), (evento)-[r2]-(dep:E_Deporte), (evento)-[r3]-(us:E_Usuario) RETURN collect(DISTINCT evento) as eventos ,collect(DISTINCT ev) as evs, collect(DISTINCT dep) as dep,collect(DISTINCT us) as us");
	}
	
}
