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
	
	private int tempoRestanteProcesso;
	private int tempoTotalProcesso;
	private String status;
	/*private int numeroProcessosFilaAtual;
	private int numeroTotalProcessosFilaAtual;*/

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
		while(trocaProcesso() != 1) {
			setChanged();
	        notifyObservers();
		}
		
	}

	// Retorna 0 se ainda tiver algum processo em alguma fila
	// Retorna 1 se não tiver mais processos em nenhuma fila
	public int trocaProcesso() {
		// Troca de fila
		Processo trocado = filaAtual.remove();
		tempoRestanteProcesso = tempoTotalProcesso = filaAtual.getQuantum();
		
		if(trocado.getTipo() == Constantes.CPU_BOUND) {
			status = "Processando " + trocado.getNome() + " CPU Bound";
			setChanged();
	        notifyObservers();
			// Atualiza progressBar com tempo restante na CPU
			for(int i = 0; i < filaAtual.getQuantum(); i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tempoRestanteProcesso--;
				setChanged();
		        notifyObservers();
			}
			
	        // Atualiza tempo total de execução, tempo restante na CPU do processo e troca de fila caso não tenha terminado de executar
			tempoTotalExecucao += filaAtual.getQuantum() + 1;
			trocado.setTempoRestanteCPU(trocado.getTempoRestanteCPU()-filaAtual.getQuantum());
			if(trocado.getTempoRestanteCPU() > 0) {
				proximaFila.adiciona(trocado);
			}
		}else if(trocado.getTipo() == Constantes.IO_BOUND) {
			status = "Processando " + trocado.getNome() + " IO Bound";
			setChanged();
	        notifyObservers();
	        tempoRestanteProcesso = tempoTotalProcesso = 1;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setChanged();
	        notifyObservers();
	        
			trocado.setTempoRestanteCPU(trocado.getTempoRestanteCPU()-1);
			tempoTotalExecucao += 1 + 1;
			if(trocado.getTempoRestanteCPU() > 0) {
				filaBloqueados.adiciona(trocado);
			}
		}
		trocasDeContexto++;
		
		if(filaAtual.estaVazia()) {
			// Verifica se tem algo na fila de bloqueado e adiciona na próxima fila
			if(!filaBloqueados.estaVazia()) {
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
			status = "Finalizado!";
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
	
	public Fila getProximaFila() {
		return proximaFila;
	}
	
	public Fila getFilaBloqueados() {
		return filaBloqueados;
	}
	
	public int getNumeroDeProcessos() {
		return numeroDeProcessos;
	}
	
	public void incrementaNumeroDeProcessos() {
		numeroDeProcessos++;
	}
	
	// Tempo que o escalonador está executando simulação
	public int getTempoTotalExecucao() {
		return tempoTotalExecucao;
	}
	
	// Número de trocas de contexto
	public int getTrocasDeContexto() {
		return trocasDeContexto;
	}
	
	// Número de quantuns que o processo atual na CPU ainda pode executar
	public int getTempoRestanteProcesso() {
		return tempoRestanteProcesso;
	}
	
	// Total de quantuns que o processo atual na CPU pode executar
	public int getTempoTotalProcesso() {
		return tempoTotalProcesso;
	}
	
	public String getStatus() {
		return status;
	}
	
}
