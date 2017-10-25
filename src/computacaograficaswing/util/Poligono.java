package computacaograficaswing.util;

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

    public static Set<Ponto> getIntersecoes(Set<Poligono> poligonos, int y) {
        Set<Ponto> pontos = new TreeSet<>((Ponto o1, Ponto o2) -> Integer.compare(o1.getX(), o2.getX()));
        
        for (Poligono poligono : poligonos) {
            for (Reta reta : poligono.getRetas()) {
                if (y <= reta.getYMaximo() && y >= reta.getYMinimo()) {
                    Ponto min = reta.getPontoMinimo();
                    int x = (int)Math.round((reta.get1SobreM() * (y - min.getY())) + min.getX());
                    pontos.add(new Ponto(x, y));
                }
            }
        }
        
        return pontos;
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
