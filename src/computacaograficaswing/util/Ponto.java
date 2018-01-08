package computacaograficaswing.util;

public class Ponto {

    protected int x;
    protected int y;

    public Ponto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void trocarCoordenadas() {
        int aux = x;
        x = y;
        y = aux;
    }
    
    public void incrementarX(int n) {
        x += n;
    }
    
    public void decrementarX(int n) {
        x -= n;
    }
    
    public void incrementarY(int n) {
        y += n;
    }
    
    public void decrementarY(int n) {
        y -= n;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ponto) {
            Ponto aux = (Ponto) obj;
            return aux.getX() == x && aux.getY() == y && hashCode() == aux.hashCode();
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.x;
        hash = 29 * hash + this.y;
        return hash;
    }
}
