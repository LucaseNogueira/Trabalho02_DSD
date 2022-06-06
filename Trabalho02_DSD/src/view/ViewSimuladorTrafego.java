package view;

import controller.ControllerOperadorMalha;
import controller.InterfaceObserver;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

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
    
    private JPanel jpPrincipal;
    private JPanel jpBottomEsquerda;
    private JPanel jpBottomDireita;
    
    private JTable jtMalha;
    
    private ControllerOperadorMalha controller;

    public ViewSimuladorTrafego(int[][] matriz) {
        controller = ControllerOperadorMalha.getInstance();
        controller.montaMalha(matriz);
        controller.addObservador(this);
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
        jpPrincipal          = new JPanel();
        jpBottomDireita      = new JPanel(new GridLayout(3, 3, 10, 10));
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
        
//        add(jpBottomEsquerda, BorderLayout.NORTH);
        jpBottomDireita.add(lblQtdVeiculos);
        jpBottomDireita.add(lblIntervaloInsercao);
        jpBottomDireita.add(jtfQtdVeiculos);
        jpBottomDireita.add(jtfIntervaloInsercao);
        jpBottomDireita.add(jbIniciar);
        jpBottomDireita.add(jbEncerrar);
        
        add(jpBottomDireita, BorderLayout.NORTH);
        
         
        
        jtMalha = new JTable();
        jtMalha.setModel(new ViaTableModel());
        for(int i = 0; i < jtMalha.getColumnModel().getColumnCount(); i++){
            jtMalha.getColumnModel().getColumn(i).setWidth(22);
            jtMalha.getColumnModel().getColumn(i).setMinWidth(22);
            jtMalha.getColumnModel().getColumn(i).setMaxWidth(22);
        }
        jtMalha.setRowHeight(22);
        jtMalha.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jtMalha.setShowGrid(false);
        jtMalha.setIntercellSpacing(new Dimension(-1, 0));
        jtMalha.setDefaultRenderer(Object.class, new ViaRenderer());
        
        jpPrincipal.setLayout(new FlowLayout(FlowLayout.CENTER));
        jpPrincipal.add(jtMalha);

        add(jpPrincipal, BorderLayout.CENTER);
        
        jbIniciar.addActionListener((e) -> actionPerformedIniciar());
        
        jbEncerrar.setEnabled(false);
        jbEncerrar.addActionListener(this);
        
        jtfQtdVeiculos.setName("Quantidade Veiculos");
        jtfIntervaloInsercao.setName("Intervalo Inserção");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == jbIniciar){
            actionPerformedIniciar();
        }
        else
        if(event.getSource() == jbEncerrar){
            actionPerformedEncerrar();
        }
    }
    
    private void actionPerformedIniciar(){
        if(validaJTextField(jtfQtdVeiculos) && validaJTextField(jtfIntervaloInsercao)){
            int quantidadeVeiculos = Integer.parseInt(jtfQtdVeiculos.getText());
            int intervalo          = Integer.parseInt(jtfIntervaloInsercao.getText());
            controller.iniciaSimulacao(quantidadeVeiculos,intervalo);
            jbEncerrar.setEnabled(true);
        }
    }
    
    private void actionPerformedEncerrar(){
        jbEncerrar.setEnabled(false);
        controller.encerraSimulacao();
    }
    
    private boolean validaJTextField(JTextField jtf){
        boolean sucesso = !jtf.getText().equals("");
        if(!sucesso){
            JOptionPane.showMessageDialog(rootPane, "Intervalo invalido para o campo " + jtf.getName());
        }
        
        return sucesso;
    }


//    private boolean validaTextField(JTextField jtfIntervaloInsercao) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
    class ViaTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        @Override
        public int getColumnCount() {
            return controller.getTotalColunas();
        }

        @Override
        public int getRowCount() {
            return controller.getTotalLinhas();
        }

        @Override
        public Object getValueAt(int row, int col) {
            try {
                return controller.getIcone(row, col);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
                return null;
            }
        }

    }

    class ViaRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {

            setIcon((ImageIcon) value);

            return this;
        }
    }

    @Override
    public void notifyAlterarItem() {
        repaint();
    }

    @Override
    public void notifyEncerrarSimulacao() {
        JOptionPane.showMessageDialog(jpBottomDireita, "Simulacao Encerrada\n"
                                                + "Quantidade de carros solicitado: " + controller.getQtdVeiculosCriados() + "\n"
                                                + "Quantidade de carros criado: " + controller.getQtdVeiculosRodando());
        jbIniciar.setEnabled(true);
        jbEncerrar.setEnabled(false);
        jtfQtdVeiculos.setText("");
        jtfIntervaloInsercao.setText("");
    }
}
