package computacaograficaswing.areasdesenho;

import static computacaograficaswing.areasdesenho.AreaDesenho.TAMANHO_RETANGULO;
import static computacaograficaswing.areasdesenho.AreaDesenho.corPadrao;
import static computacaograficaswing.areasdesenho.AreaDesenho.corSelecionada;
import computacaograficaswing.framebuffer.FrameBufferPlanoCartesiano;
import computacaograficaswing.util.transformacoes.Ponto2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class PlanoCartesiano extends AreaDesenho{

    protected GridPane gridPane;
    protected GridPane primeiroQuadrante;
    protected GridPane segundoQuadrante;
    protected GridPane terceiroQuadrante;
    protected GridPane quartoQuadrante;

    @Override
    protected Group inicializarPlano() {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        
        primeiroQuadrante = new GridPane();
        segundoQuadrante = new GridPane();
        terceiroQuadrante = new GridPane();
        quartoQuadrante = new GridPane();

        primeiroQuadrante.setGridLinesVisible(true);
        segundoQuadrante.setGridLinesVisible(true);
        terceiroQuadrante.setGridLinesVisible(true);
        quartoQuadrante.setGridLinesVisible(true);
        
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

        primeiroQuadrante.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        segundoQuadrante.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        terceiroQuadrante.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        quartoQuadrante.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setVgap(2);
        gridPane.setHgap(2);

        gridPane.add(segundoQuadrante, 0, 0);
        gridPane.add(primeiroQuadrante, 1, 0);
        gridPane.add(terceiroQuadrante, 0, 1);
        gridPane.add(quartoQuadrante, 1, 1);

        gridPane.setOnDragDetected(event -> {
            gridPane.startFullDrag();
            event.consume();
        });

        for (int i = 0; i < ORDEM / 2; i++) {
            for (int j = 0; j < ORDEM / 2; j++) {
                primeiroQuadrante.add(gerarRect(corPadrao), i, j);
                segundoQuadrante.add(gerarRect(corPadrao), i, j);
                terceiroQuadrante.add(gerarRect(corPadrao), i, j);
                quartoQuadrante.add(gerarRect(corPadrao), i, j);
            }
        }

        frameBuffer = new FrameBufferPlanoCartesiano(primeiroQuadrante, segundoQuadrante, terceiroQuadrante, quartoQuadrante);
        
        return new Group(gridPane);
    }

    protected void desenharPonto(Ponto2D p) {
        int x = p.getXArredondado();
        int y = p.getYArredondado();

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
    protected Rectangle gerarRect(Color cor) {
        Rectangle rect = new Rectangle(TAMANHO_RETANGULO, TAMANHO_RETANGULO, cor);
        rect.setOnMouseClicked(event -> {
            if (rect.getFill().equals(corPadrao)) {
                rect.setFill(corSelecionada);
                frameBuffer.getPontosDesenhados().add(rect);
            } else {
                rect.setFill(corPadrao);
                frameBuffer.getPontosDesenhados().remove(rect);
            }
        });

        rect.setOnMouseDragOver(event -> {
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

    protected void aplicarBuffer() {
        primeiroQuadrante = ((FrameBufferPlanoCartesiano)frameBuffer).getPrimeiroQuadrante();
        segundoQuadrante = ((FrameBufferPlanoCartesiano)frameBuffer).getSegundoQuadrante();
        terceiroQuadrante = ((FrameBufferPlanoCartesiano)frameBuffer).getTerceiroQuadrante();
        quartoQuadrante = ((FrameBufferPlanoCartesiano)frameBuffer).getQuartoQuadrante();
    }
}
