package utils;

/**
 *
 * @author Lucas Nogueira
 * @author Fabio Frare
 */
public class ImagemUtil {

    public static final String ARQUIVO_CAMINHO_PADRAO = "media/icone/";
    public static final String ARQUIVO_EXTENCAO = ".png";

    public static String getCaminhoImageIcon(String nomeObjeto) {
        return ARQUIVO_CAMINHO_PADRAO + nomeObjeto + ARQUIVO_EXTENCAO;
    }

    public static String getCaminhoImageIcon(int sentidoVia) {
        String nomeObjeto = getSentencaSentidoObjeto(sentidoVia);

        return ARQUIVO_CAMINHO_PADRAO + nomeObjeto + ARQUIVO_EXTENCAO;
    }

    public static String getCaminhoImageIcon(String nomeVeiculo, int sentidoVeiculo, int sentidoVia) {
        String nomeObjeto = getSentencaSentidoObjeto(sentidoVia);
        int sentidoTratado = getSentidoVeiculoTratado(sentidoVeiculo);

        return ARQUIVO_CAMINHO_PADRAO + nomeVeiculo + "_" + nomeObjeto + "_" + sentidoTratado + ARQUIVO_EXTENCAO;
    }

    public static String getNomePadraoPosicaoMatriz(int posicao) {
        String nomeObjeto = "";

        switch (posicao) {
            case 0:
                nomeObjeto = "grama";
                break;
            case 5:
                nomeObjeto = "cruzamento";
                break;
            case 6:
                nomeObjeto = "cruzamento";
                break;
            case 7:
                nomeObjeto = "cruzamento";
                break;
            case 8:
                nomeObjeto = "cruzamento";
                break;
            case 9:
                nomeObjeto = "cruzamento";
                break;
            case 10:
                nomeObjeto = "cruzamento";
                break;
            case 11:
                nomeObjeto = "cruzamento";
                break;
            case 12:
                nomeObjeto = "cruzamento";
                break;
            default:
                System.out.println("Classe: ImagemUtil no método getNomePadraoPosicaoMatriz()");

        }

//        if(posicao == 5 || posicao == 6 || posicao == 7 || posicao == 8 || posicao == 9 || posicao == 10 || posicao == 11 || posicao == 12){
//            nomeObjeto = "cruzamento";
//        }
//        else
//        if(posicao == 0){
//            nomeObjeto = "grama";
//        }
        return nomeObjeto;
    }

    private static String getSentencaSentidoObjeto(int sentidoObjeto) {
        String resposta = "";
        String respostaDirecao = "";

        if (sentidoObjeto == 1 || sentidoObjeto == 3) {
            respostaDirecao = "_esquerda";
            if (sentidoObjeto == 3) {
                respostaDirecao = "_direita";
            }
            resposta = "estrada_vertical" + respostaDirecao;
        }
        if (sentidoObjeto == 2 || sentidoObjeto == 4) {
            respostaDirecao = "_baixo";
            if (sentidoObjeto == 4) {
                respostaDirecao = "_cima";
            }
            resposta = "estrada_horizontal" + respostaDirecao;
        }

        return resposta;
    }

    private static int getSentidoVeiculoTratado(int sentidoVeiculo) {
        int i;

        if (sentidoVeiculo == 1 || sentidoVeiculo == 2) {
            i = 1;
        } else {
            i = 2;
        }

        return i;
    }

}
