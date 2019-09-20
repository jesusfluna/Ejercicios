package openCV;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class WebCam {
	private JFrame frmWebcam;	//Ventana principal
	JLabel imagen;				//Label de visualizacion del video
	HiloCamara webCam;			//Hilo de captura por webcam

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WebCam window = new WebCam();
					window.frmWebcam.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WebCam() {
		initialize();
	}

	private void initialize() {
		//Inicializacion de los contenedores de la aplicacion
		frmWebcam = new JFrame();
		frmWebcam.setTitle("WebCam");
		frmWebcam.setBounds(100, 100, 511, 475);
		frmWebcam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWebcam.getContentPane().setLayout(null);
		
		//JLabel que cargara las imagenes obtenidas por la webcam
		imagen = new JLabel("");
		imagen.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		imagen.setBounds(10, 11, 474, 361);
		frmWebcam.getContentPane().add(imagen);
		frmWebcam.setVisible(true);
		
		//Jcheckbox para activar o no la deteccion de rostros en la webcam del hilo
		JCheckBox rostro = new JCheckBox("Rostros");
		rostro.setBounds(10, 379, 75, 23);
		frmWebcam.getContentPane().add(rostro);
		
		//Boton de activacion del hilo de la webcam principal
		JButton btnNewButton = new JButton("Activar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				webCam = new HiloCamara(imagen,rostro);
				webCam.start();
			}
		});
		btnNewButton.setBounds(91, 383, 151, 42);
		frmWebcam.getContentPane().add(btnNewButton);
		
		//Boton de desactivacion del hilo de la webcam principal
		JButton btnDetener = new JButton("Detener");
		btnDetener.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(webCam!=null) {
					webCam.parar();
				}
			}
		});
		btnDetener.setBounds(252, 383, 151, 42);
		frmWebcam.getContentPane().add(btnDetener);
	}
}
