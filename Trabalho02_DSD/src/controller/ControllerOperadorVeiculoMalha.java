
package controller;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
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
                Random random               = new Random();
                int linha                   = 0;
                int coluna                  = 0;
                List<Integer> pontosPartida = new ArrayList<>();
                int linhaColunaInicial;
                this.veiculo.setSentido(random.nextInt(4) + 1);
                switch(veiculo.getSentido()){
                    case Via.SENTIDO_CIMA:
                        linha  = malha.getLinhas();
                        coluna = malha.getColunas();
                        for(int i = 0; i < coluna; i++){
                            if(malha.getVia(linha, i).getSentido() == Via.SENTIDO_CIMA){
                                pontosPartida.add(i);
                            }
                        }
                        linhaColunaInicial = random.nextInt(pontosPartida.size());
                        malha.getVia(linha, linhaColunaInicial).adicionaVeiculo(veiculo);
                        sucesso = true;
                        break;
                    case Via.SENTIDO_DIREITA:
                        linha  = malha.getLinhas();
                        for(int i = 0; i < linha; i++){
                            if(malha.getVia(i, coluna).getSentido() == Via.SENTIDO_DIREITA){
                                pontosPartida.add(i);
                            }
                        }
                        linhaColunaInicial = random.nextInt(pontosPartida.size());
                        malha.getVia(linhaColunaInicial, coluna).adicionaVeiculo(veiculo);
                        sucesso = true;
                        break;
                    case Via.SENTIDO_BAIXO:
                        coluna = malha.getColunas();
                        for(int i = 0; i < coluna; i++){
                            if(malha.getVia(linha, i).getSentido() == Via.SENTIDO_BAIXO){
                                pontosPartida.add(i);
                            }
                        }
                        linhaColunaInicial = random.nextInt(pontosPartida.size());
                        malha.getVia(linha, linhaColunaInicial).adicionaVeiculo(veiculo);
                        sucesso = true;
                        break;
                    case Via.SENTIDO_ESQUERDA:
                        linha  = malha.getLinhas();
                        coluna = malha.getColunas();
                        for(int i = 0; i < linha; i++){
                            if(malha.getVia(i, coluna).getSentido() == Via.SENTIDO_ESQUERDA){
                                pontosPartida.add(i);
                            }
                        }
                        linhaColunaInicial = random.nextInt(pontosPartida.size());
                        malha.getVia(linhaColunaInicial, coluna).adicionaVeiculo(veiculo);
                        sucesso = true;
                        break;
                }
                sleep((long) this.veiculo.getIntervaloCriacao());
            } catch (InterruptedException ex) {
                Logger.getLogger(ControllerOperadorVeiculoMalha.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sucesso;
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
            andarCruzamento(viaAtual, via);
        }
    }
    
    private void andarCruzamento(Via viaAtual, Via via){
        List<List<Via>> caminhosAlternativos = new ArrayList();
        List<Via> listaViaDefault            = new ArrayList();
        
        caminhosAlternativos.add(listaViaDefault);
        defineAlternativaCruzamento(caminhosAlternativos, via, 0);
    }
    
    private void defineAlternativaCruzamento(List<List<Via>> caminhosAlternativos, Via viaAlternativa, int alternativa){
        List<Via> via = new ArrayList();
        int alternativaPlus;
        switch(viaAlternativa.getSentido()){
            case Via.SENTIDO_CRUZAMENTO_CIMA:
                caminhosAlternativos.get(alternativa).add(viaAlternativa);
                via.add(malha.getVia(viaAlternativa.getLinha() - 1, viaAlternativa.getColuna()));
                defineAlternativaCruzamento(caminhosAlternativos, via.get(0), alternativa);
                break;
            case Via.SENTIDO_CRUZAMENTO_DIREITA:
                caminhosAlternativos.get(alternativa).add(viaAlternativa);
                via.add(malha.getVia(viaAlternativa.getLinha(), viaAlternativa.getColuna() + 1));
                defineAlternativaCruzamento(caminhosAlternativos, via.get(0), alternativa);
                break;
            case Via.SENTIDO_CRUZAMENTO_BAIXO:
                caminhosAlternativos.get(alternativa).add(viaAlternativa);
                via.add(malha.getVia(viaAlternativa.getLinha() + 1, viaAlternativa.getColuna()));
                defineAlternativaCruzamento(caminhosAlternativos, via.get(0), alternativa);
                break;
            case Via.SENTIDO_CRUZAMENTO_ESQUERDA:
                caminhosAlternativos.get(alternativa).add(viaAlternativa);
                via.add(malha.getVia(viaAlternativa.getLinha(), viaAlternativa.getColuna() - 1));
                defineAlternativaCruzamento(caminhosAlternativos, via.get(0), alternativa);
                break;
            case Via.SENTIDO_CRUZAMENTO_CIMA_DIREITA:
                caminhosAlternativos.get(alternativa).add(viaAlternativa);
                caminhosAlternativos.add(new ArrayList(caminhosAlternativos.get(alternativa)));
                via.add(malha.getVia(viaAlternativa.getLinha() - 1, viaAlternativa.getColuna()));
                via.add(malha.getVia(viaAlternativa.getLinha(), viaAlternativa.getColuna() + 1));
                alternativaPlus = alternativa + 1;
                for(int i = 0; i < via.size(); i++){
                    defineAlternativaCruzamento(caminhosAlternativos, via.get(i), alternativaPlus);
                    alternativaPlus++;
                }
                break;
            case Via.SENTIDO_CRUZAMENTO_CIMA_ESQUERDA:
                caminhosAlternativos.get(alternativa).add(viaAlternativa);
                caminhosAlternativos.add(new ArrayList(caminhosAlternativos.get(alternativa)));
                via.add(malha.getVia(viaAlternativa.getLinha() - 1, viaAlternativa.getColuna()));
                via.add(malha.getVia(viaAlternativa.getLinha(), viaAlternativa.getColuna() - 1));
                alternativaPlus = alternativa + 1;
                for(int i = 0; i < via.size(); i++){
                    defineAlternativaCruzamento(caminhosAlternativos, via.get(i), alternativaPlus);
                    alternativaPlus++;
                }
                break;
            case Via.SENTIDO_CRUZAMENTO_DIREITA_BAIXO:
                caminhosAlternativos.get(alternativa).add(viaAlternativa);
                caminhosAlternativos.add(new ArrayList(caminhosAlternativos.get(alternativa)));
                via.add(malha.getVia(viaAlternativa.getLinha(), viaAlternativa.getColuna() + 1));
                via.add(malha.getVia(viaAlternativa.getLinha() + 1, viaAlternativa.getColuna()));
                alternativaPlus = alternativa + 1;
                for(int i = 0; i < via.size(); i++){
                    defineAlternativaCruzamento(caminhosAlternativos, via.get(i), alternativaPlus);
                    alternativaPlus++;
                }
                break;
            case Via.SENTIDO_CRUZAMENTO_BAIXO_ESQUERDA:
                caminhosAlternativos.get(alternativa).add(viaAlternativa);
                caminhosAlternativos.add(new ArrayList(caminhosAlternativos.get(alternativa)));
                via.add(malha.getVia(viaAlternativa.getLinha() + 1, viaAlternativa.getColuna()));
                via.add(malha.getVia(viaAlternativa.getLinha(), viaAlternativa.getColuna() - 1));
                alternativaPlus = alternativa + 1;
                for(int i = 0; i < via.size(); i++){
                    defineAlternativaCruzamento(caminhosAlternativos, via.get(i), alternativaPlus);
                    alternativaPlus++;
                }
                break;
            default:
                caminhosAlternativos.get(alternativa).add(viaAlternativa);
        }
    }
}
