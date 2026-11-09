package model;

public class Carga extends ElementoJuego {
	public static final double DETONADOR_MINIMO = 300; // Alturas en Y.
	public static final double DETONADOR_MAXIMO = 700;
	private static final double ANCHO = 30;
	private static final double ALTO = 30;
	private static final double DURACION_EXPLOSION = 0.5; // segundos que se ve la explosion

	private final double velocidadCaida;
	private final double profundidadDetonacion;
	private boolean explotada;
	private double tiempoExplosion;

	public Carga(double x, double velocidadCaida, double profundidadDetonacion) {
		super(x, 0, ANCHO, ALTO);
		this.velocidadCaida = velocidadCaida;
		this.profundidadDetonacion = profundidadDetonacion;
		this.explotada = false;
	}

	@Override
	public void avanzar(double segundos) {
		if (explotada) {
			tiempoExplosion -= segundos;
		} else {
			// Cae en linea recta y nunca pasa de la profundidad de su detonador
			y = Math.min(y + velocidadCaida * segundos, profundidadDetonacion);
		}
	}

	public boolean llegoAlDetonador() {
		return y >= profundidadDetonacion;
	}

	public void explotar() {
		explotada = true;
		tiempoExplosion = DURACION_EXPLOSION;
	}

	public boolean estaExplotada() {
		return explotada;
	}

	public boolean terminoLaExplosion() {
		return explotada && tiempoExplosion <= 0;
	}
}