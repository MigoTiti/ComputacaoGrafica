package computacaograficaswing.util;

import java.util.LinkedHashSet;
import java.util.Set;

public class AreaDeRecorte {
    
    private final Ponto pInicio;
    private final Ponto pFim;
    
    public AreaDeRecorte(Ponto ponto1, Ponto ponto2) {
        pInicio = ponto1;
        pFim = ponto2;
    }
    
    public int xMax() {
        return Integer.max(pInicio.getX(), pFim.getX());
    }
    
    public int yMax() {
        return Integer.max(pInicio.getY(), pFim.getY());
    }
    
    public int xMin() {
        return Integer.min(pInicio.getX(), pFim.getX());
    }
    
    public int yMin() {
        return Integer.min(pInicio.getY(), pFim.getY());
    }
    
    public static Set<Ponto> gerarAreaDesenho(Ponto pInicio, Ponto pFim) {
        Set<Ponto> area = new LinkedHashSet<>();
        
        if (pInicio.getX() > pFim.getX()) {
            for (int i = pFim.getX(); i <= pInicio.getX(); i++) {
                area.add(new Ponto(i, pFim.getY()));
                area.add(new Ponto(i, pInicio.getY()));
            }
        } else {
            for (int i = pInicio.getX(); i <= pFim.getX(); i++) {
                area.add(new Ponto(i, pFim.getY()));
                area.add(new Ponto(i, pInicio.getY()));
            }
        }
        
        if (pInicio.getY() > pFim.getY()) {
            for (int i = pFim.getY(); i <= pInicio.getY(); i++) {
                area.add(new Ponto(pFim.getX(), i));
                area.add(new Ponto(pInicio.getX(), i));
            }
        } else {
            for (int i = pInicio.getY(); i <= pFim.getY(); i++) {
                area.add(new Ponto(pFim.getX(), i));
                area.add(new Ponto(pInicio.getX(), i));
            }
        }
        
        return area;
    }
    
    public Set<Ponto> getTodosOsPontos() {
        Set<Ponto> area = new LinkedHashSet<>();
        
        if (pInicio.getX() > pFim.getX()) {
            for (int i = pFim.getX(); i <= pInicio.getX(); i++) {
                area.add(new Ponto(i, pFim.getY()));
                area.add(new Ponto(i, pInicio.getY()));
            }
        } else {
            for (int i = pInicio.getX(); i <= pFim.getX(); i++) {
                area.add(new Ponto(i, pFim.getY()));
                area.add(new Ponto(i, pInicio.getY()));
            }
        }
        
        if (pInicio.getY() > pFim.getY()) {
            for (int i = pFim.getY(); i <= pInicio.getY(); i++) {
                area.add(new Ponto(pFim.getX(), i));
                area.add(new Ponto(pInicio.getX(), i));
            }
        } else {
            for (int i = pInicio.getY(); i <= pFim.getY(); i++) {
                area.add(new Ponto(pFim.getX(), i));
                area.add(new Ponto(pInicio.getX(), i));
            }
        }
        
        return area;
    }
}
