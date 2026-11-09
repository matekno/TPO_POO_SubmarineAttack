package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Juego {
	public static final double ANCHO_MUNDO = 1000;

	private final Submarino submarino;
	private final Nivel nivel;
	private final List<Barco> barcos;
	private final List<Carga> cargas;

	public Juego() {
		submarino = new Submarino(ANCHO_MUNDO);
		nivel = new Nivel(new BarcoFactory(new Random(), ANCHO_MUNDO));
		barcos = new ArrayList<>();
		cargas = new ArrayList<>();

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
			
			// Si la carga 
			if (carga.estaExplotada() == false && (carga.llegoAlDetonador() == true || carga.seSuperponeCon(submarino) == true)) {
				if (carga.llegoAlDetonador()) {
					System.out.printf("  Explota por DETONADOR | x = %.1f m | y = %.1f m%n", carga.getX(),
							carga.getY());
				} else {
					System.out.printf("  Explota por CONTACTO  | x = %.1f m | y = %.1f m%n", carga.getX(),
							carga.getY());
				}
				carga.explotar();
				// En la etapa 4, aca se calculan el dano y los puntos, o en realidad en DamageManager.
			}
			if (carga.terminoLaExplosion()) {
				iterador.remove();
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