package computacaograficaswing.util;

import java.util.HashSet;
import java.util.Set;

public class BressenhamCirculo {
    
    public static Set<Ponto2D> aplicarBressenhamCirculo(Ponto2D centro, int raio) {
        int deslocamentoX = centro.getXArredondado();
        int deslocamentoY = centro.getYArredondado();

        int x = 0;
        int y = raio;
        int p = 1 - raio;

        Set<Ponto2D> pontos = new HashSet<>();
        pontos.add(new Ponto2D(x, y));

        while (x < y) {
            x++;
            if (p < 0) {
                p += (2 * x) + 3;
            } else {
                y--;
                p += (2 * x) - (2 * y) + 5;
            }

            pontos.add(new Ponto2D(x, y));
        }

        reflexao(pontos);

        if (deslocamentoX > 0) {
            pontos.stream().forEach((ponto) -> {
                ponto.setX(ponto.getX() + deslocamentoX);
            });
        }

        if (deslocamentoY > 0) {
            pontos.stream().forEach((ponto) -> {
                ponto.setY(ponto.getY() + deslocamentoY);
            });
        }

        return pontos;
    }

    private static void reflexao(Set<Ponto2D> pontos) {
        Set<Ponto2D> aux = new HashSet<>(pontos);

        aux.stream().map((ponto) -> {
            pontos.add(new Ponto2D(-ponto.getX(), ponto.getY()));
            return ponto;
        }).map((ponto) -> {
            pontos.add(new Ponto2D(ponto.getX(), -ponto.getY()));
            return ponto;
        }).map((ponto) -> {
            pontos.add(new Ponto2D(-ponto.getX(), -ponto.getY()));
            return ponto;
        }).map((ponto) -> {
            pontos.add(new Ponto2D(ponto.getY(), ponto.getX()));
            return ponto;
        }).map((ponto) -> {
            pontos.add(new Ponto2D(-ponto.getY(), ponto.getX()));
            return ponto;
        }).map((ponto) -> {
            pontos.add(new Ponto2D(ponto.getY(), -ponto.getX()));
            return ponto;
        }).forEach((ponto) -> {
            pontos.add(new Ponto2D(-ponto.getY(), -ponto.getX()));
        });
    }
}
