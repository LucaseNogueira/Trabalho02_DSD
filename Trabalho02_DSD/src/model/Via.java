package model;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import utils.ImagemUtil;

/**
 * Representa as viás tomadas pelos veiculos
 *
 * @author Lucas Nogueira
 * @author Fabio Frare
 */
public class Via {
    
    public static final int SENTIDO_NENHUM                    = 0;
    public static final int SENTIDO_CIMA                      = 1;
    public static final int SENTIDO_DIREITA                   = 2;
    public static final int SENTIDO_BAIXO                     = 3;
    public static final int SENTIDO_ESQUERDA                  = 4;
    public static final int SENTIDO_CRUZAMENTO_CIMA           = 5;
    public static final int SENTIDO_CRUZAMENTO_DIREITA        = 6;
    public static final int SENTIDO_CRUZAMENTO_BAIXO          = 7;
    public static final int SENTIDO_CRUZAMENTO_ESQUERDA       = 8;
    public static final int SENTIDO_CRUZAMENTO_CIMA_DIREITA   = 9;
    public static final int SENTIDO_CRUZAMENTO_CIMA_ESQUERDA  = 10;
    public static final int SENTIDO_CRUZAMENTO_DIREITA_BAIXO  = 11;
    public static final int SENTIDO_CRUZAMENTO_BAIXO_ESQUERDA = 12;
    
    private int sentido;
    private int linha;
    private int coluna;
    
    private String    caminhoImagem;
    private ImageIcon imagem;
    private Semaphore movimentacao;
    private Semaphore trajeto;
    
    private Veiculo   veiculo;

    public Via(int linha, int coluna, int sentido, String caminhoImagem) {
        this.linha         = linha;
        this.coluna        = coluna;
        this.caminhoImagem = caminhoImagem;
        this.imagem        = new ImageIcon(caminhoImagem);
        this.veiculo       = null;
    }
    
    public void adicionaVeiculo(Veiculo veiculo){
        try {
            movimentacao.acquire();
            veiculo.setLinha(linha);
            veiculo.setColuna(coluna);
            veiculo.setSentido(sentido);
            this.imagem  = new ImageIcon(ImagemUtil.getCaminhoImageIcon(veiculo.getNome(), veiculo.getSentido(), this.sentido));
            this.veiculo = veiculo;
        } catch (InterruptedException ex) {
            Logger.getLogger(Via.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            movimentacao.release();
        }
    }
    
    public void retiraVeiculo(){
        try {
            movimentacao.acquire();
            this.imagem  = new ImageIcon(ImagemUtil.getCaminhoImageIcon(caminhoImagem));
            this.veiculo = null;
        } catch (InterruptedException ex) {
            Logger.getLogger(Via.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            movimentacao.release();
            trajeto.release();
        }
    }
    
    public boolean isRodovia(){
        return sentido <= SENTIDO_ESQUERDA;
    }
    
    public boolean isOcupado(){
        return veiculo != null;
    }

    public int getSentido() {
        return sentido;
    }

    public void setSentido(int sentido) {
        this.sentido = sentido;
    }

    public ImageIcon getImagem() {
        return imagem;
    }

    public void setImagem(ImageIcon imagem) {
        this.imagem = imagem;
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
    
    public boolean trajetoLivre(){
        boolean livre = false;
        try {
            livre = trajeto.tryAcquire(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(Via.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return livre;
    }
    
}
