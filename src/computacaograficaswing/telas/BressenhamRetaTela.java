package computacaograficaswing.telas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import computacaograficaswing.ComputacaoGraficaSwing;
import static computacaograficaswing.ComputacaoGraficaSwing.fxContainer;
import computacaograficaswing.areasdesenho.PlanoCartesiano;
import computacaograficaswing.util.transformacoes.BressenhamReta;
import computacaograficaswing.util.transformacoes.Ponto2D;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BressenhamRetaTela extends PlanoCartesiano {

    private TextField x1Campo;
    private TextField x2Campo;
    private TextField y1Campo;
    private TextField y2Campo;

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
                    
                    Set<Ponto2D> pontos = new BressenhamReta().aplicarBressenham(new Ponto2D(x1, y1), new Ponto2D(x2, y2));
                    pontos.stream().forEach((ponto) -> {
                        desenharPonto(ponto);
                    });

                    aplicarBuffer();
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
        root.setCenter(inicializarPlano());
        
        fxContainer.setScene(new Scene(root));
    }
}
