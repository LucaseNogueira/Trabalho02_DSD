
package controller;

import model.Malha;
import model.Veiculo;
import model.Via;

/**
 *
 * @author Lucas Nogueira
 * @author Fabio Frare
 */
public class ControllerOperadorVeiculoMalha {
    
//    public static final int SENTIDO_CIMA     = 1;
//    public static final int SENTIDO_DIREITA  = 2;
//    public static final int SENTIDO_BAIXO    = 3;
//    public static final int SENTIDO_ESQUERDA = 4;
//    public static final int SENTIDO_CRUZAMENTO = 20;
    
    private int sentido;
    
//    private Veiculo veiculo;
//    private Via     via;
    private Malha   malha;
    
    public void andar(){
        switch(sentido){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                parar();
        }
    }
    
    public void nascer(){
        
    }
    
    private void parar(){
        
    }
    
    private void defineProximoSentido(int linha, int coluna){
//        sentido = via.
    }
    
}
