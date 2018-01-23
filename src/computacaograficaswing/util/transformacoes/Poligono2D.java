package computacaograficaswing.util.transformacoes;

import java.util.LinkedHashSet;
import java.util.Set;

public class Poligono2D {
    
    private Set<RetaTransformacao> retas;

    public Poligono2D() {
        retas = new LinkedHashSet<>();
    }

    public Poligono2D(RetaTransformacao reta) {
        retas = new LinkedHashSet<>();
        retas.add(reta);
    }

    public Set<RetaTransformacao> getRetas() {
        return retas;
    }

    public void setRetas(Set<RetaTransformacao> retas) {
        this.retas = retas;
    }
}
