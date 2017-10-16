package computacaograficaswing.telas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import computacaograficaswing.ComputacaoGraficaSwing;
import static computacaograficaswing.ComputacaoGraficaSwing.fxContainer;
import computacaograficaswing.util.PlanoCartesiano;
import computacaograficaswing.util.Ponto;
import java.util.LinkedHashSet;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BressenhamCirculo extends PlanoCartesiano{
    private TextField xCampo;
    private TextField yCampo;
    private TextField raioCampo;
    
    private void aplicarBressenhamCirculo(Ponto centro, int raio) {
        int deslocamentoX = centro.getX();
        int deslocamentoY = centro.getY();
        
        int x = 0;
        int y = raio;
        int p = 1 - raio;
        
        Set<Ponto> pontos = new LinkedHashSet<>();
        pontos.add(new Ponto(x, y));
        
        while (x < y) {            
            x++;
            if (p < 0)
                p += (2 * x) + 3;
            else { 
                y--;
                p += (2 * x) - (2 * y) + 5;
            }
            
            pontos.add(new Ponto(x, y));
        }
        
        reflexao(pontos);
        
        if (deslocamentoX > 0)
            pontos.stream().forEach((ponto) -> {
                ponto.setX(ponto.getX() + deslocamentoX);
        });
        
        if (deslocamentoY > 0)
            pontos.stream().forEach((ponto) -> {
                ponto.setY(ponto.getY() + deslocamentoY);
        });
        
        pontos.stream().forEach((ponto) -> {
            desenharPonto(ponto);
        });
        
        aplicarBuffer();
    }
    
    private void reflexao(Set<Ponto> pontos) {  
        Set<Ponto> aux = new LinkedHashSet<>(pontos);
        
        aux.stream().map((ponto) -> {
            pontos.add(new Ponto(-ponto.getX(), ponto.getY()));
            return ponto;
        }).map((ponto) -> {
            pontos.add(new Ponto(ponto.getX(), -ponto.getY()));
            return ponto;
        }).map((ponto) -> {
            pontos.add(new Ponto(-ponto.getX(), -ponto.getY()));
            return ponto;
        }).map((ponto) -> {
            pontos.add(new Ponto(ponto.getY(), ponto.getX()));
            return ponto;
        }).map((ponto) -> {
            pontos.add(new Ponto(-ponto.getY(), ponto.getX()));
            return ponto;
        }).map((ponto) -> {
            pontos.add(new Ponto(ponto.getY(), -ponto.getX()));
            return ponto;
        }).forEach((ponto) -> {
            pontos.add(new Ponto(-ponto.getY(), -ponto.getX()));
        });
    }
    
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
            aplicarBressenhamCirculo(new Ponto(Integer.parseInt(xCampo.getText()), Integer.parseInt(yCampo.getText())), Integer.parseInt(raioCampo.getText()));
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
        root.setCenter(this.inicializarPlano());
        
        if (PlanoCartesiano.WIDTH_PLANO < ComputacaoGraficaSwing.JFXPANEL_WIDTH_INT) {
            root.setLeft(new Rectangle((ComputacaoGraficaSwing.JFXPANEL_WIDTH_INT - PlanoCartesiano.WIDTH_PLANO)/2 + 20, PlanoCartesiano.HEIGHT_PLANO, Color.WHITE));
            root.setRight(new Rectangle((ComputacaoGraficaSwing.JFXPANEL_WIDTH_INT - PlanoCartesiano.WIDTH_PLANO)/2 + 20, PlanoCartesiano.HEIGHT_PLANO, Color.WHITE));
        }

        fxContainer.setScene(new Scene(root));
    }
}

