package es.cifpaviles.dam2.ad.ficheros.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * UT01 - Manejo de Ficheros
 * Demostración de flujos binarios de bajo nivel (byte streams):
 * - FileInputStream / FileOutputStream.
 * - Copia en bloque usando buffer de bytes (eficiencia en E/S).
 * - Uso estricto de try-with-resources para cierre automático.
 */
public class StreamsBinariosDemo {

    public static void main(String[] args) {
        System.out.println("=== DEMO: Flujos Binarios (FileInputStream / FileOutputStream) ===\n");

        File carpeta = new File("output/temp_io");
        carpeta.mkdirs();

        File archivoOrigen = new File(carpeta, "datos_origen.dat");
        File archivoCopia = new File(carpeta, "datos_copia.dat");

        // 1. Escribir bytes directamente con FileOutputStream
        byte[] mensaje = "CIFP Avilés · Acceso a Datos 2026-2027 · Streams Binarios OK\n".getBytes();
        try (FileOutputStream fos = new FileOutputStream(archivoOrigen)) {
            fos.write(mensaje);
            System.out.println("[+] Datos binarios escritos: " + mensaje.length + " bytes.");
        } catch (IOException e) {
            System.err.println("[!] Error al escribir origen: " + e.getMessage());
            return;
        }

        // 2. Copiar fichero byte a byte usando buffer de 1024 bytes (1 KB)
        long inicio = System.nanoTime();
        int bytesLeidosTotal = 0;

        try (FileInputStream fis = new FileInputStream(archivoOrigen);
             FileOutputStream fos = new FileOutputStream(archivoCopia)) {

            byte[] buffer = new byte[1024];
            int bytesLeidos;

            while ((bytesLeidos = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesLeidos);
                bytesLeidosTotal += bytesLeidos;
            }

            long fin = System.nanoTime();
            System.out.println("[+] Copia binaria completada con éxito:");
            System.out.println("    - Archivo copia: " + archivoCopia.getAbsolutePath());
            System.out.println("    - Total transferido: " + bytesLeidosTotal + " bytes");
            System.out.println("    - Tiempo transcurrido: " + ((fin - inicio) / 1000) + " µs");

        } catch (IOException e) {
            System.err.println("[!] Error durante la copia binaria: " + e.getMessage());
        }
    }
}
