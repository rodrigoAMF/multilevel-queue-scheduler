package br.edu.ifsuldeminas.pcs.model;

public class Processo {
	private int id;
	private String nome;
	private int tempoCPU;
	private int tempoRestanteCPU;
	private int tempoRestanteBloqueado;
	private int proximaFila;
	private int tipo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		if(tipo == 0) {
			return String.valueOf(nome) + "("  +  String.valueOf(tempoRestanteCPU) + "/" + String.valueOf(tempoCPU) + ")";
		}else {
			return String.valueOf(nome) + "("  +  String.valueOf(tempoRestanteCPU) + "/" + String.valueOf(tempoCPU) + ")(" + String.valueOf(tempoRestanteBloqueado) + ")";
		}
	}
	public String getApenasNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getTempoCPU() {
		return tempoCPU;
	}
	public void setTempoCPU(int tempoCPU) {
		this.tempoCPU = tempoCPU;
		this.tempoRestanteCPU = tempoCPU;
	}
	public int getTempoRestanteCPU() {
		return tempoRestanteCPU;
	}
	public void setTempoRestanteCPU(int tempoRestanteCPU) {
		this.tempoRestanteCPU = tempoRestanteCPU;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public int getTempoRestanteBloqueado() {
		return tempoRestanteBloqueado;
	}
	
	public void setTempoRestanteBloqueado(int tempoRestanteBloqueado) {
		this.tempoRestanteBloqueado = tempoRestanteBloqueado;
	}
	
	public int getProximaFila() {
		return proximaFila;
	}
	
	public void setProximaFila(int fila) {
		this.proximaFila = fila;
	}
	
}
