package computacaograficaswing.util.transformacoes;

import java.util.Set;

public class RetaTransformacao {
    
    private final Ponto2D pontoInicial;
    private final Ponto2D pontoFinal;

    public RetaTransformacao(Ponto2D ponto1, Ponto2D ponto2) {      
        pontoInicial = ponto1;
        pontoFinal = ponto2;
    }
    
    public Set<Ponto2D> getPontos() {
        return new BressenhamTransformacao().aplicarBressenham(pontoInicial, pontoFinal);
    }
    
    @Override
    public String toString() {
        return pontoInicial.toString() + " - " + pontoFinal.toString();
    }

    public Ponto2D getPontoInicial() {
        return pontoInicial;
    }

    public Ponto2D getPontoFinal() {
        return pontoFinal;
    }
}
