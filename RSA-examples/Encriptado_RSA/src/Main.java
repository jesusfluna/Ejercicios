import clases.RSA;

public class Main {

	public static void main(String[] args) {
		String mensaje = "Enhorabuena, conseguiste cifrar y descifrar con exito."; //Mensaje que vamos a crifrar y descifrar
		System.out.println("Text sin cifrar: "+mensaje);
		
		RSA rsa = new RSA(); //Creamos nuestro objeto RSA
		rsa.genKeyPair(1024); //Generamos el par de claves. Admite tamaño de 512,1024,2048 y 4096
		
		rsa.saveToDiskPrivateKey("./almacen/rsa.pri");//Guardado en disco de la clave privada
		rsa.saveToDiskPublicKey("./almacen/rsa.pub");//Guardado en disco de la clave publica
		
		mensaje = rsa.Encrypt(mensaje); //Ciframos el texto y lo guardamos en una variable
		System.out.println("Texto cifrado: "+mensaje);
		
		RSA rsa2 = new RSA();//Creamos un segundo objeto RSA para cargar las credenciales guardadas en disco
		rsa2.openFromDiskPrivateKey("./almacen/rsa.pri"); //Cargamos la clave privada guardada anteriormente en disco
		rsa2.openFromDiskPublicKey("./almacen/rsa.pub"); //Cargamos la clave publica guardada anteriormente en disco
		
		mensaje = rsa2.Decrypt(mensaje); //Desencriptamos el mensaje previamente encriptado
		System.out.println("Texto descrifrado: "+mensaje);
	}

}
