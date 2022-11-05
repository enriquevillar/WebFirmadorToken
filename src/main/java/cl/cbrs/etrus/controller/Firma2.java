package cl.cbrs.etrus.controller;

import java.io.File;

public class Firma2 {
	private static String PATH_IN = "c:/firma/in/";
	private static String PATH_OUT = "c:/firma/out/";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File dirIn = new File(PATH_IN);
		File[] archivos = dirIn.listFiles();
		for (int i = 0; i < archivos.length; i++) {
			try {
				File archivo = archivos[i];
				new FirmadorBO().firmarDocumento(PATH_IN + archivo.getName(), PATH_OUT + archivo.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
