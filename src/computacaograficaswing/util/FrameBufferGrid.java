package computacaograficaswing.util;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FrameBufferGrid extends FrameBufferBase {

    private GridPane gridPane;

    private List<Ponto> pontosPreenchidos;
    
    private Rectangle[][] gridPaneMatriz;

    public FrameBufferGrid(GridPane gridPane, Rectangle[][] gridPaneMatriz) {
        super();
        this.gridPane = gridPane;
        this.gridPaneMatriz = gridPaneMatriz;
    }

    public void preencherRecursivamente(Color corInicial, Color corPreenchimento, int x, int y) {
        pontosPreenchidos = new ArrayList<>();
        iniciarPreenchimento(corInicial, corPreenchimento, x, y);
        System.err.println("");
    }

    public void iniciarPreenchimento(Color corInicial, Color corPreenchimento, int x, int y) {
        if (x >= 0 && y >= 0 && y < computacaograficaswing.areasdesenho.AreaDesenho.ORDEM && x < computacaograficaswing.areasdesenho.AreaDesenho.ORDEM) {
            Rectangle rect = gridPaneMatriz[x][y];
            Color corAtual = ((Color) rect.getFill());
            if (!corAtual.equals(corInicial)) {

            } else {
                rect.setFill(corPreenchimento);
                pontosDesenhados.add(rect);
                pontosPreenchidos.add(new Ponto(x, y));
                
                if (!pontosPreenchidos.contains(new Ponto(x + 1, y))) {
                    iniciarPreenchimento(corInicial, corPreenchimento, x + 1, y);
                }

                if (!pontosPreenchidos.contains(new Ponto(x - 1, y))) {
                    iniciarPreenchimento(corInicial, corPreenchimento, x - 1, y);
                }

                if (!pontosPreenchidos.contains(new Ponto(x, y + 1))) {
                    iniciarPreenchimento(corInicial, corPreenchimento, x, y + 1);
                }

                if (!pontosPreenchidos.contains(new Ponto(x, y - 1))) {
                    iniciarPreenchimento(corInicial, corPreenchimento, x, y - 1);
                }
            }
        }
    }

    public Rectangle getRectanglePorXEY(int x, int y) {
        Rectangle rect = null;

        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == y && GridPane.getColumnIndex(node) == x) {
                rect = (Rectangle) node;
                break;
            }
        }

        return rect;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPaneMatriz(Rectangle[][] gridPaneMatriz) {
        this.gridPaneMatriz = gridPaneMatriz;
    }

    public Rectangle[][] getGridPaneMatriz() {
        return gridPaneMatriz;
    }
}
