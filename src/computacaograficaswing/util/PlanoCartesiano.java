package computacaograficaswing.util;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
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

public class PlanoCartesiano {

    public static final int ORDEM = 74;
    public static final int TAMANHO_CELULA = 10;
    public static final int INSET_QUADRANTE = 1;
    public static final int TAMANHO_RETANGULO = TAMANHO_CELULA - (INSET_QUADRANTE * 2);
    public static final int WIDTH_PLANO = (ORDEM * TAMANHO_CELULA) + (INSET_QUADRANTE * 2);
    public static final int HEIGHT_PLANO = WIDTH_PLANO + 48;

    protected GridPane primeiroQuadrante;
    protected GridPane segundoQuadrante;
    protected GridPane terceiroQuadrante;
    protected GridPane quartoQuadrante;

    protected FrameBuffer frameBuffer;

    public static Color corSelecionada = Color.GREEN;
    public static Color corPadrao = Color.WHITE;
    public static Color corBressenham = Color.BLACK;

    protected GridPane inicializarPlano() {
        GridPane gridPane = new GridPane();
        //gridPane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, new Insets(0, 0, 0, 0))));
        //gridPane.setVgap(2);
        //gridPane.setHgap(2);
        //gridPane.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 0;");

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
        }

        for (int i = 0; i < ORDEM / 2; i++) {
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
            }
        }

        for (int i = 0; i < ORDEM / 2; i++) {
            for (int j = 0; j < ORDEM / 2; j++) {
                segundoQuadrante.add(gerarRect(corPadrao), i, j);
            }
        }

        for (int i = 0; i < ORDEM / 2; i++) {
            for (int j = 0; j < ORDEM / 2; j++) {
                terceiroQuadrante.add(gerarRect(corPadrao), i, j);
            }
        }

        for (int i = 0; i < ORDEM / 2; i++) {
            for (int j = 0; j < ORDEM / 2; j++) {
                quartoQuadrante.add(gerarRect(corPadrao), i, j);
            }
        }

        frameBuffer = new FrameBuffer(primeiroQuadrante, segundoQuadrante, terceiroQuadrante, quartoQuadrante);

        return gridPane;
    }

    private Rectangle gerarRect(Color cor) {
        Rectangle rect = new Rectangle(TAMANHO_RETANGULO, TAMANHO_RETANGULO, cor);
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

    protected void limparTela() {
        if (frameBuffer.getPontosDesenhados().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText(null);
            alert.setContentText("A tela já está vazia!");
            alert.showAndWait();
        } else {
            frameBuffer.limparBuffer();
            aplicarBuffer();
        }
    }

    protected void desenharPonto(Ponto p) {
        int x = p.getX();
        int y = p.getY();

        if (x >= 0 && y >= 0) {
            //Pane pane = new Pane();
            //pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, new Insets(1, 1, 1, 1))));
            int yNovo = (ORDEM / 2 - 1) - y;

            frameBuffer.desenharPonto(1, gerarRect(corBressenham), x, yNovo);
        } else if (x < 0 && y >= 0) {
            int yNovo = (ORDEM / 2 - 1) - y;
            int xNovo = ORDEM / 2 + x;

            frameBuffer.desenharPonto(2, gerarRect(corBressenham), xNovo, yNovo);
        } else if (x < 0 && y < 0) {
            int yNovo = (ORDEM / 2 - 1) - (ORDEM / 2 + y);
            int xNovo = ORDEM / 2 + x;

            frameBuffer.desenharPonto(3, gerarRect(corBressenham), xNovo, yNovo);
        } else if (x >= 0 && y < 0) {
            int yNovo = (ORDEM / 2 - 1) - (ORDEM / 2 + y);

            frameBuffer.desenharPonto(4, gerarRect(corBressenham), x, yNovo);
        }
    }

    protected void aplicarBuffer() {
        primeiroQuadrante = frameBuffer.getPrimeiroQuadrante();
        segundoQuadrante = frameBuffer.getSegundoQuadrante();
        terceiroQuadrante = frameBuffer.getTerceiroQuadrante();
        quartoQuadrante = frameBuffer.getQuartoQuadrante();
    }
}
