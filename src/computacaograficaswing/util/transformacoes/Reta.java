package computacaograficaswing.util.transformacoes;

import java.util.Set;

public class Reta {

    private final Ponto2D pontoInicial;
    private final Ponto2D pontoFinal;

    public Reta(Ponto2D ponto1, Ponto2D ponto2) {
        pontoInicial = ponto1;
        pontoFinal = ponto2;
    }

    public Set<Ponto2D> getPontos() {
        return new BressenhamReta().aplicarBressenham(pontoInicial, pontoFinal);
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

    public double yMinimo() {
        return Double.min(pontoInicial.getY(), pontoFinal.getY());
    }

    public double yMaximo() {
        return Double.max(pontoInicial.getY(), pontoFinal.getY());
    }

    public double xParaYMin() {
        if (pontoInicial.getY() < pontoFinal.getY()) {
            return pontoInicial.getX();
        } else {
            return pontoFinal.getX();
        }
    }

    public double coeficienteAngular() {
        double deltaX = pontoFinal.getX() - pontoInicial.getX();
        double deltaY = pontoFinal.getY() - pontoInicial.getY();

        double coeficienteAngular = deltaY / deltaX;
        return coeficienteAngular;
    }

    public static double intersecaoComY(double yEscolhido, Reta reta) {
        double coeficienteNovo = 1 / reta.coeficienteAngular();

        return coeficienteNovo * (yEscolhido - reta.yMinimo()) + reta.xParaYMin();
    }

    public static double intersecaoComX(double xEscolhido, Reta reta) {
        double coeficienteNovo = 1 / reta.coeficienteAngular();

        return ((xEscolhido - reta.xParaYMin()) / coeficienteNovo) + reta.yMinimo();
    }
}
