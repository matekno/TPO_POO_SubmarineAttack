package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.JuegoController;
import dto.CargaDTO;
import dto.ElementoDTO;
import event.IJuegoListener;

public class GamePanel extends JPanel implements IJuegoListener {

	private static final long serialVersionUID = 1L;
	private static final double ESCALA = 0.8; // píxeles por metro
	private static final int SUPERFICIE = 40; // píxeles de cielo arriba del agua

	private LabelConFading lblSubmarino;
	private JuegoController controller;
	private Timer timer;
	private long ultimoTick;

	private JLabel lblBarco1;
	private JLabel lblBarco2;
	private JLabel lblBarco3;
	private JLabel[] lblBarcos;

	private final ImageIcon imagenCarga = new ImageIcon(GamePanel.class.getResource("/img/carga.png"));
	private final ImageIcon imagenExplosion = new ImageIcon(GamePanel.class.getResource("/img/explosion.png"));
	private final List<JLabel> lblCargas = new ArrayList<>();

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

		lblSubmarino = new LabelConFading();
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

		JPanel panelPrimeraProfundidad = new JPanel();
		panelPrimeraProfundidad.setBackground(new Color(71, 142, 255));
		panelPrimeraProfundidad.setBounds(0, 40, 800, 228);
		add(panelPrimeraProfundidad);

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
		posicionarElemento(lblSubmarino, controller.getSubmarino());
		actualizarBarcos(controller.getBarcos());
		actualizarCargas(controller.getCargas());

	}

	private void posicionarElemento(JLabel label, ElementoDTO elemento) {
		int x = (int) (elemento.getX() * ESCALA) - label.getWidth() / 2;
		int y = (int) (elemento.getY() * ESCALA) + SUPERFICIE - label.getHeight() / 2;
		label.setLocation(x, y);
	}

	private void actualizarBarcos(List<ElementoDTO> barcos) {
		for (int i = 0; i < lblBarcos.length; i++) {
			if (i < barcos.size()) {
				posicionarElemento(lblBarcos[i], barcos.get(i));
				lblBarcos[i].setVisible(true);
			} else {
				lblBarcos[i].setVisible(false);
			}
		}
	}

	private void actualizarCargas(List<CargaDTO> cargas) {
		// Si hay mas cargas que labels, se crean los que faltan
		while (lblCargas.size() < cargas.size()) {
			JLabel label = new JLabel();
			add(label, 0); // indice 0: se dibuja por encima de los demas componentes
			lblCargas.add(label);
		}

		for (int i = 0; i < lblCargas.size(); i++) {
			JLabel label = lblCargas.get(i);
			if (i < cargas.size()) {
				CargaDTO carga = cargas.get(i);
				ImageIcon imagen;
				if (carga.isExplotada()) {
					imagen = imagenExplosion;
				} else {
					imagen = imagenCarga;
				}
				label.setIcon(imagen);
				label.setSize(imagen.getIconWidth(), imagen.getIconHeight());
				posicionarElemento(label, carga);
				label.setVisible(true);
			} else {
				label.setVisible(false);
			}
		}
	}
	
	@Override
	public void submarinoDaniado(int vida, int vidas) {
		lblSubmarino.hacerFading();
	}
}
