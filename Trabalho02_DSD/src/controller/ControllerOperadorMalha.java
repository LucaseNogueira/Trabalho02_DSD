package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import model.Malha;
import model.Veiculo;
import model.Via;
import utils.ImagemUtil;

/**
 *
 * @author lucas
 */
public class ControllerOperadorMalha {
    
    public static final int STATUS_EXECUCAO_INICIADA   = 1;
    public static final int STATUS_EXECUCAO_FINALIZADA = 2;
    public static final int STATUS_EXECUCAO_PARADA     = 3;
    
    public static final int PONTO_PARTIDA_CIMA     = 0;
    public static final int PONTO_PARTIDA_DIREITA  = 1;
    public static final int PONTO_PARTIDA_BAIXO    = 2;
    public static final int PONTO_PARTIDA_ESQUERDA = 3;
    
    private int status;
    private int qtdVeiculosRodando;
    private int qtdVeiculosCriados;
    private int qtdVeiculosDestruidos;
    private int intervalo;
    
    private List<InterfaceObserver> observadores;
    
    private Malha malha;

    private static ControllerOperadorMalha instance;
    
    private List<List<Via>> pontosPartida;

    private ControllerOperadorMalha() {
        malha              = Malha.getInstance();
        status             = STATUS_EXECUCAO_PARADA;
        qtdVeiculosRodando = 0;
        observadores       = new ArrayList<>();
        pontosPartida      = new ArrayList<>();
    }
    
    public synchronized static ControllerOperadorMalha getInstance(){
        if (instance == null) {
            instance = new ControllerOperadorMalha();
        }
        return instance;
    }
    
    public void reset(){
        qtdVeiculosRodando    = 0;
        qtdVeiculosDestruidos = 0;
        status                = STATUS_EXECUCAO_INICIADA;
    }
    
    public void criarVeiculo(){
        if((qtdVeiculosRodando < qtdVeiculosCriados) && status == STATUS_EXECUCAO_INICIADA){
            Veiculo veiculo = new Veiculo();
            veiculo.setIntervaloCriacao(this.intervalo);
            veiculo.start();
            qtdVeiculosRodando++;
        }
    }
    
    public void iniciaSimulacao(int qtd, int intervalo){
        reset();
        this.qtdVeiculosCriados = qtd;
        this.intervalo          = intervalo;
        notificarViaAlterada();
        criarVeiculo();
    }
    
    public void encerraSimulacao(){
        status = STATUS_EXECUCAO_FINALIZADA;
    }
    
    public void isEncerraSimulacao(){
        qtdVeiculosDestruidos++;
        if(qtdVeiculosDestruidos == qtdVeiculosCriados){
            for(InterfaceObserver obs : observadores){
                obs.notifyEncerrarSimulacao();
            }
        }
    }
    
    public void montaMalha(int[][] matriz){
        int linhas  = matriz.length;
        int colunas = matriz[0].length;
        
        malha.criarMalhaViaria(linhas, colunas);
        for(int i = 0; i < linhas; i++){
            for(int j = 0; j < colunas; j++){
                int    valorMatriz      = matriz[i][j];
                String nomePadraoImagem = ImagemUtil.getNomePadraoPosicaoMatriz(valorMatriz);
                String caminhoImagem    = "";
                if(nomePadraoImagem != ""){
                    caminhoImagem = ImagemUtil.getCaminhoImageIcon(nomePadraoImagem);
                }
                else{
                    caminhoImagem = ImagemUtil.getCaminhoImageIcon(valorMatriz);
                }
                
                malha.setVia(i, j, new Via(i, j, valorMatriz, caminhoImagem));
            }
        }
        criarPontosPartida();
    }
    
    private void criarPontosPartida(){
        for(int i = 0; i < 4; i++){
            pontosPartida.add(new ArrayList<Via>());
        }
        
        int linha  = malha.getLinhas() - 1;
        int coluna = malha.getColunas() - 1;
        for(int i = 0; i < coluna; i++){
            if(malha.getVia(linha, i).getSentido() == Via.SENTIDO_CIMA){
                pontosPartida.get(Via.SENTIDO_CIMA - 1).add(malha.getVia(linha, i));
            }
        }
        
        linha  = malha.getLinhas() - 1;
        coluna = 0;
        for(int i = 0; i < linha; i++){
            if(malha.getVia(i, coluna).getSentido() == Via.SENTIDO_DIREITA){
                pontosPartida.get(Via.SENTIDO_DIREITA - 1).add(malha.getVia(i, coluna));
            }
        }
        
        linha  = 0;
        coluna = malha.getColunas() - 1;
        for(int i = 0; i < coluna; i++){
            if(malha.getVia(linha, i).getSentido() == Via.SENTIDO_BAIXO){
                pontosPartida.get(Via.SENTIDO_BAIXO - 1).add(malha.getVia(linha, i));
            }
        }
        
        linha  = malha.getLinhas() - 1;
        coluna = malha.getColunas() - 1;
        for(int i = 0; i < linha; i++){
            if(malha.getVia(i, coluna).getSentido() == Via.SENTIDO_ESQUERDA){
                pontosPartida.get(Via.SENTIDO_ESQUERDA - 1).add(malha.getVia(i, coluna));
            }
        }
    }
    
    public int getTotalLinhas(){
        return malha.getLinhas();
    }
    
    public int getTotalColunas(){
        return malha.getColunas();
    }

    public int getQtdVeiculosRodando() {
        return qtdVeiculosRodando;
    }

    public void setQtdVeiculosRodando(int qtdVeiculosRodando) {
        this.qtdVeiculosRodando = qtdVeiculosRodando;
    }

    public int getQtdVeiculosCriados() {
        return qtdVeiculosCriados;
    }

    public void setQtdVeiculosCriados(int qtdVeiculosCriados) {
        this.qtdVeiculosCriados = qtdVeiculosCriados;
    }

    public int getQtdVeiculosDestruidos() {
        return qtdVeiculosDestruidos;
    }

    public void setQtdVeiculosDestruidos(int qtdVeiculosDestruidos) {
        this.qtdVeiculosDestruidos = qtdVeiculosDestruidos;
    }

    public List<List<Via>> getPontosPartida() {
        return pontosPartida;
    }

    public void setPontosPartida(List<List<Via>> pontosPartida) {
        this.pontosPartida = pontosPartida;
    }
    
    public ImageIcon getIcone(int linha, int coluna){
        return malha.getVia(linha, coluna).getImagem();
    }
    
    public void addObservador(InterfaceObserver observador){
        observadores.add(observador);
    }
    
    public void notificarViaAlterada(){
        for(InterfaceObserver observador : observadores){
            observador.notifyAlterarItem();
        }
    }
    
    public void notificarSimulacaoEncerrada(){
        qtdVeiculosDestruidos++;
        if ((qtdVeiculosDestruidos == qtdVeiculosCriados)) {
            for (InterfaceObserver observador : observadores) {
                observador.notifyEncerrarSimulacao();
            }
        }
    }
    
}
