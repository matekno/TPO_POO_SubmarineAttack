package controller;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import dto.ElementoDTO;
import model.Direccion;
import model.Juego;
import model.Submarino;

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
        Submarino submarino = juego.getSubmarino();
        return new ElementoDTO(submarino.getX(), submarino.getY(), submarino.getAncho(), submarino.getAlto());
    }
}