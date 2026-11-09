package event;

// La implementan las clases que quieren enterarse de lo que pasa en el juego.
// Los metodos son default (vacios) para que cada una implemente solo los que le interesan.

// En Juego se van a disparar esos eventos. 
// HUDPanel y GamePanel implementan esta interfaz y son las que tienen que hacer acciones segun evento.

public interface IJuegoListener {
	default void submarinoDaniado(int vida, int vidas) {
	}

	default void vidaExtraGanada(int vidas) {
	}

	default void puntosCambiaron(int puntos) {
	}
}