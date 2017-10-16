package computacaograficaswing.telas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import computacaograficaswing.ComputacaoGraficaSwing;
import static computacaograficaswing.ComputacaoGraficaSwing.fxContainer;
import computacaograficaswing.util.PlanoCartesiano;
import computacaograficaswing.util.Ponto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Bressenham extends PlanoCartesiano {

    private boolean trocaXY;
    private boolean trocaX;
    private boolean trocaY;

    private TextField x1Campo;
    private TextField x2Campo;
    private TextField y1Campo;
    private TextField y2Campo;

    private void aplicarBressenham(Ponto p1, Ponto p2) {
        int[] novosPontos = reflexao(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        int x1 = novosPontos[0];
        int y1 = novosPontos[1];
        int x2 = novosPontos[2];
        int y2 = novosPontos[3];

        int deltaX = x2 - x1;
        int deltaY = y2 - y1;

        double coeficienteAngular = (double) deltaY / (double) deltaX;

        BigDecimal bd = new BigDecimal(coeficienteAngular);
        bd = bd.setScale(1, RoundingMode.HALF_DOWN);
        coeficienteAngular = bd.doubleValue();

        double erro = coeficienteAngular - 0.5;

        int x = x1;
        int y = y1;

        ArrayList<Ponto> pontos = new ArrayList<>();
        pontos.add(new Ponto(x, y));

        while (x < x2) {
            if (erro >= 0) {
                y++;
                erro--;
            }

            x++;
            erro += coeficienteAngular;
            pontos.add(new Ponto(x, y));
        }

        pontos = reflexaoVolta(pontos);

        for (Ponto ponto : pontos) {
            desenharPonto(ponto);
        }

        aplicarBuffer();
    }

    private int[] reflexao(int x1, int y1, int x2, int y2) {
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;

        double coeficienteAngular = (double) deltaY / (double) deltaX;

        BigDecimal bd = new BigDecimal(coeficienteAngular);
        bd = bd.setScale(1, RoundingMode.HALF_DOWN);
        coeficienteAngular = bd.doubleValue();

        if (coeficienteAngular > 1 || coeficienteAngular <= -1) {
            int aux = x1;
            x1 = y1;
            y1 = aux;

            aux = x2;
            x2 = y2;
            y2 = aux;

            trocaXY = true;
        }

        if (x1 > x2) {
            x1 = -x1;
            x2 = -x2;

            trocaX = true;
        }

        if (y1 > y2) {
            y1 = -y1;
            y2 = -y2;

            trocaY = true;
        }

        return new int[]{x1, y1, x2, y2};
    }

    private ArrayList<Ponto> reflexaoVolta(ArrayList<Ponto> pontos) {
        if (trocaY) {
            pontos.stream().forEach((ponto) -> {
                ponto.negarY();
            });
            trocaY = false;
        }

        if (trocaX) {
            pontos.stream().forEach((ponto) -> {
                ponto.negarX();
            });
            trocaX = false;
        }

        if (trocaXY) {
            pontos.stream().forEach((ponto) -> {
                ponto.trocarCoordenadas();
            });
            trocaXY = false;
        }

        return pontos;
    }

    public void iniciarTela() {
        ComputacaoGraficaSwing.mudarTitulo("Algoritmo de Bressenham");
        Button btn = new Button();
        btn.setText("Voltar");
        btn.setOnAction((ActionEvent) -> {
            ComputacaoGraficaSwing.createScene();
        });

        BorderPane root = new BorderPane();

        HBox hboxTop = new HBox();
        hboxTop.setPadding(new Insets(15, 12, 15, 12));
        hboxTop.setSpacing(10);

        x1Campo = new TextField();
        x1Campo.setPrefWidth(100);
        x1Campo.setMaxWidth(100);
        x1Campo.setMinWidth(100);

        y1Campo = new TextField();
        y1Campo.setPrefWidth(100);
        y1Campo.setMaxWidth(100);
        y1Campo.setMinWidth(100);

        x2Campo = new TextField();
        x2Campo.setPrefWidth(100);
        x2Campo.setMaxWidth(100);
        x2Campo.setMinWidth(100);

        y2Campo = new TextField();
        y2Campo.setPrefWidth(100);
        y2Campo.setMaxWidth(100);
        y2Campo.setMinWidth(100);

        Text helpText = new Text("Pontos: (");
        helpText.setFill(Color.BLACK);
        helpText.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        Text helpText2 = new Text(",");
        helpText2.setFill(Color.BLACK);
        helpText2.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        Text helpText3 = new Text("), (");
        helpText3.setFill(Color.BLACK);
        helpText3.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        Text helpText4 = new Text(",");
        helpText4.setFill(Color.BLACK);
        helpText4.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        Text helpText5 = new Text(") ");
        helpText5.setFill(Color.BLACK);
        helpText5.setFont(Font.font("Arial", FontWeight.NORMAL, 20));

        Button calcularButton = new Button();
        calcularButton.setText("Desenhar reta");
        calcularButton.setOnAction((ActionEvent) -> {
            if (!x1Campo.getText().equals("") && !y1Campo.getText().equals("") && !x2Campo.getText().equals("") && !y2Campo.getText().equals("")) {
                try {
                    int x1 = Integer.parseInt(x1Campo.getText());
                    int y1 = Integer.parseInt(y1Campo.getText());
                    int x2 = Integer.parseInt(x2Campo.getText());
                    int y2 = Integer.parseInt(y2Campo.getText());
                    aplicarBressenham(new Ponto(x1, y1), new Ponto(x2, y2));
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Digite apenas inteiros");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Preencha os 4 campos!");
                alert.showAndWait();
            }
        });

        Button limparTudoBtn = new Button();
        limparTudoBtn.setText("Limpar tela");
        limparTudoBtn.setOnAction((ActionEvent) -> {
            limparTela();
        });

        ColorPicker colorPicker = new ColorPicker(PlanoCartesiano.corSelecionada);
        colorPicker.setOnAction((ActionEvent event) -> {
            PlanoCartesiano.corSelecionada = colorPicker.getValue();
        });

        hboxTop.getChildren().addAll(btn, helpText, x1Campo, helpText2, y1Campo, helpText3, x2Campo, helpText4, y2Campo, helpText5, calcularButton, limparTudoBtn, colorPicker);

        root.setTop(hboxTop);
        root.setCenter(this.inicializarPlano());

        if (PlanoCartesiano.WIDTH_PLANO < ComputacaoGraficaSwing.JFXPANEL_WIDTH_INT) {
            root.setLeft(new Rectangle((ComputacaoGraficaSwing.JFXPANEL_WIDTH_INT - PlanoCartesiano.WIDTH_PLANO) / 2 + 20, PlanoCartesiano.HEIGHT_PLANO, Color.WHITE));
            root.setRight(new Rectangle((ComputacaoGraficaSwing.JFXPANEL_WIDTH_INT - PlanoCartesiano.WIDTH_PLANO) / 2 + 20, PlanoCartesiano.HEIGHT_PLANO, Color.WHITE));
        }

        fxContainer.setScene(new Scene(root));
    }
}
