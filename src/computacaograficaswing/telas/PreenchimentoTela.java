package computacaograficaswing.telas;

import computacaograficaswing.ComputacaoGraficaSwing;
import static computacaograficaswing.ComputacaoGraficaSwing.fxContainer;
import static computacaograficaswing.areasdesenho.AreaDesenho.TAMANHO_CELULA;
import static computacaograficaswing.areasdesenho.AreaDesenho.corPadrao;
import static computacaograficaswing.areasdesenho.AreaDesenho.corSelecionada;
import computacaograficaswing.areasdesenho.PlanoGrid;
import computacaograficaswing.framebuffer.FrameBufferGrid;
import computacaograficaswing.util.transformacoes.BressenhamReta;
import computacaograficaswing.util.transformacoes.Poligono2D;
import computacaograficaswing.util.transformacoes.Ponto2D;
import computacaograficaswing.util.transformacoes.Reta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
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

public class PreenchimentoTela extends PlanoGrid {

    private boolean preenchimentoAtivado;
    private boolean poligonoAtivado;
    private boolean poligonoEmConstrucao;
    private Ponto2D pontoInicialReta;
    private Ponto2D pontoInicialRetaAtual;
    private Poligono2D poligonoAtual;
    private Set<Poligono2D> poligonos;

    private Set<Ponto2D> pontosTemp;
    
    public static Color corPreenchimento = Color.BLACK;

    public void iniciarTela() {
        ComputacaoGraficaSwing.mudarTitulo("Preenchimento");
        Button btn = new Button();
        btn.setText("Voltar");
        btn.setOnAction(event -> {
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
                gridPane.setGridLinesVisible(false);
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
            preencherPorVarredura();
        });

        Button limparTudoBtn = new Button();
        limparTudoBtn.setText("Limpar tela");
        limparTudoBtn.setOnAction((ActionEvent) -> {
            limparTela();
            poligonoAtual = null;
            poligonos = null;
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
        root.setCenter(inicializarPlano());

        poligonos = new HashSet<>();
        pontosTemp = new HashSet<>();
        
        fxContainer.setScene(new Scene(root));
    }

    protected void preencherPorVarredura() {
        Set<Reta> retas = new LinkedHashSet<>();

        poligonos.stream().forEach((poligono) -> {
            retas.addAll(poligono.getRetas());
        });

        for (int yVarredura = 0; yVarredura < gridPaneMatriz.length; yVarredura++) {
            List<Double> intersecoes = new ArrayList<>();

            for (Reta reta : retas) {
                if (yVarredura >= reta.yMinimo() && yVarredura <= reta.yMaximo()) {
                    double intersecao = Reta.intersecaoComY(yVarredura, reta);

                    if (intersecao >= 0 && intersecao < gridPaneMatriz.length) {
                        intersecoes.add(intersecao);
                    }
                }
            }

            Collections.sort(intersecoes);

            System.out.print("\nLinha " + yVarredura + ", numero de intersecoes = " + intersecoes.size() + "; " + Arrays.toString(intersecoes.toArray()));
            
            Set<Double> pontosASeremRemovidos = new HashSet<>();
            
            intersecoes.stream().forEach(intersecao -> {
                int contagem = Collections.frequency(intersecoes, intersecao);
                if (contagem > 1) {
                    int contagemPontosMaximos = 0;
                    
                    for (Reta reta : retas) {
                        if (intersecao.equals(reta.xParaYMin()))
                            contagemPontosMaximos++;
                    }
                    
                    System.out.print(", pontos máximos = " + contagemPontosMaximos);
                    
                    if (contagemPontosMaximos == 1)
                        pontosASeremRemovidos.add(intersecao);
                }
            });
            
            pontosASeremRemovidos.stream().forEach((pontoASerRemovido) -> {
                intersecoes.remove(pontoASerRemovido);
            });
            
            if (intersecoes.size() % 2 == 0) {
                for (int i = 0; i < intersecoes.size() - 1; i += 2) {
                    for (int j = (int)Math.round(intersecoes.get(i)); j <= (int)Math.round(intersecoes.get(i + 1)); j++) {
                        desenharPonto(corPreenchimento, j, yVarredura);
                    }
                }
            }
            
            retas.stream().forEach((reta) -> {
                (new BressenhamReta().aplicarBressenham(reta.getPontoInicial(), reta.getPontoFinal())).forEach((ponto -> {
                    desenharPonto(corSelecionada, ponto);
                }));
            });
        }
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
                    Ponto2D p = new Ponto2D(GridPane.getColumnIndex(rect), (int) GridPane.getRowIndex(rect));
                    if (!p.equals(pontoInicialRetaAtual)) {
                        (new BressenhamReta().aplicarBressenham(pontoInicialRetaAtual, p)).forEach((ponto) -> {
                            desenharPonto(corSelecionada, ponto);
                        });
                        
                        Reta aux = new Reta(new Ponto2D(pontoInicialRetaAtual.getX(), pontoInicialRetaAtual.getY()), new Ponto2D(p.getX(), p.getY()));
                        poligonoAtual.getRetas().add(aux);

                        if (p.equals(pontoInicialReta)) {
                            poligonoEmConstrucao = false;
                            poligonos.add(poligonoAtual);
                            poligonoAtual = null;
                        } else {
                            pontoInicialRetaAtual = p;
                        }
                        
                        pontosTemp.clear();
                    }
                } else {
                    poligonoAtual = new Poligono2D();
                    poligonoEmConstrucao = true;
                    pontoInicialReta = new Ponto2D(GridPane.getColumnIndex(rect), (int) GridPane.getRowIndex(rect));
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

        rect.setOnMouseEntered(event -> {
            if (poligonoAtivado) {
                if (poligonoEmConstrucao) {
                    Ponto2D p = new Ponto2D(GridPane.getColumnIndex(rect), (int) GridPane.getRowIndex(rect));
                    if (!p.equals(pontoInicialRetaAtual)) {
                        (new BressenhamReta().aplicarBressenham(pontoInicialRetaAtual, p)).forEach((ponto) -> {
                            if (gridPaneMatriz[ponto.getXArredondado()][ponto.getYArredondado()].getFill().equals(corPadrao)) {
                                pontosTemp.add(ponto);
                                desenharPonto(corSelecionada, ponto);
                            }
                        });
                    }
                }
            }
        });
        
        rect.setOnMouseExited(event -> {
            if (poligonoAtivado) {
                if (poligonoEmConstrucao) {
                    pontosTemp.stream().forEach((ponto) -> {
                        desenharPonto(corPadrao, ponto);
                    });

                    pontosTemp.clear();
                }
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
}
