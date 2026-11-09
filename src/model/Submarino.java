package model;

import java.util.EnumSet;
import java.util.Set;

public class Submarino extends ElementoJuego {
	public static final double PROFUNDIDAD_MINIMA = 300;
	public static final double PROFUNDIDAD_MAXIMA = 800;
	private static final double ANCHO = 100;
	private static final double ALTO = 30;
	private static final double VELOCIDAD = 200; // metros por segundo

	private final double anchoMundo;

	// La idea para el mecanismo de movimiento del submarino es la siguiente: Se
	// tienen los 4 puntos a donde puede moverse en un enum (Direccion).
	// Se guarda en un EnumSet los movimientos ACTUALES del usuario que esta
	// haciendo con las flechitas. Esto signifca que en el EnumSet siempre hay entre
	// 0 a 4 elementos. 
	// Quien agrega y saca es empezarAMover() y dejarDeMover().

	// Cada tick, se llama al metodo avanzar() que simplemente revisa el EnumSet y mueve al submarino
	// en esa direccion (o esas direcciones), y NO VA A PARAR hasta que se suelte la
	// tecla, y se marque esa/s direccion/es como que no esta "prendida, es decir,
	// que se vayan del EnumSet, y avanzar() deje de tomarla como accion que esta pasando.
	
	// El metodo avanzar simplemente avanza el tiempo y ejecuta ese movimiento.
	// Esto permite por un lado que el submarino no se mueve a los tirones,
	// especialmente cuando se mueve en diagonal, cosa que este diseno permite
	// perfetamente.
	// Tambien hace que anden bien las colisiones para que todo se mueva en el mismo
	// tick.
	// Y tambien que hacer una pausa del juego sea simplemente no llamar a
	// avanzar().
	// Este diseno fue hecho con ayuda de la IA

	private final Set<Direccion> direcciones = EnumSet.noneOf(Direccion.class); // EnumSet es un Set de Enum xd. Con
																				// .noneOf() lo arranca vacio.

	public Submarino(double anchoMundo) {
		super(anchoMundo / 2, (PROFUNDIDAD_MINIMA + PROFUNDIDAD_MAXIMA) / 2, ANCHO, ALTO);
		this.anchoMundo = anchoMundo;
	}

	public void empezarAMover(Direccion direccion) {
		direcciones.add(direccion);
	}

	public void dejarDeMover(Direccion direccion) {
		direcciones.remove(direccion);
	}

	@Override
	public void avanzar(double segundos) {
		int dx = 0;
		int dy = 0;
		for (Direccion direccion : direcciones) {
			dx += direccion.getDx();
			dy += direccion.getDy();
		}
		x = limitar(x + dx * VELOCIDAD * segundos, ancho / 2, anchoMundo - ancho / 2);
		y = limitar(y + dy * VELOCIDAD * segundos, PROFUNDIDAD_MINIMA, PROFUNDIDAD_MAXIMA);
	}

	private double limitar(double valor, double minimo, double maximo) {
		return Math.max(minimo, Math.min(valor, maximo));
	}
}