package com.sna_deportivo.utils.gr;

import java.util.ArrayList;

import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
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
					System.out.println(objetos[i].stringJson());
				}
			}catch(BDException e){
				throw e;
			}catch(ExcepcionJsonDeserializacion e){
				e.printStackTrace();
			}
		}
		
		return objetos;
	}
	
	private String producirNodoMatchAbstracto(Integer i, boolean json){
		StringBuilder nodo = new StringBuilder();
		nodo.append("(");
		if(i != null){
			nodo.append(this.identificadorQueries + i);
		}else{
			nodo.append(this.identificadorQueries);
		}
		nodo.append(":");
		nodo.append(this.tipoObjetoSNS);
		if(json){
			nodo.append(this.objectSNSDeportivo.stringJson());
		}
		nodo.append(")");
		return nodo.toString();
	}
	
	public String producirNodoMathIndice(Integer i){
		return this.producirNodoMatchAbstracto(i, true);
	}
	
	public String producirNodoMatchNoJsonIndice(Integer i){
		return this.producirNodoMatchAbstracto(i,false);
	}
	
	public String producirNodoMatch(){
		return this.producirNodoMatchAbstracto(null, true);
	}
	
	public String producirNodoMatchNoJson(){
		return this.producirNodoMatchAbstracto(null, false);
	}
	
	public boolean crearRelacion(RelacionSNS relacionACrear,
							     ProductorSNSDeportivo productor){
		if(this.objectSNSDeportivo != null &&
		   relacionACrear != null &&
		   productor != null){
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
				queryIndividual.append(relacionACrear.stringJson());
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
	
	public boolean mergeRelacion(RelacionSNS relacionACrear,
		     ProductorSNSDeportivo productor){
		if(this.objectSNSDeportivo != null &&
		   relacionACrear != null &&
		   productor != null){
			String queryNodoPrincipal = this.producirNodoMatch();
			StringBuilder queryIndividual = new StringBuilder();
			for(ObjectSNSDeportivo obj:relacionACrear.getObjetosRelacion()){
				ObjectSNSDeportivoDAO dao = 
					productor.
					producirFacObjetoSNS(obj.getClass().getSimpleName()).
					getObjetoSNSDAO();
				dao.setObjetcSNSDeportivo(obj);
				queryIndividual.append("MERGE ");
				queryIndividual.append(queryNodoPrincipal);
				queryIndividual.append(relacionACrear.stringJson());
				queryIndividual.append(dao.producirNodoMatch());
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
	
	public abstract void encontrarObjetoManejado() 
			throws BDException;
	
	public StringBuilder integrarQueryRelacion(StringBuilder query,
											   RelacionSNS relacion,
											   ProductorSNSDeportivo factory){
		ArrayList<String> patrones = new ArrayList<String>();
		
		//Hay que lograr generar los factory desde la relacion
		patrones = relacion.stringJsonPatrones(
				factory.producirFacObjetoSNS(relacion.getObjetoRelacion().
						getClass().getSimpleName()).
				getObjetoSNSDAO());
		for(String patron:patrones){
			query.append(this.identificadorQueries);
			query.append(patron);
			query.append(",");
		}

		return query;
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
	/*protected ArrayList<ArrayList<ObjectSNSDeportivo>> 
				obtenerRelacionesObjSNS(ArrayList<RelacionSNS> relacionAObtener,
		     							ArrayList<ProductorSNSDeportivo> productor,
		     							ArrayList<String> direccion){
		StringBuilder query = new StringBuilder();
		StringBuilder where = new StringBuilder();
		StringBuilder clausulaReturn = new StringBuilder("RETURN ");
		String nodoManejado = this.producirNodoMatch();
		query.append("MATCH ");
		RelacionSNS relacionTratada;
		ObjectSNSDeportivoDAO dao;
		String nodoManejado2;
		for(int i = 0; i < relacionAObtener.size(); i++){
			int j = 0;
			BDUtils.condicionWhere(objetoRedSocial, identificador)
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
	}*/
	
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
	
}
