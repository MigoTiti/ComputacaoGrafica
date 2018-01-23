package computacaograficaswing.util.transformacoes;

public class Ponto2D {

    protected double x;
    protected double y;

    public Ponto2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getXArredondado() {
        return Math.toIntExact(Math.round(x));
    }
    
    public int getYArredondado() {
        return Math.toIntExact(Math.round(y));
    }
    
    public void trocarCoordenadas() {
        double aux = x;
        x = y;
        y = aux;
    }
    
    public void negarX() {
        x = -x;
    }
    
    public void negarY() {
        y = -y;
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ponto2D) {
            Ponto2D aux = (Ponto2D) obj;
            return aux.getX() == x && aux.getY() == y && hashCode() == aux.hashCode();
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }
}
