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

    public Juego() {
        submarino = new Submarino(ANCHO_MUNDO);
        nivel = new Nivel(new BarcoFactory(new Random(), ANCHO_MUNDO));
        barcos = new ArrayList<>();
    }

    public void avanzar(double segundos) {
        submarino.avanzar(segundos);
        moverBarcos(segundos);

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
            if (barco.terminoSusPasadas()) {
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
}