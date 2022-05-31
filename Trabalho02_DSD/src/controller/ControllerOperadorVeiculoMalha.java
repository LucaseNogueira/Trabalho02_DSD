
package controller;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public ControllerOperadorVeiculoMalha(Veiculo veiculo) {
        this.veiculo = veiculo;
        this.malha   = Malha.getInstance();
    }
    
    public void andar(){
        int linha;
        int coluna;
        
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
        boolean sucesso = false;
        while(!sucesso){
            sucesso = spawnarVeiculo();
        }
    }
    
    private void parar(){
        
    }
    
    private boolean spawnarVeiculo(){
        boolean sucesso = true;
        
        while(!sucesso){
            try {
                Random random = new Random();
                int linha  = 0;
                int coluna = 0;
                this.veiculo.setSentido(random.nextInt(4) + 1);
                switch(veiculo.getSentido()){
                    case Via.SENTIDO_CIMA:
//                        sucesso = localidadeSpawn(Via.SENTIDO_CIMA);
                        linha  = malha.getLinhas();
                        coluna = malha.getColunas();
                        
                        break;
                    case Via.SENTIDO_DIREITA:
//                        sucesso = localidadeSpawn(Via.SENTIDO_DIREITA);
                        linha  = malha.getLinhas();
                        break;
                    case Via.SENTIDO_BAIXO:
                        coluna = malha.getColunas();
//                        sucesso = localidadeSpawn(Via.SENTIDO_BAIXO);
                        break;
                    case Via.SENTIDO_ESQUERDA:
//                        sucesso = localidadeSpawn(Via.SENTIDO_ESQUERDA);
                        linha  = malha.getLinhas();
                        coluna = malha.getColunas();
                        break;
                }
                sleep((long) this.veiculo.getIntervaloCriacao());
            } catch (InterruptedException ex) {
                Logger.getLogger(ControllerOperadorVeiculoMalha.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sucesso;
    }
    
//    private boolean localidadeSpawn(int hemisferio){
//        
//    }
    
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
