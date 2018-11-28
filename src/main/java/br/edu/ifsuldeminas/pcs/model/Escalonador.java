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
	private Fila proximaFilaDoProcessoDoTopoDaFilaDeBloqueados;
	
	private int tempoTotalExecucao;		// Tempo total para executar todos os processos no escalonador
	private int trocasDeContexto;
	private int numeroDeProcessos;
	
	private int tempoRestanteProcesso;
	private int tempoTotalProcesso;
	private String status;
	private int quantumsQueOProcessoIraExecutar;
	private boolean foiTrocadoDeFila;
	
	private Processo processoAtual;
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

	/* Retorna 0 se ainda tiver algum processo em alguma fila
	   Retorna 1 se não tiver mais processos em nenhuma fila  */
	public int trocaProcesso() {
		processoAtual = null;
		foiTrocadoDeFila = false;
		if(filaAtual != filaBloqueados) {
			processoAtual = filaAtual.remove();
			if(processoAtual.getTipo() == Constantes.CPU_BOUND) {
				if(processoAtual.getTempoRestanteCPU() < filaAtual.getQuantum()) {
		        	quantumsQueOProcessoIraExecutar = processoAtual.getTempoRestanteCPU();
		        }else {
		        	quantumsQueOProcessoIraExecutar = filaAtual.getQuantum();
		        }
			}else if(processoAtual.getTipo() == Constantes.IO_BOUND) {
				quantumsQueOProcessoIraExecutar = 1;
			}
			// Usado para atualizar progressBar da interface
			tempoTotalProcesso = tempoRestanteProcesso = quantumsQueOProcessoIraExecutar;
			while(tempoRestanteProcesso > 0) {
				status = "Processando " + processoAtual.getApenasNome() + "(" + String.valueOf(tempoRestanteProcesso) + "/" 
						+ String.valueOf(tempoTotalProcesso) +  ") " + ((processoAtual.getTipo() == 0) ? "CPU Bound": "IO Bound");
				setChanged();
		        notifyObservers();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tempoRestanteProcesso--;
			}
			
			processoAtual.setTempoRestanteCPU(processoAtual.getTempoRestanteCPU()-quantumsQueOProcessoIraExecutar);
			tempoTotalExecucao += quantumsQueOProcessoIraExecutar + 1;
			trocasDeContexto++;
			if(processoAtual.getTempoRestanteCPU() > 0) {
				processoAtual.setProximaFila(processoAtual.getProximaFila()+1);
				if(processoAtual.getProximaFila() > 8) {
					processoAtual.setProximaFila(1);
				}
				
				if(processoAtual.getTipo() == Constantes.CPU_BOUND) {
					proximaFila.adiciona(processoAtual);
				}else if(processoAtual.getTipo() == Constantes.IO_BOUND){
					processoAtual.setTempoRestanteBloqueado(5);
					filaBloqueados.adiciona(processoAtual);
				}
			}
			foiTrocadoDeFila = true;
			
			// Diminui tempo dos processos na fila de bloqueado
			for(Processo p: filaBloqueados.getProcessos()) {
				// Não diminui tempo na fila do processo que acabou de ser adicionado
				if(p == processoAtual) continue;
				p.setTempoRestanteBloqueado(p.getTempoRestanteBloqueado()-quantumsQueOProcessoIraExecutar-1);
			}
			
			// Adiciona à próxima fila caso tenha acabo tempo na fila de bloqueado
			while(!filaBloqueados.estaVazia() && filaBloqueados.getTopo().getTempoRestanteBloqueado() <= 0) {
				Processo p = filaBloqueados.remove();
				p.setTempoRestanteBloqueado(0);
				adicionaBloqueadoProximaFila(p);
			}
			// Atualiza interface e aguarda 1 seg (Simular troca de contexto)
			setChanged();
	        notifyObservers();
	        status = "Efetuando troca de contexto...";
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}else {
			// Diminui tempo dos processos na fila de bloqueado
			for(Processo p: filaBloqueados.getProcessos()) {
				p.setTempoRestanteBloqueado(p.getTempoRestanteBloqueado()-1);
				tempoTotalExecucao+=1;
			}
			// Adiciona à próxima fila caso tenha acabo tempo na fila de bloqueado
			while(!filaBloqueados.estaVazia() && filaBloqueados.getTopo().getTempoRestanteBloqueado() <= 0) {
				Processo p = filaBloqueados.remove();
				p.setTempoRestanteBloqueado(0);
				adicionaBloqueadoProximaFila(p);
			}
			// Atualiza interface e aguarda 1 seg (Simular troca de contexto)
			setChanged();
	        notifyObservers();
	        status = "Processando fila de bloqueados...";
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(filaAtual.estaVazia() && !proximaFila.estaVazia()) {
			trocaFilaAtualEProximaFila();
		}else if(filasEstaoVazias() && !filaBloqueados.estaVazia() && filaAtual != filaBloqueados) {
			filaAtual = filaBloqueados;
			AlteraProximaFilaDoProcessoDoTopoDaFilaDeBloqueados(filaBloqueados.getTopo());
			proximaFila = proximaFilaDoProcessoDoTopoDaFilaDeBloqueados;
		}else if(filaAtual.estaVazia() && filaAtual == filaBloqueados) {
			filaAtual = proximaFila;
			encontraProximaFila();
		}
		if(filaBloqueados.estaVazia() && filasEstaoVazias()) {
			status = "Finalizado!";
			setChanged();
	        notifyObservers();
			return 1; 
		}else {
			return 0;
		}
	}
	
	public void encontraProximaFila() {
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
	
	public void AlteraProximaFilaDoProcessoDoTopoDaFilaDeBloqueados(Processo p) {
		if(p.getProximaFila() == 1) {
			proximaFilaDoProcessoDoTopoDaFilaDeBloqueados = filaA;
		}else if(p.getProximaFila() == 2) {
			proximaFilaDoProcessoDoTopoDaFilaDeBloqueados = filaB;
		}else if(p.getProximaFila() == 3) {
			proximaFilaDoProcessoDoTopoDaFilaDeBloqueados = filaC;
		}else if(p.getProximaFila() == 4) {
			proximaFilaDoProcessoDoTopoDaFilaDeBloqueados = filaD;
		}else if(p.getProximaFila() == 5) {
			proximaFilaDoProcessoDoTopoDaFilaDeBloqueados = filaE;
		}else if(p.getProximaFila() == 6) {
			proximaFilaDoProcessoDoTopoDaFilaDeBloqueados = filaF;
		}else if(p.getProximaFila() == 7) {
			proximaFilaDoProcessoDoTopoDaFilaDeBloqueados = filaG;
		}else if(p.getProximaFila() == 8) {
			proximaFilaDoProcessoDoTopoDaFilaDeBloqueados = filaH;
		}
	}
	
	public boolean filasEstaoVazias() {
		if(filaA.estaVazia() && filaB.estaVazia() && filaC.estaVazia() && filaD.estaVazia() && filaE.estaVazia() && filaF.estaVazia() && filaG.estaVazia() && filaH.estaVazia())
			return true;
		else
			return false;
	}
	
	public void trocaFilaAtualEProximaFila() {
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
	
	public void adicionaBloqueadoProximaFila(Processo p) {
		if(p.getProximaFila() == 1) {
			filaA.adiciona(p);
		}else if(p.getProximaFila() == 2) {
			filaB.adiciona(p);
		}else if(p.getProximaFila() == 3) {
			filaC.adiciona(p);
		}else if(p.getProximaFila() == 4) {
			filaD.adiciona(p);
		}else if(p.getProximaFila() == 5) {
			filaE.adiciona(p);
		}else if(p.getProximaFila() == 6) {
			filaF.adiciona(p);
		}else if(p.getProximaFila() == 7) {
			filaG.adiciona(p);
		}else if(p.getProximaFila() == 8) {
			filaH.adiciona(p);
		}
	}
	
	/*public int proximaFilaIO(Fila filaAtual) {
		if(p.getProximaFila() == ) {
			
		}
	}*/
	
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
	
	public Processo getProcessoAtual() {
		return processoAtual;
	}
	
	public boolean isFoiTrocadoDeFila() {
		return foiTrocadoDeFila;
	}
	
}
