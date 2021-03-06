package computacaograficaswing;

import computacaograficaswing.telas.BressenhamRetaTela;
import computacaograficaswing.telas.BressenhamCirculoTela;
import computacaograficaswing.telas.PreenchimentoTela;
import computacaograficaswing.areasdesenho.PlanoCartesiano;
import computacaograficaswing.telas.RecorteLinhasTela;
import computacaograficaswing.telas.Transformacoes2DTela;
import computacaograficaswing.telas.Transformacoes3DTela;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ComputacaoGraficaSwing extends JApplet {

    public static final int JFXPANEL_WIDTH_INT = 1200;
    public static JFXPanel fxContainer;
    private static JFrame frame;
    public static JApplet applet;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            }

            frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            applet = new ComputacaoGraficaSwing();
            applet.init();

            frame.setContentPane(applet.getContentPane());

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);

            applet.start();
        });
    }

    @Override
    public void init() {
        fxContainer = new JFXPanel();

        add(fxContainer, BorderLayout.CENTER);
        
        Platform.runLater(() -> {
            fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, (int)PlanoCartesiano.HEIGHT_PLANO));
            createScene();
        });
    }

    public static void mudarTitulo(String titulo) {
        frame.setTitle(titulo);
    }

    public static void createScene() {
        mudarTitulo("Paint v1");

        Button bressenhamBotao = new Button("Algoritmo de Bressenham");
        bressenhamBotao.setOnAction(ActionEvent -> {
            new BressenhamRetaTela().iniciarTela();
        });

        Button bressenhamCircularBotao = new Button("Algoritmo de Bressenham (círculos)");
        bressenhamCircularBotao.setOnAction(ActionEvent -> {
            new BressenhamCirculoTela().iniciarTela();
        });
        
        Button preenchimentoButton = new Button("Preenchimento de formas");
        preenchimentoButton.setOnAction(ActionEvent -> {
            new PreenchimentoTela().iniciarTela();
        });
        
        Button recorteLinhasButton = new Button("Recorte de linhas");
        recorteLinhasButton.setOnAction(ActionEvent -> {
            new RecorteLinhasTela().iniciarTela();
        });
        
        Button transformacoes2DButton = new Button("Transformações 2D");
        transformacoes2DButton.setOnAction(ActionEvent -> {
            new Transformacoes2DTela().iniciarTela();
        });
        
        Button transformacoes3DButton = new Button("Transformações 3D");
        transformacoes3DButton.setOnAction(ActionEvent -> {
            new Transformacoes3DTela().iniciarTela();
        });
        
        HBox hboxTop = new HBox();
        hboxTop.setPadding(new Insets(15, 12, 15, 12));
        hboxTop.setSpacing(10);
        hboxTop.setAlignment(Pos.CENTER);
        hboxTop.getChildren().addAll(bressenhamBotao, bressenhamCircularBotao, preenchimentoButton, recorteLinhasButton, transformacoes2DButton, transformacoes3DButton);

        BorderPane root = new BorderPane();
        root.setCenter(hboxTop);
        fxContainer.setScene(new Scene(root));
    }

}
