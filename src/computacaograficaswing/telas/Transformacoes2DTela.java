package computacaograficaswing.telas;

import computacaograficaswing.ComputacaoGraficaSwing;
import static computacaograficaswing.ComputacaoGraficaSwing.fxContainer;
import static computacaograficaswing.areasdesenho.AreaDesenho.TAMANHO_CELULA;
import static computacaograficaswing.areasdesenho.AreaDesenho.corPadrao;
import static computacaograficaswing.areasdesenho.AreaDesenho.corSelecionada;
import computacaograficaswing.areasdesenho.PlanoGrid;
import static computacaograficaswing.telas.PreenchimentoTela.corPreenchimento;
import computacaograficaswing.util.transformacoes.Matriz;
import computacaograficaswing.util.BressenhamReta;
import computacaograficaswing.util.Poligono2D;
import computacaograficaswing.util.Ponto2D;
import computacaograficaswing.util.Reta;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Transformacoes2DTela extends PlanoGrid {

    private Set<Ponto2D> pontosTemp;

    private Button desenharPoligono;

    private boolean poligonoAtivado;
    private boolean poligonoEmConstrucao;
    private Ponto2D pontoInicialReta;
    private Ponto2D pontoInicialRetaAtual;
    private Poligono2D poligonoAtual;
    private Set<Poligono2D> poligonos;
    
    private boolean stop = false;

    public void iniciarTela() {
        ComputacaoGraficaSwing.mudarTitulo("Transformações 2D");

        Button btn = new Button("Voltar");
        btn.setOnAction((ActionEvent) -> {
            ComputacaoGraficaSwing.createScene();
        });

        BorderPane root = new BorderPane();

        HBox hboxTop = new HBox();
        hboxTop.setPadding(new Insets(15, 12, 15, 12));
        hboxTop.setSpacing(10);

        desenharPoligono = new Button("Desenhar polígono");
        desenharPoligono.setTextFill(Color.RED);
        desenharPoligono.setOnAction((ActionEvent) -> {
            setBotaoRetaEmConstrucao();
        });

        Button limparTudoBtn = new Button("Limpar tela");
        limparTudoBtn.setOnAction(event -> {
            limparTela();
        });

        VBox vBoxDireita = new VBox();
        vBoxDireita.setSpacing(10);
        vBoxDireita.setPadding(new Insets(15));

        TextField campoAngulo = new TextField();
        Button rotacaoX = new Button("Rotação");
        rotacaoX.setOnAction(event -> {
            stop = !stop;
            new Thread(() -> {
                while (stop) {
                    try {
                        aplicarRotacao(Double.parseDouble(campoAngulo.getText()));
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Transformacoes3DTela.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        });
        
        vBoxDireita.getChildren().addAll(campoAngulo, rotacaoX);
        
        ColorPicker colorPicker = new ColorPicker(corSelecionada);
        colorPicker.setOnAction(event -> {
            corSelecionada = colorPicker.getValue();
        });

        ColorPicker colorPicker2 = new ColorPicker(corPreenchimento);
        colorPicker2.setOnAction(event -> {
            corPreenchimento = colorPicker2.getValue();
        });

        Button translacao = new Button("Translação");
        translacao.setOnAction(event -> {
            aplicarTransformacao(1);
        });

        Button rotacao = new Button("Rotação");
        rotacao.setOnAction(event -> {
            aplicarTransformacao(2);
        });

        Button escala = new Button("Escala");
        escala.setOnAction(event -> {
            aplicarTransformacao(3);
        });

        hboxTop.getChildren().addAll(btn, desenharPoligono, limparTudoBtn, colorPicker, colorPicker2, translacao, rotacao, escala);

        root.setTop(hboxTop);
        root.setCenter(inicializarPlano());
        root.setRight(vBoxDireita);

        poligonos = new HashSet<>();
        pontosTemp = new HashSet<>();

        fxContainer.setScene(new Scene(root));
    }

    private void aplicarTranslacao(int taxaX, int taxaY) {
        poligonos.stream().forEach((poligono) -> {
            poligono.getRetas().stream().forEach((reta) -> {
                double[] pontoTransformado = Matriz.translacao2D(reta.getPontoInicial(), taxaX, taxaY);

                reta.getPontoInicial().setX(pontoTransformado[0]);
                reta.getPontoInicial().setY(pontoTransformado[1]);

                double[] pontoTransformadoFinal = Matriz.translacao2D(reta.getPontoFinal(), taxaX, taxaY);

                reta.getPontoFinal().setX(pontoTransformadoFinal[0]);
                reta.getPontoFinal().setY(pontoTransformadoFinal[1]);
            });
        });
    }

    private Ponto2D getCenter(Poligono2D p) {
        double x = 0;
        double y = 0;

        for (Reta reta : p.getRetas()) {
            x += (reta.getPontoInicial().getX() + reta.getPontoFinal().getX());
            y += (reta.getPontoInicial().getY() + reta.getPontoFinal().getY());
        }

        x /= p.getRetas().size() * 2;
        y /= p.getRetas().size() * 2;

        return new Ponto2D(x, y);
    }
    
    private void aplicarRotacao(double angulo) {
        poligonos.stream().forEach((poligono) -> {
            poligono.getRetas().stream().forEach((reta) -> {
                reta.getPontos().stream().forEach((ponto) -> {
                    desenharPonto(corPadrao, ponto);
                });
            });
        });

        poligonos.stream().forEach((poligono) -> {
            Ponto2D centro = getCenter(poligono);
            aplicarTranslacao(-centro.getXArredondado(), -centro.getYArredondado());
            
            poligono.getRetas().stream().forEach((reta) -> {
                double[] pontoTransformado = Matriz.rotacao2D(reta.getPontoInicial(), angulo);

                reta.getPontoInicial().setX(pontoTransformado[0]);
                reta.getPontoInicial().setY(pontoTransformado[1]);

                double[] pontoTransformadoFinal = Matriz.rotacao2D(reta.getPontoFinal(), angulo);

                reta.getPontoFinal().setX(pontoTransformadoFinal[0]);
                reta.getPontoFinal().setY(pontoTransformadoFinal[1]);
            });
            
            aplicarTranslacao(centro.getXArredondado(), centro.getYArredondado());
            
            poligono.getRetas().stream().forEach((reta) -> {
                reta.getPontos().stream().forEach((ponto) -> {
                    desenharPonto(corSelecionada, ponto);
                });
            });
        });
    }

    private void aplicarEscala(double taxaX, double taxaY) {
        poligonos.stream().forEach((poligono) -> {
            poligono.getRetas().stream().forEach((reta) -> {
                reta.getPontos().stream().forEach((ponto) -> {
                    desenharPonto(corPadrao, ponto);
                });
            });
        });

        poligonos.stream().forEach((poligono) -> {
            poligono.getRetas().stream().forEach((reta) -> {
                double[] pontoTransformado = Matriz.escala2D(reta.getPontoInicial(), taxaX, taxaY);

                reta.getPontoInicial().setX(pontoTransformado[0]);
                reta.getPontoInicial().setY(pontoTransformado[1]);

                double[] pontoTransformadoFinal = Matriz.escala2D(reta.getPontoFinal(), taxaX, taxaY);

                reta.getPontoFinal().setX(pontoTransformadoFinal[0]);
                reta.getPontoFinal().setY(pontoTransformadoFinal[1]);

                reta.getPontos().stream().forEach((ponto) -> {
                    desenharPonto(corSelecionada, ponto);
                });
            });
        });
    }

    private void aplicarTransformacao(int tipo) {
        class Transformacao {

            double param1;
            double param2;

            public Transformacao(double param1, double param2) {
                this.param1 = param1;
                this.param2 = param2;
            }
        }

        Dialog<Transformacao> dialog = new Dialog<>();
        dialog.setResizable(false);

        switch (tipo) {
            case 1: {
                dialog.setTitle("Translação");
                Label label1 = new Label("Translação em x: ");
                Label label2 = new Label("Translação em y: ");
                TextField text1 = new TextField();
                TextField text2 = new TextField();

                GridPane grid = new GridPane();
                grid.add(label1, 1, 1);
                grid.add(text1, 2, 1);
                grid.add(label2, 1, 2);
                grid.add(text2, 2, 2);
                dialog.getDialogPane().setContent(grid);

                ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
                dialog.setResultConverter((ButtonType b) -> {
                    if (b == buttonTypeOk) {
                        if (text1.getText() == null || text2.getText() == null || text1.getText().equals("") || text2.getText().equals("")) {
                            return null;
                        }

                        return new Transformacao(Double.parseDouble(text1.getText()), Double.parseDouble(text2.getText()));
                    }

                    return null;
                });

                Optional<Transformacao> result = dialog.showAndWait();

                if (result.isPresent()) {
                    poligonos.stream().forEach((poligono) -> {
                        poligono.getRetas().stream().forEach((reta) -> {
                            reta.getPontos().stream().forEach((ponto) -> {
                                desenharPonto(corPadrao, ponto);
                            });
                        });
                    });

                    aplicarTranslacao((int) result.get().param1, (int) result.get().param2);

                    poligonos.stream().forEach(poligono -> {
                        poligono.getRetas().stream().forEach(reta -> {
                            reta.getPontos().stream().forEach((ponto) -> {
                                desenharPonto(corSelecionada, ponto);
                            });
                        });
                    });
                }

                break;
            }
            case 2: {
                dialog.setTitle("Rotação");
                Label label1 = new Label("Ângulo (graus): ");
                TextField text1 = new TextField();

                GridPane grid = new GridPane();
                grid.add(label1, 1, 1);
                grid.add(text1, 2, 1);
                dialog.getDialogPane().setContent(grid);

                ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
                dialog.setResultConverter((ButtonType b) -> {
                    if (b == buttonTypeOk) {
                        if (text1.getText() == null || text1.getText().equals("")) {
                            return null;
                        }

                        return new Transformacao(Double.parseDouble(text1.getText()), 0);
                    }

                    return null;
                });

                Optional<Transformacao> result = dialog.showAndWait();

                if (result.isPresent()) {
                    aplicarRotacao(result.get().param1);
                }

                break;
            }
            case 3: {
                dialog.setTitle("Escala");
                Label label1 = new Label("Escala em x: ");
                Label label2 = new Label("Escala em y: ");
                TextField text1 = new TextField();
                TextField text2 = new TextField();

                GridPane grid = new GridPane();
                grid.add(label1, 1, 1);
                grid.add(text1, 2, 1);
                grid.add(label2, 1, 2);
                grid.add(text2, 2, 2);
                dialog.getDialogPane().setContent(grid);

                ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
                dialog.setResultConverter((ButtonType b) -> {
                    if (b == buttonTypeOk) {
                        if (text1.getText() == null || text2.getText() == null || text1.getText().equals("") || text2.getText().equals("")) {
                            return null;
                        }

                        return new Transformacao(Double.parseDouble(text1.getText()), Double.parseDouble(text2.getText()));
                    }

                    return null;
                });

                Optional<Transformacao> result = dialog.showAndWait();

                if (result.isPresent()) {
                    aplicarEscala(result.get().param1, result.get().param2);
                }

                break;
            }
        }
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

        poligonoAtivado = !poligonoAtivado;
    }

    @Override
    protected void limparTela() {
        super.limparTela();
        poligonos.clear();
        pontoInicialReta = null;
        pontoInicialRetaAtual = null;
        poligonoEmConstrucao = false;
        pontosTemp.clear();
    }

    @Override
    protected Rectangle gerarRect(Color cor) {
        Rectangle rect = new Rectangle(TAMANHO_CELULA, TAMANHO_CELULA, cor);

        rect.setOnMouseClicked(event -> {
            if (poligonoAtivado) {
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

        return rect;
    }
}
