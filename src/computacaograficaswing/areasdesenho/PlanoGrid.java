package computacaograficaswing.areasdesenho;

import static computacaograficaswing.areasdesenho.AreaDesenho.ORDEM;
import static computacaograficaswing.areasdesenho.AreaDesenho.TAMANHO_CELULA;
import static computacaograficaswing.areasdesenho.AreaDesenho.corPadrao;
import computacaograficaswing.framebuffer.FrameBufferGrid;
import computacaograficaswing.util.Ponto2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class PlanoGrid extends AreaDesenho {

    protected GridPane gridPane;
    protected Rectangle[][] gridPaneMatriz;
    
    protected void desenharPonto(Color cor, Ponto2D p) {
        desenharPonto(cor, p.getXArredondado(), p.getYArredondado());
    }

    protected void desenharPonto(Color cor, int x, int y) {
        desenharPonto(cor, gridPaneMatriz[x][y]);
    }

    @Override
    protected Group inicializarPlano() {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-border-color: black; -fx-border-width: 1 1 1 1; -fx-padding: 0;");

        int ordem = ORDEM;

        for (int i = 0; i < ordem; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(TAMANHO_CELULA));
            gridPane.getRowConstraints().add(new RowConstraints(TAMANHO_CELULA));
        }

        gridPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        gridPane.setOnDragDetected((MouseEvent event) -> {
            gridPane.startFullDrag();
            event.consume();
        });

        gridPaneMatriz = new Rectangle[ordem][ordem];

        for (int i = 0; i < ordem; i++) {
            for (int j = 0; j < ordem; j++) {
                Rectangle rect = gerarRect(corPadrao);
                gridPane.add(rect, i, j);
                gridPaneMatriz[i][j] = rect;
            }
        }

        frameBuffer = new FrameBufferGrid(gridPaneMatriz);
        
        return new Group(gridPane);
    }

    protected void desenharPonto(Color cor, Rectangle rect) {
        rect.setFill(cor);

        if (!cor.equals(corPadrao)) {
            frameBuffer.getPontosDesenhados().add(rect);
        } else {
            frameBuffer.getPontosDesenhados().remove(rect);
        }
    }
}
