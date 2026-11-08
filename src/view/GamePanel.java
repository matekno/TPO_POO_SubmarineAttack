package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.JuegoController;
import dto.ElementoDTO;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final double ESCALA = 0.8; // píxeles por metro
    private static final int SUPERFICIE = 40; // píxeles de cielo arriba del agua


	private JLabel lblSubmarino;
	private JuegoController controller;
	private Timer timer;
	private long ultimoTick;

	private JLabel lblBarco1;
	private JLabel lblBarco2;
	private JLabel lblBarco3;
	private JLabel[] lblBarcos;

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
		setPreferredSize(new Dimension(800, 720));

		setBackground(new Color(0, 51, 102));
		setLayout(null);

		lblSubmarino = new JLabel("");
		lblSubmarino.setIcon(new ImageIcon(GamePanel.class.getResource("/img/submarino.png")));
		lblSubmarino.setBounds(300, 468, 80, 24);
		add(lblSubmarino);

		lblBarco1 = new JLabel("");
		lblBarco1.setVisible(false);
		lblBarco1.setIcon(new ImageIcon(GamePanel.class.getResource("/img/barco.png")));
		lblBarco1.setBounds(0, 24, 96, 32);
		add(lblBarco1);

		lblBarco2 = new JLabel("");
		lblBarco2.setVisible(false);
		lblBarco2.setIcon(new ImageIcon(GamePanel.class.getResource("/img/barco.png")));
		lblBarco2.setBounds(0, 0, 96, 32);
		add(lblBarco2);

		lblBarco3 = new JLabel("");
		lblBarco3.setVisible(false);
		lblBarco3.setIcon(new ImageIcon(GamePanel.class.getResource("/img/barco.png")));
		lblBarco3.setBounds(0, 0, 96, 32);
		add(lblBarco3);

		JPanel panelCielo = new JPanel();
		panelCielo.setBackground(new Color(135, 206, 235));
		panelCielo.setBounds(0, 0, 800, 40);
		add(panelCielo);

	}

	public void iniciar(JuegoController controller) {
		this.controller = controller;
		lblBarcos = new JLabel[] { lblBarco1, lblBarco2, lblBarco3 };
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
		actualizarBarcos(controller.getBarcos());

	}

	private void ubicar(JLabel label, ElementoDTO elemento) {
		int x = (int) ((elemento.getX() - elemento.getAncho() / 2) * ESCALA);
		int y = (int) ((elemento.getY() - elemento.getAlto() / 2) * ESCALA) + SUPERFICIE;;
		label.setLocation(x, y);
	}

	private void actualizarBarcos(List<ElementoDTO> barcos) {
		for (int i = 0; i < lblBarcos.length; i++) {
			if (i < barcos.size()) {
				ubicar(lblBarcos[i], barcos.get(i));
				lblBarcos[i].setVisible(true);
			} else {
				lblBarcos[i].setVisible(false);
			}
		}
	}
}
