package areasdesenho;

import static areasdesenho.AreaDesenho.corPadrao;
import static areasdesenho.AreaDesenho.corSelecionada;
import computacaograficaswing.util.FrameBufferGrid;
import javafx.geometry.Insets;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridDesenho extends AreaDesenho{
    
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
    protected Rectangle gerarRect(Color cor) {
        Rectangle rect = new Rectangle(TAMANHO_CELULA, TAMANHO_CELULA, cor);
        rect.setOnMouseClicked((MouseEvent event) -> {
            if (rect.getFill().equals(corPadrao)) {
                rect.setFill(corSelecionada);
                frameBuffer.getPontosDesenhados().add(rect);
            } else {
                rect.setFill(corPadrao);
                frameBuffer.getPontosDesenhados().remove(rect);
            }
        });

        rect.setOnMouseDragOver((MouseDragEvent event) -> {
            if (corSelecionada.equals(corPadrao) && !rect.getFill().equals(corPadrao)) {
                rect.setFill(corPadrao);
                frameBuffer.getPontosDesenhados().remove(rect);
            } else if (corSelecionada.equals(corPadrao) && rect.getFill().equals(corPadrao)) {

            } else {
                rect.setFill(corSelecionada);
                frameBuffer.getPontosDesenhados().add(rect);
            }
        });

        return rect;
    }
    
    @Override
    protected void aplicarBuffer() {
        gridPane = ((FrameBufferGrid)frameBuffer).getGridPane();
    }
}
