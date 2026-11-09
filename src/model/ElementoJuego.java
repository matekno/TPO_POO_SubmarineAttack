package model;

// La idea es que todo lo que este en el agua sea ElementoJuego, porque de aca saca la posicion, la movilidad, etc.
public abstract class ElementoJuego {
	// x e y son el centro del elemento, en metros. y es la profundidad (0 =
	// superficie).
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

	// Dos rectangulos se tocan si la distancia entre sus centros es menor
	// que la mitad de la suma de sus medidas, en x y en y a la vez.
	public boolean seSuperponeCon(ElementoJuego otro) {
		return Math.abs(x - otro.x) < (ancho + otro.ancho) / 2 && Math.abs(y - otro.y) < (alto + otro.alto) / 2;
	}
	
	// Distancia desde un punto hasta el borde mas cercano de este elemento (0 si el punto esta adentro)
	public double distanciaA(double puntoX, double puntoY) {
		double dx = Math.max(Math.abs(puntoX - x) - ancho / 2, 0);
		double dy = Math.max(Math.abs(puntoY - y) - alto / 2, 0);
		return Math.sqrt(dx * dx + dy * dy);
	}

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