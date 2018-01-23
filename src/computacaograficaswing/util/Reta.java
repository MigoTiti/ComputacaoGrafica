package computacaograficaswing.util;

import java.util.Set;

public class Reta {
    
    private final Ponto pontoInicial;
    private final Ponto pontoFinal;

    public Reta(Ponto ponto1, Ponto ponto2) {      
        pontoInicial = ponto1;
        pontoFinal = ponto2;
    }
    
    @Override
    public String toString() {
        return pontoInicial.toString() + " - " + pontoFinal.toString();
    }
    
    public Set<Ponto> getPontos() {
        return new BressenhamReta().aplicarBressenham(pontoInicial, pontoFinal);
    }
    
    public int yMinimo() {
        return Integer.min(pontoInicial.getY(), pontoFinal.getY());
    }
   
    public int yMaximo() {
        return Integer.max(pontoInicial.getY(), pontoFinal.getY());
    }
    
    public int xParaYMin() {
        if (pontoInicial.getY() < pontoFinal.getY()) {
            return pontoInicial.getX();
        } else {
            return pontoFinal.getX();
        }
    }
    
    public double coeficienteAngular() {
        int deltaX = pontoFinal.getX() - pontoInicial.getX();
        int deltaY = pontoFinal.getY() - pontoInicial.getY();

        double coeficienteAngular = (double) deltaY / (double) deltaX;
        return coeficienteAngular;
    }

    public Ponto getPontoInicial() {
        return pontoInicial;
    }

    public Ponto getPontoFinal() {
        return pontoFinal;
    }
    
    public static int intersecaoComY(int yEscolhido, Reta reta) {
        double coeficienteNovo = 1 / reta.coeficienteAngular();
        
        return Math.toIntExact(Math.round(coeficienteNovo * (yEscolhido - reta.yMinimo()) + reta.xParaYMin()));
    }
    
    public static int intersecaoComX(int xEscolhido, Reta reta) {
        double coeficienteNovo = 1 / reta.coeficienteAngular();
        
        return Math.toIntExact(Math.round(((xEscolhido - reta.xParaYMin())/coeficienteNovo) + reta.yMinimo()));
    }
}
