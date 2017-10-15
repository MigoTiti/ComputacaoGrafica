package computacaograficaswing.util;

import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class FrameBuffer {
    
    private GridPane primeiroQuadrante;
    private GridPane segundoQuadrante;
    private GridPane terceiroQuadrante;
    private GridPane quartoQuadrante;
    
    private final ArrayList<Rectangle> pontosDesenhados;

    public FrameBuffer(GridPane primeiroQuadrante, GridPane segundoQuadrante, GridPane terceiroQuadrante, GridPane quartoQuadrante) {
        this.primeiroQuadrante = primeiroQuadrante;
        this.segundoQuadrante = segundoQuadrante;
        this.terceiroQuadrante = terceiroQuadrante;
        this.quartoQuadrante = quartoQuadrante;
        this.pontosDesenhados = new ArrayList<>();
    }

    public void desenharPonto(int quadrante, Rectangle rect, int x, int y) {
        pontosDesenhados.add(rect);
        switch (quadrante) {
            case 1:
                primeiroQuadrante.add(rect, x, y);
                break;
            case 2:
                segundoQuadrante.add(rect, x, y);
                break;
            case 3:
                terceiroQuadrante.add(rect, x, y);
                break;
            case 4:
                quartoQuadrante.add(rect, x, y);
                break;
        }
    }
    
    public void adicionarRect(Rectangle rect) {
        pontosDesenhados.add(rect);
    }
    
    public void removerRect(Rectangle rect) {
        pontosDesenhados.remove(rect);
    }
    
    public void limparBuffer() {
        pontosDesenhados.stream().forEach((pontoDesenhado) -> {
            pontoDesenhado.setFill(PlanoCartesiano.corPadrao);
        });
        
        pontosDesenhados.clear();
    }
    
    public void setPrimeiroQuadrante(GridPane primeiroQuadrante) {
        this.primeiroQuadrante = primeiroQuadrante;
    }

    public void setQuartoQuadrante(GridPane quartoQuadrante) {
        this.quartoQuadrante = quartoQuadrante;
    }

    public void setSegundoQuadrante(GridPane segundoQuadrante) {
        this.segundoQuadrante = segundoQuadrante;
    }

    public void setTerceiroQuadrante(GridPane terceiroQuadrante) {
        this.terceiroQuadrante = terceiroQuadrante;
    }

    public GridPane getPrimeiroQuadrante() {
        return primeiroQuadrante;
    }

    public GridPane getQuartoQuadrante() {
        return quartoQuadrante;
    }

    public GridPane getSegundoQuadrante() {
        return segundoQuadrante;
    }

    public GridPane getTerceiroQuadrante() {
        return terceiroQuadrante;
    }
}
