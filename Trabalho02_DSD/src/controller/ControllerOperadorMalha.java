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
    
    private int status;
    private int qtdVeiculosRodando;
    private int qtdVeiculosCriados;
    private int qtdVeiculosDestruidos;
    private int intervalo;
    
    private List<InterfaceObserver> observadores;
    
    private Malha malha;

    private static ControllerOperadorMalha instance;

    private ControllerOperadorMalha() {
        malha              = Malha.getInstance();
        status             = STATUS_EXECUCAO_PARADA;
        qtdVeiculosRodando = 0;
        observadores       = new ArrayList<>();
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
        }
    }
    
    public void iniciaSimulacao(int qtd, int intervalo){
        reset();
        this.qtdVeiculosCriados = qtd;
        this.intervalo          = intervalo;
        criarVeiculo();
    }
    
    public void encerraSimulacao(){
        status = STATUS_EXECUCAO_FINALIZADA;
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
    }
    
    public int getTotalLinhas(){
        return malha.getLinhas();
    }
    
    public int getTotalColunas(){
        return malha.getColunas();
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
