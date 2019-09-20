package clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {
	private PrivateKey privada = null;
	private PublicKey publica = null;
	
	
	/**Establece un key privada*/
	public void setPrivateKey(String key) {
		byte[] encodedPrivateKey = stringToBytes(key);
		
		try {
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
			PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
			this.privada = privateKey;
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}
	
	/**Establece una key publica*/
	public void setPublicKey(String key) {
		byte[] encodedPublicKey = stringToBytes(key);
		
		try {
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec publicKeySpec = new PKCS8EncodedKeySpec(encodedPublicKey);
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
			this.publica = publicKey;
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			System.out.println("¡¡Cuidad!!, no cumple el estandar X509EncodedKeySpec");
			//e.printStackTrace();
		}
	}
	
	/**Valor de la clave privada*/
	public String getPrivateKey(){
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(this.privada.getEncoded());
	    return bytesToString(pkcs8EncodedKeySpec.getEncoded());
	}

	/**Valor de la clave publica*/
	public String getPublicKey(){
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(this.publica.getEncoded());
		return bytesToString(x509EncodedKeySpec.getEncoded());
	}
	
	/**Genera un par de claves privada-publica*/
	public void genKeyPair(int size){
			try {
				
				KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
				kpg.initialize(size);
			    KeyPair kp = kpg.genKeyPair();
			    
			    PublicKey publicKey = kp.getPublic();
			    PrivateKey privateKey = kp.getPrivate();
			    
			    this.privada = privateKey;
			    this.publica = publicKey;
				
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
	
	/**Encriptacion del texto*/
	public String Encrypt(String plain) {
	    byte[] encryptedBytes; 

		try {
			
			Cipher cipher = Cipher.getInstance("RSA");
		    cipher.init(Cipher.ENCRYPT_MODE, this.publica);
		    encryptedBytes = cipher.doFinal(plain.getBytes());
		    return bytesToString(encryptedBytes);
		    
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**Desencriptacion del texto*/
	public String Decrypt(String result){
	    byte[] decryptedBytes;

	    try {
		    Cipher cipher = Cipher.getInstance("RSA");
		    cipher.init(Cipher.DECRYPT_MODE, this.privada);
			decryptedBytes = cipher.doFinal(stringToBytes(result));
		    return new String(decryptedBytes);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	    return null;
	}
	
	/**Guardar contraseña privada en disco*/
	public void saveToDiskPrivateKey(String path) {
		try {
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
			out.write(this.getPrivateKey());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**Guardar contraseña publica en disco*/
	public void saveToDiskPublicKey(String path) {
		try {
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
			out.write(this.getPublicKey());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**Leer contraseña privada desde el disco*/
	public void openFromDiskPrivateKey(String path){
		String content = this.readFileAsString(path);
		this.setPrivateKey(content);
	}
	
	/**Leer contraseña publica desde el disco*/
	public void openFromDiskPublicKey(String path){
		String content = this.readFileAsString(path);
		this.setPublicKey(content);
	}
	
	
	/*Metodos privados de uso no especifico de la logica RSA, usados internamente para el funcionamiento de la clase*/

	private String readFileAsString(String filePath) {
        StringBuffer fileData = new StringBuffer();

        try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			char[] buf = new char[1024];
		       int numRead=0;
		        
		       while((numRead=reader.read(buf)) != -1){
		           String readData = String.valueOf(buf, 0, numRead);
		           fileData.append(readData);
		       }
		        
		       reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
       
        return fileData.toString();
    }
	
	
	private String bytesToString(byte[] b) {
	    byte[] b2 = new byte[b.length + 1];
	    b2[0] = 1;
	    System.arraycopy(b, 0, b2, 1, b.length);
	    return new BigInteger(b2).toString(36);
	}

	private byte[] stringToBytes(String s) {
	    byte[] b2 = new BigInteger(s, 36).toByteArray();
	    return Arrays.copyOfRange(b2, 1, b2.length);
	}
}
