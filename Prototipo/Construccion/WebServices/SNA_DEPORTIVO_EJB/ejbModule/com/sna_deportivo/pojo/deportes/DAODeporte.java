package com.sna_deportivo.pojo.deportes;

import com.sna_deportivo.utils.bd.BDUtils;
import com.sna_deportivo.utils.bd.Entidades;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;

public class DAODeporte extends ObjectSNSDeportivoDAO{

	private Deporte deporte;

	public DAODeporte(){}
	
	public DAODeporte(Deporte deporte){
		this.deporte = deporte;
	}
	
	public Deporte[] getDeportesDB() throws BDException{
		Deporte[] deportes = null;
		String where = BDUtils.condicionWhere(this.deporte, "deporte");
		if(where != null || this.deporte == null){
			StringBuilder query = new StringBuilder("MATCH (deporte:");
			query.append(Entidades.DEPORTE);
			query.append(")");
			if(where != null)
				query.append(where);
			query.append(" RETURN deporte");
			try{
				Object[] resultadoQuery = BDUtils.
						ejecutarQueryREST(query.toString());
				deportes = new Deporte[resultadoQuery.length];
				for(int i = 0; i < resultadoQuery.length; i++){
					JsonObject datos =
							(JsonObject)BDUtils.obtenerRestRegistro(resultadoQuery[i]).
							getPropiedades().
							get("data")[0];
					deportes[i] = new Deporte();
					deportes[i].deserializarJson(datos);
				}
			}catch (BDException e){
				throw e;
			}catch (ExcepcionJsonDeserializacion e){
				e.printStackTrace();
			}
		}
		return deportes;
	}
	
	public String generalUpdate(Deporte deporte){
		String set = BDUtils.producirSET(deporte, "deporte");
		StringBuilder query = new StringBuilder("MATCH (deporte:");
		query.append(Entidades.DEPORTE);
		query.append("{id:");
		query.append(deporte.getId());
		query.append("}) ");
		query.append(set);
		return query.toString();
	}
	
	public void updateDeporteDB() throws BDException{
		try{
			BDUtils.ejecutarQueryREST(
					this.generalUpdate(this.deporte));
		}catch(BDException e){
			throw e;
		}
	}
	
	public void updateDeporteDB(Deporte deporte) throws BDException{
		try{
			BDUtils.ejecutarQueryREST(this.generalUpdate(deporte));
		}catch(BDException e){
			throw e;
		}
	}
	
	public void deleteDeporteDB() throws BDException{
		try{
			Deporte deporte = new Deporte();
			deporte.setId(this.deporte.getId());
			this.updateDeporteDB(deporte);
		}catch(BDException e){
			throw e;
		}
	}
	
	public Deporte crearDeporte() throws BDException{
		StringBuilder query = new StringBuilder("CREATE (deporte:"+
					Entidades.DEPORTE);
		//Generar UUID
		this.deporte.setId(new Integer(1));
		query.append(this.deporte.stringJson());
		query.append(") RETURN deporte");
		try{
			BDUtils.ejecutarQueryREST(query.toString());
		} catch (BDException e){
			throw e;
		}
		
		return this.deporte;
		
	}
	
	public void setDeporte(Deporte deporte){
		this.deporte = deporte;
	}
	
	public Deporte getDeporte(){
		return this.deporte;
	}
	
}
