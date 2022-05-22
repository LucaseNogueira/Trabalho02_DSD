package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Leitor de arquivos txt de malhas viárias
 *
 * @author Lucas Nogueira
 * @author Fabio Frare
 */
public class Leitor {
    
    private int matriz[][];

    public Leitor(File arquivo) {
        converteFile(arquivo);
    }
    
    /**
     * Retorna a matriz do conteúdo do arquivo;
     * @return 
     */
    public int[][] getMatriz(){
        return matriz;
    }
    
    /**
     * Quantidade de linhas da matriz;
     *
     * @return 
     */
    public int getComprimento(){
        return matriz.length;
    }
    
    /**
     * Quantidade de Colunas da matriz;
     * 
     * @return 
     */
    public int getLargura(){
        return matriz[0].length;
    }
    
    /**
     * Converte o conteudo do arquivo txt em uma matriz
     * @param arquivo 
     */
    private void converteFile(File arquivo){
        try {
            Scanner sacanner = new Scanner(arquivo);
            int linha        = Integer.parseInt(sacanner.next().trim());
            int coluna       = Integer.parseInt(sacanner.next().trim());
            matriz           = new int[linha][coluna];
            
            for(int i = 0; i < linha; i++){
                for(int j = 0; j < coluna; j++){
                    matriz[i][j] = Integer.parseInt(sacanner.next().trim());
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Leitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
