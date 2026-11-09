package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.JuegoController;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private GamePanel gamePanel;
	private HUDPanel hudPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.pack(); // Ajusta al tamano adecuado. Esto cuando lo haga responsive vuela.
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					frame.iniciarJuego(new JuegoController());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setResizable(false);
		setTitle("Submarine Attack");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		gamePanel = new GamePanel();
		contentPane.add(gamePanel, BorderLayout.CENTER);
		
		hudPanel = new HUDPanel();
		contentPane.add(hudPanel, BorderLayout.NORTH);

	}

	public void iniciarJuego(JuegoController controller) {
		controller.agregarJuegoListener(hudPanel);
		controller.agregarJuegoListener(gamePanel);
		gamePanel.iniciar(controller);

	}

}
