package com.sna_deportivo.services.deporte;

import com.sna_deportivo.pojo.deportes.DAODeporte;
import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public class GestionDeporte {

	public GestionDeporte(){}
	
	public Deporte crearDeporte(Deporte deporte){
		try{
			DAODeporte accesoDeporte = new DAODeporte(deporte);
			return accesoDeporte.crearDeporte();
		}catch(BDException e){
			throw e;
		}
	}
	
	public String consultarDeportes(Deporte deporte) throws 
		BDException,ExcepcionJsonDeserializacion{
		try{
			DAODeporte accesoDeporte = new DAODeporte(deporte);
			return JsonUtils.arrayObjectSNSToStringJson(
					(ObjectSNSDeportivo[])accesoDeporte.getDeportesDB());
		}catch (BDException e){
			throw e;
		}
	}
	
	public void desactivarDeporte(Deporte deporte) throws ExcepcionJsonDeserializacion{
		try{
			DAODeporte accesoDeporte = new DAODeporte(deporte);
			accesoDeporte.deleteDeporteDB();
		}catch(BDException e){
			throw e;
		}
	}
	
	public void actualizarDeporte(Deporte deporte) throws ExcepcionJsonDeserializacion{
		try{
			DAODeporte accesoDeporte = new DAODeporte(deporte);
			accesoDeporte.updateDeporteDB();
		}catch(BDException e){
			throw e;
		}
	}
	
}
