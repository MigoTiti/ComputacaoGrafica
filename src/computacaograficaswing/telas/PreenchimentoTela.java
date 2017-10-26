package computacaograficaswing.telas;

import computacaograficaswing.ComputacaoGraficaSwing;
import static computacaograficaswing.ComputacaoGraficaSwing.fxContainer;
import computacaograficaswing.areasdesenho.AreaDesenho;
import static computacaograficaswing.areasdesenho.AreaDesenho.ORDEM;
import static computacaograficaswing.areasdesenho.AreaDesenho.TAMANHO_CELULA;
import static computacaograficaswing.areasdesenho.AreaDesenho.corPadrao;
import static computacaograficaswing.areasdesenho.AreaDesenho.corSelecionada;
import computacaograficaswing.util.BressenhamReta;
import computacaograficaswing.framebuffer.FrameBufferGrid;
import computacaograficaswing.util.Poligono;
import computacaograficaswing.util.Ponto;
import computacaograficaswing.util.Reta;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
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

public class PreenchimentoTela extends AreaDesenho {

    private boolean preenchimentoAtivado;
    private boolean poligonoAtivado;
    private boolean poligonoEmConstrucao;
    private Ponto pontoInicialReta;
    private Ponto pontoInicialRetaAtual;
    private Set<Ponto> retaAtual;
    private GridPane gridPane;
    private Poligono poligonoAtual;
    private Set<Poligono> poligonos;

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
                pontoInicialRetaAtual = null;
                pontoInicialReta = null;
                poligonoAtual = null;
                poligonoEmConstrucao = false;
                retaAtual = null;
                desenharForma.setTextFill(Color.RED);
            } else {
                gridPane.setGridLinesVisible(true);
                desenharForma.setTextFill(Color.GREEN);
            }

            if (preenchimentoAtivado) {
                preenchimentoAtivado = false;
                preencherBalde.setTextFill(Color.RED);
            }

            if (poligonoAtivado) {
                poligonoAtivado = false;
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
            //preencherPorVarredura();
            preencherPorVarredura2();
        });

        Button limparTudoBtn = new Button();
        limparTudoBtn.setText("Limpar tela");
        limparTudoBtn.setOnAction((ActionEvent) -> {
            limparTela();
            poligonoAtual = null;
            poligonos = null;
            retaAtual = null;
            pontoInicialReta = null;
            pontoInicialRetaAtual = null;
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

        poligonos = new HashSet<>();

        fxContainer.setScene(new Scene(root));
    }

    protected void preencherPorVarredura2() {
        /*List<Ponto> pontosTeste = new ArrayList<>();
        pontosTeste.add(new Ponto(5, 1));
        pontosTeste.add(new Ponto(6, 1));
        pontosTeste.add(new Ponto(7, 1));
        pontosTeste.add(new Ponto(8, 1));
        pontosTeste.add(new Ponto(20, 1));
        pontosTeste.add(new Ponto(21, 1));
        pontosTeste.add(new Ponto(22, 1));
        pontosTeste.add(new Ponto(41, 1));
        pontosTeste.add(new Ponto(42, 1));
        pontosTeste.add(new Ponto(50, 1));
        pontosTeste.add(new Ponto(51, 1));
        pontosTeste.add(new Ponto(52, 1));
        Poligono.refatorarPontos(pontosTeste);*/
        
        for (int i = 0; i < gridPaneMatriz.length; i++) {
            for (int j = 0; j < gridPaneMatriz.length; j++) {
                List<Ponto> pontos = Poligono.getIntersecoes(poligonos, j);

                if (pontos.size() % 2 == 0) {
                    for (int k = 0; k < pontos.size(); k += 2) {
                        for (int n = pontos.get(k).getX() + 1; n <= pontos.get(k + 1).getX(); n++) {
                            if (!Poligono.hasIntersecao(poligonos, n, j))
                                desenharPonto(corPreenchimento, gridPaneMatriz[n][j]);
                        }
                    }
                }
            }
        }
    }

    protected void preencherPorVarredura() {
        boolean preencher = false;
        for (int i = 0; i < gridPaneMatriz.length; i++) {
            for (int j = 0; j < gridPaneMatriz.length; j++) {
                int contagem = Poligono.getIntersecoes(poligonos, j, i);

                if (contagem % 2 != 0 && Poligono.getIntersecoes(poligonos, j - 1, i) == 0) {
                    preencher = !preencher;
                }

                if (contagem == 0 && preencher && !Poligono.hasIntersecao(poligonos, j, i)) {
                    desenharPonto(corPreenchimento, gridPaneMatriz[j][i]);
                }
            }
            preencher = false;
        }
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

        frameBuffer = new FrameBufferGrid(gridPaneMatriz);
    }

    private void preenchimentoRecursivo(Color corInicial, Color corPreenchimento, int x, int y) {
        long tInicial = System.nanoTime();
        ((FrameBufferGrid) frameBuffer).preencherRecursivamente(corInicial, corPreenchimento, x, y);
        System.out.println("Tempo de execucao em segundos: " + ((System.nanoTime() - tInicial) / Math.pow(10, 9)));
    }

    @Override
    protected Rectangle gerarRect(Color cor) {
        Rectangle rect = new Rectangle(TAMANHO_CELULA, TAMANHO_CELULA, cor);

        rect.setStyle("-fx-padding: 0;");
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

                        Reta aux = new Reta(retaAtual);
                        poligonoAtual.getRetas().add(aux);

                        if (p.equals(pontoInicialReta)) {
                            poligonoEmConstrucao = false;
                            poligonos.add(poligonoAtual);
                            poligonoAtual = null;
                        } else {
                            pontoInicialRetaAtual = p;
                        }
                    }
                } else {
                    poligonoAtual = new Poligono();
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

    private void desenharPonto(Color cor, Rectangle rect) {
        rect.setFill(cor);

        if (!cor.equals(corPadrao)) {
            frameBuffer.getPontosDesenhados().add(rect);
        } else {
            frameBuffer.getPontosDesenhados().remove(rect);
        }
    }
}
