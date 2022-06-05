
import controller.ControllerServiceSimuladorTrafego;
import java.io.IOException;
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
       
    public static void main(String[] args) throws IOException{
        
        ConfiguraMalha configMalha = new ConfiguraMalha();
//        String caminhoArquivo                 = configMalha.escolheCaminhoArquivo(configMalha.escolheTipoBuscaArquivo());

        String caminhoArquivo = configMalha.informaCaminhoPadrao();

        ControllerServiceSimuladorTrafego controller = new ControllerServiceSimuladorTrafego(caminhoArquivo);
        controller.iniciaSimulador();

//        System.exit(0);
    }
    
    
}
