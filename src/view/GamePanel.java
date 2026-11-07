package view;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import controller.JuegoController;
import dto.ElementoDTO;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final double ESCALA = 0.8; // píxeles por metro

	private JLabel lblSubmarino;
	private JuegoController controller;
	private Timer timer;
	private long ultimoTick;

	/**
	 * Create the panel.
	 */
	public GamePanel() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				controller.teclaPresionada(e.getKeyCode());
			}

			@Override
			public void keyReleased(KeyEvent e) {
				controller.teclaSoltada(e.getKeyCode());

			}
		});
		setFocusable(true);
		setPreferredSize(new Dimension(800, 680));

		setBackground(new Color(0, 51, 102));
		setLayout(null);

		lblSubmarino = new JLabel("");
		lblSubmarino.setIcon(new ImageIcon(GamePanel.class.getResource("/img/submarino.png")));
		lblSubmarino.setBounds(300, 428, 80, 24);
		add(lblSubmarino);

	}

	public void iniciar(JuegoController controller) {
		this.controller = controller;
		ultimoTick = System.nanoTime();
		timer = new Timer(16, e -> actualizar());
		timer.start();
		requestFocusInWindow();
	}

	private void actualizar() {
		long ahora = System.nanoTime();
		double segundos = (ahora - ultimoTick) / 1_000_000_000.0;
		ultimoTick = ahora;

		controller.avanzar(segundos);
		ubicar(lblSubmarino, controller.getSubmarino());
	}

	private void ubicar(JLabel label, ElementoDTO elemento) {
		int x = (int) ((elemento.getX() - elemento.getAncho() / 2) * ESCALA);
		int y = (int) ((elemento.getY() - elemento.getAlto() / 2) * ESCALA);
		label.setLocation(x, y);
	}

}
