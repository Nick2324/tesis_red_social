package com.sna_deportivo.pojo.ubicacion;

import com.sna_deportivo.utils.bd.Entidades;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;

public class LugarPracticaDAO extends ObjectSNSDeportivoDAO{

	@Override
	public void encontrarObjetoManejado() throws BDException, ProductorFactoryExcepcion, ProductorFactoryExcepcion {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void setUpDAOGeneral() {
		this.identificadorQueries = "lugarpractica";
		this.tipoObjetoSNS = Entidades.LUGARPRACTICA;
		this.factoryOSNS = null;
	}

	@Override
	public ObjectSNSDeportivo crearObjetoSNS() throws ProductorFactoryExcepcion {
		throw new UnsupportedOperationException();
	}

}
