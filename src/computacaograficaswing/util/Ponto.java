package computacaograficaswing.util;

public class Ponto {

    private int x;
    private int y;

    public Ponto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void trocarCoordenadas() {
        int aux = x;
        x = y;
        y = aux;
    }
    
    public void negarX() {
        x = -x;
    }
    
    public void negarY() {
        y = -y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
