
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
//                default:
//                    parar(); //É BEM POSSÍVEL QUE QUANDO O MÉTODO PARAR NUNCA SERÁ CHAMADO POR AQUI
            }
            controller.notificarViaAlterada();
        }
    }
    
    public void nascer(){
        boolean sucesso = false;
        while(!sucesso){
            sucesso = spawnarVeiculo();
        }
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
                Random random               = new Random();
                int linha                   = 0;
                int coluna                  = 0;
                List<Integer> pontosPartida = new ArrayList<>();
                List<Via> pontoPartida;
                Via viaInicial;
                int linhaColunaInicial;
                this.veiculo.setSentido(random.nextInt(4) + 1);
                switch(veiculo.getSentido()){
                    case Via.SENTIDO_CIMA:
                        pontoPartida = controller.getPontosPartida().get(Via.SENTIDO_CIMA - 1);
                        viaInicial = pontoPartida.get(random.nextInt(pontoPartida.size()));
                        viaInicial.adicionaVeiculo(veiculo);
                        veiculo.setRodando(true);
                        sucesso = true;
                        break;
                    case Via.SENTIDO_DIREITA:
                        pontoPartida = controller.getPontosPartida().get(Via.SENTIDO_DIREITA - 1);
                        viaInicial = pontoPartida.get(random.nextInt(pontoPartida.size()));
                        viaInicial.adicionaVeiculo(veiculo);
                        veiculo.setRodando(true);
                        sucesso = true;
                        break;
                    case Via.SENTIDO_BAIXO:
                        pontoPartida = controller.getPontosPartida().get(Via.SENTIDO_BAIXO - 1);
                        viaInicial = pontoPartida.get(random.nextInt(pontoPartida.size()));
                        viaInicial.adicionaVeiculo(veiculo);
                        veiculo.setRodando(true);
                        sucesso = true;
                        break;
                    case Via.SENTIDO_ESQUERDA:
                        pontoPartida = controller.getPontosPartida().get(Via.SENTIDO_ESQUERDA - 1);
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
        List<List<Via>> caminhosAlternativos = new ArrayList();
        List<Via> listaViaDefault            = new ArrayList();
        
        caminhosAlternativos.add(listaViaDefault);
        defineAlternativaCruzamento(caminhosAlternativos, via, 0);
        
        Random random              = new Random();
        int totalAlternativas      = caminhosAlternativos.size();
        List<Via> caminho          = caminhosAlternativos.get(random.nextInt(totalAlternativas));
        if(caminho.size() == getCaminhosReservados(caminho).size()){
            for(Via viaAndar : caminho){
                viaAtual.retiraVeiculo();
                viaAndar.adicionaVeiculo(veiculo);
                viaAtual = viaAndar;
            }
            controller.notificarViaAlterada();
        }
    }
    
    private List<Via> getCaminhosReservados(List<Via> caminho){
        List<Via> caminhoReservado = new ArrayList();
        for(Via via : caminho){
            if(via.trajetoLivre()){
                caminhoReservado.add(via);
            }
        }
        
        return caminhoReservado;
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
