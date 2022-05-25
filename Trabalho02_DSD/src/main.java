
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import utils.Leitor;
import utils.Template;

/**
 * Método principal de inciação do sistema
 * 
 * @author lucas
 * @author Fabio Frare
 * 
 * @since 2022
 */
public class main {
    
    private static JFrame frame;
    public static final String NOME_TRABALHO = "Trabalho02_DSD";
    
    public static void main(String[] args){
        
        Template template = new Template();
        template.iniciarTema();
        
        frame                   = new JFrame("JOptionPane");
        int    tipoBuscaArquivo = escolheTipoBuscaArquivo();
        String caminhoArquivo   = escolheCaminhoArquivo(tipoBuscaArquivo);
        
        System.exit(0);
    }
    
    private static int escolheTipoBuscaArquivo(){
        String[] tipoBuscaArquivo  = new String[2];
        tipoBuscaArquivo[0]        = "Caminho do Arquivo";//Busca pelo caminho do arquivo;
        tipoBuscaArquivo[1]        = "Opções Padrões";    //Escolhe uma das opções padrões;
        
        int opcao = JOptionPane.showOptionDialog(
                        frame,
                        "Escolha um Tipo de Busca de Arquivo",
                        NOME_TRABALHO,
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        tipoBuscaArquivo,
                        null);
        
        
        return opcao;
    }
    
    private static String escolheCaminhoArquivo(int tipoBuscaArquivo){
        String caminhoArquivo = "";
        switch(tipoBuscaArquivo){
            case 0:
                caminhoArquivo = informaCaminhoArquivo();
                break;
            case 1:
                caminhoArquivo = informaCaminhoPadrao();
                break;
        }
        
        return caminhoArquivo;
    }
    
    private static String informaCaminhoArquivo(){
        String caminho = JOptionPane.showInputDialog(frame, "Insira o Caminho do Arquivo!", NOME_TRABALHO, 0);
        
        return caminho;
    }
    
    private static String informaCaminhoPadrao(){
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
                caminho = "src/malha-exemplo-1.txt";
                break;
            case 1:
                caminho = "src/malha-exemplo-2.txt";
                break;
            case 2:
                caminho = "src/malha-exemplo-3.txt";
                break;
        }
        
        return caminho;
    }
    
}
