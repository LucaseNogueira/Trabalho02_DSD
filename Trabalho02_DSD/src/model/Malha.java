package model;

/**
 * Representa a malha viária
 *
 * @author Lucas Nogueira
 * @author Fabio Frare
 */
public class Malha {
    
    private int linha;
    private int coluna;
    
    private Via[][] vias;
    private static Malha instance;
    
    private Malha(){}
    
    public synchronized static Malha getInstance(){
        if(instance == null){
            instance = new Malha();
        }
        
        return instance;
    }
    
    public void criarMalhaViaria(int linha, int coluna){
        this.vias = new Via[linha][coluna];
    }
    
    public int getLinha(){
        return vias.length;
    }
    
    public int getColunas(){
        return vias[0].length;
    }
    
}
