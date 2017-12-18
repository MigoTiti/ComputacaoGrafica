package computacaograficaswing.areasdesenho;

import computacaograficaswing.framebuffer.FrameBufferBase;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class AreaDesenho {

    public static final int ORDEM = 80;
    public static final double TAMANHO_CELULA = 10;
    public static final double INSET_QUADRANTE = 0;
    public static final double TAMANHO_RETANGULO = TAMANHO_CELULA - (INSET_QUADRANTE * 2);
    public static final double HEIGHT_PLANO = (ORDEM * TAMANHO_CELULA) + (INSET_QUADRANTE * 2) + 48;

    public static Color corSelecionada = Color.GREEN;
    public static Color corPadrao = Color.WHITE;
    public static Color corBressenham = Color.BLACK;
    
    protected FrameBufferBase frameBuffer;

    protected abstract Rectangle gerarRect(Color cor);
    
    protected abstract Group inicializarPlano();
    
    protected void limparTela() {
        if (frameBuffer.getPontosDesenhados().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText(null);
            alert.setContentText("A tela já está vazia!");
            alert.showAndWait();
        } else {
            frameBuffer.limparBuffer();
        }
    }
}
