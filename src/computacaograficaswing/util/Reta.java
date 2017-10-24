package computacaograficaswing.util;

import java.util.Set;

public class Reta {
    
    private Set<Ponto> pontos;

    public Reta(Set<Ponto> reta) {
        this.pontos = reta;
    }

    public Set<Ponto> getPontos() {
        return pontos;
    }

    public void setPontos(Set<Ponto> pontos) {
        this.pontos = pontos;
    }
    
    public int getYMinimo() {
        int yMin = Integer.MAX_VALUE;
        
        for (Ponto ponto : pontos) {
            int aux = ponto.getY();
            if (aux < yMin)
                yMin = aux;
        }
        
        return yMin;
    }
    
    public int getYMaximo() {
        int yMax = Integer.MAX_VALUE;
        
        for (Ponto ponto : pontos) {
            int aux = ponto.getY();
            if (aux < yMax)
                yMax = aux;
        }
        
        return yMax;
    }
}
