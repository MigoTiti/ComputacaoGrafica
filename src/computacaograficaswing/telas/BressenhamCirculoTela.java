package computacaograficaswing.telas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import computacaograficaswing.ComputacaoGraficaSwing;
import static computacaograficaswing.ComputacaoGraficaSwing.fxContainer;
import computacaograficaswing.areasdesenho.PlanoCartesiano;
import computacaograficaswing.util.Ponto;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import computacaograficaswing.util.BressenhamCirculo;

public class BressenhamCirculoTela extends PlanoCartesiano {

    private TextField xCampo;
    private TextField yCampo;
    private TextField raioCampo;

    public void iniciarTela() {
        ComputacaoGraficaSwing.mudarTitulo("Algoritmo de Bressenham (círculo)");
        Button btn = new Button();
        btn.setText("Voltar");
        btn.setOnAction((ActionEvent) -> {
            ComputacaoGraficaSwing.createScene();
        });

        BorderPane root = new BorderPane();

        HBox hboxTop = new HBox();
        hboxTop.setPadding(new Insets(15, 12, 15, 12));
        hboxTop.setSpacing(10);

        xCampo = new TextField();
        xCampo.setPrefWidth(100);
        xCampo.setMaxWidth(100);
        xCampo.setMinWidth(100);

        yCampo = new TextField();
        yCampo.setPrefWidth(100);
        yCampo.setMaxWidth(100);
        yCampo.setMinWidth(100);

        raioCampo = new TextField();
        raioCampo.setPrefWidth(100);
        raioCampo.setMaxWidth(100);
        raioCampo.setMinWidth(100);

        Text helpText = new Text("Ponto: (");
        helpText.setFill(Color.BLACK);
        helpText.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        Text helpText2 = new Text(",");
        helpText2.setFill(Color.BLACK);
        helpText2.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        Text helpText3 = new Text(")  Raio: ");
        helpText3.setFill(Color.BLACK);
        helpText3.setFont(Font.font("Arial", FontWeight.NORMAL, 20));

        Button calcularButton = new Button();
        calcularButton.setText("Desenhar círculo");
        calcularButton.setOnAction((ActionEvent) -> {
            Set<Ponto> pontos = BressenhamCirculo.aplicarBressenhamCirculo(new Ponto(Integer.parseInt(xCampo.getText()), Integer.parseInt(yCampo.getText())), Integer.parseInt(raioCampo.getText()));

            pontos.stream().forEach((ponto) -> {
                desenharPonto(ponto);
            });

            aplicarBuffer();
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

        hboxTop.getChildren().addAll(btn, helpText, xCampo, helpText2, yCampo, helpText3, raioCampo, calcularButton, limparTudoBtn, colorPicker);

        root.setTop(hboxTop);
        root.setCenter(inicializarPlano());

        fxContainer.setScene(new Scene(root));
    }
}
