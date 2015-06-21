package com.sna_deportivo.pojo.evento;

public class DEPracticaDeportiva extends DAOEvento{

	public DEPracticaDeportiva(){
		super();
		this.eventoManejado = ConstantesEventos.PRACTICADEPORTIVALIBRE.getServicio();
	}
	
	public DEPracticaDeportiva(PracticaDeportiva pd){
		super(pd);
		this.eventoManejado = ConstantesEventos.PRACTICADEPORTIVALIBRE.getServicio();
	}
}