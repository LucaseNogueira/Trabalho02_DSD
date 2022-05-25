
import view.ConfiguraMalha;

/**
 * Classe responsável pela inciação do sistema.
 * 
 * @author Lucas Nogueira
 * @author Fabio Frare
 * 
 * @since 2022
 */
public class main {
       
    public static void main(String[] args){
        
        ConfiguraMalha configMalha = new ConfiguraMalha();
        int    tipoBuscaArquivo    = configMalha.escolheTipoBuscaArquivo();
        String caminhoArquivo      = configMalha.escolheCaminhoArquivo(tipoBuscaArquivo);

        
        System.exit(0);
    }
    
    
}
