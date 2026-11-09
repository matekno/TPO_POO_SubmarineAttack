package model;

import java.util.Random;

public class BarcoFactory {
	private static final double VELOCIDAD_BARCOS = 150; // metros por segundo
	private static final int MAXIMO_PASADAS = 3;
	private static final double VELOCIDAD_CAIDA = 100; // metros por segundo

	private final Random random;
	private final double anchoMundo;

	public BarcoFactory(Random random, double anchoMundo) {
		this.random = random;
		this.anchoMundo = anchoMundo;
	}

	public Barco crearBarco() {
		boolean desdeIzquierda = random.nextBoolean();
		int pasadas = 1 + random.nextInt(MAXIMO_PASADAS);
		// System.out.println("Sale un barco nuevo. Quedan por salir: " +
		// barcosPorSalir);
		return new Barco(anchoMundo, desdeIzquierda, VELOCIDAD_BARCOS, pasadas, VELOCIDAD_CAIDA, random);
	}

}
