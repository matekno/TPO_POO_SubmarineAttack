package view;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import event.IJuegoListener;

public class HUDPanel extends JPanel implements IJuegoListener {

	private static final long serialVersionUID = 1L;
	private JLabel lblPuntos;
	private JProgressBar barraVida;
	private JLabel lblVidas;

	/**
	 * Create the panel.
	 */
	public HUDPanel() {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setHgap(20);
		flowLayout.setAlignment(FlowLayout.LEFT);

		lblPuntos = new JLabel("Puntos: 0");
		add(lblPuntos);

		JLabel lblTituloVidaBarra = new JLabel("HPs:");
		add(lblTituloVidaBarra);

		barraVida = new JProgressBar();
		barraVida.setStringPainted(true);
		barraVida.setValue(100);
		add(barraVida);

		lblVidas = new JLabel("Vidas: 3");
		add(lblVidas);
	}

	@Override
	public void submarinoDaniado(int vida, int vidas) {
		barraVida.setValue(vida);
		lblVidas.setText("Vidas: " + vidas);
	}

	@Override
	public void vidaExtraGanada(int vidas) {
		lblVidas.setText("Vidas: " + vidas);
	}

	@Override
	public void puntosCambiaron(int puntos) {
		lblPuntos.setText("Puntos: " + puntos);
	}

}
