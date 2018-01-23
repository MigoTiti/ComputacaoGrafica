package computacaograficaswing.util.transformacoes;

import java.util.LinkedHashSet;
import java.util.Set;

public class BressenhamTransformacao {
    
    private boolean trocaXY;
    private boolean trocaX;
    private boolean trocaY;

    public BressenhamTransformacao() {
        trocaX = false;
        trocaXY = false;
        trocaY = false;
    }

    public Set<Ponto2D> aplicarBressenham(Ponto2D p1, Ponto2D p2) {
        double[] novosPontos = reflexao(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        double x1 = novosPontos[0];
        double y1 = novosPontos[1];
        double x2 = novosPontos[2];
        double y2 = novosPontos[3];

        double deltaX = x2 - x1;
        double deltaY = y2 - y1;

        double coeficienteAngular = deltaY / deltaX;

        double erro = coeficienteAngular - 0.5;

        double x = x1;
        double y = y1;

        Set<Ponto2D> pontos = new LinkedHashSet<>();
        pontos.add(new Ponto2D(x, y));

        while (x < x2) {
            if (erro >= 0) {
                y++;
                erro--;
            }

            x++;
            erro += coeficienteAngular;
            pontos.add(new Ponto2D(x, y));
        }

        reflexaoVolta(pontos);
        return pontos;
    }

    private double[] reflexao(double x1, double y1, double x2, double y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;

        double coeficienteAngular = deltaY / deltaX;

        if (coeficienteAngular > 1 || coeficienteAngular <= -1) {
            double aux = x1;
            x1 = y1;
            y1 = aux;

            aux = x2;
            x2 = y2;
            y2 = aux;

            trocaXY = true;
        }

        if (x1 > x2) {
            x1 = -x1;
            x2 = -x2;

            trocaX = true;
        }

        if (y1 > y2) {
            y1 = -y1;
            y2 = -y2;

            trocaY = true;
        }

        return new double[]{x1, y1, x2, y2};
    }

    private void reflexaoVolta(Set<Ponto2D> pontos) {
        if (trocaY) {
            pontos.stream().forEach((ponto) -> {
                ponto.negarY();
            });
            trocaY = false;
        }

        if (trocaX) {
            pontos.stream().forEach((ponto) -> {
                ponto.negarX();
            });
            trocaX = false;
        }

        if (trocaXY) {
            pontos.stream().forEach((ponto) -> {
                ponto.trocarCoordenadas();
            });
            trocaXY = false;
        }
    }
}
