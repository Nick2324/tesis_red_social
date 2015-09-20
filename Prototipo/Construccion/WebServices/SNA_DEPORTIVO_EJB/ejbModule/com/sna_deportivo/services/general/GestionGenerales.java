package com.sna_deportivo.services.general;

import com.sna_deportivo.pojo.entidadesEstaticas.ProductorGeneral;
import com.sna_deportivo.utils.json.JsonUtils;

public class GestionGenerales {

	public String getEntidadGeneral(String tipo){
		return JsonUtils.arrayObjectSNSToStringJson(
				new ProductorGeneral().
					getFactorySegunTipo(tipo).
					getObjetoSNSDAO().getObjetoSNSDeportivoDB());
	}
	
}
