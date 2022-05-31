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
    
    private int     linha;
    private int     coluna;
    private int     sentido;
    private int     intervaloCriacao;
    private boolean visivel;
    private boolean rodando;
    
    private String  nome;
    private Random  aceleracao;
    
    private ControllerOperadorVeiculoMalha operador;
    
    public Veiculo(){
        defineNome();
        this.linha   = 0;
        this.coluna  = 0;
        this.visivel = true;
        this.rodando = false;
        operador = new ControllerOperadorVeiculoMalha(this);
    }
    
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

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public boolean isVisivel() {
        return visivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    public int getIntervaloCriacao() {
        return intervaloCriacao;
    }

    public void setIntervaloCriacao(int intervaloCriacao) {
        this.intervaloCriacao = intervaloCriacao;
    }
    
    private void defineNome(){
        Random random = new Random();
        
        switch(random.nextInt(4)){
            case 0:
                this.nome = "vaca";
                break;
            case 1:
                this.nome = "ovelha";
                break;
            case 2:
                this.nome = "galo";
                break;
            case 3:
                this.nome = "porco";
                break;
        }
    }
    
}
