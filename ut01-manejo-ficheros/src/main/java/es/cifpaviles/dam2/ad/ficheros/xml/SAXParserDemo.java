package es.cifpaviles.dam2.ad.ficheros.xml;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * UT01 - Manejo de Ficheros
 * Demostración de Procesamiento XML mediante SAX (Simple API for XML):
 * - Parser secuencial guiado por eventos (push-parser).
 * - Uso mínimo de memoria RAM (ideal para archivos XML gigantescos de gigabytes).
 * - Implementación de DefaultHandler con startElement, characters y endElement.
 */
public class SAXParserDemo {

    public static void main(String[] args) {
        System.out.println("=== DEMO: Parseo XML con SAX (Eventos secuenciales) ===\n");

        File archivoXml = new File("ut01-manejo-ficheros/src/main/resources/catalogo.xml");
        if (!archivoXml.exists()) {
            archivoXml = new File("src/main/resources/catalogo.xml");
        }

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            ManejadorCatalogo handler = new ManejadorCatalogo();
            saxParser.parse(archivoXml, handler);

            System.out.println("\n[+] Total de libros procesados con SAX: " + handler.getTotalLibros());

        } catch (Exception e) {
            System.err.println("[!] Error al parsear con SAX: " + e.getMessage());
        }
    }

    // Manejador de eventos SAX
    static class ManejadorCatalogo extends DefaultHandler {
        private final StringBuilder valorActual = new StringBuilder();
        private String titulo;
        private String autor;
        private String precio;
        private int totalLibros = 0;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            valorActual.setLength(0); // Limpiar buffer de texto
            if ("libro".equalsIgnoreCase(qName)) {
                String id = attributes.getValue("id");
                String cat = attributes.getValue("categoria");
                System.out.printf("--- Inicio Libro [%s] (Categoría: %s) ---%n", id, cat);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            valorActual.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            String texto = valorActual.toString().trim();
            switch (qName.toLowerCase()) {
                case "titulo" -> titulo = texto;
                case "autor"  -> autor = texto;
                case "precio" -> precio = texto;
                case "libro"  -> {
                    System.out.printf("  -> '%s' | Autor: %s | Precio: %s EUR%n", titulo, autor, precio);
                    totalLibros++;
                }
            }
        }

        public int getTotalLibros() {
            return totalLibros;
        }
    }
}
