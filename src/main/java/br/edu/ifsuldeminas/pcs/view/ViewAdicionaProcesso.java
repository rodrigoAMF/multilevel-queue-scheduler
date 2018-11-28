package br.edu.ifsuldeminas.pcs.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.edu.ifsuldeminas.pcs.model.Constantes;
import br.edu.ifsuldeminas.pcs.model.Escalonador;
import br.edu.ifsuldeminas.pcs.model.Processo;

import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class ViewAdicionaProcesso extends JFrame {

	private JPanel contentPane;
	private JTextField txtNomeProcesso;
	private JTextField txtTempoDeCPU;
	private Escalonador escalonador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewAdicionaProcesso frame = new ViewAdicionaProcesso();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ViewAdicionaProcesso() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				txtNomeProcesso.setText("P" + String.valueOf(escalonador.getNumeroDeProcessos()+1));
			}
		});
		escalonador = Escalonador.getInstance();
		
		setTitle("Adicionar processos ao Simulador");
		setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 591, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtNomeProcesso = new JTextField();
		txtNomeProcesso.setEditable(false);
		txtNomeProcesso.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		txtNomeProcesso.setBounds(12, 40, 300, 40);
		contentPane.add(txtNomeProcesso);
		txtNomeProcesso.setColumns(10);
		
		JLabel lblNomeProcesso = new JLabel("Nome do Processo");
		lblNomeProcesso.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblNomeProcesso.setBounds(12, 11, 154, 16);
		contentPane.add(lblNomeProcesso);
		
		txtTempoDeCPU = new JTextField();
		txtTempoDeCPU.setText("5");
		txtTempoDeCPU.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		txtTempoDeCPU.setColumns(10);
		txtTempoDeCPU.setBounds(12, 121, 300, 40);
		contentPane.add(txtTempoDeCPU);
		
		JLabel lblTempoDeCPU = new JLabel("Tempo de CPU [ms]");
		lblTempoDeCPU.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblTempoDeCPU.setBounds(12, 92, 154, 16);
		contentPane.add(lblTempoDeCPU);
		
		JLabel lblTipoProcesso = new JLabel("Tipo do Processo");
		lblTipoProcesso.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblTipoProcesso.setBounds(12, 174, 154, 16);
		contentPane.add(lblTipoProcesso);
		
		final JComboBox cbxTipoDoProcesso = new JComboBox();
		cbxTipoDoProcesso.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		cbxTipoDoProcesso.setModel(new DefaultComboBoxModel(new String[] {"CPU Bound", "I/O Bound"}));
		cbxTipoDoProcesso.setBackground(Color.WHITE);
		cbxTipoDoProcesso.setBounds(12, 204, 300, 40);
		contentPane.add(cbxTipoDoProcesso);
		
		JScrollPane scrollPaneTxtaProcessosAdicionados = new JScrollPane();
		scrollPaneTxtaProcessosAdicionados.setBounds(339, 40, 200, 400);
		contentPane.add(scrollPaneTxtaProcessosAdicionados);
		
		final JTextArea txtaProcessosAdicionado = new JTextArea();
		scrollPaneTxtaProcessosAdicionados.setViewportView(txtaProcessosAdicionado);
		txtaProcessosAdicionado.setTabSize(4);
		txtaProcessosAdicionado.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		
		JButton btnAdicionarProcesso = new JButton("Adicionar Processo");
		btnAdicionarProcesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nomeDoProcesso = txtNomeProcesso.getText();
				if(txtTempoDeCPU.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite algum valor no campo Tempo de CPU", "Erro", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				int tempoDeCPU = Integer.valueOf(txtTempoDeCPU.getText());
				//int tipoProcesso = cbxTipoDoProcesso.getSelectedItem();
				int tipoProcesso = (cbxTipoDoProcesso.getSelectedItem() == "CPU Bound") ? Constantes.CPU_BOUND: Constantes.IO_BOUND;
				Processo novo = new Processo();
				novo.setId(escalonador.getNumeroDeProcessos());
				novo.setNome(nomeDoProcesso);
				novo.setTempoCPU(tempoDeCPU);
				novo.setTipo(tipoProcesso);
				novo.setProximaFila(1);
				escalonador.getFilaAtual().adiciona(novo);
				escalonador.incrementaNumeroDeProcessos();
				
				txtNomeProcesso.setText("P" + String.valueOf(escalonador.getNumeroDeProcessos()+1));
				
				txtaProcessosAdicionado.setText(escalonador.mostraProcessos());
			}
		});
		btnAdicionarProcesso.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		btnAdicionarProcesso.setBounds(139, 275, 173, 40);
		contentPane.add(btnAdicionarProcesso);
		
		JLabel lblProcessoJaAdicionados = new JLabel("Processos j\u00E1 adicionados");
		lblProcessoJaAdicionados.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblProcessoJaAdicionados.setBounds(353, 11, 200, 16);
		contentPane.add(lblProcessoJaAdicionados);
		
		JButton btnIrParaSimulacao = new JButton("Ir para Simula\u00E7\u00E3o");
		btnIrParaSimulacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewSimulador simulador = new ViewSimulador();
				simulador.setVisible(true);
				setVisible(false); // Seta invisível
				dispose(); // Destrói a JFrame
			}
		});
		btnIrParaSimulacao.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		btnIrParaSimulacao.setBounds(139, 331, 173, 40);
		contentPane.add(btnIrParaSimulacao);
	}
}
