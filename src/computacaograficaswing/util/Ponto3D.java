package computacaograficaswing.util;

import java.io.Serializable;

public class Ponto3D extends Ponto2D implements Serializable{

    private double z;

    public Ponto3D(Ponto3D ponto) {
        super(ponto.getX(), ponto.getY());
        this.z = ponto.getZ();
    }
    
    public Ponto3D(double x, double y, double z) {
        super(x, y);

        this.z = z;
    }
    
    public Ponto3D(double x, double y) {
        super(x, y);

        this.z = 0;
    }

    public int getZArredondado() {
        return Math.toIntExact(Math.round(z));
    }
    
    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ponto3D) {
            Ponto3D aux = (Ponto3D) obj;
            return aux.getX() == x && aux.getY() == y && aux.getZ() == z && hashCode() == aux.hashCode();
        }

        return false;

}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }
}
