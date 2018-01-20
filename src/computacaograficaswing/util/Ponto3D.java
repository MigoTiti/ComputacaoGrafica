package computacaograficaswing.util;

import java.io.Serializable;

public class Ponto3D extends Ponto implements Serializable{

    private int z;

    public Ponto3D(Ponto3D ponto) {
        super(ponto.getX(), ponto.getY());
        this.z = ponto.getZ();
    }
    
    public Ponto3D(int x, int y, int z) {
        super(x, y);

        this.z = z;
    }
    
    public Ponto3D(int x, int y) {
        super(x, y);

        this.z = 0;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
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
        hash = 29 * hash + this.x;
        hash = 29 * hash + this.y;
        hash = 29 * hash + this.z;
        return hash;
    }

}
