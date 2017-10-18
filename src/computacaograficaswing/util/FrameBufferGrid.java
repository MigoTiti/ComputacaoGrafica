package computacaograficaswing.util;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FrameBufferGrid extends FrameBufferBase {

    private GridPane gridPane;

    public FrameBufferGrid(GridPane gridPane) {
        super();
        this.gridPane = gridPane;
    }

    public void preencherRecursivamente(Color corInicial, Color corPreenchimento, int x, int y) {
        if (x >= 0 && y >= 0 && y < areasdesenho.AreaDesenho.ORDEM && x < areasdesenho.AreaDesenho.ORDEM) {
            Rectangle rect = getRectanglePorXEY(x, y);
            Color corAtual = ((Color) rect.getFill());
            if (!corAtual.equals(corInicial)) {

            } else {
                rect.setFill(corPreenchimento);
                pontosDesenhados.add(rect);
                preencherRecursivamente(corInicial, corPreenchimento, x + 1, y);
                preencherRecursivamente(corInicial, corPreenchimento, x - 1, y);
                preencherRecursivamente(corInicial, corPreenchimento, x, y + 1);
                preencherRecursivamente(corInicial, corPreenchimento, x, y - 1);
            }
        }
    }

    private Rectangle getRectanglePorXEY(int x, int y) {
        Rectangle rect = null;

        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == y && GridPane.getColumnIndex(node) == x) {
                rect = (Rectangle) node;
                break;
            }
        }

        return rect;
    }

    public void desenharPonto(Rectangle rect, int x, int y) {
        pontosDesenhados.add(rect);
        gridPane.add(rect, x, y);
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
