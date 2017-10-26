package computacaograficaswing.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Poligono {

    private Set<Reta> retas;

    public Poligono(Reta reta) {
        this.retas = new HashSet<>();
        this.retas.add(reta);
    }

    public Poligono(Set<Reta> retas) {
        this.retas = retas;
    }

    public Poligono() {
        this.retas = new HashSet<>();
    }

    public Set<Reta> getRetas() {
        return retas;
    }

    public void setRetas(Set<Reta> retas) {
        this.retas = retas;
    }

    public static List<Ponto> getIntersecoes(Set<Poligono> poligonos, int yVarredura) {
        List<Ponto> pontos = new ArrayList<>();

        for (Poligono poligono : poligonos) {
            for (Reta reta : poligono.getRetas()) {
                if (yVarredura <= reta.getYMaximo() && yVarredura >= reta.getYMinimo()) {
                    Ponto min = reta.getPontoMinimo();
                    int x = (int) ((reta.get1SobreM() * (yVarredura - min.getY())) + min.getX());
                    pontos.add(new Ponto(x, yVarredura));
                }
            }
        }

        pontos.sort((Ponto o1, Ponto o2) -> Integer.compare(o1.getX(), o2.getX()));

        for (int i = pontos.size() - 1; i >= 0; i--) {
            Ponto ponto = pontos.get(i);
            int contagem = Collections.frequency(pontos, ponto);
            if (contagem > 1) {
                if (getIntersecoes(poligonos, ponto.getX(), ponto.getY()) == 1) {
                    pontos.remove(i);
                }
            }
        }


        refatorarPontos(pontos);



        return pontos;
    }

    public static void refatorarPontos(List<Ponto> pontos) {
        int indiceInicial = 0;
        int indiceFinal = 0;
        List<Integer> pontosARemover = new ArrayList<>();

        for (int i = 1; i < pontos.size(); i++) {
            int xAux = pontos.get(i).getX();
            if (xAux == pontos.get(i - 1).getX() + 1) {
                indiceFinal = i;
            } else {
                if (indiceFinal - indiceInicial - 1 > 0) {
                    if (indiceInicial == 0) {
                        pontosARemover.add(0);
                    }

                    for (int j = indiceInicial + 1; j < indiceFinal; j++) {
                        pontosARemover.add(j);
                    }

                    if (indiceFinal == pontos.size() - 1) {
                        pontosARemover.add(pontos.size() - 1);
                    }
                }

                indiceInicial = i;
                indiceFinal = i;
            }
        }

        if (indiceFinal - indiceInicial - 1 > 0) {
            if (indiceInicial == 0) {
                pontosARemover.add(0);
            }

            for (int j = indiceInicial + 1; j < indiceFinal; j++) {
                pontosARemover.add(j);
            }

            if (indiceFinal == pontos.size() - 1) {
                pontosARemover.add(pontos.size() - 1);
            }
        } else if (indiceInicial != indiceFinal && indiceFinal == pontos.size() - 1) {
            pontosARemover.add(pontos.size() - 1);
        }
        
        Collections.reverse(pontosARemover);
        
        pontosARemover.stream().forEach((integer) -> {
            pontos.remove(integer.intValue());
        });
    }

    public static int getIntersecoes(Set<Poligono> poligonos, int x, int y) {
        int contagem = 0;
        for (Poligono poligono : poligonos) {
            for (Reta reta : poligono.getRetas()) {
                for (Ponto ponto : reta.getPontos()) {
                    if (ponto.getX() == x && ponto.getY() == y && ponto.getY() != reta.getYMinimo()) {
                        contagem++;
                    }
                }
            }
        }

        return contagem;
    }

    public static boolean hasIntersecao(Set<Poligono> poligonos, int x, int y) {
        for (Poligono poligono : poligonos) {
            for (Reta reta : poligono.getRetas()) {
                for (Ponto ponto : reta.getPontos()) {
                    if (ponto.getX() == x && ponto.getY() == y) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
