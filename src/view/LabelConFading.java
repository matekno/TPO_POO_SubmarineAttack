package view;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.Timer;

// Un JLabel que sabe hacer un fading: baja su opacidad hasta un minimo y vuelve a subirla.
// La idea es que el submarino cuando es golpeado, este fading. 
// Esta clase fue hecha por la IA. Despues ver de simplificar.
// TODO

public class LabelConFading extends JLabel {
	private static final long serialVersionUID = 1L;
	private static final int PASOS = 10; // pasos para bajar (y otros tantos para subir)
	private static final float OPACIDAD_MINIMA = 0.2f;

	private float opacidad = 1f;
	private int paso;
	private final Timer timerFading = new Timer(50, e -> avanzarFading());

	public void hacerFading() {
		paso = 0;
		timerFading.restart();
	}

	private void avanzarFading() {
		paso++;
		float cambioPorPaso = (1 - OPACIDAD_MINIMA) / PASOS;
		if (paso <= PASOS) {
			opacidad = 1 - cambioPorPaso * paso;
		} else {
			opacidad = OPACIDAD_MINIMA + cambioPorPaso * (paso - PASOS);
		}
		if (paso >= 2 * PASOS) {
			opacidad = 1;
			timerFading.stop();
		}
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// Se dibuja en una copia de g con la opacidad actual
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacidad));
		super.paintComponent(g2);
		g2.dispose();
	}
}