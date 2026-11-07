package model;

// La idea es que todo lo que este en el agua sea ElementoJuego, porque de aca saca la posicion, la movilidad, etc.
public abstract class ElementoJuego {
    // x e y son el centro del elemento, en metros. y es la profundidad (0 = superficie).
	// Uso protected para que las hijas puedan cambiarlas.
    protected double x;
    protected double y;
    protected final double ancho;
    protected final double alto;

    protected ElementoJuego(double x, double y, double ancho, double alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    public abstract void avanzar(double segundos); // Cada clase se mueve distinto, por eso no esta implementado.

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAncho() {
        return ancho;
    }

    public double getAlto() {
        return alto;
    }
}