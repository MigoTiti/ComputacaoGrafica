package computacaograficaswing.telas;

import computacaograficaswing.ComputacaoGraficaSwing;
import static computacaograficaswing.ComputacaoGraficaSwing.fxContainer;
import static computacaograficaswing.areasdesenho.AreaDesenho.ORDEM;
import static computacaograficaswing.areasdesenho.AreaDesenho.TAMANHO_CELULA;
import static computacaograficaswing.areasdesenho.AreaDesenho.corPadrao;
import static computacaograficaswing.areasdesenho.AreaDesenho.corSelecionada;
import computacaograficaswing.areasdesenho.GridDesenho;
import computacaograficaswing.util.BressenhamReta;
import computacaograficaswing.util.FrameBufferGrid;
import computacaograficaswing.util.Ponto;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PreenchimentoTela extends GridDesenho {

    private boolean preenchimentoAtivado;
    private boolean poligonoAtivado;
    private boolean poligonoEmConstrucao;
    private Ponto pontoInicialReta;
    private Ponto pontoInicialRetaAtual;
    private Set<Ponto> retaAtual;

    private Rectangle[][] gridPaneMatriz;

    public static Color corPreenchimento = Color.BLACK;

    public void iniciarTela() {
        ComputacaoGraficaSwing.mudarTitulo("Preenchimento");
        Button btn = new Button();
        btn.setText("Voltar");
        btn.setOnAction((ActionEvent) -> {
            ComputacaoGraficaSwing.createScene();
        });

        BorderPane root = new BorderPane();

        HBox hboxTop = new HBox();
        hboxTop.setPadding(new Insets(15, 12, 15, 12));
        hboxTop.setSpacing(10);

        Button preencherBalde = new Button();
        preencherBalde.setText("Preencher");
        preencherBalde.setTextFill(Color.RED);

        Button desenharForma = new Button();
        desenharForma.setText("Desenhar forma");
        desenharForma.setTextFill(Color.RED);
        desenharForma.setOnAction((ActionEvent) -> {
            if (poligonoAtivado) {
                desenharForma.setTextFill(Color.RED);
                gridPane.setGridLinesVisible(false);
            } else {
                desenharForma.setTextFill(Color.GREEN);
                gridPane.setGridLinesVisible(true);
            }

            if (preenchimentoAtivado) {
                preenchimentoAtivado = false;
                preencherBalde.setTextFill(Color.RED);
            }

            if (poligonoAtivado) {
                poligonoAtivado = false;
                poligonoEmConstrucao = true;
                pontoInicialReta = null;
            } else {
                poligonoAtivado = true;
            }
        });

        preencherBalde.setOnAction((ActionEvent) -> {

            if (preenchimentoAtivado) {
                preencherBalde.setTextFill(Color.RED);
                gridPane.setGridLinesVisible(false);
            } else {
                preencherBalde.setTextFill(Color.GREEN);
                gridPane.setGridLinesVisible(true);
            }

            if (poligonoAtivado) {
                poligonoAtivado = false;
                poligonoEmConstrucao = false;
                pontoInicialReta = null;
                desenharForma.setTextFill(Color.RED);
            }

            preenchimentoAtivado = !preenchimentoAtivado;
        });

        Button preencherVarredura = new Button();
        preencherVarredura.setText("Preencher por varredura");
        preencherVarredura.setOnAction((ActionEvent) -> {

        });

        Button limparTudoBtn = new Button();
        limparTudoBtn.setText("Limpar tela");
        limparTudoBtn.setOnAction((ActionEvent) -> {
            limparTela();
        });

        ColorPicker colorPicker = new ColorPicker(corSelecionada);
        colorPicker.setOnAction((ActionEvent event) -> {
            corSelecionada = colorPicker.getValue();
        });

        ColorPicker colorPicker2 = new ColorPicker(corPreenchimento);
        colorPicker2.setOnAction((ActionEvent event) -> {
            corPreenchimento = colorPicker2.getValue();
        });

        hboxTop.getChildren().addAll(btn, preencherBalde, desenharForma, preencherVarredura, limparTudoBtn, colorPicker, colorPicker2);

        root.setTop(hboxTop);

        inicializarPlano();
        
        root.setCenter(gridPane);

        if (WIDTH_PLANO < ComputacaoGraficaSwing.JFXPANEL_WIDTH_INT) {
            root.setLeft(new Rectangle((ComputacaoGraficaSwing.JFXPANEL_WIDTH_INT - WIDTH_PLANO) / 2 + 20, HEIGHT_PLANO, Color.WHITE));
            root.setRight(new Rectangle((ComputacaoGraficaSwing.JFXPANEL_WIDTH_INT - WIDTH_PLANO) / 2 + 20, HEIGHT_PLANO, Color.WHITE));
        }

        fxContainer.setScene(new Scene(root));
    }

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

        gridPaneMatriz = new Rectangle[ORDEM][ORDEM];
        
        for (int i = 0; i < ORDEM; i++) {
            for (int j = 0; j < ORDEM; j++) {
                Rectangle rect = gerarRect(corPadrao);
                gridPane.add(rect, i, j);
                gridPaneMatriz[i][j] = rect;
            }
        }

        frameBuffer = new FrameBufferGrid(gridPane, gridPaneMatriz);
    }

    private void preenchimentoRecursivo(Color corInicial, Color corPreenchimento, int x, int y) {
        long tInicial = System.nanoTime();
        ((FrameBufferGrid) frameBuffer).preencherRecursivamente(corInicial, corPreenchimento, x, y);
        System.out.println("Tempo de execucao em segundos: " + ((System.nanoTime() - tInicial) / Math.pow(10, 9)));
    }

    @Override
    protected Rectangle gerarRect(Color cor) {
        Rectangle rect = new Rectangle(TAMANHO_CELULA, TAMANHO_CELULA, cor);

        rect.setOnMouseClicked((MouseEvent event) -> {
            if (preenchimentoAtivado) {
                preenchimentoRecursivo(((Color) rect.getFill()), corPreenchimento, (int) GridPane.getColumnIndex(rect), (int) GridPane.getRowIndex(rect));
            } else if (poligonoAtivado) {
                if (poligonoEmConstrucao) {
                    Ponto p = new Ponto(GridPane.getColumnIndex(rect), (int) GridPane.getRowIndex(rect));
                    if (!p.equals(pontoInicialRetaAtual)) {
                        retaAtual = new BressenhamReta().aplicarBressenham(pontoInicialRetaAtual, p);
                        retaAtual.forEach((ponto) -> {
                            desenharPonto(corSelecionada, gridPaneMatriz[ponto.getX()][ponto.getY()]);
                        });
                        if (p.equals(pontoInicialReta)) {
                            poligonoEmConstrucao = false;
                        } else {
                            pontoInicialRetaAtual = p;
                        }
                    }
                } else {
                    poligonoEmConstrucao = true;
                    pontoInicialReta = new Ponto(GridPane.getColumnIndex(rect), (int) GridPane.getRowIndex(rect));
                    pontoInicialRetaAtual = pontoInicialReta;
                    desenharPonto(corSelecionada, rect);
                }
            } else if (rect.getFill().equals(corPadrao)) {
                rect.setFill(corSelecionada);
                frameBuffer.getPontosDesenhados().add(rect);
            } else {
                rect.setFill(corPadrao);
                frameBuffer.getPontosDesenhados().remove(rect);
            }
        });

        rect.setOnMouseDragOver((MouseDragEvent) -> {
            if (!(preenchimentoAtivado || poligonoAtivado)) {
                if (corSelecionada.equals(corPadrao) && !rect.getFill().equals(corPadrao)) {
                    desenharPonto(corPadrao, rect);
                } else if (corSelecionada.equals(corPadrao) && rect.getFill().equals(corPadrao)) {

                } else {
                    desenharPonto(corSelecionada, rect);
                }
            }
        });

        return rect;
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

    private void desenharPonto(Color cor, Rectangle rect) {
        rect.setFill(cor);

        if (!cor.equals(corPadrao)) {
            frameBuffer.getPontosDesenhados().add(rect);
        } else {
            frameBuffer.getPontosDesenhados().remove(rect);
        }
    }
}
