package com.sna_deportivo.services.general;

import com.sna_deportivo.pojo.entidadesEstaticas.ProductorFactoryGenerales;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public class GestionGenerales {

	public String getEntidadGeneral(String tipo) throws ExcepcionJsonDeserializacion{
		return JsonUtils.arrayObjectSNSToStringJson(
				new ProductorFactoryGenerales().
					getFactorySegunTipo(tipo).
					getObjetoSNSDAO().getObjetoSNSDeportivoDB());
	}
	
}
