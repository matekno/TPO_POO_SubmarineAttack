package model;

import java.util.Random;

public class Nivel {
    private static final int BARCOS_POR_SERIE = 12;
    private static final int MAXIMO_BARCOS_EN_PANTALLA = 3;
    private static final double DEMORA_ENTRE_BARCOS = 1.5; // segundos
    private static final double VELOCIDAD_BARCOS = 150; // metros por segundo
    private static final int MAXIMO_PASADAS = 3;

    private final Random random;
    private int barcosPorSalir;
    private double tiempoParaProximoBarco;

    public Nivel(Random random) {
        this.random = random;
        barcosPorSalir = BARCOS_POR_SERIE;
        tiempoParaProximoBarco = DEMORA_ENTRE_BARCOS;
    }

    public void avanzar(double segundos) {
        tiempoParaProximoBarco -= segundos;
    }

    public boolean puedeSalirBarco(int barcosEnPantalla) {
        return barcosPorSalir > 0
                && barcosEnPantalla < MAXIMO_BARCOS_EN_PANTALLA
                && tiempoParaProximoBarco <= 0;
    }

    public Barco crearBarco(double anchoMundo) {
        barcosPorSalir--;
        tiempoParaProximoBarco = DEMORA_ENTRE_BARCOS;
        boolean desdeIzquierda = random.nextBoolean();
        int pasadas = 1 + random.nextInt(MAXIMO_PASADAS);
        // System.out.println("Sale un barco nuevo. Quedan por salir: " + barcosPorSalir);
        return new Barco(anchoMundo, desdeIzquierda, VELOCIDAD_BARCOS, pasadas);
    }
}