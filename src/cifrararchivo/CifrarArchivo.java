/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cifrararchivo;

import   javax.crypto.*;
import   javax.crypto.spec.*;

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author Magali
 */
public class CifrarArchivo {

    public static Scanner lectura = new Scanner(System.in);
	public static void main(String[] args) {
		
            System.out.println("Introduce la ruta del fichero: ");
            String fichero = lectura.next();
            System.out.println("Introduce la clave: ");
            String clave = lectura.next();
            encriptar(fichero, clave);
            
	}

	
	public static void procesar(String key, int mode, InputStream is, OutputStream os) throws Throwable {
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = skf.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		if (mode == Cipher.ENCRYPT_MODE) {
			cipher.init(Cipher.ENCRYPT_MODE, desKey);
			CipherInputStream cis = new CipherInputStream(is, cipher);
			copiar(cis, os);
		} else if (mode == Cipher.DECRYPT_MODE) {
			cipher.init(Cipher.DECRYPT_MODE, desKey);
			CipherOutputStream cos = new CipherOutputStream(os, cipher);
			copiar(is, cos);
		}
	}

	public static void copiar(InputStream is, OutputStream os) throws IOException {
		byte[] bytes = new byte[64];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			os.write(bytes, 0, numBytes);
		}
		os.flush();
		os.close();
		is.close();
	}
	
	public static void encriptar(String fichero, String clave) {
		String key = clave;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(fichero);
			fos = new FileOutputStream(fichero);
			procesar(key, Cipher.ENCRYPT_MODE, fis, fos);
		} catch (Throwable e) {
                    
		} finally {
			try { fis.close(); } catch (Exception e) { }
			try { fos.close(); } catch (Exception e) { }
		}
	}
}