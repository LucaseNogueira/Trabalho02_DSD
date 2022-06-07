
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
    
    public static final int CRUZAMENTO_LEVE     = 1;
    public static final int CRUZAMENTO_MODERADO = 2;
    public static final int CRUZAMENTO_PESADO   = 3;
    
    private Veiculo veiculo;
    private Malha   malha;
    private ControllerOperadorMalha controller;

    public ControllerOperadorVeiculoMalha(Veiculo veiculo) {
        this.veiculo    = veiculo;
        this.malha      = Malha.getInstance();
        this.controller = ControllerOperadorMalha.getInstance();
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
            }
            controller.notificarViaAlterada();
        }
    }
    
    public void nascer(){
        boolean sucesso = false;
        while(!sucesso){
            sucesso = spawnarVeiculo();
        }
        controller.criarVeiculo();
    }
    
    private void parar(){
        malha.getVia(veiculo.getLinha(), veiculo.getColuna()).retiraVeiculo();
        veiculo.setVisivel(false);
        veiculo.setRodando(false);
        
        controller = ControllerOperadorMalha.getInstance();
        controller.isEncerraSimulacao();
    }
    
    private boolean isUltimaVia(int linha, int coluna){
        boolean isUltima = false;
        
        try{
            malha.getVia(linha, coluna);
        }
        catch(Exception e){
            isUltima = true;
        }
        
        return isUltima;
    }
    
    private boolean spawnarVeiculo(){
        boolean sucesso = true;
        
            try {
                Random random = new Random();
                List<Via> pontoPartida;
                Via viaInicial;
                this.veiculo.setSentido(random.nextInt(4) + 1);
                switch(veiculo.getSentido()){
                    case Via.SENTIDO_CIMA:
                        pontoPartida = controller.getPontosPartida().get(ControllerOperadorMalha.PONTO_PARTIDA_CIMA);
                        viaInicial = pontoPartida.get(random.nextInt(pontoPartida.size()));
                        viaInicial.adicionaVeiculo(veiculo);
                        veiculo.setRodando(true);
                        sucesso = true;
                        break;
                    case Via.SENTIDO_DIREITA:
                        pontoPartida = controller.getPontosPartida().get(ControllerOperadorMalha.PONTO_PARTIDA_DIREITA);
                        viaInicial = pontoPartida.get(random.nextInt(pontoPartida.size()));
                        viaInicial.adicionaVeiculo(veiculo);
                        veiculo.setRodando(true);
                        sucesso = true;
                        break;
                    case Via.SENTIDO_BAIXO:
                        pontoPartida = controller.getPontosPartida().get(ControllerOperadorMalha.PONTO_PARTIDA_BAIXO);
                        viaInicial = pontoPartida.get(random.nextInt(pontoPartida.size()));
                        viaInicial.adicionaVeiculo(veiculo);
                        veiculo.setRodando(true);
                        sucesso = true;
                        break;
                    case Via.SENTIDO_ESQUERDA:
                        pontoPartida = controller.getPontosPartida().get(ControllerOperadorMalha.PONTO_PARTIDA_ESQUERDA);
                        viaInicial = pontoPartida.get(random.nextInt(pontoPartida.size()));
                        viaInicial.adicionaVeiculo(veiculo);
                        veiculo.setRodando(true);
                        sucesso = true;
                        break;
                }
                sleep((long) this.veiculo.getIntervaloCriacao());
                controller.notificarViaAlterada();
            } catch (InterruptedException ex) {
                Logger.getLogger(ControllerOperadorVeiculoMalha.class.getName()).log(Level.SEVERE, null, ex);
            }
        return sucesso;
    }
    
    private void gerenciarVias(int linha, int coluna){
        if(!isUltimaVia(linha, coluna)){
            Via viaAtual = malha.getVia(veiculo.getLinha(), veiculo.getColuna());
            Via via      = malha.getVia(linha, coluna);
            if(!via.isOcupado()){
                andarProximaVia(viaAtual, via);
            }
        }
        else {
            parar();
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
        List<Via> caminho = new ArrayList();
        defineAlternativaCruzamento(caminho, via);
        if(caminho.size() == getCaminhosReservados(caminho).size()){
            for(Via viaAndar : caminho){
                viaAtual.retiraVeiculo();
                viaAndar.adicionaVeiculo(veiculo);
                viaAtual = viaAndar;
                controller.notificarViaAlterada();
            }
        }
    }
    
    private List<Via> getCaminhosReservados(List<Via> caminho){
        List<Via> caminhoReservado = new ArrayList();
        for(Via via : caminho){
            if(via.trajetoLivre()){
                caminhoReservado.add(via);
            }
            else{
                via.liberarTrajeto();
            }
        }
        
        return caminhoReservado;
    }
    
    private void defineAlternativaCruzamento(List<Via> caminhosAlternativos, Via viaAlternativa){
        Random random = new Random();
        switch(viaAlternativa.getSentido()){
            case Via.SENTIDO_CRUZAMENTO_CIMA:
                switch(random.nextInt(2)){
                    case 0:
                        defineCruzamentoCima(caminhosAlternativos, viaAlternativa, CRUZAMENTO_MODERADO);
                        break;
                    case 1:
                        defineCruzamentoEsquerda(caminhosAlternativos, viaAlternativa, CRUZAMENTO_PESADO);
                        break;
                }
                break;
            case Via.SENTIDO_CRUZAMENTO_DIREITA:
                switch(random.nextInt(2)){
                    case 0:
                        defineCruzamentoDireita(caminhosAlternativos, viaAlternativa, CRUZAMENTO_MODERADO);
                        break;
                    case 1:
                        defineCruzamentoCima(caminhosAlternativos, viaAlternativa, CRUZAMENTO_PESADO);
                        break;
                }
                break;
            case Via.SENTIDO_CRUZAMENTO_BAIXO:
                switch(random.nextInt(2)){
                    case 0:
                        defineCruzamentoBaixo(caminhosAlternativos, viaAlternativa, CRUZAMENTO_MODERADO);
                        break;
                    case 1:
                        defineCruzamentoDireita(caminhosAlternativos, viaAlternativa, CRUZAMENTO_PESADO);
                        break;
                }
                break;
            case Via.SENTIDO_CRUZAMENTO_ESQUERDA:
                switch(random.nextInt(2)){
                    case 0:
                        defineCruzamentoEsquerda(caminhosAlternativos, viaAlternativa, CRUZAMENTO_MODERADO);
                        break;
                    case 1:
                        defineCruzamentoBaixo(caminhosAlternativos, viaAlternativa, CRUZAMENTO_PESADO);
                        break;
                }
                break;
            case Via.SENTIDO_CRUZAMENTO_CIMA_DIREITA:
                switch(random.nextInt(3)){
                    case 0:
                        defineCruzamentoEsquerda(caminhosAlternativos, viaAlternativa, CRUZAMENTO_PESADO);
                        break;
                    case 1:
                        defineCruzamentoCima(caminhosAlternativos, viaAlternativa, CRUZAMENTO_MODERADO);
                        break;
                    case 2:
                        defineCruzamentoDireita(caminhosAlternativos, viaAlternativa, CRUZAMENTO_LEVE);
                        break;
                }
                break;
                           
            case Via.SENTIDO_CRUZAMENTO_CIMA_ESQUERDA:
                switch(random.nextInt(3)){
                    case 0:
                        defineCruzamentoBaixo(caminhosAlternativos, viaAlternativa, CRUZAMENTO_PESADO);
                        break;
                    case 1:
                        defineCruzamentoEsquerda(caminhosAlternativos, viaAlternativa, CRUZAMENTO_MODERADO);
                        break;
                    case 2:
                        defineCruzamentoCima(caminhosAlternativos, viaAlternativa, CRUZAMENTO_LEVE);
                        break;
                }
                break;
            case Via.SENTIDO_CRUZAMENTO_DIREITA_BAIXO:
                switch(random.nextInt(3)){
                    case 0:
                        defineCruzamentoCima(caminhosAlternativos, viaAlternativa, CRUZAMENTO_PESADO);
                        break;
                    case 1:
                        defineCruzamentoDireita(caminhosAlternativos, viaAlternativa, CRUZAMENTO_MODERADO);
                        break;
                    case 2:
                        defineCruzamentoBaixo(caminhosAlternativos, viaAlternativa, CRUZAMENTO_LEVE);
                        break;
                }
                break;      
            case Via.SENTIDO_CRUZAMENTO_BAIXO_ESQUERDA:
                switch(random.nextInt(3)){
                    case 0:
                        defineCruzamentoDireita(caminhosAlternativos, viaAlternativa, CRUZAMENTO_PESADO);
                        break;
                    case 1:
                        defineCruzamentoBaixo(caminhosAlternativos, viaAlternativa, CRUZAMENTO_MODERADO);
                        break;
                    case 2:
                        defineCruzamentoEsquerda(caminhosAlternativos, viaAlternativa, CRUZAMENTO_LEVE);
                        break;
                }
                break;      
        }
    }
    
    private void defineCruzamentoCima(List<Via> caminhosAlternativos, Via viaAlternativa, int gravidadeCruzamento){
        switch(gravidadeCruzamento){
            case CRUZAMENTO_LEVE:
                caminhosAlternativos.add(viaAlternativa);
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha() - 1, viaAlternativa.getColuna()));
                break;
            case CRUZAMENTO_MODERADO:
                caminhosAlternativos.add(viaAlternativa);
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha() - 1, viaAlternativa.getColuna()));
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha() - 2, viaAlternativa.getColuna()));
                break;
            case CRUZAMENTO_PESADO:
                caminhosAlternativos.add(viaAlternativa);
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha(), viaAlternativa.getColuna() + 1));
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha() - 1, viaAlternativa.getColuna() + 1));
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha() - 2, viaAlternativa.getColuna() + 1));
                break;
        }
    }
    
    private void defineCruzamentoDireita(List<Via> caminhosAlternativos, Via viaAlternativa, int gravidadeCruzamento){
        switch(gravidadeCruzamento){
            case CRUZAMENTO_LEVE:
                caminhosAlternativos.add(viaAlternativa);
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha(), viaAlternativa.getColuna() + 1));
                break;
            case CRUZAMENTO_MODERADO:
                caminhosAlternativos.add(viaAlternativa);
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha(), viaAlternativa.getColuna() + 1));
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha(), viaAlternativa.getColuna() + 2));
                break;
            case CRUZAMENTO_PESADO:
                caminhosAlternativos.add(viaAlternativa);
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha() + 1, viaAlternativa.getColuna()));
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha() + 1, viaAlternativa.getColuna() + 1));
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha() + 1, viaAlternativa.getColuna() + 2));
                break;
        }
    }
    
    private void defineCruzamentoBaixo(List<Via> caminhosAlternativos, Via viaAlternativa, int gravidadeCruzamento){
        switch(gravidadeCruzamento){
            case CRUZAMENTO_LEVE:
                caminhosAlternativos.add(viaAlternativa);
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha() + 1, viaAlternativa.getColuna()));
                break;
            case CRUZAMENTO_MODERADO:
                caminhosAlternativos.add(viaAlternativa);
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha() + 1, viaAlternativa.getColuna()));
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha() + 2, viaAlternativa.getColuna()));
                break;
            case CRUZAMENTO_PESADO:
                caminhosAlternativos.add(viaAlternativa);
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha(), viaAlternativa.getColuna() - 1));
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha() + 1, viaAlternativa.getColuna() - 1));
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha() + 2, viaAlternativa.getColuna() - 1));
                break;
        }
    }
    
    private void defineCruzamentoEsquerda(List<Via> caminhosAlternativos, Via viaAlternativa, int gravidadeCruzamento){
        switch(gravidadeCruzamento){
            case CRUZAMENTO_LEVE:
                caminhosAlternativos.add(viaAlternativa);
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha(), viaAlternativa.getColuna() - 1));
                break;
            case CRUZAMENTO_MODERADO:
                caminhosAlternativos.add(viaAlternativa);
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha(), viaAlternativa.getColuna() - 1));
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha(), viaAlternativa.getColuna() - 2));
                break;
            case CRUZAMENTO_PESADO:
                caminhosAlternativos.add(viaAlternativa);
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha() - 1, viaAlternativa.getColuna()));
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha() - 1, viaAlternativa.getColuna() - 1));
                caminhosAlternativos.add(malha.getVia(viaAlternativa.getLinha() - 1, viaAlternativa.getColuna() - 2));
                break;
        }
    }
}
