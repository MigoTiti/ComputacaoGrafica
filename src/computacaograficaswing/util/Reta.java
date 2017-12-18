package computacaograficaswing.util;

public class Reta {
    
    private final Ponto pontoInicial;
    private final Ponto pontoFinal;

    public Reta(Ponto ponto1, Ponto ponto2) {      
        pontoInicial = ponto1;
        pontoFinal = ponto2;
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
        
        return (int)Math.round(coeficienteNovo * (yEscolhido - reta.yMinimo()) + reta.xParaYMin());
    }
}
