package computacaograficaswing.framebuffer;

import computacaograficaswing.util.Ponto;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FrameBufferGrid extends FrameBufferBase {

    private List<Ponto> pontosPreenchidos;

    private Rectangle[][] gridPaneMatriz;

    public FrameBufferGrid(Rectangle[][] gridPaneMatriz) {
        super();
        this.gridPaneMatriz = gridPaneMatriz;
    }

    public void preencherRecursivamente(Color corInicial, Color corPreenchimento, int x, int y) {
        pontosPreenchidos = new ArrayList<>();
        iniciarPreenchimento(corInicial, new Ponto(x, y));
        pontosPreenchidos.forEach((ponto) -> {
            desenharPonto(corPreenchimento, ponto.getX(), ponto.getY());
        });
    }

    public void iniciarPreenchimento(Color corInicial, Ponto p) {
        int x = p.getX();
        int y = p.getY();
        if (x >= 0 && y >= 0 && y < computacaograficaswing.areasdesenho.AreaDesenho.ORDEM && x < computacaograficaswing.areasdesenho.AreaDesenho.ORDEM) {
            Rectangle rect = gridPaneMatriz[x][y];
            Color corAtual = ((Color) rect.getFill());
            
            if (corAtual.equals(corInicial)) {
                pontosDesenhados.add(rect);
                pontosPreenchidos.add(p);

                if (!pontosPreenchidos.contains(new Ponto(x + 1, y))) {

                    iniciarPreenchimento(corInicial, new Ponto(x + 1, y));
                }

                if (!pontosPreenchidos.contains(new Ponto(x - 1, y))) {
                    iniciarPreenchimento(corInicial, new Ponto(x - 1, y));
                }

                if (!pontosPreenchidos.contains(new Ponto(x, y + 1))) {
                    iniciarPreenchimento(corInicial, new Ponto(x, y + 1));
                }

                if (!pontosPreenchidos.contains(new Ponto(x, y - 1))) {
                    iniciarPreenchimento(corInicial, new Ponto(x, y - 1));
                }
            }
        }
    }

    private void desenharPonto(Color cor, int x, int y) {
        gridPaneMatriz[x][y].setFill(cor);
        pontosDesenhados.add(gridPaneMatriz[x][y]);
    }

    public void setGridPaneMatriz(Rectangle[][] gridPaneMatriz) {
        this.gridPaneMatriz = gridPaneMatriz;
    }

    public Rectangle[][] getGridPaneMatriz() {
        return gridPaneMatriz;
    }
}
