/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.File;
import utils.Leitor;
import view.ViewSimuladorTrafego;

/**
 *
 * @author lucas
 */
public class ControllerServiceSimuladorTrafego {
    
    private File arquivo;
    private ViewSimuladorTrafego view;

    public ControllerServiceSimuladorTrafego(String caminhoArquivo) {
        this.arquivo = new File(caminhoArquivo);
    }
    
    public void iniciaSimulador(){
        try{
            if(!arquivo.exists()){
                throw new Exception("Arquivo não encontrado");
            }
            montaTela();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void montaTela(){
        Leitor leitorArquivo = new Leitor(arquivo);
        int[][] matriz       = leitorArquivo.getMatriz();
        view                 = new ViewSimuladorTrafego(matriz);
        
        view.setVisible(true);
    }
}
