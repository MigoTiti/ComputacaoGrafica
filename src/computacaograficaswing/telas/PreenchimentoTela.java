package computacaograficaswing.telas;

import computacaograficaswing.ComputacaoGraficaSwing;
import static computacaograficaswing.ComputacaoGraficaSwing.fxContainer;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PreenchimentoTela extends GridDesenho {

    private boolean preenchimentoAtivado;
    private boolean poligonoAtivado;
    private boolean poligonoEmConstrucao;
    private Ponto pontoInicialReta;
    private Set<Ponto> retaAtual;

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
            } else {
                desenharForma.setTextFill(Color.GREEN);
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
            } else {
                preencherBalde.setTextFill(Color.GREEN);
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

    private void preenchimentoRecursivo(Color corInicial, Color corPreenchimento, int x, int y) {
        long tInicial = System.nanoTime();
        ((FrameBufferGrid) frameBuffer).preencherRecursivamente(corInicial, corPreenchimento, x, y);
        System.out.println("Tempo de execucao em segundos: " + ((System.nanoTime() - tInicial)/Math.pow(10, 9)));
    }

    @Override
    protected Rectangle gerarRect(Color cor) {
        Rectangle rect = new Rectangle(TAMANHO_CELULA, TAMANHO_CELULA, cor);

        rect.setOnMouseClicked((MouseEvent event) -> {
            if (preenchimentoAtivado) {
                preenchimentoRecursivo(((Color) rect.getFill()), corPreenchimento, (int) GridPane.getColumnIndex(rect), (int) GridPane.getRowIndex(rect));
            } else if (poligonoAtivado) {
                if (poligonoEmConstrucao) {
                    retaAtual = new BressenhamReta().aplicarBressenham(pontoInicialReta, new Ponto(GridPane.getColumnIndex(rect), GridPane.getRowIndex(rect)));
                    retaAtual.forEach((ponto) -> {
                        Rectangle aux = gerarRect(corSelecionada);
                        ((FrameBufferGrid) frameBuffer).desenharPonto(aux, ponto.getX(), ponto.getY());
                    });
                    rect.setFill(corSelecionada);
                    frameBuffer.getPontosDesenhados().add(rect);
                } else {
                    rect.setFill(corSelecionada);
                    frameBuffer.getPontosDesenhados().add(rect);
                    poligonoEmConstrucao = true;
                    pontoInicialReta = new Ponto(GridPane.getColumnIndex(rect), GridPane.getRowIndex(rect));
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
                    rect.setFill(corPadrao);
                    frameBuffer.getPontosDesenhados().remove(rect);
                } else if (corSelecionada.equals(corPadrao) && rect.getFill().equals(corPadrao)) {

                } else {
                    rect.setFill(corSelecionada);
                    frameBuffer.getPontosDesenhados().add(rect);
                }
            }
        });

        return rect;
    }
}