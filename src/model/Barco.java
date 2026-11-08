package model;

public class Barco extends ElementoJuego {
    private static final double ANCHO = 120;
    private static final double ALTO = 40;

    private final double anchoMundo;
    private final double velocidad;
    private int sentido; // 1 = hacia la derecha, -1 = hacia la izquierda
    private int pasadasRestantes;

    public Barco(double anchoMundo, boolean desdeIzquierda, double velocidad, int pasadas) {
        super(0, 0, ANCHO, ALTO);
        this.anchoMundo = anchoMundo;
        this.velocidad = velocidad;
        this.pasadasRestantes = pasadas;
        // Arranca justo afuera de la pantalla, del lado que le toco
        if (desdeIzquierda) {
            x = -ANCHO / 2;
            sentido = 1;
        } else {
            x = anchoMundo + ANCHO / 2;
            sentido = -1;
        }
    }

    @Override
    public void avanzar(double segundos) {
        x += sentido * velocidad * segundos;
        if (salioDePantalla()) {
            pasadasRestantes--;
            sentido = -sentido;
        }
    }

    private boolean salioDePantalla() {
        if (sentido == 1) {
            return x > anchoMundo + ancho / 2;
        }
        return x < -ancho / 2;
    }

    public boolean terminoSusPasadas() {
        return pasadasRestantes == 0;
    }
}