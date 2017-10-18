package computacaograficaswing.areasdesenho;

import computacaograficaswing.util.FrameBufferBase;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class AreaDesenho {

    public static final int ORDEM = 74;
    public static final int TAMANHO_CELULA = 10;
    public static final int INSET_QUADRANTE = 1;
    public static final int TAMANHO_RETANGULO = TAMANHO_CELULA - (INSET_QUADRANTE * 2);
    public static final int WIDTH_PLANO = (ORDEM * TAMANHO_CELULA) + (INSET_QUADRANTE * 2);
    public static final int HEIGHT_PLANO = WIDTH_PLANO + 48;

    public static Color corSelecionada = Color.GREEN;
    public static Color corPadrao = Color.WHITE;
    public static Color corBressenham = Color.BLACK;
    
    protected FrameBufferBase frameBuffer;

    protected Rectangle gerarRect(Color cor) {
        Rectangle rect = new Rectangle(TAMANHO_RETANGULO, TAMANHO_RETANGULO, cor);
        rect.setOnMouseClicked((MouseEvent event) -> {
            if (rect.getFill().equals(corPadrao)) {
                rect.setFill(corSelecionada);
                frameBuffer.getPontosDesenhados().add(rect);
            } else {
                rect.setFill(corPadrao);
                frameBuffer.getPontosDesenhados().remove(rect);
            }
        });

        rect.setOnMouseDragOver((MouseDragEvent event) -> {
            if (corSelecionada.equals(corPadrao) && !rect.getFill().equals(corPadrao)) {
                rect.setFill(corPadrao);
                frameBuffer.getPontosDesenhados().remove(rect);
            } else if (corSelecionada.equals(corPadrao) && rect.getFill().equals(corPadrao)) {

            } else {
                rect.setFill(corSelecionada);
                frameBuffer.getPontosDesenhados().add(rect);
            }
        });

        return rect;
    }
    
    protected abstract void aplicarBuffer();
    
    protected void limparTela() {
        if (frameBuffer.getPontosDesenhados().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText(null);
            alert.setContentText("A tela já está vazia!");
            alert.showAndWait();
        } else {
            frameBuffer.limparBuffer();
            aplicarBuffer();
        }
    }
}
