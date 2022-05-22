
import java.io.File;
import utils.Leitor;

/**
 *
 * @author lucas
 */
public class main {
    
    public static void main(String[] args){
        File   arquivo = new File("src/media/malha-exemplo-1.txt");
        Leitor leitor  = new Leitor(arquivo);
        int[][] matriz = leitor.getMatriz();
        
        System.out.println("Quantidade de Linhas: " + leitor.getComprimento());
        System.out.println("Quantidade de Colunas: " + leitor.getLargura());
        for (int i = 0; i < leitor.getComprimento(); i++) {
            for (int j = 0; j < leitor.getLargura(); j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
}
