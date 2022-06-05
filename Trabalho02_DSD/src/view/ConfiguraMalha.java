package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import utils.Template;


/**
 * Classe responsável por deinir a configuração inicial da malha.
 * 
 * @author Lucas Nogueira
 * @author Fabio Frare
 * 
 * @since 2022
 */
public class ConfiguraMalha {
    
    private static JFrame frame;
    public static final String NOME_TRABALHO = "Trabalho02_DSD";

    public ConfiguraMalha() {
        defineTemplate();
        frame = new JFrame("JOptionPane");        
    }    

    
    public String informaCaminhoPadrao(){
        String[] opcao  = new String[3];
        opcao[0]        = "Exemplo 1";
        opcao[1]        = "Exemplo 2";
        opcao[2]        = "Exemplo 3";
        
        int opção = JOptionPane.showOptionDialog(
                        frame,
                        "Escolha um Tipo de Busca de Arquivo",
                        NOME_TRABALHO,
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opcao,
                        null);
        
        String caminho = "";
        switch(opção){
            case 0:
                caminho = "media/malha-exemplo-1.txt";
                break;
            case 1:
                caminho = "media/malha-exemplo-2.txt";
                break;
            case 2:
                caminho = "media/malha-exemplo-3.txt";
                break;
        }
        
        return caminho;
    }
            
    private void defineTemplate() {
        Template template = new Template();
        template.iniciarTema();
    }
    
}
