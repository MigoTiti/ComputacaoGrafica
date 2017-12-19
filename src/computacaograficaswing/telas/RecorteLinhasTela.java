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

        Button cortarPontoMedio = new Button("Cortar por ponto médio");
        cortarPontoMedio.setOnAction(event -> {
            pontoMedioClip();
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

        hboxTop.getChildren().addAll(btn, escolherAreaRecorte, desenharReta, cortarCSClip, cortarPontoMedio, limparTudoBtn, colorPicker, colorPicker2);

        root.setTop(hboxTop);
        root.setCenter(inicializarPlano());

        retas = new HashSet<>();
        pontosTemp = new HashSet<>();

        fxContainer.setScene(new Scene(root));
    }

    private void pontoMedioClip() {
        retas.stream().forEach((reta) -> {
            limparReta(reta);
        });

        retas.stream().forEach((reta) -> {
            Reta retaFinal = pontoMedioClip(reta);
            if (retaFinal != null) {
                desenharReta(corSelecionada, retaFinal);
            }
        });
    }

    private Reta pontoMedioClip(Reta reta) {
        Ponto p1 = reta.getPontoInicial();
        Ponto p2 = reta.getPontoFinal();
        boolean[] codigoInicio = gerarCodigo(p1);
        boolean[] codigoFim = gerarCodigo(p2);

        if (!or(codigoInicio, codigoFim)) {
            return reta;
        } else if (and(codigoInicio, codigoFim)) {
            return null;
        } else {
            int xNovo = Math.round((reta.getPontoInicial().getX() + reta.getPontoFinal().getX()) / 2);
            int yNovo = Math.round((reta.getPontoInicial().getY() + reta.getPontoFinal().getY()) / 2);

            Ponto p = new Ponto(xNovo, yNovo);

            if (p.getX() == p1.getX() || p.getX() == p2.getX() || p.getY() == p1.getY() || p.getY() == p2.getY())
                return null; 
            
            Reta reta1 = pontoMedioClip(new Reta(reta.getPontoInicial(), p));
            Reta reta2 = pontoMedioClip(new Reta(p, reta.getPontoFinal()));
            
            if (reta1 != null) {
                if (reta2 != null) {
                    reta = new Reta(reta1.getPontoInicial(), reta2.getPontoFinal());
                } else {
                    reta = new Reta(reta1.getPontoInicial(), reta1.getPontoFinal());
                }
            } else {
                if (reta2 != null) {
                    reta = new Reta(reta2.getPontoInicial(), reta2.getPontoFinal());
                }
            }
        }

        return reta;
    }

    private void csClip() {
        retas.stream().forEach((reta) -> {
            limparReta(reta);
        });

        retas.stream().forEach((reta) -> {
            Reta retaFinal = csClip(reta);
            if (retaFinal != null) {
                desenharReta(corSelecionada, retaFinal);
            }
        });
    }

    private void limparReta(Reta reta) {
        reta.getPontos().forEach(ponto -> {
            if (gridPaneMatriz[ponto.getX()][ponto.getY()].getFill().equals(corSelecionada)) {
                desenharPonto(corPadrao, ponto);
            }
        });
    }

    private void desenharReta(Color cor, Reta reta) {
        reta.getPontos().forEach(ponto -> {
            if (gridPaneMatriz[ponto.getX()][ponto.getY()].getFill().equals(corPadrao)) {
                desenharPonto(cor, ponto);
            }
        });
    }

    private Reta csClip(Reta reta) {
        Ponto p1 = reta.getPontoInicial();
        Ponto p2 = reta.getPontoFinal();
        boolean[] codigoInicio = gerarCodigo(p1);
        boolean[] codigoFim = gerarCodigo(p2);

        if (!or(codigoInicio, codigoFim)) {
            return reta;
        } else if (and(codigoInicio, codigoFim)) {
            return null;
        } else {
            int bitDiferente = bitDiferente(codigoInicio, codigoFim);

            Ponto p = intersecao(bitDiferente, reta);

            if (dentro(reta.getPontoInicial())) {
                reta = csClip(new Reta(p, reta.getPontoInicial()));
            } else {
                reta = csClip(new Reta(reta.getPontoFinal(), p));
            }
        }

        return reta;
    }

    private Ponto intersecao(int bit, Reta reta) {
        switch (bit) {
            case 0: {
                int x = areaDeRecorte.xMin();
                return new Ponto(x, Reta.intersecaoComX(x, reta));
            }
            case 1: {
                int x = areaDeRecorte.xMax();
                return new Ponto(x, Reta.intersecaoComX(x, reta));
            }
            case 2: {
                int y = areaDeRecorte.yMin();
                return new Ponto(Reta.intersecaoComY(y, reta), y);
            }
            case 3: {
                int y = areaDeRecorte.yMax();
                return new Ponto(Reta.intersecaoComY(y, reta), y);
            }
            default:
                return null;
        }
    }

    private boolean dentro(Ponto p) {
        boolean[] codigoPonto = gerarCodigo(p);

        return !(codigoPonto[0] || codigoPonto[1] || codigoPonto[2] || codigoPonto[3]);
    }

    private int bitDiferente(boolean[] codigo1, boolean[] codigo2) {
        for (int i = 0; i < codigo1.length; i++) {
            if (codigo1[i] != codigo2[i]) {
                return i;
            }
        }

        return -1;
    }

    private boolean or(boolean[] codigo1, boolean[] codigo2) {
        return ((codigo1[0] || codigo2[0]) || (codigo1[1] || codigo2[1]) || (codigo1[2] || codigo2[2]) || (codigo1[3] || codigo2[3]));
    }

    private boolean and(boolean[] codigo1, boolean[] codigo2) {
        return ((codigo1[0] && codigo2[0]) || (codigo1[1] && codigo2[1]) || (codigo1[2] && codigo2[2]) || (codigo1[3] && codigo2[3]));
    }

    private boolean[] gerarCodigo(Ponto p) {
        boolean[] codigo = new boolean[]{false, false, false, false};

        int yMax = areaDeRecorte.yMax();
        int yMin = areaDeRecorte.yMin();
        int xMax = areaDeRecorte.xMax();
        int xMin = areaDeRecorte.xMin();

        if (sinal(yMax - p.getY()) == 1) {
            codigo[3] = true;
        }

        if (sinal(p.getY() - yMin) == 1) {
            codigo[2] = true;
        }

        if (sinal(xMax - p.getX()) == 1) {
            codigo[1] = true;
        }

        if (sinal(p.getX() - xMin) == 1) {
            codigo[0] = true;
        }

        return codigo;
    }

    private int sinal(int n) {
        return n >= 0 ? 0 : 1;
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
