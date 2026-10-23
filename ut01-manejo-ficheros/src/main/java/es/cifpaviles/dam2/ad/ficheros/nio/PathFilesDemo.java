package es.cifpaviles.dam2.ad.ficheros.nio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

/**
 * UT01 - Manejo de Ficheros
 * Demostración de la API moderna Java NIO.2 (java.nio.file):
 * - Interfaz Path y clase de utilidad estática Files.
 * - Operaciones atómicas de lectura/escritura en una sola línea.
 * - Copia con StandardCopyOption.REPLACE_EXISTING.
 * - Recorrido recursivo de árboles de directorios con Files.walk().
 */
public class PathFilesDemo {

    public static void main(String[] args) {
        System.out.println("=== DEMO: Java NIO.2 (Path y Files) ===\n");

        Path dirNio = Paths.get("output", "temp_nio");
        Path archivo = dirNio.resolve("servidores_cloud.csv");
        Path archivoBackup = dirNio.resolve("servidores_cloud_backup.csv");

        try {
            // 1. Crear directorios intermedios si no existen
            if (!Files.exists(dirNio)) {
                Files.createDirectories(dirNio);
                System.out.println("[+] Directorio creado con Files: " + dirNio.toAbsolutePath());
            }

            // 2. Escritura atómica de texto con Files.writeString()
            String contenidoInicial = "ID;HOST;IP;ESTADO;CPU_CORES\n" +
                                      "SRV-01;web-frontend;192.168.1.10;ONLINE;4\n" +
                                      "SRV-02;database-primary;192.168.1.20;ONLINE;16\n" +
                                      "SRV-03;redis-cache;192.168.1.30;MAINTENANCE;2\n";

            Files.writeString(archivo, contenidoInicial, StandardCharsets.UTF_8, 
                             StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("[+] Archivo creado y escrito con Files.writeString(): " + archivo.getFileName());

            // 3. Añadir líneas adicionales con Files.write()
            List<String> lineasExtra = List.of(
                "SRV-04;api-gateway;192.168.1.40;ONLINE;8",
                "SRV-05;backup-server;192.168.1.50;OFFLINE;4"
            );
            Files.write(archivo, lineasExtra, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            System.out.println("[+] " + lineasExtra.size() + " líneas añadidas con StandardOpenOption.APPEND");

            // 4. Copia atómica con reemplazo
            Files.copy(archivo, archivoBackup, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("[+] Copia de seguridad generada: " + archivoBackup.getFileName());

            // 5. Lectura completa con Files.readAllLines()
            System.out.println("\n--- Contenido leído con Files.readAllLines() ---");
            List<String> todasLasLineas = Files.readAllLines(archivo, StandardCharsets.UTF_8);
            todasLasLineas.forEach(l -> System.out.println("  " + l));

            // 6. Recorrido del directorio con Files.walk() y Streams
            System.out.println("\n--- Explorando árbol de ficheros con Files.walk() ---");
            try (Stream<Path> stream = Files.walk(Paths.get("output"))) {
                stream.forEach(p -> System.out.printf("  [%s] %s (%d bytes)%n",
                        Files.isDirectory(p) ? "DIR " : "FILE",
                        p.toString(),
                        Files.isDirectory(p) ? 0 : p.toFile().length()));
            }

        } catch (IOException e) {
            System.err.println("[!] Error en operación NIO.2: " + e.getMessage());
        }
    }
}
