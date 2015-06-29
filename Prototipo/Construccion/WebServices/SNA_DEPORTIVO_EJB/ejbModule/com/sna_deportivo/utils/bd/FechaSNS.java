package com.sna_deportivo.utils.bd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FechaSNS {

	//Date
	public static final String ANIOS = "yyyy";
	public static final String MESES = "MM";
	public static final String DIAS = "dd";
	
	//Separador
	public static final String SEPARADORFECHA = "/";
	
	private Date fecha;
	
	public FechaSNS(){}
	
	public void setFecha(String fecha){
		if(fecha != null && !fecha.equals("")){
			fecha = fecha.replaceAll("\\\\", "");
			SimpleDateFormat formato = new SimpleDateFormat(
					FechaSNS.DIAS + FechaSNS.SEPARADORFECHA +
					FechaSNS.MESES + FechaSNS.SEPARADORFECHA +
					FechaSNS.ANIOS);
			try {
				this.fecha = formato.parse(fecha);
			} catch (ParseException e) {
				this.fecha = null;
			}
		}
		
	}
	
	@Override
	public String toString(){
		if(this.fecha != null){
			SimpleDateFormat formato = new SimpleDateFormat(
				FechaSNS.DIAS + FechaSNS.SEPARADORFECHA +
				FechaSNS.MESES + FechaSNS.SEPARADORFECHA +
				FechaSNS.ANIOS);
			return formato.format(this.fecha);
		}

		return "";
		
	}
	
}