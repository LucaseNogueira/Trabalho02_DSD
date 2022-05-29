package model;

import controller.ControllerOperadorVeiculoMalha;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Veiculos 
 *
 * @author Lucas Nogueira
 * @author Fabio Frare
 */
public class Veiculo extends Thread{
    
    private int     linhaAtual;
    private int     colunaAtual;
    private int     sentido;
    private boolean visivel;
    private boolean rodando;
    
    private String  nome;
    private Random  aceleracao;
    
    private ControllerOperadorVeiculoMalha operador;
    
    @Override
    public void run(){
        while(visivel){
            if(rodando){
                operador.andar();
            }
            else{
                operador.nascer();
            }
            try{
                sleep(aceleracao.nextInt(1000));
            } catch (InterruptedException ex) {
                Logger.getLogger(Veiculo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSentido() {
        return sentido;
    }

    public void setSentido(int sentido) {
        this.sentido = sentido;
    }
}
