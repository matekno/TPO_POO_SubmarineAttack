package model;

import java.util.Random;

public class Nivel {
    private static final int BARCOS_POR_SERIE = 12;
    private static final int MAXIMO_BARCOS_EN_PANTALLA = 3;
    private static final double DEMORA_ENTRE_BARCOS = 1.5; // segundos
    

    private int barcosPorSalir;
    private double tiempoParaProximoBarco;
    private BarcoFactory barcoFactory;

    public Nivel(BarcoFactory barcoFactory) {
        barcosPorSalir = BARCOS_POR_SERIE;
        tiempoParaProximoBarco = DEMORA_ENTRE_BARCOS;
        this.barcoFactory = barcoFactory;
    }

    public void avanzar(double segundos) {
        tiempoParaProximoBarco -= segundos;
    }

    public boolean puedeSalirBarco(int barcosEnPantalla) {
        return barcosPorSalir > 0
                && barcosEnPantalla < MAXIMO_BARCOS_EN_PANTALLA
                && tiempoParaProximoBarco <= 0;
    }

    public Barco crearBarco() {
        barcosPorSalir--;
        tiempoParaProximoBarco = DEMORA_ENTRE_BARCOS;
        Barco barco = this.barcoFactory.crearBarco();
        System.out.println("Sale un barco nuevo. Quedan por salir: " + barcosPorSalir);
        return barco;
    }
}