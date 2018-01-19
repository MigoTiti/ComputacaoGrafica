package computacaograficaswing.util;

public class Matriz {
    
    public static double[] escala2D(int x, int y, double taxaX, double taxaY) {
        double[][] matrizEscala = {{taxaX, 0, 0}, {0, taxaY, 0}, {0, 0, 1}};
        double[] coordenadas = {x, y, 1};
        
        return multiplicacaoMatrizPorVetor(matrizEscala, coordenadas);
    }
    
    public static double[] translacao2D(int x, int y, int taxaX, int taxaY) {
        double[][] matrizTranslacao = {{1, 0, taxaX}, {0, 1, taxaY}, {0, 0, 1}};
        double[] coordenadas = {x, y, 1};
        
        return multiplicacaoMatrizPorVetor(matrizTranslacao, coordenadas);
    }
    
    public static double[] rotacao2D(int x, int y, double angulo) {
        double[][] matrizRotacao = {{Math.cos(Math.toRadians(angulo)), -Math.sin(Math.toRadians(angulo)), 0}, {Math.sin(Math.toRadians(angulo)), Math.cos(Math.toRadians(angulo)), 0}, {0, 0, 1}};
        double[] coordenadas = {x, y, 1};
        
        return multiplicacaoMatrizPorVetor(matrizRotacao, coordenadas);
    }
    
    private static double[] multiplicacaoMatrizPorVetor(double[][] matriz, double[] vetor) {
        int tamanho = vetor.length;
        
        double resultado[] = new double[tamanho];
        
        for (int i = 0; i < tamanho; i++) {
            double soma = 0;
            
            for (int j = 0; j < tamanho; j++) {
                soma += matriz[i][j] * vetor[j];
            }
            
            resultado[i] = soma;
        }
        
        return resultado;
    }
}
