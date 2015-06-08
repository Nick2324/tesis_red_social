package com.sna_deportivo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TiempoSNS {

	//Time
	public static final String HORAS = "HH";
	public static  final String MINUTOS = "mm";
	public static  final String SEGUNDOS = "ss";
		
	//Separador
	public static  final String SEPARADORTIEMPO = ":";
	
	private Date hora;
	
	public TiempoSNS(){}
	
	public void setHora(String hora){
		if(hora != null && !hora.equals("")){
			SimpleDateFormat formato = new SimpleDateFormat(
					TiempoSNS.HORAS + TiempoSNS.SEPARADORTIEMPO + 
					TiempoSNS.MINUTOS + TiempoSNS.SEPARADORTIEMPO + 
					TiempoSNS.SEGUNDOS);
			try {
				this.hora = formato.parse(hora);
			} catch (ParseException e) {
				this.hora = null;
			}
		}
	
	}
	
	@Override
	public String toString(){
		if(this.hora != null){
			SimpleDateFormat formato = new SimpleDateFormat(
					TiempoSNS.HORAS + TiempoSNS.SEPARADORTIEMPO + 
					TiempoSNS.MINUTOS + TiempoSNS.SEPARADORTIEMPO + 
					TiempoSNS.SEGUNDOS);
			return formato.format(this.hora);
		}
	
		return "";
	}
	
}
