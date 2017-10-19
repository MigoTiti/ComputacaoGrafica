package computacaograficaswing.util;

import java.util.LinkedHashSet;
import java.util.Set;

public class BressenhamReta {
    
    private boolean trocaXY;
    private boolean trocaX;
    private boolean trocaY;

    public BressenhamReta() {
        trocaX = false;
        trocaXY = false;
        trocaY = false;
    }

    public Set<Ponto> aplicarBressenham(Ponto p1, Ponto p2) {
        int[] novosPontos = reflexao(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        int x1 = novosPontos[0];
        int y1 = novosPontos[1];
        int x2 = novosPontos[2];
        int y2 = novosPontos[3];

        int deltaX = x2 - x1;
        int deltaY = y2 - y1;

        double coeficienteAngular = (double) deltaY / (double) deltaX;

        double erro = coeficienteAngular - 0.5;

        int x = x1;
        int y = y1;

        Set<Ponto> pontos = new LinkedHashSet<>();
        pontos.add(new Ponto(x, y));

        while (x < x2) {
            if (erro >= 0) {
                y++;
                erro--;
            }

            x++;
            erro += coeficienteAngular;
            pontos.add(new Ponto(x, y));
        }

        reflexaoVolta(pontos);
        return pontos;
    }

    private int[] reflexao(int x1, int y1, int x2, int y2) {
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;

        double coeficienteAngular = (double) deltaY / (double) deltaX;

        if (coeficienteAngular > 1 || coeficienteAngular <= -1) {
            int aux = x1;
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

        return new int[]{x1, y1, x2, y2};
    }

    private void reflexaoVolta(Set<Ponto> pontos) {
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
