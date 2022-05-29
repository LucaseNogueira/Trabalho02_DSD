package model;

import java.util.concurrent.Semaphore;
import javax.swing.ImageIcon;

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
    
    private boolean cruzamento;
    private boolean isRodovia;
    
    private String    imagemPadrao;
    private ImageIcon imagem;
    private Semaphore mutex;
    
    private Veiculo   veiculo;
    
    
    public void adicionaVeiculoEstrada(Veiculo veiculo){
        this.imagem = new ImageIcon("assets/" + veiculo.getNome()+imagemPadrao.replace("assets/", ""));
    }
}
