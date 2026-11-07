package model;

public class Juego {
    public static final double ANCHO_MUNDO = 1000;

    private final Submarino submarino;

    public Juego() {
        submarino = new Submarino(ANCHO_MUNDO);
    }

    public void avanzar(double segundos) {
        submarino.avanzar(segundos);
    }

    public Submarino getSubmarino() {
        return submarino;
    }
}