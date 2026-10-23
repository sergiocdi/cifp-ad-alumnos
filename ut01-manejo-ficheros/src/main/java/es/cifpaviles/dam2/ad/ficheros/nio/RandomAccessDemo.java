package es.cifpaviles.dam2.ad.ficheros.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * UT01 - Manejo de Ficheros
 * Demostración de Acceso Aleatorio (RandomAccessFile):
 * - Posicionamiento de puntero con seek() y getFilePointer().
 * - Modificación de registros 'in-situ' sin reescribir el archivo entero.
 * - Estructura de registro de longitud fija (ID: 4 bytes, Saldo: 8 bytes = 12 bytes/reg).
 */
public class RandomAccessDemo {

    // Tamaño de cada registro fijo en bytes: int (4) + double (8) = 12 bytes
    private static final int RECORD_SIZE = Integer.BYTES + Double.BYTES;

    public static void main(String[] args) {
        System.out.println("=== DEMO: Acceso Aleatorio (RandomAccessFile) ===\n");

        File carpeta = new File("output/temp_io");
        carpeta.mkdirs();
        File archivoCuentas = new File(carpeta, "cuentas_bancarias.raf");

        // Modo "rw" -> lectura y escritura
        try (RandomAccessFile raf = new RandomAccessFile(archivoCuentas, "rw")) {

            // 1. Escribir 5 cuentas iniciales (ID cuenta, saldo)
            raf.setLength(0); // Vaciar fichero si existía
            int[] ids = { 101, 102, 103, 104, 105 };
            double[] saldos = { 1500.0, 3200.5, 450.0, 8900.25, 230.0 };

            for (int i = 0; i < ids.length; i++) {
                raf.writeInt(ids[i]);
                raf.writeDouble(saldos[i]);
            }
            System.out.println("[+] 5 cuentas escritas. Tamaño del fichero: " + raf.length() + " bytes.");

            // 2. Acceso directo: Leer la cuenta número 3 (índice 2) directamente sin leer 1 ni 2
            int indiceDeseado = 2; // Tercera cuenta (ID 103)
            long posicion = (long) indiceDeseado * RECORD_SIZE;
            raf.seek(posicion);

            System.out.println("\n--- Acceso directo a la posición: " + posicion + " bytes ---");
            int idLeido = raf.readInt();
            double saldoLeido = raf.readDouble();
            System.out.printf("  Cuenta encontrada: [ID: %d] Saldo actual: %.2f EUR%n", idLeido, saldoLeido);

            // 3. Modificación 'in-situ': Actualizar el saldo sumándole un ingreso de 500 EUR
            double nuevoSaldo = saldoLeido + 500.0;
            // Retrocedemos el puntero exactamente los 8 bytes del double para sobreescribir solo el saldo
            raf.seek(posicion + Integer.BYTES);
            raf.writeDouble(nuevoSaldo);
            System.out.printf("[+] Saldo actualizado in-situ: %.2f EUR (sin reescribir el resto del fichero)%n", nuevoSaldo);

            // 4. Verificación: Listar todas las cuentas saltando con seek()
            System.out.println("\n--- Listado completo final de cuentas ---");
            long totalRegistros = raf.length() / RECORD_SIZE;
            for (int i = 0; i < totalRegistros; i++) {
                raf.seek((long) i * RECORD_SIZE);
                System.out.printf("  [Registro %d en offset %3d B] ID: %d | Saldo: %8.2f EUR%n",
                        i, raf.getFilePointer(), raf.readInt(), raf.readDouble());
            }

        } catch (IOException e) {
            System.err.println("[!] Error en RandomAccessFile: " + e.getMessage());
        }
    }
}
