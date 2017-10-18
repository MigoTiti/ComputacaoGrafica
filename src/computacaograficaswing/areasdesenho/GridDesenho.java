package computacaograficaswing.areasdesenho;

import static computacaograficaswing.areasdesenho.AreaDesenho.corPadrao;
import computacaograficaswing.util.FrameBufferGrid;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class GridDesenho extends AreaDesenho{
    
    protected GridPane gridPane;
    
    protected void inicializarPlano() {
        gridPane = new GridPane();
        gridPane.setStyle("-fx-border-color: black; -fx-border-width: 1 1 1 1; -fx-padding: 0;");
        
        for (int i = 0; i < ORDEM; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(TAMANHO_CELULA));         
            gridPane.getRowConstraints().add(new RowConstraints(TAMANHO_CELULA));
        }
        
        gridPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, new Insets(0.5))));
        
        gridPane.setOnDragDetected((MouseEvent event) -> {
            gridPane.startFullDrag();
            event.consume();
        });
        
        for (int i = 0; i < ORDEM; i++) {
            for (int j = 0; j < ORDEM; j++) {
                gridPane.add(gerarRect(corPadrao), i, j);
            }
        }
        
        frameBuffer = new FrameBufferGrid(gridPane);
    }

    @Override
    protected abstract Rectangle gerarRect(Color cor);
    
    @Override
    protected void aplicarBuffer() {
        gridPane = ((FrameBufferGrid)frameBuffer).getGridPane();
    }
}
