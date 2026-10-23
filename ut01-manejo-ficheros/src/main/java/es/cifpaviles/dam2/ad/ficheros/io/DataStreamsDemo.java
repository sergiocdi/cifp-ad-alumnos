package es.cifpaviles.dam2.ad.ficheros.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * UT01 - Manejo de Ficheros
 * Demostración de flujos de datos tipados (DataInputStream / DataOutputStream):
 * - Escritura y lectura de tipos primitivos (int, double, boolean, UTF).
 * - Importancia crítica del orden de lectura (FIFO estricto).
 * - Manejo de EOFException como finalización normal de fichero.
 */
public class DataStreamsDemo {

    public static void main(String[] args) {
        System.out.println("=== DEMO: Datos Tipados (DataOutputStream / DataInputStream) ===\n");

        File carpeta = new File("output/temp_io");
        carpeta.mkdirs();

        File archivoBin = new File(carpeta, "sensores_iot.bin");

        // 1. Escribir registros binarios con tipos primitivos
        try (DataOutputStream dos = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(archivoBin)))) {

            // Registro 1: ID sensor (int), Nombre (UTF), Temperatura (double), Activo (boolean)
            dos.writeInt(1001);
            dos.writeUTF("Sensor-Nave-A");
            dos.writeDouble(21.45);
            dos.writeBoolean(true);

            // Registro 2
            dos.writeInt(1002);
            dos.writeUTF("Sensor-Camara-Frio");
            dos.writeDouble(-4.20);
            dos.writeBoolean(true);

            // Registro 3
            dos.writeInt(1003);
            dos.writeUTF("Sensor-Caldera");
            dos.writeDouble(68.90);
            dos.writeBoolean(false);

            System.out.println("[+] 3 registros binarios escritos en: " + archivoBin.getName() + 
                               " (" + archivoBin.length() + " bytes)");

        } catch (IOException e) {
            System.err.println("[!] Error al escribir binario: " + e.getMessage());
            return;
        }

        // 2. Leer registros en EL MISMO ORDEN EXACTO
        System.out.println("\n--- Recuperando registros tipados ---");
        try (DataInputStream dis = new DataInputStream(
                new BufferedInputStream(new FileInputStream(archivoBin)))) {

            while (true) {
                int id = dis.readInt();
                String nombre = dis.readUTF();
                double temp = dis.readDouble();
                boolean activo = dis.readBoolean();

                System.out.printf("  [ID: %4d] %-20s | Temp: %6.2f ºC | Activo: %s%n",
                        id, nombre, temp, (activo ? "SÍ" : "NO"));
            }

        } catch (EOFException e) {
            System.out.println("[+] Fin de fichero alcanzado de forma controlada (EOFException).");
        } catch (IOException e) {
            System.err.println("[!] Error de lectura: " + e.getMessage());
        }
    }
}
