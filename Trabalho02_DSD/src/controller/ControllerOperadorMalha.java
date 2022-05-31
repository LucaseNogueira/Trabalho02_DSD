/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import model.Malha;
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
    private int qtdCarrosRodando;
    private int qtdCarrosCriados;
    private int qtdCarrosDestruidos;
    
    private List<InterfaceObserver> observadores;
    
    private Malha malha;

    private static ControllerOperadorMalha instance;

    private ControllerOperadorMalha() {
        malha            = Malha.getInstance();
        status           = STATUS_EXECUCAO_PARADA;
        qtdCarrosRodando = 0;
        observadores     = new ArrayList<>();
    }
    
    public synchronized static ControllerOperadorMalha getInstance(){
        if (instance == null) {
            instance = new ControllerOperadorMalha();
        }
        return instance;
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
        qtdCarrosDestruidos++;
        if ((qtdCarrosDestruidos == qtdCarrosCriados)) {
            for (InterfaceObserver observador : observadores) {
                observador.notifyEncerrarSimulacao();
            }
        }
    }
    
}
