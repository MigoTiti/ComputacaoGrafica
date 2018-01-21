package computacaograficaswing.util;

public class Matriz {
    
    public static int[] projecaoObliqua(Ponto3D p, double fatorProfundidade, double angulo) {
        double[][] matrizProjecao = {{1, 0, fatorProfundidade * Math.cos(Math.toRadians(angulo)), 0}, 
                                     {0, 1, fatorProfundidade * Math.sin(Math.toRadians(angulo)), 0},
                                     {0, 0, 0, 0},
                                     {0, 0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), p.getZ(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizProjecao, coordenadas);
    }
    
    public static int[] escala3D(Ponto3D p, double taxaX, double taxaY, double taxaZ) {
        double[][] matrizEscala = {{taxaX, 0, 0, 0}, 
                                   {0, taxaY, 0, 0}, 
                                   {0, 0, taxaZ, 0}, 
                                   {0, 0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), p.getZ(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizEscala, coordenadas);
    }
    
    public static int[] translacao3D(Ponto3D p, int taxaX, int taxaY, int taxaZ) {
        double[][] matrizTranslacao = {{1, 0, 0, taxaX}, 
                                       {0, 1, 0, taxaY}, 
                                       {0, 0, 1, taxaZ},
                                       {0, 0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), p.getZ(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizTranslacao, coordenadas);
    }
    
    public static int[] rotacao3DX(Ponto3D p, double angulo) {
        double[][] matrizRotacao = {{1, 0, 0, 0},
                                    {0, Math.cos(Math.toRadians(angulo)), -Math.sin(Math.toRadians(angulo)), 0}, 
                                    {0, Math.sin(Math.toRadians(angulo)), Math.cos(Math.toRadians(angulo)), 0}, 
                                    {0, 0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), p.getZ(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizRotacao, coordenadas);
    }
    
    public static int[] rotacao3DY(Ponto3D p, double angulo) {
        double[][] matrizRotacao = {{Math.cos(Math.toRadians(angulo)), 0, Math.sin(Math.toRadians(angulo)), 0}, 
                                    {0, 1, 0, 0},
                                    {-Math.sin(Math.toRadians(angulo)), 0, Math.cos(Math.toRadians(angulo)), 0},
                                    {0, 0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), p.getZ(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizRotacao, coordenadas);
    }
    
    public static int[] rotacao3DZ(Ponto3D p, double angulo) {
        double[][] matrizRotacao = {{Math.cos(Math.toRadians(angulo)), -Math.sin(Math.toRadians(angulo)), 0, 0}, 
                                    {Math.sin(Math.toRadians(angulo)), Math.cos(Math.toRadians(angulo)), 0, 0}, 
                                    {0, 0, 1, 0},
                                    {0, 0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), p.getZ(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizRotacao, coordenadas);
    }
    
    public static int[] escala2D(Ponto p, double taxaX, double taxaY) {
        double[][] matrizEscala = {{taxaX, 0, 0}, 
                                   {0, taxaY, 0}, 
                                   {0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizEscala, coordenadas);
    }
    
    public static int[] translacao2D(Ponto p, int taxaX, int taxaY) {
        double[][] matrizTranslacao = {{1, 0, taxaX}, 
                                       {0, 1, taxaY}, 
                                       {0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizTranslacao, coordenadas);
    }
    
    public static int[] rotacao2D(Ponto p, double angulo) {
        double[][] matrizRotacao = {{Math.cos(Math.toRadians(angulo)), -Math.sin(Math.toRadians(angulo)), 0}, 
                                    {Math.sin(Math.toRadians(angulo)), Math.cos(Math.toRadians(angulo)), 0}, 
                                    {0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizRotacao, coordenadas);
    }
    
    private static int[] multiplicacaoMatrizPorVetor(double[][] matriz, double[] vetor) {
        int tamanho = vetor.length;
        
        int resultado[] = new int[tamanho];
        
        for (int i = 0; i < tamanho; i++) {
            double soma = 0;
            
            for (int j = 0; j < tamanho; j++) {
                soma += matriz[i][j] * vetor[j];
            }
            
            resultado[i] = Math.toIntExact(Math.round(soma));
        }
        
        return resultado;
    }
}
