package computacaograficaswing.util.transformacoes;

import computacaograficaswing.util.Ponto2D;
import computacaograficaswing.util.Ponto3D;

public class Matriz {
    
    public static int[] projecaoPerspectiva3PontosFuga(Ponto3D p) {
        int[] resultado = new int[] {};
        return resultado;
    }
    
    public static double[] projecaoObliqua(Ponto3D p, double fatorProfundidade, double angulo) {
        double[][] matrizProjecao = {{1, 0, fatorProfundidade * Math.cos(Math.toRadians(angulo)), 0}, 
                                     {0, 1, fatorProfundidade * Math.sin(Math.toRadians(angulo)), 0},
                                     {0, 0, 0, 0},
                                     {0, 0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), p.getZ(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizProjecao, coordenadas);
    }
    
    public static double[] escala3D(Ponto3D p, double taxaX, double taxaY, double taxaZ) {
        double[][] matrizEscala = {{taxaX, 0, 0, 0}, 
                                   {0, taxaY, 0, 0}, 
                                   {0, 0, taxaZ, 0}, 
                                   {0, 0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), p.getZ(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizEscala, coordenadas);
    }
    
    public static double[] translacao3D(Ponto3D p, int taxaX, int taxaY, int taxaZ) {
        double[][] matrizTranslacao = {{1, 0, 0, taxaX}, 
                                       {0, 1, 0, taxaY}, 
                                       {0, 0, 1, taxaZ},
                                       {0, 0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), p.getZ(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizTranslacao, coordenadas);
    }
    
    public static double[] rotacao3DX(Ponto3D p, double angulo) {
        double[][] matrizRotacao = {{1, 0, 0, 0},
                                    {0, Math.cos(Math.toRadians(angulo)), -Math.sin(Math.toRadians(angulo)), 0}, 
                                    {0, Math.sin(Math.toRadians(angulo)), Math.cos(Math.toRadians(angulo)), 0}, 
                                    {0, 0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), p.getZ(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizRotacao, coordenadas);
    }
    
    public static double[] rotacao3DY(Ponto3D p, double angulo) {
        double[][] matrizRotacao = {{Math.cos(Math.toRadians(angulo)), 0, Math.sin(Math.toRadians(angulo)), 0}, 
                                    {0, 1, 0, 0},
                                    {-Math.sin(Math.toRadians(angulo)), 0, Math.cos(Math.toRadians(angulo)), 0},
                                    {0, 0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), p.getZ(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizRotacao, coordenadas);
    }
    
    public static double[] rotacao3DZ(Ponto3D p, double angulo) {
        double[][] matrizRotacao = {{Math.cos(Math.toRadians(angulo)), -Math.sin(Math.toRadians(angulo)), 0, 0}, 
                                    {Math.sin(Math.toRadians(angulo)), Math.cos(Math.toRadians(angulo)), 0, 0}, 
                                    {0, 0, 1, 0},
                                    {0, 0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), p.getZ(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizRotacao, coordenadas);
    }
    
    public static double[] escala2D(Ponto2D p, double taxaX, double taxaY) {
        double[][] matrizEscala = {{taxaX, 0, 0}, 
                                   {0, taxaY, 0}, 
                                   {0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizEscala, coordenadas);
    }
    
    public static double[] translacao2D(Ponto2D p, int taxaX, int taxaY) {
        double[][] matrizTranslacao = {{1, 0, taxaX}, 
                                       {0, 1, taxaY}, 
                                       {0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), 1};
        
        return multiplicacaoMatrizPorVetor(matrizTranslacao, coordenadas);
    }
    
    public static double[] rotacao2D(Ponto2D p, double angulo) {
        double[][] matrizRotacao = {{Math.cos(Math.toRadians(angulo)), -Math.sin(Math.toRadians(angulo)), 0}, 
                                    {Math.sin(Math.toRadians(angulo)), Math.cos(Math.toRadians(angulo)), 0}, 
                                    {0, 0, 1}};
        
        double[] coordenadas = {p.getX(), p.getY(), 1};
        
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
