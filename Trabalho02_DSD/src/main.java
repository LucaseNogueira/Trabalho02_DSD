
import java.io.IOException;
import view.ConfiguraMalha_Desativado;


/**
 * Classe responsável pela inciação do sistema.
 * 
 * @author Lucas Nogueira
 * @author Fabio Frare
 * 
 * @since 2022
 */
public class main {
       
    public static void main(String[] args) throws IOException{
        
        ConfiguraMalha_Desativado configMalha = new ConfiguraMalha_Desativado();
//        int    tipoBuscaArquivo               = configMalha.escolheTipoBuscaArquivo();
        String caminhoArquivo                 = configMalha.escolheCaminhoArquivo(configMalha.escolheTipoBuscaArquivo());


     


//        System.exit(0);
    }
    
    
}
