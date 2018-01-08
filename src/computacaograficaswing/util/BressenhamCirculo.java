package computacaograficaswing.util;

import java.util.HashSet;
import java.util.Set;

public class BressenhamCirculo {
    
    public static Set<Ponto> aplicarBressenhamCirculo(Ponto centro, int raio) {
        int deslocamentoX = centro.getX();
        int deslocamentoY = centro.getY();

        int x = 0;
        int y = raio;
        int p = 1 - raio;

        Set<Ponto> pontos = new HashSet<>();
        pontos.add(new Ponto(x, y));

        while (x < y) {
            x++;
            if (p < 0) {
                p += (2 * x) + 3;
            } else {
                y--;
                p += (2 * x) - (2 * y) + 5;
            }

            pontos.add(new Ponto(x, y));
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

    private static void reflexao(Set<Ponto> pontos) {
        Set<Ponto> aux = new HashSet<>(pontos);

        aux.stream().map((ponto) -> {
            pontos.add(new Ponto(-ponto.getX(), ponto.getY()));
            return ponto;
        }).map((ponto) -> {
            pontos.add(new Ponto(ponto.getX(), -ponto.getY()));
            return ponto;
        }).map((ponto) -> {
            pontos.add(new Ponto(-ponto.getX(), -ponto.getY()));
            return ponto;
        }).map((ponto) -> {
            pontos.add(new Ponto(ponto.getY(), ponto.getX()));
            return ponto;
        }).map((ponto) -> {
            pontos.add(new Ponto(-ponto.getY(), ponto.getX()));
            return ponto;
        }).map((ponto) -> {
            pontos.add(new Ponto(ponto.getY(), -ponto.getX()));
            return ponto;
        }).forEach((ponto) -> {
            pontos.add(new Ponto(-ponto.getY(), -ponto.getX()));
        });
    }
}
