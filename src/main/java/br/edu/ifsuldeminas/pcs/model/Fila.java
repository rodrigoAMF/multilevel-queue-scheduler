package br.edu.ifsuldeminas.pcs.model;

import java.util.LinkedList;
import java.util.Queue;

public class Fila {
	private Queue<Processo> fila;
	private final int quantum;

	
	public Fila(int quantum) {
		this.fila = new LinkedList<Processo>();
		this.quantum = quantum;
	}
	
	public LinkedList<Processo> getProcessos(){
		LinkedList<Processo> lista = new LinkedList<Processo>();
		for(Processo p: fila) {
			lista.add(p);
		}
		return lista;
	}
	
	public void adiciona(Processo p) {
	    this.fila.add(p);
	}
	
	public Processo remove() {
		return this.fila.poll();
	}
	
	public Processo getTopo() {
		return fila.peek();
	}
	
	public boolean estaVazia() {
		return fila.isEmpty();
	}
	
	public int getQuantum() {
		return this.quantum;
	}
}
