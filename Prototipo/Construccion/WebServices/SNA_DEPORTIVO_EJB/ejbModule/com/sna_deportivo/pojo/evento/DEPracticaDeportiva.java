package com.sna_deportivo.pojo.evento;

public class DEPracticaDeportiva extends DAOEvento{

	public DEPracticaDeportiva(){
		super();
	}
	
	public DEPracticaDeportiva(PracticaDeportiva pd){
		super(pd);
		this.eventoManejado = ConstantesEventos.PRACTICADEPORTIVALIBRE;
	}
}