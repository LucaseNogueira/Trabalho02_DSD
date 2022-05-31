
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
    
    private Veiculo veiculo;
    private Malha   malha;
    
    public void andar(){
        int linha;
        int coluna;
        int proximoSentido;
        
        if(veiculo.isVisivel()){
            switch(veiculo.getSentido()){
                case Via.SENTIDO_CIMA:
                    linha          = veiculo.getLinha() - 1;
                    coluna         = veiculo.getColuna();
                    gerenciarVias(linha, coluna);
                    break;
                case Via.SENTIDO_DIREITA:
                    linha          = veiculo.getLinha();
                    coluna         = veiculo.getColuna() + 1;
                    gerenciarVias(linha, coluna);
                    break;
                case Via.SENTIDO_BAIXO:
                    linha          = veiculo.getLinha() + 1;
                    coluna         = veiculo.getColuna();
                    gerenciarVias(linha, coluna);
                    break;
                case Via.SENTIDO_ESQUERDA:
                    linha          = veiculo.getLinha();
                    coluna         = veiculo.getColuna() - 1;
                    gerenciarVias(linha, coluna);
                    break;
                default:
                    parar();
            }
        }
    }
    
    public void nascer(){
        
    }
    
    private void parar(){
        
    }
    
    private void gerenciarVias(int linha, int coluna){
        Via viaAtual = malha.getVia(veiculo.getLinha(), veiculo.getColuna());
        Via via      = malha.getVia(linha, coluna);
        if(!via.isOcupado()){
            andarProximaVia(viaAtual, via);
        }
    }
    
    private void andarProximaVia(Via viaAtual, Via via){
        if(via.isRodovia()){
            viaAtual.retiraVeiculo();
            via.adicionaVeiculo(veiculo);
        }
        else{
            andarCruzamento();
        }
    }
    
    private void andarCruzamento(){
        
    }
}
