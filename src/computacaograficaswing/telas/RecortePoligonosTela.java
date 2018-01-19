package computacaograficaswing.telas;

import computacaograficaswing.ComputacaoGraficaSwing;
import static computacaograficaswing.ComputacaoGraficaSwing.fxContainer;
import static computacaograficaswing.areasdesenho.AreaDesenho.TAMANHO_CELULA;
import static computacaograficaswing.areasdesenho.AreaDesenho.corPadrao;
import static computacaograficaswing.areasdesenho.AreaDesenho.corSelecionada;
import computacaograficaswing.areasdesenho.PlanoGrid;
import static computacaograficaswing.telas.PreenchimentoTela.corPreenchimento;
import computacaograficaswing.util.AreaDeRecorte;
import computacaograficaswing.util.BressenhamReta;
import computacaograficaswing.util.Poligono;
import computacaograficaswing.util.Ponto;
import computacaograficaswing.util.Reta;
import java.util.HashSet;
import java.util.Set;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RecortePoligonosTela extends PlanoGrid {

    private boolean escolherAreaLigada;
    private boolean areaEmConstrucao;
    private AreaDeRecorte areaDeRecorte;

    private Set<Ponto> pontosTemp;

    private Button desenharPoligono;
    private Button escolherAreaRecorte;

    private boolean poligonoAtivado;
    private boolean poligonoEmConstrucao;
    private Ponto pontoInicialReta;
    private Ponto pontoInicialRetaAtual;
    private Poligono poligonoAtual;
    private Set<Poligono> poligonos;

    public void iniciarTela() {
        ComputacaoGraficaSwing.mudarTitulo("Recorte de polígonos");

        Button btn = new Button("Voltar");
        btn.setOnAction((ActionEvent) -> {
            ComputacaoGraficaSwing.createScene();
        });

        BorderPane root = new BorderPane();

        HBox hboxTop = new HBox();
        hboxTop.setPadding(new Insets(15, 12, 15, 12));
        hboxTop.setSpacing(10);

        escolherAreaRecorte = new Button("Escolher area de recorte");
        escolherAreaRecorte.setTextFill(Color.RED);

        desenharPoligono = new Button("Desenhar polígono");
        desenharPoligono.setTextFill(Color.RED);
        desenharPoligono.setOnAction((ActionEvent) -> {
            setBotaoRetaEmConstrucao();
        });

        escolherAreaRecorte.setOnAction((ActionEvent) -> {
            setBotaoAreaDeRecorte();
        });

        Button cortar = new Button("Cortar");
        cortar.setOnAction(event -> {
            recortePoligonos();
        });

        Button limparTudoBtn = new Button("Limpar tela");
        limparTudoBtn.setOnAction(event -> {
            limparTela();
        });

        ColorPicker colorPicker = new ColorPicker(corSelecionada);
        colorPicker.setOnAction(event -> {
            corSelecionada = colorPicker.getValue();
        });

        ColorPicker colorPicker2 = new ColorPicker(corPreenchimento);
        colorPicker2.setOnAction(event -> {
            corPreenchimento = colorPicker2.getValue();
        });

        hboxTop.getChildren().addAll(btn, escolherAreaRecorte, desenharPoligono, cortar, limparTudoBtn, colorPicker, colorPicker2);

        root.setTop(hboxTop);
        root.setCenter(inicializarPlano());

        poligonos = new HashSet<>();
        pontosTemp = new HashSet<>();

        fxContainer.setScene(new Scene(root));
    }

    private void recortePoligonos() {
        
    }
    
    @Override
    protected Rectangle gerarRect(Color cor) {
        Rectangle rect = new Rectangle(TAMANHO_CELULA, TAMANHO_CELULA, cor);

        rect.setOnMouseClicked(event -> {
            if (poligonoAtivado) {
                if (poligonoEmConstrucao) {
                    Ponto p = new Ponto(GridPane.getColumnIndex(rect), (int) GridPane.getRowIndex(rect));
                    if (!p.equals(pontoInicialRetaAtual)) {
                        (new BressenhamReta().aplicarBressenham(pontoInicialRetaAtual, p)).forEach((ponto) -> {
                            desenharPonto(corSelecionada, ponto);
                        });

                        Reta aux = new Reta(new Ponto(pontoInicialRetaAtual.getX(), pontoInicialRetaAtual.getY()), new Ponto(p.getX(), p.getY()));
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
                    poligonoAtual = new Poligono();
                    poligonoEmConstrucao = true;
                    pontoInicialReta = new Ponto(GridPane.getColumnIndex(rect), (int) GridPane.getRowIndex(rect));
                    pontoInicialRetaAtual = pontoInicialReta;
                    desenharPonto(corSelecionada, rect);
                }
            } else if (escolherAreaLigada) {
                if (areaEmConstrucao) {
                    Ponto p = new Ponto(GridPane.getColumnIndex(rect), (int) GridPane.getRowIndex(rect));

                    areaDeRecorte = new AreaDeRecorte(pontoInicialReta, p);

                    areaDeRecorte.getTodosOsPontos().stream().forEach(ponto -> {
                        if (gridPaneMatriz[ponto.getX()][ponto.getY()].getFill().equals(corPadrao)) {
                            desenharPonto(corPreenchimento, ponto);
                        }
                    });

                    areaEmConstrucao = false;
                    pontosTemp.clear();
                } else {
                    areaEmConstrucao = true;
                    pontoInicialReta = new Ponto(GridPane.getColumnIndex(rect), (int) GridPane.getRowIndex(rect));
                    desenharPonto(corPreenchimento, rect);
                }
            }
        });

        rect.setOnMouseEntered(event -> {
            if (poligonoAtivado) {
                if (poligonoEmConstrucao) {
                    Ponto p = new Ponto(GridPane.getColumnIndex(rect), (int) GridPane.getRowIndex(rect));
                    if (!p.equals(pontoInicialRetaAtual)) {
                        (new BressenhamReta().aplicarBressenham(pontoInicialRetaAtual, p)).forEach((ponto) -> {
                            if (gridPaneMatriz[ponto.getX()][ponto.getY()].getFill().equals(corPadrao)) {
                                pontosTemp.add(ponto);
                                desenharPonto(corSelecionada, ponto);
                            }
                        });
                    }
                }
            } else if (escolherAreaLigada) {
                if (areaEmConstrucao) {
                    Ponto p = new Ponto(GridPane.getColumnIndex(rect), (int) GridPane.getRowIndex(rect));
                    if (!p.equals(pontoInicialReta)) {
                        AreaDeRecorte.gerarAreaDesenho(pontoInicialReta, p).forEach((ponto) -> {
                            if (gridPaneMatriz[ponto.getX()][ponto.getY()].getFill().equals(corPadrao)) {
                                pontosTemp.add(ponto);
                                desenharPonto(corPreenchimento, ponto);
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
            } else if (escolherAreaLigada) {
                if (areaEmConstrucao) {
                    pontosTemp.stream().forEach((ponto) -> {
                        desenharPonto(corPadrao, ponto);
                    });

                    pontosTemp.clear();
                }
            }
        });

        return rect;
    }

    @Override
    protected void limparTela() {
        super.limparTela();
        poligonos.clear();
        pontoInicialReta = null;
        pontoInicialRetaAtual = null;
        poligonoEmConstrucao = false;
        areaEmConstrucao = false;
        pontosTemp.clear();
        areaDeRecorte = null;
    }

    private void setBotaoAreaDeRecorte() {
        if (escolherAreaLigada) {
            gridPane.setGridLinesVisible(false);
            escolherAreaRecorte.setTextFill(Color.RED);
        } else {
            gridPane.setGridLinesVisible(true);
            escolherAreaRecorte.setTextFill(Color.GREEN);
        }

        if (poligonoAtivado) {
            poligonoAtivado = false;
            poligonoEmConstrucao = false;
            pontoInicialReta = null;
            pontoInicialRetaAtual = null;
            desenharPoligono.setTextFill(Color.RED);
        }

        escolherAreaLigada = !escolherAreaLigada;
    }

    private void setBotaoRetaEmConstrucao() {
        if (poligonoAtivado) {
            pontoInicialReta = null;
            poligonoEmConstrucao = false;
            gridPane.setGridLinesVisible(false);
            desenharPoligono.setTextFill(Color.RED);
        } else {
            gridPane.setGridLinesVisible(true);
            desenharPoligono.setTextFill(Color.GREEN);
        }

        if (escolherAreaLigada) {
            escolherAreaLigada = false;
            escolherAreaRecorte.setTextFill(Color.RED);
            areaEmConstrucao = false;
        }

        poligonoAtivado = !poligonoAtivado;
    }
}
