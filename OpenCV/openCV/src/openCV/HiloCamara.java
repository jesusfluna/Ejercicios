package openCV;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class HiloCamara extends Thread{
	JLabel imagen;
	JCheckBox rostro;
	Mat frames;
    VideoCapture camera;
    boolean salir = false;
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	
    //Constructor principal
	public HiloCamara(JLabel imagen,JCheckBox rostro) {
		this.imagen = imagen;
		this.rostro = rostro;
		frames = new Mat();
	    camera = new VideoCapture(0);
	}
	
	//Ejecucion del hilo
	@Override
	public void run() {
		DetectorRostros detector = new DetectorRostros();
		while (!salir) {
	        if (camera.read(frames)) {
	        	ImageIcon image;
	        	
	        	if(rostro.isSelected()) {
		        	image = new ImageIcon(toBufferedImage(detector.detecta(frames)));
	        	}else {
		            image = new ImageIcon(toBufferedImage(frames));
	        	}

	            imagen.setIcon(image);
	            imagen.repaint();
	        }
	    }
	}
	
	/*
	//Ejecucion del hilo (Version anterior a la modificacion de deteccion de rostro)
	@Override
	public void run() {
		while (!salir) {
	        if (camera.read(frames)) {
	            ImageIcon image = new ImageIcon(toBufferedImage(frames));
	            imagen.setIcon(image);
	            imagen.repaint();
	        }
	    }
	}*/

	//Detencion del hilo
	public void parar() {
		salir = true;
		camera.release();
	}
	
	//Metodo de conversion del frame obtenido en una imagen estandar para su visualizacion
	private static  BufferedImage toBufferedImage(Mat videoMatImage) {
        int type = videoMatImage.channels() == 1 ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;
        int bufferSize = videoMatImage.channels() * videoMatImage.cols() * videoMatImage.rows();
        byte[] buffer = new byte[bufferSize];
        videoMatImage.get(0, 0, buffer);
        BufferedImage image = new BufferedImage(videoMatImage.cols(), videoMatImage.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);
        return image;
    }
}
