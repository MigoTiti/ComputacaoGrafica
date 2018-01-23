package computacaograficaswing.util;

public class Poligono3D extends Poligono2D {
    
    private Poligono2D poligonoProjetado;
    
    public Poligono3D() {
        super();
    }

    public Poligono2D getPoligonoProjetado() {
        return poligonoProjetado;
    }

    public void setPoligonoProjetado(Poligono2D poligonoProjetado) {
        this.poligonoProjetado = poligonoProjetado;
    }
}
