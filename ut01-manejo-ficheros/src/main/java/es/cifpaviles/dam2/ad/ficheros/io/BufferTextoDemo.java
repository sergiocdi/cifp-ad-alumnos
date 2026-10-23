package es.cifpaviles.dam2.ad.ficheros.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * UT01 - Manejo de Ficheros
 * Demostración de flujos de caracteres con buffer:
 * - BufferedReader con readLine() para lectura eficiente línea a línea.
 * - PrintWriter para escritura formateada de texto (println, printf).
 * - Control de codificación UTF-8.
 */
public class BufferTextoDemo {

    public static void main(String[] args) {
        System.out.println("=== DEMO: Flujos de Caracteres con Buffer (BufferedReader / PrintWriter) ===\n");

        File carpeta = new File("output/temp_io");
        carpeta.mkdirs();

        File archivoTexto = new File(carpeta, "informe_alumnos.txt");

        // 1. Escritura de texto formateado con PrintWriter
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivoTexto, StandardCharsets.UTF_8))) {
            pw.println("# INFORME DE CALIFICACIONES - CIFP AVILÉS");
            pw.println("# Módulo: Acceso a Datos (0486)");
            pw.println("----------------------------------------------");
            pw.printf("%-10s %-20s %-6s%n", "EXPEDIENTE", "ALUMNO", "NOTA");
            pw.printf("%-10s %-20s %-6.2f%n", "DAM2-001", "García, Laura", 9.25);
            pw.printf("%-10s %-20s %-6.2f%n", "DAM2-002", "Pérez, Marcos", 7.50);
            pw.printf("%-10s %-20s %-6.2f%n", "DAM2-003", "Fernández, Lucía", 8.80);
            System.out.println("[+] Fichero de texto escrito en: " + archivoTexto.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("[!] Error al escribir texto: " + e.getMessage());
            return;
        }

        // 2. Lectura secuencial línea a línea con BufferedReader
        System.out.println("\n--- Leyendo contenido línea a línea ---");
        try (BufferedReader br = new BufferedReader(new FileReader(archivoTexto, StandardCharsets.UTF_8))) {
            String linea;
            int numLinea = 1;
            while ((linea = br.readLine()) != null) {
                System.out.printf("[%02d] %s%n", numLinea++, linea);
            }
        } catch (IOException e) {
            System.err.println("[!] Error al leer texto: " + e.getMessage());
        }
    }
}
