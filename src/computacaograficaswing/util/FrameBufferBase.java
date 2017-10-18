package computacaograficaswing.util;

import java.util.LinkedHashSet;
import java.util.Set;
import javafx.scene.shape.Rectangle;

public abstract class FrameBufferBase {

    protected final Set<Rectangle> pontosDesenhados;

    public FrameBufferBase() {
        this.pontosDesenhados = new LinkedHashSet<>();
    }

    public Set<Rectangle> getPontosDesenhados() {
        return pontosDesenhados;
    }

    public void limparBuffer() {
        pontosDesenhados.stream().forEach((pontoDesenhado) -> {
            pontoDesenhado.setFill(areasdesenho.AreaDesenho.corPadrao);
        });

        pontosDesenhados.clear();
    }
}
