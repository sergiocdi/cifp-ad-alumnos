package es.cifpaviles.dam2.ad.ficheros.io;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * UT01 - Manejo de Ficheros
 * Demostración de operaciones básicas con java.io.File:
 * - Creación y comprobación de rutas.
 * - Inspección de metadatos (tamaño, permisos, última modificación).
 * - Creación de carpetas y listado de directorios.
 */
public class FileBasicsDemo {

    public static void main(String[] args) {
        System.out.println("=== DEMO: Operaciones Básicas con java.io.File ===\n");

        // 1. Definir directorio de trabajo temporal para la prueba
        File tempDir = new File("output/temp_io");
        if (!tempDir.exists()) {
            boolean creado = tempDir.mkdirs();
            System.out.println("[+] Directorio creado: " + tempDir.getAbsolutePath() + " (" + creado + ")");
        }

        // 2. Crear un archivo nuevo en disco
        File fichero = new File(tempDir, "notas_dam2.txt");
        try {
            if (fichero.createNewFile()) {
                System.out.println("[+] Fichero creado con éxito: " + fichero.getName());
            } else {
                System.out.println("[i] El fichero ya existía en disco: " + fichero.getName());
            }

            // 3. Inspección de metadatos
            System.out.println("\n--- Metadatos del Fichero ---");
            System.out.println("Ruta absoluta:        " + fichero.getAbsolutePath());
            System.out.println("¿Existe?:             " + fichero.exists());
            System.out.println("¿Es un fichero?:      " + fichero.isFile());
            System.out.println("¿Es un directorio?:   " + fichero.isDirectory());
            System.out.println("Tamaño en bytes:      " + fichero.length() + " B");
            System.out.println("¿Permiso de lectura?: " + fichero.canRead());
            System.out.println("¿Permiso de escritura?:" + fichero.canWrite());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("Última modificación:  " + sdf.format(new Date(fichero.lastModified())));

            // 4. Listado de elementos dentro de una carpeta
            System.out.println("\n--- Listado de elementos en " + tempDir.getName() + " ---");
            String[] lista = tempDir.list();
            if (lista != null) {
                for (String elemento : lista) {
                    System.out.println(" -> " + elemento);
                }
            }

        } catch (IOException e) {
            System.err.println("[!] Error de E/S: " + e.getMessage());
        }
    }
}
