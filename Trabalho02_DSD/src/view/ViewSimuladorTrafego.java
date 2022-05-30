/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author lucas
 */
public class ViewSimuladorTrafego extends JFrame implements ActionListener{
    
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

    public ViewSimuladorTrafego(int[][] matriz) {
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
        jbIniciar            = new JButton("Encerrar");
    }
    
    private void configuraComponentes(){
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
