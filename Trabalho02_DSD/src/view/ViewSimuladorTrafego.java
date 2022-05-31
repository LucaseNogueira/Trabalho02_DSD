/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.ControllerOperadorMalha;
import controller.InterfaceObserver;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author lucas
 */
public class ViewSimuladorTrafego extends JFrame implements ActionListener, InterfaceObserver{
    
    private JLabel lblQtdVeiculos;
    private JLabel lblIntervaloInsercao;
    
    private JTextField jtfQtdVeiculos;
    private JTextField jtfIntervaloInsercao;
    
    private JButton jbIniciar;
    private JButton jbEncerrar;
    
    private GridBagLayout      layout;
    private GridLayout         gridLayout;
    private GridBagConstraints constraints;
    
    private JPanel jpPrincipal;
    private JPanel jpBottomEsquerda;
    private JPanel jpBottomDireita;
    
    private JTable jtMalha;
    
    private ControllerOperadorMalha controller;

    public ViewSimuladorTrafego(int[][] matriz) {
        controller = ControllerOperadorMalha.getInstance();
        controller.montaMalha(matriz);
        configuracoesBasicas();
        iniciaComponentes();
        configuraComponentes();
    }
    
    private void configuracoesBasicas(){
        setTitle("Simulador de Trafego");
        setBounds(200, 200, 1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
    }
    
    private void iniciaComponentes(){
        constraints          = new GridBagConstraints();
        layout               = new GridBagLayout();
        gridLayout           = new GridLayout(3, 3, 10, 10);
        jpPrincipal          = new JPanel();
        jpBottomDireita      = new JPanel(gridLayout);
        jpBottomEsquerda     = new JPanel();
        lblQtdVeiculos       = new JLabel("Quantidade de carros: ");
        lblIntervaloInsercao = new JLabel("Intervalo de Insercao (ms): ");
        jtfQtdVeiculos       = new JTextField();
        jtfIntervaloInsercao = new JTextField();
        jbIniciar            = new JButton("Iniciar");
        jbEncerrar           = new JButton("Encerrar");
        jtMalha              = new JTable();
    }
    
    private void configuraComponentes(){
        jpBottomDireita.add(lblQtdVeiculos);
        jpBottomDireita.add(lblIntervaloInsercao);
        
        add(jpBottomDireita, BorderLayout.NORTH);
        
        jpBottomDireita.add(jtfQtdVeiculos);
        jpBottomDireita.add(jtfIntervaloInsercao);
        jpBottomDireita.add(jbIniciar);
        jpBottomDireita.add(jbEncerrar);
//        
        add(jpBottomDireita, BorderLayout.NORTH);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
//    class EstradaTableModel extends AbstractTableModel {
//
//        private static final long serialVersionUID = 1L;
//
//        @Override
//        public int getColumnCount() {
//            return matriz[0].length;
//        }
//
//        @Override
//        public int getRowCount() {
//            return matriz.length;
//        }
//
//        @Override
//        public Object getValueAt(int row, int col) {
//            try {
//                return gerenciador.getImageMatriz(col, row);
//
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, e.toString());
//                return null;
//            }
//        }
//
////    }
//    

    @Override
    public void notifyAlterarItem() {
        repaint();
    }

    @Override
    public void notifyEncerrarSimulacao() {
//        JOptionPane.showMessageDialog(panelOpcoes, "Simulacao Encerrada\n"
//                                                + "Quantidade de carros solicitados: " + gerenciador.getQuantidadeDeCarros() + "\n"
//                                                + "Quantidade de carros criados: " + gerenciador.getCarrosSpawnados());
//        jbIniciar.setEnabled(true);       
//        jrbMonitor.setEnabled(true);
//        jrbSemaforo.setEnabled(true);
//        jtfQtdCarros.setEnabled(true);
//        jbEncerrar.setEnabled(false);
//        jtfQtdCarros.setText("");
//        jtfIntervaloInsercao.setEnabled(true);
//        jtfIntervaloInsercao.setText("");
    }
}
