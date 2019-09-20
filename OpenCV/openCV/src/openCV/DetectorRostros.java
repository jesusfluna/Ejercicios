package openCV;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

class DetectorRostros {
	private CascadeClassifier clasificador;
 
	public DetectorRostros() {
		clasificador = new CascadeClassifier("haarcascade_frontalface_alt.xml");//Ruta a la biblioteca de opencv para rostros
		if (clasificador.empty()) {
			System.out.println("Error de lectura.");
			return;
		} else {
			System.out.println("Detector de rostros leido.");
		}
	}
 
	//Funcion con la logica de la deteccion
	public Mat detecta(Mat frameDeEntrada) {
		Mat mRgba = new Mat();
		Mat mGrey = new Mat();
		MatOfRect rostros = new MatOfRect();
		frameDeEntrada.copyTo(mRgba);
		frameDeEntrada.copyTo(mGrey);
		Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(mGrey, mGrey);
		clasificador.detectMultiScale(mGrey, rostros);
		System.out.println(String.format("Detectando %s rostros", rostros.toArray().length));
		
		//Dibujado del recuadro en el rostro de la imagen
		for (Rect rect : rostros.toArray()) {
			Imgproc.rectangle(mRgba, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
		}
		return mRgba;
	}
}
