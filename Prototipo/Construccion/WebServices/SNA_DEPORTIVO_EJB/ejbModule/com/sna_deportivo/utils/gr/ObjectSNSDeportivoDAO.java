package com.sna_deportivo.utils.gr;

import com.sna_deportivo.utils.bd.BDUtils;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public class ObjectSNSDeportivoDAO {

	protected ObjectSNSDeportivo objectSNSDeportivo;
	protected FactoryObjectSNSDeportivo factoryOSNS;
	protected String tipoObjetoSNS;
	protected String identificadorQueries;
	
	public ObjectSNSDeportivo[] getObjetoSNSDeportivoDB() throws BDException{
		ObjectSNSDeportivo[] objetos = null;
		String where = BDUtils.condicionWhere(this.objectSNSDeportivo, identificadorQueries);
		System.out.println("Si llego "+where+" objectSNSDEPORTIVO??");
		if(this.objectSNSDeportivo == null)
			System.out.println("El objeto es nulo");
		else
			System.out.println("El objeto no es nulo!!!!!!!!!!!******************");
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
	
}
