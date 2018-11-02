package br.edu.ifsuldeminas.pcs.model;

import java.util.LinkedList;
import java.util.Observable;

public class Escalonador extends Observable {
	private static final Escalonador instance = new Escalonador();
	private Fila filaA;
	private Fila filaB;
	private Fila filaC;
	private Fila filaD;
	private Fila filaE;
	private Fila filaF;
	private Fila filaG;
	private Fila filaH;
	private Fila filaBloqueados;
	
	private Fila filaAtual;
	private Fila proximaFila;
	
	private int tempoTotalExecucao;		// Tempo total para executar todos os processos no escalonador
	private int trocasDeContexto;
	private int numeroDeProcessos;

	public Escalonador() {
		filaA = new Fila(2, 1);
		filaB = new Fila(4, 2);
		filaC = new Fila(8, 3);
		filaD = new Fila(16, 4);
		filaE = new Fila(32, 5);
		filaF = new Fila(64, 6);
		filaG = new Fila(128, 7);
		filaH = new Fila(256, 8);
		filaBloqueados = new Fila(-1, -1);
		tempoTotalExecucao = 0;
		trocasDeContexto = 0;
		numeroDeProcessos = 0;
		filaAtual = filaA;
		proximaFila = filaB;
	}
	
	public static Escalonador getInstance() {
        return instance;
    }
	
	public void inicializaSimulacao() {
		setChanged();
        notifyObservers();
	}

	// Retorna 0 se ainda tiver algum processo em alguma fila
	// Retorna 1 se não tiver mais processos em nenhuma fila
	public int trocaProcesso(int fila) {
		// Troca de fila
		Processo trocado = filaAtual.remove();
		if(trocado.getTipo() == Constantes.CPU_BOUND) {
			tempoTotalExecucao += filaAtual.getQuantum() + 1;
			trocado.setTempoRestanteCPU(trocado.getTempoRestanteCPU()-filaAtual.getQuantum());
			proximaFila.adiciona(trocado);
		}else if(trocado.getTipo() == Constantes.IO_BOUND) {
			filaBloqueados.adiciona(trocado);
			trocado.setTempoRestanteCPU(trocado.getTempoRestanteCPU()-1);
			tempoTotalExecucao += 1 + 1;
		}
		trocasDeContexto++;
		
		if(filaAtual.estaVazia()) {
			// Verifica se tem algo na fila de bloqueado e adiciona na próxima fila
			if(!filaBloqueados.estaVazia()) {
				// 
				while(!filaBloqueados.estaVazia()) {
					proximaFila.adiciona(filaBloqueados.remove());
				}
			}
			
			// Troca a fila atual
			filaAtual = proximaFila;
			
			// Define a próxima fila
			if(filaAtual == filaA) {
				proximaFila = filaB;
			}else if(filaAtual == filaB) {
				proximaFila = filaC;
			}else if(filaAtual == filaC) {
				proximaFila = filaD;
			}else if(filaAtual == filaD) {
				proximaFila = filaE;
			}else if(filaAtual == filaE) {
				proximaFila = filaF;
			}else if(filaAtual == filaF) {
				proximaFila = filaG;
			}else if(filaAtual == filaG) {
				proximaFila = filaH;
			}else if(filaAtual == filaH) {
				proximaFila = filaA;
			}
		}
		
		if(filaA.estaVazia() && filaB.estaVazia() && filaC.estaVazia() && filaD.estaVazia() 
				&& filaE.estaVazia() && filaF.estaVazia() && filaG.estaVazia() && filaH.estaVazia() && filaBloqueados.estaVazia()) {
			return 1;
		}else {
			return 0;
		}
	}
	
	public String mostraProcessos() {
		String saida = "";
		
		LinkedList<Processo> processos = filaA.getProcessos();
		for(int i = 0; i < processos.size(); i++) {
			saida += "  " + processos.get(i).getNome() + "\t" + ((processos.get(i).getTipo() == 0) ? "CPU Bound": "I/O Bound")  + "\t" + processos.get(i).getTempoCPU() + "\n";
		}
		 
		return saida;
	}
	
	
	public Fila getFilaAtual() {
		return filaAtual;
	}
	
	public int getNumeroDeProcessos() {
		return numeroDeProcessos;
	}
	
	public void incrementaNumeroDeProcessos() {
		numeroDeProcessos++;
	}
	
}
