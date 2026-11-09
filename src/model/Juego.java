package model;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import event.IJuegoListener;

public class Juego {
	public static final double ANCHO_MUNDO = 1000;
	private static final int PUNTOS_POR_VIDA_EXTRA = 500;

	private final Submarino submarino;
	private final Nivel nivel;
	private final List<Barco> barcos;
	private final List<Carga> cargas;

	private final List<IJuegoListener> listeners;
	private int puntos;
	private int proximaVidaExtra;

	public Juego() {
		submarino = new Submarino(ANCHO_MUNDO);
		nivel = new Nivel(new BarcoFactory(new Random(), ANCHO_MUNDO));
		barcos = new ArrayList<>();
		cargas = new ArrayList<>();

		listeners = new ArrayList<>();
		puntos = 0;
		proximaVidaExtra = PUNTOS_POR_VIDA_EXTRA;
	}

	public void agregarListener(IJuegoListener listener) {
		listeners.add(listener);
	}

	public void avanzar(double segundos) {
		submarino.avanzar(segundos);
		moverBarcos(segundos);
		moverCargas(segundos);

		nivel.avanzar(segundos);
		if (nivel.puedeSalirBarco(barcos.size())) {
			barcos.add(nivel.crearBarco());
		}
	}

	private void moverBarcos(double segundos) {
		Iterator<Barco> iterador = barcos.iterator();
		while (iterador.hasNext()) {
			Barco barco = iterador.next();
			barco.avanzar(segundos);
			if (barco.puedeLanzarCarga()) {
				cargas.add(barco.lanzarCarga());
			}
			if (barco.terminoSusPasadas()) {
				iterador.remove();
			}
		}
	}

	private void moverCargas(double segundos) { // La carga se mueve en cada tick.
		Iterator<Carga> iterador = cargas.iterator(); // Ojo con el iterator.
		while (iterador.hasNext()) {
			Carga carga = iterador.next();
			carga.avanzar(segundos);

			// Si la carga ya llego a su posicion de detonacion o el submarino se choca, 
			if (carga.estaExplotada() == false
					&& (carga.llegoAlDetonador() == true || carga.seSuperponeCon(submarino) == true)) {
				if (carga.llegoAlDetonador()) {
					System.out.printf("  Explota por DETONADOR | x = %.1f m | y = %.1f m%n", carga.getX(),
							carga.getY());
				} else {
					System.out.printf("  Explota por CONTACTO  | x = %.1f m | y = %.1f m%n", carga.getX(),
							carga.getY());
				}
				carga.explotar();
				procesarExplosion(carga);
			}
			if (carga.terminoLaExplosion()) {
				iterador.remove();
			}
		}
	}
	
	private void procesarExplosion(Carga carga) {
		RangoImpacto rango = submarino.recibirExplosion(carga.getX(), carga.getY());
		if (rango.haceDano()) {
			for (IJuegoListener listener : listeners) {
				// Notifica a los listeners que el submarino fue danado
				listener.submarinoDaniado(submarino.getVida(), submarino.getVidas());
			}
		}
		sumarPuntos(rango.getPuntos());
	}

	private void sumarPuntos(int cantidad) {
		puntos += cantidad;
		for (IJuegoListener listener : listeners) {
			listener.puntosCambiaron(puntos);
		}
		
		// while y no if porque si de una vez se cruzan dos umbrales, se dan las dos vidas.
		while (puntos >= proximaVidaExtra) {
			submarino.ganarVida();
			proximaVidaExtra += PUNTOS_POR_VIDA_EXTRA;
			for (IJuegoListener listener : listeners) {
				listener.vidaExtraGanada(submarino.getVidas());
			}
		}
	}
	
	public Submarino getSubmarino() {
		return submarino;
	}

	public List<Barco> getBarcos() {
		return Collections.unmodifiableList(barcos);
	}

	public List<Carga> getCargas() {
		return Collections.unmodifiableList(cargas);
	}
}