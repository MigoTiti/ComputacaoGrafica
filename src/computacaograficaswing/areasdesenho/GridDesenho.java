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

    @Override
    protected abstract Rectangle gerarRect(Color cor);
    
    @Override
    protected void aplicarBuffer() {
        gridPane = ((FrameBufferGrid)frameBuffer).getGridPane();
    }
}
