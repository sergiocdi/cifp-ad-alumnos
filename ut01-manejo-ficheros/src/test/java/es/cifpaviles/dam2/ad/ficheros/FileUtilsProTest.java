package es.cifpaviles.dam2.ad.ficheros;

import es.cifpaviles.dam2.ad.ficheros.utils.FileUtilsPro;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias con JUnit 5 para FileUtilsPro.
 */
class FileUtilsProTest {

    private Path tempDir;
    private Path testFile;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("test_fileutils_");
        testFile = tempDir.resolve("datos.txt");
        Files.writeString(testFile, "Linea 1\nLinea 2 con token_reemplazo\nLinea 3\n", StandardCharsets.UTF_8);
    }

    @AfterEach
    void tearDown() throws IOException {
        if (Files.exists(tempDir)) {
            try (var stream = Files.walk(tempDir)) {
                stream.sorted((a, b) -> b.compareTo(a))
                      .forEach(p -> {
                          try {
                              Files.deleteIfExists(p);
                          } catch (IOException ignored) {
                          }
                      });
            }
        }
    }

    @Test
    @DisplayName("countLines debe devolver exactamente 3 líneas")
    void testCountLines() throws IOException {
        long lines = FileUtilsPro.countLines(testFile);
        assertEquals(3, lines);
    }

    @Test
    @DisplayName("safeCopy debe copiar el archivo y crear subdirectorios intermedios")
    void testSafeCopy() throws IOException {
        Path destFile = tempDir.resolve("subdir").resolve("copia.txt");
        FileUtilsPro.safeCopy(testFile, destFile);

        assertTrue(Files.exists(destFile));
        assertEquals(Files.size(testFile), Files.size(destFile));
    }

    @Test
    @DisplayName("safeCopy debe lanzar IllegalArgumentException si el origen no existe")
    void testSafeCopyOrigenNoExiste() {
        Path noExiste = tempDir.resolve("fantasma.txt");
        Path dest = tempDir.resolve("dest.txt");

        assertThrows(IllegalArgumentException.class, () -> FileUtilsPro.safeCopy(noExiste, dest));
    }

    @Test
    @DisplayName("replaceTextInFile debe sustituir la cadena atómicamente")
    void testReplaceText() throws IOException {
        FileUtilsPro.replaceTextInFile(testFile, "token_reemplazo", "VALOR_FINAL");

        String content = Files.readString(testFile, StandardCharsets.UTF_8);
        assertTrue(content.contains("VALOR_FINAL"));
        assertFalse(content.contains("token_reemplazo"));
    }

    @Test
    @DisplayName("searchByExtension debe encontrar archivos según su extensión")
    void testSearchByExtension() throws IOException {
        Files.createFile(tempDir.resolve("otro.txt"));
        Files.createFile(tempDir.resolve("imagen.png"));

        List<Path> encontrados = FileUtilsPro.searchByExtension(tempDir, ".txt");
        assertEquals(2, encontrados.size());
    }
}
