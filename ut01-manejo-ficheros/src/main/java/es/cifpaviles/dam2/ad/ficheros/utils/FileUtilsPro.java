package es.cifpaviles.dam2.ad.ficheros.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * UT01 - Manejo de Ficheros
 * Clase utilitaria profesional completa (FileUtilsPro):
 * Reúne patrones estándar de la industria para manipulación segura de ficheros:
 * - Copia atómica con creación previa de directorios padre.
 * - Conteo optimizado de líneas mediante Streams de E/S.
 * - Reemplazo de texto mediante fichero temporal atómico.
 * - Búsqueda recursiva por extensión con NIO.2.
 */
public class FileUtilsPro {

    /**
     * Copia segura de ficheros: asegura que el directorio de destino existe
     * antes de realizar la copia atómica con reemplazo.
     */
    public static void safeCopy(Path origen, Path destino) throws IOException {
        if (!Files.exists(origen)) {
            throw new IllegalArgumentException("El archivo origen no existe: " + origen);
        }

        Path carpetaDestino = destino.getParent();
        if (carpetaDestino != null && !Files.exists(carpetaDestino)) {
            Files.createDirectories(carpetaDestino);
        }

        Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
    }

    /**
     * Cuenta el número total de líneas de un fichero de texto mediante Files.lines().
     */
    public static long countLines(Path archivo) throws IOException {
        try (Stream<String> lines = Files.lines(archivo, StandardCharsets.UTF_8)) {
            return lines.count();
        }
    }

    /**
     * Reemplaza todas las apariciones de un texto en un archivo usando
     * una técnica atómica: escribe en un fichero temporal y lo renombra al final.
     */
    public static void replaceTextInFile(Path archivo, String textoViejo, String textoNuevo) throws IOException {
        Path tempFile = Files.createTempFile(archivo.getParent(), "fup_temp_", ".tmp");

        try (BufferedReader reader = Files.newBufferedReader(archivo, StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8)) {

            String linea;
            boolean primera = true;
            while ((linea = reader.readLine()) != null) {
                if (!primera) {
                    writer.newLine();
                }
                writer.write(linea.replace(textoViejo, textoNuevo));
                primera = false;
            }
        }

        // Reemplazo atómico del original por el temporal
        Files.move(tempFile, archivo, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    /**
     * Busca recursivamente todos los archivos con una extensión determinada.
     */
    public static List<Path> searchByExtension(Path directorioRaiz, String extension) throws IOException {
        String extNormalizada = extension.startsWith(".") ? extension.toLowerCase() : "." + extension.toLowerCase();
        List<Path> encontrados = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(directorioRaiz)) {
            stream.filter(Files::isRegularFile)
                  .filter(p -> p.getFileName().toString().toLowerCase().endsWith(extNormalizada))
                  .forEach(encontrados::add);
        }

        return encontrados;
    }

    /**
     * Método main para probar todas las operaciones de la clase utilitaria.
     */
    public static void main(String[] args) {
        System.out.println("=== DEMO: Utilidades Profesionales FileUtilsPro ===\n");

        Path baseDir = Paths.get("output", "demo_fileutils");
        Path archivoDemo = baseDir.resolve("configuracion.ini");
        Path archivoBackup = baseDir.resolve("backup").resolve("configuracion_bak.ini");

        try {
            // 1. Preparar archivo de prueba
            Files.createDirectories(baseDir);
            String configInicial = "[SERVER]\n" +
                                   "port=8080\n" +
                                   "host=localhost\n" +
                                   "environment=DEVELOPMENT\n" +
                                   "max_connections=50\n";
            Files.writeString(archivoDemo, configInicial, StandardCharsets.UTF_8);
            System.out.println("[1] Archivo creado: " + archivoDemo.toAbsolutePath());

            // 2. Contar líneas
            long lineas = countLines(archivoDemo);
            System.out.println("[2] Total de líneas detectadas: " + lineas);

            // 3. Copia segura a subdirectorio nuevo (crea la carpeta 'backup/' automáticamente)
            safeCopy(archivoDemo, archivoBackup);
            System.out.println("[3] safeCopy() ejecutada con éxito hacia: " + archivoBackup.toAbsolutePath());

            // 4. Reemplazo atómico de texto
            System.out.println("[4] Reemplazando 'DEVELOPMENT' por 'PRODUCTION' atómicamente...");
            replaceTextInFile(archivoDemo, "environment=DEVELOPMENT", "environment=PRODUCTION");

            System.out.println("--- Contenido final de " + archivoDemo.getFileName() + " ---");
            Files.readAllLines(archivoDemo, StandardCharsets.UTF_8).forEach(l -> System.out.println("  | " + l));

            // 5. Búsqueda por extensión
            System.out.println("\n[5] Buscando archivos '.ini' en " + baseDir.toAbsolutePath() + "...");
            List<Path> encontrados = searchByExtension(baseDir, ".ini");
            encontrados.forEach(p -> System.out.println("  -> Encontrado: " + p.getFileName() + " (" + p + ")"));

            System.out.println("\n[OK] Todas las operaciones de FileUtilsPro finalizaron con éxito.");

        } catch (IOException e) {
            System.err.println("[!] Error en FileUtilsPro: " + e.getMessage());
        }
    }
}
