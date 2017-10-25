package computacaograficaswing.util;

import java.util.HashSet;
import java.util.Set;

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
