package computacaograficaswing.telas;

import computacaograficaswing.ComputacaoGraficaSwing;
import static computacaograficaswing.ComputacaoGraficaSwing.fxContainer;
import static computacaograficaswing.areasdesenho.AreaDesenho.TAMANHO_CELULA;
import static computacaograficaswing.areasdesenho.AreaDesenho.corPadrao;
import static computacaograficaswing.areasdesenho.AreaDesenho.corSelecionada;
import computacaograficaswing.areasdesenho.PlanoGrid;
import static computacaograficaswing.telas.PreenchimentoTela.corPreenchimento;
import computacaograficaswing.util.Matriz;
import computacaograficaswing.util.transformacoes.Poligono3D;
import computacaograficaswing.util.transformacoes.Ponto3D;
import computacaograficaswing.util.transformacoes.Poligono2D;
import computacaograficaswing.util.transformacoes.RetaTransformacao;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Transformacoes3DTela extends PlanoGrid {

    private Set<Poligono3D> poligonos;

    private final Button cavalier = new Button("Cavalier");
    private final Button cabinet = new Button("Cabinet");

    public static final int PROJECAO_CABINET = 1;
    public static final int PROJECAO_CAVALIER = 2;

    private int tipoProjecao = PROJECAO_CABINET;

    private boolean stop = false;

    public void iniciarTela() {
        ComputacaoGraficaSwing.mudarTitulo("Transformações 3D");

        Button btn = new Button("Voltar");
        btn.setOnAction((ActionEvent) -> {
            ComputacaoGraficaSwing.createScene();
        });

        BorderPane root = new BorderPane();

        VBox vBoxDireita = new VBox();
        vBoxDireita.setSpacing(10);
        vBoxDireita.setPadding(new Insets(15));

        TextField campoAngulo = new TextField();
        Button rotacaoX = new Button("Rotação X");
        rotacaoX.setOnAction(event -> {
            stop = !stop;
            new Thread(() -> {
                while (stop) {
                    try {
                        aplicarRotacao(Double.parseDouble(campoAngulo.getText()), 1);
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Transformacoes3DTela.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        });

        Button rotacaoY = new Button("Rotação Y");
        rotacaoY.setOnAction(event -> {
            stop = !stop;
            new Thread(() -> {
                while (stop) {
                    try {
                        aplicarRotacao(Double.parseDouble(campoAngulo.getText()), 2);
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Transformacoes3DTela.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        });

        Button rotacaoZ = new Button("Rotação Z");
        rotacaoZ.setOnAction(event -> {
            stop = !stop;
            new Thread(() -> {
                while (stop) {
                    try {
                        aplicarRotacao(Double.parseDouble(campoAngulo.getText()), 3);
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Transformacoes3DTela.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        });

        vBoxDireita.getChildren().addAll(campoAngulo, rotacaoX, rotacaoY, rotacaoZ);

        HBox hboxTop = new HBox();
        hboxTop.setPadding(new Insets(15, 12, 15, 12));
        hboxTop.setSpacing(10);

        Button limparTudoBtn = new Button("Limpar tela");
        limparTudoBtn.setOnAction(event -> {
            limparTela();
        });

        Button criarPoligono = new Button("Criar polígono");
        criarPoligono.setOnAction(event -> {
            criarPoligono();
        });

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

        Button criarCubo = new Button("Criar cubo");
        criarCubo.setOnAction(event -> {
            criarCubo();
        });

        cavalier.setOnAction(event -> {
            if (tipoProjecao != PROJECAO_CAVALIER) {
                cabinet.setTextFill(Color.RED);
                cavalier.setTextFill(Color.GREEN);

                tipoProjecao = PROJECAO_CAVALIER;

                poligonos.stream().forEach((poligono) -> {
                    projetar(poligono);
                });
            }
        });

        cabinet.setOnAction(event -> {
            if (tipoProjecao != PROJECAO_CABINET) {
                cavalier.setTextFill(Color.RED);
                cabinet.setTextFill(Color.GREEN);

                tipoProjecao = PROJECAO_CABINET;

                poligonos.stream().forEach((poligono) -> {
                    projetar(poligono);
                });
            }
        });

        cavalier.setTextFill(Color.RED);
        cabinet.setTextFill(Color.GREEN);

        hboxTop.getChildren().addAll(btn, criarPoligono, criarCubo, limparTudoBtn, colorPicker, colorPicker2, translacao, rotacao, escala, cabinet, cavalier);

        root.setTop(hboxTop);
        root.setCenter(inicializarPlano());
        root.setRight(vBoxDireita);

        poligonos = new HashSet<>();

        fxContainer.setScene(new Scene(root));
    }

    private Ponto3D getCenter(Poligono2D p) {
        double x = 0;
        double y = 0;
        double z = 0;

        for (RetaTransformacao reta : p.getRetas()) {
            x += (reta.getPontoInicial().getX() + reta.getPontoFinal().getX());
            y += (reta.getPontoInicial().getY() + reta.getPontoFinal().getY());
            z += (((Ponto3D) reta.getPontoInicial()).getZ() + ((Ponto3D) reta.getPontoFinal()).getZ());
        }

        x /= p.getRetas().size() * 2;
        y /= p.getRetas().size() * 2;
        z /= p.getRetas().size() * 2;

        return new Ponto3D(x, y, z);
    }

    private void criarCubo() {
        Dialog<Poligono3D> dialog = new Dialog<>();
        dialog.setTitle("Criar cubo");
        dialog.setResizable(true);

        Label label11 = new Label("X inicial: ");
        Label label12 = new Label("Y inicial: ");
        Label label13 = new Label("Z inicial: ");
        TextField xCampo1 = new TextField();
        TextField yCampo1 = new TextField();
        TextField zCampo1 = new TextField();

        Label labelPonto2 = new Label("Medida dos lados: ");
        TextField ladoCampo = new TextField();

        GridPane grid = new GridPane();
        grid.add(label11, 1, 1);
        grid.add(xCampo1, 2, 1);
        grid.add(label12, 1, 2);
        grid.add(yCampo1, 2, 2);
        grid.add(label13, 1, 3);
        grid.add(zCampo1, 2, 3);

        grid.add(labelPonto2, 1, 4);
        grid.add(ladoCampo, 2, 4);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Criar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk) {
                Poligono3D p = new Poligono3D();

                int xInicial = Integer.parseInt(xCampo1.getText());
                int yInicial = Integer.parseInt(yCampo1.getText());
                int zInicial = Integer.parseInt(zCampo1.getText());

                int lado = Integer.parseInt(ladoCampo.getText());

                Ponto3D pontoInicial = new Ponto3D(xInicial, yInicial, zInicial);

                p.getRetas().add(new RetaTransformacao(pontoInicial, new Ponto3D(xInicial + lado, yInicial, zInicial)));
                p.getRetas().add(new RetaTransformacao(new Ponto3D(xInicial, yInicial, zInicial), new Ponto3D(xInicial, yInicial + lado, zInicial)));
                p.getRetas().add(new RetaTransformacao(new Ponto3D(xInicial, yInicial + lado, zInicial), new Ponto3D(xInicial + lado, yInicial + lado, zInicial)));
                p.getRetas().add(new RetaTransformacao(new Ponto3D(xInicial + lado, yInicial + lado, zInicial), new Ponto3D(xInicial + lado, yInicial, zInicial)));
                p.getRetas().add(new RetaTransformacao(new Ponto3D(xInicial + lado, yInicial, zInicial), new Ponto3D(xInicial + lado, yInicial, zInicial + lado)));
                p.getRetas().add(new RetaTransformacao(new Ponto3D(xInicial + lado, yInicial, zInicial + lado), new Ponto3D(xInicial + lado, yInicial + lado, zInicial + lado)));
                p.getRetas().add(new RetaTransformacao(new Ponto3D(xInicial + lado, yInicial + lado, zInicial + lado), new Ponto3D(xInicial + lado, yInicial + lado, zInicial)));
                p.getRetas().add(new RetaTransformacao(new Ponto3D(xInicial + lado, yInicial + lado, zInicial + lado), new Ponto3D(xInicial, yInicial + lado, zInicial + lado)));
                p.getRetas().add(new RetaTransformacao(new Ponto3D(xInicial, yInicial + lado, zInicial + lado), new Ponto3D(xInicial, yInicial, zInicial + lado)));
                p.getRetas().add(new RetaTransformacao(new Ponto3D(xInicial, yInicial, zInicial + lado), new Ponto3D(xInicial + lado, yInicial, zInicial + lado)));
                p.getRetas().add(new RetaTransformacao(new Ponto3D(xInicial, yInicial, zInicial + lado), new Ponto3D(xInicial, yInicial, zInicial)));
                p.getRetas().add(new RetaTransformacao(new Ponto3D(xInicial, yInicial + lado, zInicial + lado), new Ponto3D(xInicial, yInicial + lado, zInicial)));

                return p;
            }

            return null;
        });

        Optional<Poligono3D> result = dialog.showAndWait();

        if (result.isPresent()) {
            poligonos.add(result.get());
            projetar(result.get());
        }
    }

    private void criarPoligono() {
        Dialog<Poligono3D> dialog = new Dialog<>();
        dialog.setTitle("Criar polígono");
        dialog.setResizable(false);

        final ObservableList<RetaTransformacao> retas = FXCollections.observableArrayList();
        TableView<RetaTransformacao> table = new TableView<>();
        table.setEditable(false);

        TableColumn pontoInicialColuna = new TableColumn("Ponto Inicial");
        pontoInicialColuna.setMinWidth(100);
        pontoInicialColuna.setCellValueFactory(
                new PropertyValueFactory<>("pontoInicial"));
        pontoInicialColuna.setResizable(false);

        TableColumn pontoFinalColuna = new TableColumn("Ponto final");
        pontoFinalColuna.setMinWidth(100);
        pontoFinalColuna.setCellValueFactory(
                new PropertyValueFactory<>("pontoFinal"));
        pontoFinalColuna.setResizable(false);

        table.setItems(retas);
        table.getColumns().addAll(pontoInicialColuna, pontoFinalColuna);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button adicionarBotao = new Button("Adicionar reta");
        adicionarBotao.setOnAction(event -> {
            Dialog<RetaTransformacao> dialogoPonto = new Dialog<>();
            dialogoPonto.setTitle("Adicionar ponto");
            dialogoPonto.setResizable(false);

            Label labelPonto1 = new Label("Ponto inicial");
            labelPonto1.setAlignment(Pos.CENTER);
            Label label11 = new Label("X: ");
            Label label12 = new Label("Y: ");
            Label label13 = new Label("Z: ");
            TextField xCampo1 = new TextField();
            TextField yCampo1 = new TextField();
            TextField zCampo1 = new TextField();

            Label labelPonto2 = new Label("Ponto final");
            labelPonto2.setAlignment(Pos.CENTER);
            Label label21 = new Label("X: ");
            Label label22 = new Label("Y: ");
            Label label23 = new Label("Z: ");
            TextField xCampo2 = new TextField();
            TextField yCampo2 = new TextField();
            TextField zCampo2 = new TextField();

            GridPane grid = new GridPane();
            grid.add(labelPonto1, 1, 1);
            grid.add(label11, 1, 2);
            grid.add(xCampo1, 2, 2);
            grid.add(label12, 1, 3);
            grid.add(yCampo1, 2, 3);
            grid.add(label13, 1, 4);
            grid.add(zCampo1, 2, 4);

            grid.add(labelPonto2, 1, 5);
            grid.add(label21, 1, 6);
            grid.add(xCampo2, 2, 6);
            grid.add(label22, 1, 7);
            grid.add(yCampo2, 2, 7);
            grid.add(label23, 1, 8);
            grid.add(zCampo2, 2, 8);
            dialogoPonto.getDialogPane().setContent(grid);

            ButtonType buttonTypeOk = new ButtonType("Criar", ButtonBar.ButtonData.OK_DONE);
            dialogoPonto.getDialogPane().getButtonTypes().add(buttonTypeOk);

            dialogoPonto.setResultConverter((ButtonType b) -> {
                if (b == buttonTypeOk) {
                    Ponto3D pontoInicial = new Ponto3D(Integer.parseInt(xCampo1.getText()), Integer.parseInt(yCampo1.getText()), Integer.parseInt(zCampo1.getText()));
                    Ponto3D pontoFinal = new Ponto3D(Integer.parseInt(xCampo2.getText()), Integer.parseInt(yCampo2.getText()), Integer.parseInt(zCampo2.getText()));

                    return new RetaTransformacao(pontoInicial, pontoFinal);
                }

                return null;
            });

            Optional<RetaTransformacao> result = dialogoPonto.showAndWait();

            if (result.isPresent()) {
                retas.add(result.get());
            }
        });

        Button removerBotao = new Button("Remover reta");
        Button editarBotao = new Button("Editar reta");

        HBox hBox = new HBox(adicionarBotao, removerBotao, editarBotao);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(5));

        GridPane grid = new GridPane();
        grid.add(table, 1, 1);
        grid.add(hBox, 1, 2);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Criar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk) {
                Poligono3D p = new Poligono3D();

                retas.stream().forEach((reta) -> {
                    p.getRetas().add(reta);
                });

                return p;
            }

            return null;
        });

        Optional<Poligono3D> result = dialog.showAndWait();

        if (result.isPresent()) {
            poligonos.add(result.get());
            projetar(result.get());
        }
    }

    private void apagarPoligono(Poligono2D p) {
        if (p != null) {
            p.getRetas().stream().forEach((reta) -> {
                reta.getPontos().stream().forEach((ponto) -> {
                    desenharPonto(corPadrao, ponto);
                });
            });
        }
    }

    private void projetar(Poligono3D p) {
        Poligono2D projetado = new Poligono2D();

        p.getRetas().stream().forEach((reta) -> {
            double[] pontoTransformado = Matriz.projecaoObliqua((Ponto3D) reta.getPontoInicial(), tipoProjecao == PROJECAO_CABINET ? 0.5 : 1, 45);
            double[] pontoTransformadoFinal = Matriz.projecaoObliqua((Ponto3D) reta.getPontoFinal(), tipoProjecao == PROJECAO_CABINET ? 0.5 : 1, 45);

            Ponto3D novoPontoInicial = new Ponto3D(pontoTransformado[0], pontoTransformado[1], pontoTransformado[2]);
            Ponto3D novoPontoFinal = new Ponto3D(pontoTransformadoFinal[0], pontoTransformadoFinal[1], pontoTransformadoFinal[2]);

            projetado.getRetas().add(new RetaTransformacao(novoPontoInicial, novoPontoFinal));
        });

        apagarPoligono(p.getPoligonoProjetado());
        p.setPoligonoProjetado(projetado);

        projetado.getRetas().stream().forEach((reta) -> {
            reta.getPontos().stream().forEach((ponto) -> {
                desenharPonto(corSelecionada, ponto);
            });
        });
    }

    private void aplicarTranslacao(int taxaX, int taxaY, int taxaZ) {
        poligonos.stream().forEach((poligono) -> {
            poligono.getRetas().stream().forEach((reta) -> {
                double[] pontoTransformado = Matriz.translacao3D((Ponto3D) reta.getPontoInicial(), taxaX, taxaY, taxaZ);
                double[] pontoTransformadoFinal = Matriz.translacao3D((Ponto3D) reta.getPontoFinal(), taxaX, taxaY, taxaZ);

                reta.getPontoInicial().setX(pontoTransformado[0]);
                reta.getPontoInicial().setY(pontoTransformado[1]);
                ((Ponto3D) reta.getPontoInicial()).setZ(pontoTransformado[2]);

                reta.getPontoFinal().setX(pontoTransformadoFinal[0]);
                reta.getPontoFinal().setY(pontoTransformadoFinal[1]);
                ((Ponto3D) reta.getPontoFinal()).setZ(pontoTransformadoFinal[2]);
            });

            //projetar(poligono);
        });
    }

    private void aplicarRotacao(double angulo, int eixo) {
        poligonos.stream().forEach((poligono) -> {
            Ponto3D centro = getCenter(poligono);
            aplicarTranslacao(-centro.getXArredondado(), -centro.getYArredondado(), -centro.getZArredondado());

            poligono.getRetas().stream().forEach((reta) -> {
                double[] pontoTransformado = null;
                double[] pontoTransformadoFinal = null;

                switch (eixo) {
                    case 1:
                        pontoTransformado = Matriz.rotacao3DX((Ponto3D) reta.getPontoInicial(), angulo);
                        pontoTransformadoFinal = Matriz.rotacao3DX((Ponto3D) reta.getPontoFinal(), angulo);
                        break;
                    case 2:
                        pontoTransformado = Matriz.rotacao3DY((Ponto3D) reta.getPontoInicial(), angulo);
                        pontoTransformadoFinal = Matriz.rotacao3DY((Ponto3D) reta.getPontoFinal(), angulo);
                        break;
                    case 3:
                        pontoTransformado = Matriz.rotacao3DZ((Ponto3D) reta.getPontoInicial(), angulo);
                        pontoTransformadoFinal = Matriz.rotacao3DZ((Ponto3D) reta.getPontoFinal(), angulo);
                        break;
                }

                reta.getPontoInicial().setX(pontoTransformado[0]);
                reta.getPontoInicial().setY(pontoTransformado[1]);
                ((Ponto3D) reta.getPontoInicial()).setZ(pontoTransformado[2]);

                reta.getPontoFinal().setX(pontoTransformadoFinal[0]);
                reta.getPontoFinal().setY(pontoTransformadoFinal[1]);
                ((Ponto3D) reta.getPontoFinal()).setZ(pontoTransformadoFinal[2]);
            });

            aplicarTranslacao(centro.getXArredondado(), centro.getYArredondado(), centro.getZArredondado());

            projetar(poligono);
        });
    }

    private void aplicarEscala(double taxaX, double taxaY, double taxaZ) {
        poligonos.stream().forEach((poligono) -> {
            poligono.getRetas().stream().forEach((reta) -> {
                double[] pontoTransformado = Matriz.escala3D((Ponto3D) reta.getPontoInicial(), taxaX, taxaY, taxaZ);
                double[] pontoTransformadoFinal = Matriz.escala3D((Ponto3D) reta.getPontoFinal(), taxaX, taxaY, taxaZ);

                reta.getPontoInicial().setX(pontoTransformado[0]);
                reta.getPontoInicial().setY(pontoTransformado[1]);
                ((Ponto3D) reta.getPontoInicial()).setZ(pontoTransformado[2]);

                reta.getPontoFinal().setX(pontoTransformadoFinal[0]);
                reta.getPontoFinal().setY(pontoTransformadoFinal[1]);
                ((Ponto3D) reta.getPontoFinal()).setZ(pontoTransformadoFinal[2]);
            });

            projetar(poligono);
        });
    }

    private void aplicarTransformacao(int tipo) {
        class Transformacao {

            double param1;
            double param2;
            double param3;

            public Transformacao(double param1, double param2, double param3) {
                this.param1 = param1;
                this.param2 = param2;
                this.param3 = param3;
            }
        }

        Dialog<Transformacao> dialog = new Dialog<>();
        dialog.setResizable(false);

        switch (tipo) {
            case 1: {
                dialog.setTitle("Translação");
                Label label1 = new Label("Translação em x: ");
                Label label2 = new Label("Translação em y: ");
                Label label3 = new Label("Translação em z: ");
                TextField text1 = new TextField();
                TextField text2 = new TextField();
                TextField text3 = new TextField();

                GridPane grid = new GridPane();
                grid.add(label1, 1, 1);
                grid.add(text1, 2, 1);
                grid.add(label2, 1, 2);
                grid.add(text2, 2, 2);
                grid.add(label3, 1, 3);
                grid.add(text3, 2, 3);
                dialog.getDialogPane().setContent(grid);

                ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
                dialog.setResultConverter((ButtonType b) -> {
                    if (b == buttonTypeOk) {
                        if (text1.getText() == null || text2.getText() == null || text1.getText().equals("") || text2.getText().equals("")) {
                            return null;
                        }

                        return new Transformacao(Double.parseDouble(text1.getText()), Double.parseDouble(text2.getText()), Double.parseDouble(text3.getText()));
                    }

                    return null;
                });

                Optional<Transformacao> result = dialog.showAndWait();

                if (result.isPresent()) {
                    aplicarTranslacao((int) result.get().param1, (int) result.get().param2, (int) result.get().param3);
                    poligonos.stream().forEach(poligono -> {
                        projetar(poligono);
                    });
                }

                break;
            }
            case 2: {
                dialog.setTitle("Rotação");
                Label label1 = new Label("Ângulo (graus): ");
                TextField text1 = new TextField();
                Label label2 = new Label("Eixo: ");
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
                        if (text1.getText() == null || text1.getText().equals("")) {
                            return null;
                        }

                        int eixo = 1;

                        switch (text2.getText()) {
                            case "x":
                                eixo = 1;
                                break;
                            case "X":
                                eixo = 1;
                                break;
                            case "y":
                                eixo = 2;
                                break;
                            case "Y":
                                eixo = 2;
                                break;
                            case "z":
                                eixo = 3;
                                break;
                            case "Z":
                                eixo = 3;
                                break;
                        }

                        return new Transformacao(Double.parseDouble(text1.getText()), eixo, 0);
                    }

                    return null;
                });

                Optional<Transformacao> result = dialog.showAndWait();

                if (result.isPresent()) {
                    aplicarRotacao(result.get().param1, (int) result.get().param2);
                }

                break;
            }
            case 3: {
                dialog.setTitle("Escala");
                Label label1 = new Label("Escala em x: ");
                Label label2 = new Label("Escala em y: ");
                Label label3 = new Label("Escala em z: ");
                TextField text1 = new TextField();
                TextField text2 = new TextField();
                TextField text3 = new TextField();

                GridPane grid = new GridPane();
                grid.add(label1, 1, 1);
                grid.add(text1, 2, 1);
                grid.add(label2, 1, 2);
                grid.add(text2, 2, 2);
                grid.add(label3, 1, 3);
                grid.add(text3, 2, 3);
                dialog.getDialogPane().setContent(grid);

                ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
                dialog.setResultConverter((ButtonType b) -> {
                    if (b == buttonTypeOk) {
                        if (text1.getText() == null || text2.getText() == null || text1.getText().equals("") || text2.getText().equals("")) {
                            return null;
                        }

                        return new Transformacao(Double.parseDouble(text1.getText()), Double.parseDouble(text2.getText()), Double.parseDouble(text3.getText()));
                    }

                    return null;
                });

                Optional<Transformacao> result = dialog.showAndWait();

                if (result.isPresent()) {
                    aplicarEscala(result.get().param1, result.get().param2, result.get().param3);
                }

                break;
            }
        }
    }

    @Override
    protected void limparTela() {
        super.limparTela();
        poligonos.clear();
    }

    @Override
    protected Rectangle gerarRect(Color cor) {
        Rectangle rect = new Rectangle(TAMANHO_CELULA, TAMANHO_CELULA, cor);

        return rect;
    }
}
