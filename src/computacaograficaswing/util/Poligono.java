package computacaograficaswing.util;

import java.util.LinkedHashSet;
import java.util.Set;

public class Poligono {

    private Set<Reta> retas;

    public Poligono(Reta reta) {
        this.retas = new LinkedHashSet<>();
        this.retas.add(reta);
    }

    public Poligono() {
        this.retas = new LinkedHashSet<>();
    }

    public Set<Reta> getRetas() {
        return retas;
    }

    public void setRetas(Set<Reta> retas) {
        this.retas = retas;
    }
}
