package controller;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.ElementoDTO;
import model.Barco;
import model.Direccion;
import model.ElementoJuego;
import model.Juego;

public class JuegoController {
	private final Juego juego;
	private final Map<Integer, Direccion> teclas;

	public JuegoController() {
		juego = new Juego();
		teclas = new HashMap<>();
		teclas.put(KeyEvent.VK_UP, Direccion.ARRIBA);
		teclas.put(KeyEvent.VK_DOWN, Direccion.ABAJO);
		teclas.put(KeyEvent.VK_LEFT, Direccion.IZQUIERDA);
		teclas.put(KeyEvent.VK_RIGHT, Direccion.DERECHA);
	}

	public void avanzar(double segundos) {
		juego.avanzar(segundos);
	}

	public void teclaPresionada(int codigoTecla) {
		Direccion direccion = teclas.get(codigoTecla);
		if (direccion != null) {
			juego.getSubmarino().empezarAMover(direccion);
		}
	}

	public void teclaSoltada(int codigoTecla) {
		Direccion direccion = teclas.get(codigoTecla);
		if (direccion != null) {
			juego.getSubmarino().dejarDeMover(direccion);
		}
	}

	public ElementoDTO getSubmarino() {
		return crearDTO(juego.getSubmarino());
	}

	public List<ElementoDTO> getBarcos() {
		List<ElementoDTO> barcos = new ArrayList<>();
		for (Barco barco : juego.getBarcos()) {
			barcos.add(crearDTO(barco));
		}
		return barcos;
	}

	private ElementoDTO crearDTO(ElementoJuego elemento) {
		return new ElementoDTO(elemento.getX(), elemento.getY(), elemento.getAncho(), elemento.getAlto());
	}
}