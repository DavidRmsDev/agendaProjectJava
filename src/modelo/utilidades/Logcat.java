package modelo.utilidades;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logcat {

    public Logcat(String mensaje) {
        capturar(mensaje);
    }

    // Metodo para las capturas de excepciones en un fichero de texto
    public void capturar(String cadena) {
        File f = new File("Logcat.txt");
        BufferedWriter bw = null;
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                capturar("Logcat.capturar()" + "\n" + e.toString() + "\n" + e.getStackTrace());
            }
        }
        try {
            bw = new BufferedWriter(new FileWriter(f, true));
            bw.write(cadena);
            bw.newLine();
            bw.newLine();
        } catch (IOException e) {
            capturar("Logcat.capturar()" + "\n" + e.toString() + "\n" + e.getStackTrace());
        } finally {
            try {
                bw.close();
            } catch (IOException e2) {

                capturar("Logcat.capturar()" + "\n" + e2.toString() + "\n" + e2.getStackTrace());
            }
        }
    }
}
