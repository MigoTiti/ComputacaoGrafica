package computacaograficaswing.util;

import computacaograficaswing.util.transformacoes.Ponto2D;
import java.util.LinkedHashSet;
import java.util.Set;

public class AreaDeRecorte {
    
    private final Ponto2D pInicio;
    private final Ponto2D pFim;
    
    public AreaDeRecorte(Ponto2D ponto1, Ponto2D ponto2) {
        pInicio = ponto1;
        pFim = ponto2;
    }
    
    public double xMax() {
        return Double.max(pInicio.getX(), pFim.getX());
    }
    
    public double yMax() {
        return Double.max(pInicio.getY(), pFim.getY());
    }
    
    public double xMin() {
        return Double.min(pInicio.getX(), pFim.getX());
    }
    
    public double yMin() {
        return Double.min(pInicio.getY(), pFim.getY());
    }
    
    public static Set<Ponto2D> gerarAreaDesenho(Ponto2D pInicio, Ponto2D pFim) {
        Set<Ponto2D> area = new LinkedHashSet<>();
        
        if (pInicio.getX() > pFim.getX()) {
            for (int i = pFim.getXArredondado(); i <= pInicio.getXArredondado(); i++) {
                area.add(new Ponto2D(i, pFim.getY()));
                area.add(new Ponto2D(i, pInicio.getY()));
            }
        } else {
            for (int i = pInicio.getXArredondado(); i <= pFim.getXArredondado(); i++) {
                area.add(new Ponto2D(i, pFim.getY()));
                area.add(new Ponto2D(i, pInicio.getY()));
            }
        }
        
        if (pInicio.getY() > pFim.getY()) {
            for (int i = pFim.getYArredondado(); i <= pInicio.getYArredondado(); i++) {
                area.add(new Ponto2D(pFim.getX(), i));
                area.add(new Ponto2D(pInicio.getX(), i));
            }
        } else {
            for (int i = pInicio.getYArredondado(); i <= pFim.getYArredondado(); i++) {
                area.add(new Ponto2D(pFim.getX(), i));
                area.add(new Ponto2D(pInicio.getX(), i));
            }
        }
        
        return area;
    }
    
    public Set<Ponto2D> getTodosOsPontos() {
        Set<Ponto2D> area = new LinkedHashSet<>();
        
        if (pInicio.getX() > pFim.getX()) {
            for (int i = pFim.getXArredondado(); i <= pInicio.getXArredondado(); i++) {
                area.add(new Ponto2D(i, pFim.getY()));
                area.add(new Ponto2D(i, pInicio.getY()));
            }
        } else {
            for (int i = pInicio.getXArredondado(); i <= pFim.getXArredondado(); i++) {
                area.add(new Ponto2D(i, pFim.getY()));
                area.add(new Ponto2D(i, pInicio.getY()));
            }
        }
        
        if (pInicio.getY() > pFim.getY()) {
            for (int i = pFim.getYArredondado(); i <= pInicio.getYArredondado(); i++) {
                area.add(new Ponto2D(pFim.getX(), i));
                area.add(new Ponto2D(pInicio.getX(), i));
            }
        } else {
            for (int i = pInicio.getYArredondado(); i <= pFim.getYArredondado(); i++) {
                area.add(new Ponto2D(pFim.getX(), i));
                area.add(new Ponto2D(pInicio.getX(), i));
            }
        }
        
        return area;
    }
}
