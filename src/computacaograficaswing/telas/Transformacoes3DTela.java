package computacaograficaswing.telas;

import computacaograficaswing.ComputacaoGraficaSwing;
import static computacaograficaswing.ComputacaoGraficaSwing.fxContainer;
import static computacaograficaswing.areasdesenho.AreaDesenho.TAMANHO_CELULA;
import static computacaograficaswing.areasdesenho.AreaDesenho.corPadrao;
import static computacaograficaswing.areasdesenho.AreaDesenho.corSelecionada;
import computacaograficaswing.areasdesenho.PlanoGrid;
import static computacaograficaswing.telas.PreenchimentoTela.corPreenchimento;
import computacaograficaswing.util.Matriz;
import computacaograficaswing.util.Poligono;
import computacaograficaswing.util.Ponto3D;
import computacaograficaswing.util.Reta;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Transformacoes3DTela extends PlanoGrid {

    private Set<Poligono> poligonos;

    public void iniciarTela() {
        ComputacaoGraficaSwing.mudarTitulo("Transformações 3D");

        Button btn = new Button("Voltar");
        btn.setOnAction((ActionEvent) -> {
            ComputacaoGraficaSwing.createScene();
        });

        BorderPane root = new BorderPane();

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

        hboxTop.getChildren().addAll(btn, criarPoligono, limparTudoBtn, colorPicker, colorPicker2, translacao, rotacao, escala);

        root.setTop(hboxTop);
        root.setCenter(inicializarPlano());

        poligonos = new HashSet<>();

        fxContainer.setScene(new Scene(root));
    }

    private void criarPoligono() {
        Dialog<Poligono> dialog = new Dialog<>();
        dialog.setTitle("Criar polígono");
        dialog.setResizable(false);

        final ObservableList<Reta> retas = FXCollections.observableArrayList();
        TableView<Reta> table = new TableView<>();
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
            Dialog<Reta> dialogoPonto = new Dialog<>();
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

                    return new Reta(pontoInicial, pontoFinal);
                }

                return null;
            });

            Optional<Reta> result = dialogoPonto.showAndWait();

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
                Poligono p = new Poligono();

                retas.stream().forEach((reta) -> {
                    p.getRetas().add(reta);
                });

                return p;
            }

            return null;
        });

        Optional<Poligono> result = dialog.showAndWait();

        if (result.isPresent()) {
            poligonos.add(result.get());
            projetar(result.get());
        }
    }

    private void projetar(Poligono p) {
        p.getRetas().stream().forEach((reta) -> {
            int[] pontoTransformado = Matriz.projecaoObliqua((Ponto3D) reta.getPontoInicial(), 0.5, 45);

            reta.getPontoInicial().setX(pontoTransformado[0]);
            reta.getPontoInicial().setY(pontoTransformado[1]);
            ((Ponto3D) reta.getPontoInicial()).setZ(pontoTransformado[2]);

            int[] pontoTransformadoFinal = Matriz.projecaoObliqua((Ponto3D) reta.getPontoFinal(), 0.5, 45);

            reta.getPontoFinal().setX(pontoTransformadoFinal[0]);
            reta.getPontoFinal().setY(pontoTransformadoFinal[1]);
            ((Ponto3D) reta.getPontoFinal()).setZ(pontoTransformadoFinal[2]);

            reta.getPontos().stream().forEach((ponto) -> {
                desenharPonto(corSelecionada, ponto);
            });
        });
    }

    private void aplicarTranslacao(int taxaX, int taxaY) {
        poligonos.stream().forEach((poligono) -> {
            poligono.getRetas().stream().forEach((reta) -> {
                reta.getPontos().stream().forEach((ponto) -> {
                    desenharPonto(corPadrao, ponto);
                });
            });
        });

        poligonos.stream().forEach((poligono) -> {
            poligono.getRetas().stream().forEach((reta) -> {
                int[] pontoTransformado = Matriz.translacao2D(reta.getPontoInicial(), taxaX, taxaY);

                int xNovo = (int) Math.round(pontoTransformado[0]);
                int yNovo = (int) Math.round(pontoTransformado[1]);

                reta.getPontoInicial().setX(xNovo);
                reta.getPontoInicial().setY(yNovo);

                int[] pontoTransformadoFinal = Matriz.translacao2D(reta.getPontoFinal(), taxaX, taxaY);

                int xNovoFinal = (int) Math.round(pontoTransformadoFinal[0]);
                int yNovoFinal = (int) Math.round(pontoTransformadoFinal[1]);

                reta.getPontoFinal().setX(xNovoFinal);
                reta.getPontoFinal().setY(yNovoFinal);

                reta.getPontos().stream().forEach((ponto) -> {
                    desenharPonto(corSelecionada, ponto);
                });
            });
        });
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
            poligono.getRetas().stream().forEach((reta) -> {
                int[] pontoTransformado = Matriz.rotacao2D(reta.getPontoInicial(), angulo);

                int xNovo = (int) Math.round(pontoTransformado[0]);
                int yNovo = (int) Math.round(pontoTransformado[1]);

                reta.getPontoInicial().setX(xNovo);
                reta.getPontoInicial().setY(yNovo);

                int[] pontoTransformadoFinal = Matriz.rotacao2D(reta.getPontoFinal(), angulo);

                int xNovoFinal = (int) Math.round(pontoTransformadoFinal[0]);
                int yNovoFinal = (int) Math.round(pontoTransformadoFinal[1]);

                reta.getPontoFinal().setX(xNovoFinal);
                reta.getPontoFinal().setY(yNovoFinal);

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
                int[] pontoTransformado = Matriz.escala2D(reta.getPontoInicial(), taxaX, taxaY);

                int xNovo = (int) Math.round(pontoTransformado[0]);
                int yNovo = (int) Math.round(pontoTransformado[1]);

                reta.getPontoInicial().setX(xNovo);
                reta.getPontoInicial().setY(yNovo);

                int[] pontoTransformadoFinal = Matriz.escala2D(reta.getPontoFinal(), taxaX, taxaY);

                int xNovoFinal = (int) Math.round(pontoTransformadoFinal[0]);
                int yNovoFinal = (int) Math.round(pontoTransformadoFinal[1]);

                reta.getPontoFinal().setX(xNovoFinal);
                reta.getPontoFinal().setY(yNovoFinal);

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
                    aplicarTranslacao((int) result.get().param1, (int) result.get().param2);
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
