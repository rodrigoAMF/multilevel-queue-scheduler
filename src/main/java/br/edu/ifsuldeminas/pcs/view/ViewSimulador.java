package br.edu.ifsuldeminas.pcs.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.ifsuldeminas.pcs.model.Escalonador;
import br.edu.ifsuldeminas.pcs.model.Processo;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ViewSimulador extends JFrame implements Observer {
	private Escalonador escalonador;
	private JPanel contentPane;
	
	private Observable processor;
	ExecutorService executor;
	
	JTextArea txtaFilaA;
	JTextArea txtaFilaB;
	JTextArea txtaFilaC;
	JTextArea txtaFilaD;
	JTextArea txtaFilaE;
	JTextArea txtaFilaF;
	JTextArea txtaFilaG;
	JTextArea txtaFilaH;
	JTextArea txtaFilaBloqueados;
	
	JProgressBar pbFilaA;
	JProgressBar pbFilaB;
	JProgressBar pbFilaC;
	JProgressBar pbFilaD;
	JProgressBar pbFilaE;
	JProgressBar pbFilaF;
	JProgressBar pbFilaG;
	JProgressBar pbFilaH;
	JProgressBar pbFilaBloqueados;
	
	public void registerObserver(Observable processor) {
        this.processor = processor;
        processor.addObserver(this);
    }
	
	/**
	 * Create the frame.
	 */
	public ViewSimulador() {
		this.escalonador = Escalonador.getInstance();
		registerObserver(escalonador);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1324, 924);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane spListaA = new JScrollPane();
		spListaA.setBounds(27, 192, 600, 53);
		contentPane.add(spListaA);
		
		txtaFilaA = new JTextArea();
		txtaFilaA.setEditable(false);
		txtaFilaA.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		spListaA.setViewportView(txtaFilaA);
		
		JLabel lblFilaA = new JLabel("Fila A");
		lblFilaA.setFont(new Font("Segoe UI Light", Font.BOLD, 18));
		lblFilaA.setBounds(27, 163, 56, 16);
		contentPane.add(lblFilaA);
		
		pbFilaA = new JProgressBar();
		pbFilaA.setBounds(27, 262, 600, 26);
		contentPane.add(pbFilaA);
		
		JLabel lblFilaB = new JLabel("Fila B");
		lblFilaB.setFont(new Font("Segoe UI Light", Font.BOLD, 18));
		lblFilaB.setBounds(27, 304, 56, 16);
		contentPane.add(lblFilaB);
		
		pbFilaB = new JProgressBar();
		pbFilaB.setBounds(27, 403, 600, 26);
		contentPane.add(pbFilaB);
		
		pbFilaC = new JProgressBar();
		pbFilaC.setBounds(27, 548, 600, 26);
		contentPane.add(pbFilaC);
		
		JLabel lblFilaC = new JLabel("Fila C");
		lblFilaC.setFont(new Font("Segoe UI Light", Font.BOLD, 18));
		lblFilaC.setBounds(27, 449, 56, 16);
		contentPane.add(lblFilaC);
		
		pbFilaD = new JProgressBar();
		pbFilaD.setBounds(27, 703, 600, 26);
		contentPane.add(pbFilaD);
		
		JLabel lblFilaD = new JLabel("Fila D");
		lblFilaD.setFont(new Font("Segoe UI Light", Font.BOLD, 18));
		lblFilaD.setBounds(27, 604, 56, 16);
		contentPane.add(lblFilaD);
		
		JScrollPane spFilaB = new JScrollPane();
		spFilaB.setBounds(27, 333, 600, 53);
		contentPane.add(spFilaB);
		
		txtaFilaB = new JTextArea();
		txtaFilaB.setEditable(false);
		txtaFilaB.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		spFilaB.setViewportView(txtaFilaB);
		
		JScrollPane spFilaC = new JScrollPane();
		spFilaC.setBounds(27, 478, 600, 53);
		contentPane.add(spFilaC);
		
		txtaFilaC = new JTextArea();
		txtaFilaC.setEditable(false);
		txtaFilaC.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		spFilaC.setViewportView(txtaFilaC);
		
		JScrollPane spFilaD = new JScrollPane();
		spFilaD.setBounds(27, 633, 600, 53);
		contentPane.add(spFilaD);
		
		txtaFilaD = new JTextArea();
		txtaFilaD.setEditable(false);
		txtaFilaD.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		spFilaD.setViewportView(txtaFilaD);
		
		JScrollPane spFilaE = new JScrollPane();
		spFilaE.setBounds(675, 192, 600, 53);
		contentPane.add(spFilaE);
		
		txtaFilaE = new JTextArea();
		txtaFilaE.setEditable(false);
		spFilaE.setViewportView(txtaFilaE);
		
		JLabel lblFilaE = new JLabel("Fila E");
		lblFilaE.setFont(new Font("Segoe UI Light", Font.BOLD, 18));
		lblFilaE.setBounds(675, 163, 56, 16);
		contentPane.add(lblFilaE);
		
		pbFilaE = new JProgressBar();
		pbFilaE.setBounds(675, 262, 600, 26);
		contentPane.add(pbFilaE);
		
		JLabel lblFilaF = new JLabel("Fila F");
		lblFilaF.setFont(new Font("Segoe UI Light", Font.BOLD, 18));
		lblFilaF.setBounds(675, 304, 56, 16);
		contentPane.add(lblFilaF);
		
		pbFilaF = new JProgressBar();
		pbFilaF.setBounds(675, 403, 600, 26);
		contentPane.add(pbFilaF);
		
		pbFilaG = new JProgressBar();
		pbFilaG.setBounds(675, 548, 600, 26);
		contentPane.add(pbFilaG);
		
		JLabel lblFilaG = new JLabel("Fila G");
		lblFilaG.setFont(new Font("Segoe UI Light", Font.BOLD, 18));
		lblFilaG.setBounds(675, 449, 56, 16);
		contentPane.add(lblFilaG);
		
		pbFilaH = new JProgressBar();
		pbFilaH.setBounds(675, 703, 600, 26);
		contentPane.add(pbFilaH);
		
		JLabel lblFilaH = new JLabel("Fila H");
		lblFilaH.setFont(new Font("Segoe UI Light", Font.BOLD, 18));
		lblFilaH.setBounds(675, 604, 56, 16);
		contentPane.add(lblFilaH);
		
		JScrollPane spFilaF = new JScrollPane();
		spFilaF.setBounds(675, 333, 600, 53);
		contentPane.add(spFilaF);
		
		txtaFilaF = new JTextArea();
		txtaFilaF.setEditable(false);
		spFilaF.setViewportView(txtaFilaF);
		
		JScrollPane spFilaG = new JScrollPane();
		spFilaG.setBounds(675, 478, 600, 53);
		contentPane.add(spFilaG);
		
		txtaFilaG = new JTextArea();
		txtaFilaG.setEditable(false);
		spFilaG.setViewportView(txtaFilaG);
		
		JScrollPane spFilaH = new JScrollPane();
		spFilaH.setBounds(675, 633, 600, 53);
		contentPane.add(spFilaH);
		
		txtaFilaH = new JTextArea();
		txtaFilaH.setEditable(false);
		spFilaH.setViewportView(txtaFilaH);
		
		JLabel lblFilaBloqueados = new JLabel("Fila Bloqueados");
		lblFilaBloqueados.setFont(new Font("Segoe UI Light", Font.BOLD, 18));
		lblFilaBloqueados.setBounds(27, 25, 359, 16);
		contentPane.add(lblFilaBloqueados);
		
		pbFilaBloqueados = new JProgressBar();
		pbFilaBloqueados.setBounds(27, 124, 1248, 26);
		contentPane.add(pbFilaBloqueados);
		
		JScrollPane spFilaBloqueados = new JScrollPane();
		spFilaBloqueados.setBounds(27, 58, 1248, 53);
		contentPane.add(spFilaBloqueados);
		
		txtaFilaBloqueados = new JTextArea();
		txtaFilaBloqueados.setEditable(false);
		txtaFilaBloqueados.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		spFilaBloqueados.setViewportView(txtaFilaBloqueados);
		
		JLabel lblTrocasDeContexto = new JLabel("Trocas de Contexto: 000");
		lblTrocasDeContexto.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		lblTrocasDeContexto.setBounds(230, 747, 227, 30);
		contentPane.add(lblTrocasDeContexto);
		
		JLabel tempoDeExecução = new JLabel("Tempo de Execu\u00E7\u00E3o: 0s");
		tempoDeExecução.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		tempoDeExecução.setBounds(469, 747, 227, 30);
		contentPane.add(tempoDeExecução);
		
		JLabel lblTempoTotalExecucao = new JLabel("Tempo Total de Execu\u00E7\u00E3o: -");
		lblTempoTotalExecucao.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		lblTempoTotalExecucao.setBounds(708, 747, 296, 30);
		contentPane.add(lblTempoTotalExecucao);
		
		JButton btnIniciarSimulacao = new JButton("Iniciar Simula\u00E7\u00E3o");
		btnIniciarSimulacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escalonador.inicializaSimulacao();
			}
		});
		btnIniciarSimulacao.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		btnIniciarSimulacao.setBounds(427, 800, 200, 50);
		contentPane.add(btnIniciarSimulacao);
		
		JButton btnPausarSimulação = new JButton("Pausar Simula\u00E7\u00E3o");
		btnPausarSimulação.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPausarSimulação.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		btnPausarSimulação.setBounds(675, 800, 200, 50);
		contentPane.add(btnPausarSimulação);
		
		JLabel lblStatus = new JLabel("Status: ");
		lblStatus.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		lblStatus.setBounds(27, 747, 64, 30);
		contentPane.add(lblStatus);
		
		JLabel lblStatusAtual = new JLabel("Aguardando...");
		lblStatusAtual.setForeground(new Color(255, 127, 80));
		lblStatusAtual.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		lblStatusAtual.setBounds(84, 747, 117, 30);
		contentPane.add(lblStatusAtual);
	}

	public void update(Observable o, Object arg) {
		Thread t1 = new Thread(() -> {
            synchronized (this) {
                if (o instanceof Escalonador) {
                	Escalonador e = (Escalonador) o;
                	
                	JTextArea txtaAtual = null;
                	JProgressBar pbAtual = null;
                	if(e.getFilaAtual().getIdFila() == 1) {
                		txtaFilaA.setText("");
                		txtaAtual = txtaFilaA;
                		pbAtual = pbFilaA;
                	}else if(e.getFilaAtual().getIdFila() == 2) {
                		txtaFilaB.setText("");
                		txtaAtual = txtaFilaB;
                		pbAtual = pbFilaB;
                	}else if(e.getFilaAtual().getIdFila() == 3) {
                		txtaFilaC.setText("");
                		txtaAtual = txtaFilaC;
                		pbAtual = pbFilaC;
                	}else if(e.getFilaAtual().getIdFila() == 4) {
                		txtaFilaD.setText("");
                		txtaAtual = txtaFilaD;
                		pbAtual = pbFilaD;
                	}else if(e.getFilaAtual().getIdFila() == 5) {
                		txtaFilaE.setText("");
                		txtaAtual = txtaFilaE;
                		pbAtual = pbFilaE;
                	}else if(e.getFilaAtual().getIdFila() == 6) {
                		txtaFilaF.setText("");
                		txtaAtual = txtaFilaF;
                		pbAtual = pbFilaF;
                	}else if(e.getFilaAtual().getIdFila() == 7) {
                		txtaFilaG.setText("");
                		txtaAtual = txtaFilaG;
                		pbAtual = pbFilaG;
                	}else if(e.getFilaAtual().getIdFila() == 8) {
                		txtaFilaH.setText("");
                		txtaAtual = txtaFilaH;
                		pbAtual = pbFilaH;
                	}else if(e.getFilaAtual().getIdFila() == -1) {
                		txtaFilaBloqueados.setText("");
                		txtaAtual = txtaFilaBloqueados;
                		pbAtual = pbFilaBloqueados;
                	}
                	
                	if(e.getFilaAtual().getIdFila() == 1) {
                		for(Processo p: e.getFilaAtual().getProcessos()) {
                			if(!txtaAtual.getText().equals("")) {
                				txtaAtual.setText(txtaAtual.getText() + "<" + p.getNome());
                			}else {
                				txtaAtual.setText(txtaAtual.getText() + p.getNome());
                			}
                    	}
                	}
                	
                	
                }
            }
        });
        t1.start();
	}
}
