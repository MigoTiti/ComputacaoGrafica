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
import computacaograficaswing.util.Ponto;
import computacaograficaswing.util.Reta;
import java.util.HashSet;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RecorteLinhasTela extends PlanoGrid {

    private boolean criarRetaLigada;
    private boolean retaEmConstrucao;
    private Ponto pontoInicialReta;

    private boolean escolherAreaLigada;
    private boolean areaEmConstrucao;
    private AreaDeRecorte areaDeRecorte;

    private Set<Ponto> pontosTemp;

    private Button desenharReta;
    private Button escolherAreaRecorte;

    private Set<Reta> retas;

    public void iniciarTela() {
        ComputacaoGraficaSwing.mudarTitulo("Recorte");

        Button btn = new Button();
        btn.setText("Voltar");
        btn.setOnAction((ActionEvent) -> {
            ComputacaoGraficaSwing.createScene();
        });

        BorderPane root = new BorderPane();

        HBox hboxTop = new HBox();
        hboxTop.setPadding(new Insets(15, 12, 15, 12));
        hboxTop.setSpacing(10);

        escolherAreaRecorte = new Button("Escolher area de recorte");
        escolherAreaRecorte.setTextFill(Color.RED);

        desenharReta = new Button("Desenhar reta");
        desenharReta.setTextFill(Color.RED);
        desenharReta.setOnAction((ActionEvent) -> {
            setBotaoRetaEmConstrucao();
        });

        escolherAreaRecorte.setOnAction((ActionEvent) -> {
            setBotaoAreaDeRecorte();
        });

        Button cortarCSClip = new Button("Cortar por CS clip");
        cortarCSClip.setOnAction(event -> {
            csClip();
        });

        Button limparTudoBtn = new Button("Limpar tela");
        limparTudoBtn.setOnAction(event -> {
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

        hboxTop.getChildren().addAll(btn, escolherAreaRecorte, desenharReta, cortarCSClip, limparTudoBtn, colorPicker, colorPicker2);

        root.setTop(hboxTop);
        root.setCenter(inicializarPlano());

        retas = new HashSet<>();
        pontosTemp = new HashSet<>();

        fxContainer.setScene(new Scene(root));
    }

    private void csClip() {
        retas.stream().forEach((reta) -> {
            csClip(reta);
        });
    }

    private void csClip(Reta reta) {
        Ponto p1 = reta.getPontoInicial();
        Ponto p2 = reta.getPontoFinal();
        int codigoInicio = gerarCodigo(p1);
        int codigoFim = gerarCodigo(p2);

        if ((codigoInicio | codigoFim) == 0b0000) {
            System.out.println("Totalmente dentro");
        } else if ((codigoInicio & codigoFim) != 0b0000) {
            System.out.println("Totalmente fora");
        } else {
            
        }
    }

    private int gerarCodigo(Ponto p) {
        int codigo = 0b0000;

        int yMax = areaDeRecorte.yMax();
        int yMin = areaDeRecorte.yMin();
        int xMax = areaDeRecorte.xMax();
        int xMin = areaDeRecorte.xMin();

        if (sinal(yMax - p.getY()) == 1) {
            codigo += 0b1000;
        }

        if (sinal(p.getY() - yMin) == 1) {
            codigo += 0b0100;
        }

        if (sinal(xMax - p.getX()) == 1) {
            codigo += 0b0010;
        }

        if (sinal(p.getX() - xMin) == 1) {
            codigo += 0b0001;
        }

        return codigo;
    }

    private int sinal(int n) {
        return (n >= 0 ? 0 : 1);
    }

    @Override
    protected Rectangle gerarRect(Color cor) {
        Rectangle rect = new Rectangle(TAMANHO_CELULA, TAMANHO_CELULA, cor);

        rect.setOnMouseClicked(event -> {
            if (criarRetaLigada) {
                if (retaEmConstrucao) {
                    Ponto p = new Ponto(GridPane.getColumnIndex(rect), (int) GridPane.getRowIndex(rect));
                    if (!p.equals(pontoInicialReta)) {
                        (new BressenhamReta().aplicarBressenham(pontoInicialReta, p)).forEach((ponto) -> {
                            if (gridPaneMatriz[ponto.getX()][ponto.getY()].getFill().equals(corPadrao)) {
                                desenharPonto(corSelecionada, ponto);
                            }
                        });

                        Reta aux = new Reta(new Ponto(pontoInicialReta.getX(), pontoInicialReta.getY()), new Ponto(p.getX(), p.getY()));

                        retaEmConstrucao = false;
                        retas.add(aux);
                        pontosTemp.clear();
                    }
                } else {
                    retaEmConstrucao = true;
                    pontoInicialReta = new Ponto(GridPane.getColumnIndex(rect), (int) GridPane.getRowIndex(rect));
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
            if (criarRetaLigada) {
                if (retaEmConstrucao) {
                    Ponto p = new Ponto(GridPane.getColumnIndex(rect), (int) GridPane.getRowIndex(rect));

                    if (!p.equals(pontoInicialReta)) {
                        (new BressenhamReta().aplicarBressenham(pontoInicialReta, p)).forEach((ponto) -> {
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
            if (criarRetaLigada) {
                if (retaEmConstrucao) {
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
        retas.clear();
        pontoInicialReta = null;
        retaEmConstrucao = false;
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

        if (criarRetaLigada) {
            criarRetaLigada = false;
            retaEmConstrucao = false;
            pontoInicialReta = null;
            desenharReta.setTextFill(Color.RED);
        }

        escolherAreaLigada = !escolherAreaLigada;
    }

    private void setBotaoRetaEmConstrucao() {
        if (criarRetaLigada) {
            pontoInicialReta = null;
            retaEmConstrucao = false;
            gridPane.setGridLinesVisible(false);
            desenharReta.setTextFill(Color.RED);
        } else {
            gridPane.setGridLinesVisible(true);
            desenharReta.setTextFill(Color.GREEN);
        }

        if (escolherAreaLigada) {
            escolherAreaLigada = false;
            escolherAreaRecorte.setTextFill(Color.RED);
            areaEmConstrucao = false;
        }

        criarRetaLigada = !criarRetaLigada;
    }
}
