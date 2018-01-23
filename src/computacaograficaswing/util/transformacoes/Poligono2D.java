package computacaograficaswing.util.transformacoes;

import java.util.LinkedHashSet;
import java.util.Set;

public class Poligono2D {
    
    private Set<Reta> retas;

    public Poligono2D() {
        retas = new LinkedHashSet<>();
    }

    public Poligono2D(Reta reta) {
        retas = new LinkedHashSet<>();
        retas.add(reta);
    }

    public Set<Reta> getRetas() {
        return retas;
    }

    public void setRetas(Set<Reta> retas) {
        this.retas = retas;
    }
}
