package computacaograficaswing;

import computacaograficaswing.telas.Bressenham;
import computacaograficaswing.telas.BressenhamCirculo;
import computacaograficaswing.util.PlanoCartesiano;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
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

    public static final int JFXPANEL_WIDTH_INT = 1030;
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
            fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, PlanoCartesiano.HEIGHT_PLANO));
            //new Bressenham().iniciarTela();
            new BressenhamCirculo().iniciarTela();
            //createScene();
        });
    }

    public static void mudarTitulo(String titulo) {
        frame.setTitle(titulo);
    }

    public static void createScene() {
        mudarTitulo("Paint v1");

        Button bressenhamBotao = new Button();
        bressenhamBotao.setText("Algoritmo de Bressenham");
        bressenhamBotao.setOnAction((ActionEvent) -> {
            new Bressenham().iniciarTela();
        });

        Button bressenhamCircularBotao = new Button();
        bressenhamCircularBotao.setText("Algoritmo de Bressenham (círculos)");
        bressenhamCircularBotao.setOnAction((ActionEvent) -> {
            new BressenhamCirculo().iniciarTela();
        });

        HBox hboxTop = new HBox();
        hboxTop.setPadding(new Insets(15, 12, 15, 12));
        hboxTop.setSpacing(10);
        hboxTop.getChildren().addAll(bressenhamBotao, bressenhamCircularBotao);

        BorderPane root = new BorderPane();
        root.setCenter(hboxTop);
        fxContainer.setScene(new Scene(root));
    }

}
