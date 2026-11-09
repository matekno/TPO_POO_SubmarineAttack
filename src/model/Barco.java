package model;

import java.util.Random;

public class Barco extends ElementoJuego {
	private static final double ANCHO = 120;
	private static final double ALTO = 40;

	private static final double MINIMO_ENTRE_CARGAS = 1; // segundos
	private static final double MAXIMO_ENTRE_CARGAS = 3; // segundos

	private final double anchoMundo;
	private final double velocidad;
	private int sentido; // 1 = hacia la derecha, -1 = hacia la izquierda
	private int pasadasRestantes;

	private final double velocidadCaida;
	private final Random random;
	private double tiempoParaProximaCarga;

	// Un barco funciona de la siguiente manera:
	// Un barco solo se mueve en el eje X, apoyado sobre la superficie del agua.
	// Tiene algunos parametros random determinados por el Nivel y ejecutados por
	// BarcoFactory (ver clases para más detalle): el sentido, la velocidad, y la
	// cantidad de pasadas.

	// Su movimiento determinado por avanzar() es ir cambiando su posicion segun
	// sentido y velocidad. Ademas, cuando llega al otro extremo, si le quedan
	// pasadas, invierte el sentido y vuelve a salir pero para el otro lado.

	public Barco(double anchoMundo, boolean desdeIzquierda, double velocidad, int pasadas, double velocidadCaida,
			Random random) {
		super(0, 0, ANCHO, ALTO);
		this.anchoMundo = anchoMundo;
		this.velocidad = velocidad;
		this.pasadasRestantes = pasadas;

		this.velocidadCaida = velocidadCaida;
		this.random = random;
		this.tiempoParaProximaCarga = randomEntre(MINIMO_ENTRE_CARGAS, MAXIMO_ENTRE_CARGAS);

		// Arranca justo afuera de la pantalla, del lado que le toco
		if (desdeIzquierda == true) {
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
		tiempoParaProximaCarga -= segundos;
		if (salioDePantalla() == true) {
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

	public boolean puedeLanzarCarga() {
		return tiempoParaProximaCarga <= 0 && estaEnPantalla();
	}

	private boolean estaEnPantalla() {
		return x >= 0 && x <= anchoMundo;
	}

	public Carga lanzarCarga() {
		tiempoParaProximaCarga = randomEntre(MINIMO_ENTRE_CARGAS, MAXIMO_ENTRE_CARGAS);
		double profundidadDetonacion = randomEntre(Carga.DETONADOR_MINIMO, Carga.DETONADOR_MAXIMO);
		System.out.printf(
				"Carga lanzada | x = %.1f m | detonador = %.1f m | caida = %.0f m/s | explota en %.1f s | proxima carga de este barco en %.2f s%n",
				x, profundidadDetonacion, velocidadCaida, profundidadDetonacion / velocidadCaida,
				tiempoParaProximaCarga);
		return new Carga(x, velocidadCaida, profundidadDetonacion);
	}

	private double randomEntre(double minimo, double maximo) {
		return minimo + random.nextDouble() * (maximo - minimo);
	}

}