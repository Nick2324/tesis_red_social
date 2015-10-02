package com.sna_deportivo.utils.gr;

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
		System.out.println("*********************Y AHORA???**************************");
		System.out.println(this.objectSNSDeportivo.stringJson());
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
