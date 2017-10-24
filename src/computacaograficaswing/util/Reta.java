package computacaograficaswing.util;

import java.util.Set;

public class Reta {
    
    private Set<Ponto> reta;

    public Reta(Set<Ponto> reta) {
        this.reta = reta;
    }

    public Set<Ponto> getReta() {
        return reta;
    }

    public void setReta(Set<Ponto> reta) {
        this.reta = reta;
    }
    
    public int getYMinimo() {
        int yMin = Integer.MAX_VALUE;
        
        for (Ponto ponto : reta) {
            int aux = ponto.getY();
            if (aux < yMin)
                yMin = aux;
        }
        
        return yMin;
    }
    
    public int getYMaximo() {
        int yMax = Integer.MAX_VALUE;
        
        for (Ponto ponto : reta) {
            int aux = ponto.getY();
            if (aux < yMax)
                yMax = aux;
        }
        
        return yMax;
    }
}
