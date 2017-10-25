package computacaograficaswing.util;

import java.util.Iterator;
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
    
    public double get1SobreM() {
        Ponto p1 = getPonto1();
        Ponto p2 = null;
        
        Iterator iterator = pontos.iterator();
        while (iterator.hasNext()) { 
            p2 = (Ponto)iterator.next(); 
        }
        
        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();

        int deltaX = x2 - x1;
        int deltaY = y2 - y1;

        double coeficienteAngular = (double) deltaY / (double) deltaX;
        
        return 1/coeficienteAngular;
    }
    
    public Ponto getPonto2() {
        Ponto p2 = null;
        
        Iterator iterator = pontos.iterator();
        while (iterator.hasNext()) { 
            p2 = (Ponto)iterator.next(); 
        }
        
        return p2;
    }
    
    public Ponto getPonto1() {
        return pontos.iterator().next();
    }
    
    public Ponto getPontoMinimo() {
        int yMin = Integer.MAX_VALUE;
        Ponto pontoMin = null;
        
        for (Ponto ponto : pontos) {
            int aux = ponto.getY();
            if (aux <= yMin) {
                yMin = aux;
                pontoMin = ponto;
            }
        }
        
        return pontoMin;
    }
    
    public Ponto getPontoMaximo() {
        int yMax = 0;
        Ponto pontoMax = null;
        
        for (Ponto ponto : pontos) {
            int aux = ponto.getY();
            if (aux >= yMax) {
                yMax = aux;
                pontoMax = ponto;
            }
        }
        
        return pontoMax;
    }
    
    public int getYMaximo() {
        int yMax = 0;
        
        for (Ponto ponto : pontos) {
            int aux = ponto.getY();
            if (aux > yMax)
                yMax = aux;
        }
        
        return yMax;
    }
}
