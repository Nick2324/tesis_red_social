package com.sna_deportivo.pojo.ubicacion;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;

public class FactoryUbicacion implements FactoryObjectSNSDeportivo{

	@Override
	public ObjectSNSDeportivo getObjetoSNS() {
		return new Ubicacion();
	}

	@Override
	public ObjectSNSDeportivoDAO getObjetoSNSDAO() {
		return new UbicacionDAO();
	}

}
