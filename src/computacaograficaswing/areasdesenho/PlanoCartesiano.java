package computacaograficaswing.areasdesenho;

import computacaograficaswing.util.FrameBufferPlanoCartesiano;
import computacaograficaswing.util.Ponto;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class PlanoCartesiano extends AreaDesenho{

    protected GridPane primeiroQuadrante;
    protected GridPane segundoQuadrante;
    protected GridPane terceiroQuadrante;
    protected GridPane quartoQuadrante;

    protected GridPane inicializarPlano() {
        GridPane gridPane = new GridPane();

        primeiroQuadrante = new GridPane();
        segundoQuadrante = new GridPane();
        terceiroQuadrante = new GridPane();
        quartoQuadrante = new GridPane();

        primeiroQuadrante.setStyle("-fx-border-color: blue; -fx-border-width: 2 1 1 1; -fx-padding: 0;");
        segundoQuadrante.setStyle("-fx-border-color: blue; -fx-border-width: 2 1 1 2.5; -fx-padding: 0;");
        terceiroQuadrante.setStyle("-fx-border-color: blue; -fx-border-width: 1 1 2 2.5; -fx-padding: 0;");
        quartoQuadrante.setStyle("-fx-border-color: blue; -fx-border-width: 1 1 2 1; -fx-padding: 0;");

        for (int i = 0; i < ORDEM / 2; i++) {
            primeiroQuadrante.getColumnConstraints().add(new ColumnConstraints(TAMANHO_CELULA));
            segundoQuadrante.getColumnConstraints().add(new ColumnConstraints(TAMANHO_CELULA));
            terceiroQuadrante.getColumnConstraints().add(new ColumnConstraints(TAMANHO_CELULA));
            quartoQuadrante.getColumnConstraints().add(new ColumnConstraints(TAMANHO_CELULA));
            
            primeiroQuadrante.getRowConstraints().add(new RowConstraints(TAMANHO_CELULA));
            segundoQuadrante.getRowConstraints().add(new RowConstraints(TAMANHO_CELULA));
            terceiroQuadrante.getRowConstraints().add(new RowConstraints(TAMANHO_CELULA));
            quartoQuadrante.getRowConstraints().add(new RowConstraints(TAMANHO_CELULA));
        }

        primeiroQuadrante.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, new Insets(0.5))));
        segundoQuadrante.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, new Insets(0.5))));
        terceiroQuadrante.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, new Insets(0.5))));
        quartoQuadrante.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, new Insets(0.5))));

        gridPane.add(segundoQuadrante, 0, 0);
        gridPane.add(primeiroQuadrante, 1, 0);
        gridPane.add(terceiroQuadrante, 0, 1);
        gridPane.add(quartoQuadrante, 1, 1);

        gridPane.setOnDragDetected((MouseEvent event) -> {
            gridPane.startFullDrag();
            event.consume();
        });

        for (int i = 0; i < ORDEM / 2; i++) {
            for (int j = 0; j < ORDEM / 2; j++) {
                //Pane pane = new Pane();
                //pane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, new Insets(1, 1, 1, 1))));
                primeiroQuadrante.add(gerarRect(corPadrao), i, j);
                segundoQuadrante.add(gerarRect(corPadrao), i, j);
                terceiroQuadrante.add(gerarRect(corPadrao), i, j);
                quartoQuadrante.add(gerarRect(corPadrao), i, j);
            }
        }

        frameBuffer = new FrameBufferPlanoCartesiano(primeiroQuadrante, segundoQuadrante, terceiroQuadrante, quartoQuadrante);

        return gridPane;
    }

    protected void desenharPonto(Ponto p) {
        int x = p.getX();
        int y = p.getY();

        if (x >= 0 && y >= 0) {
            int yNovo = (ORDEM / 2 - 1) - y;

            ((FrameBufferPlanoCartesiano)frameBuffer).desenharPonto(1, gerarRect(corBressenham), x, yNovo);
        } else if (x < 0 && y >= 0) {
            int yNovo = (ORDEM / 2 - 1) - y;
            int xNovo = ORDEM / 2 + x;

            ((FrameBufferPlanoCartesiano)frameBuffer).desenharPonto(2, gerarRect(corBressenham), xNovo, yNovo);
        } else if (x < 0 && y < 0) {
            int yNovo = (ORDEM / 2 - 1) - (ORDEM / 2 + y);
            int xNovo = ORDEM / 2 + x;

            ((FrameBufferPlanoCartesiano)frameBuffer).desenharPonto(3, gerarRect(corBressenham), xNovo, yNovo);
        } else if (x >= 0 && y < 0) {
            int yNovo = (ORDEM / 2 - 1) - (ORDEM / 2 + y);

            ((FrameBufferPlanoCartesiano)frameBuffer).desenharPonto(4, gerarRect(corBressenham), x, yNovo);
        }
    }

    @Override
    protected void aplicarBuffer() {
        primeiroQuadrante = ((FrameBufferPlanoCartesiano)frameBuffer).getPrimeiroQuadrante();
        segundoQuadrante = ((FrameBufferPlanoCartesiano)frameBuffer).getSegundoQuadrante();
        terceiroQuadrante = ((FrameBufferPlanoCartesiano)frameBuffer).getTerceiroQuadrante();
        quartoQuadrante = ((FrameBufferPlanoCartesiano)frameBuffer).getQuartoQuadrante();
    }
}
