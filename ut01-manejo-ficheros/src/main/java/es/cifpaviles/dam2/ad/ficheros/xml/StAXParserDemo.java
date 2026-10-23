package es.cifpaviles.dam2.ad.ficheros.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 * UT01 - Manejo de Ficheros
 * Demostración de Procesamiento XML mediante StAX (Streaming API for XML):
 * - Parser unidireccional basado en cursor (pull-parser: la aplicación pide el siguiente evento).
 * - Máxima eficiencia y control: lee bajo demanda sin cargar el árbol en memoria.
 * - Escritura secuencial ultrarrápida con XMLStreamWriter.
 */
public class StAXParserDemo {

    public static void main(String[] args) {
        System.out.println("=== DEMO: Parseo XML con StAX (Cursor XMLStreamReader / Writer) ===\n");

        File archivoXml = new File("ut01-manejo-ficheros/src/main/resources/catalogo.xml");
        if (!archivoXml.exists()) {
            archivoXml = new File("src/main/resources/catalogo.xml");
        }

        // 1. Lectura por eventos con XMLStreamReader (Pull Parsing)
        try (FileInputStream fis = new FileInputStream(archivoXml)) {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = inputFactory.createXMLStreamReader(fis);

            String etiquetaActual = "";
            int contadorLibros = 0;

            while (reader.hasNext()) {
                int evento = reader.next();

                switch (evento) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        etiquetaActual = reader.getLocalName();
                        if ("libro".equalsIgnoreCase(etiquetaActual)) {
                            String id = reader.getAttributeValue(null, "id");
                            System.out.println("  [StAX] Libro ID: " + id);
                            contadorLibros++;
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        String texto = reader.getText().trim();
                        if (!texto.isEmpty() && "titulo".equalsIgnoreCase(etiquetaActual)) {
                            System.out.println("         Título: " + texto);
                        }
                    }
                    case XMLStreamConstants.END_ELEMENT -> etiquetaActual = "";
                }
            }
            reader.close();
            System.out.println("[+] Total libros leídos con StAX: " + contadorLibros);

        } catch (Exception e) {
            System.err.println("[!] Error al leer con StAX: " + e.getMessage());
        }

        // 2. Escritura de un nuevo XML con XMLStreamWriter
        File carpetaSalida = new File("output/temp_xml");
        carpetaSalida.mkdirs();
        File salidaStax = new File(carpetaSalida, "catalogo_generado_stax.xml");

        try (FileWriter fw = new FileWriter(salidaStax)) {
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = outputFactory.createXMLStreamWriter(fw);

            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeCharacters("\n");
            writer.writeStartElement("catalogo");
            writer.writeCharacters("\n  ");

            writer.writeStartElement("libro");
            writer.writeAttribute("id", "L99");
            writer.writeCharacters("\n    ");
            
            writer.writeStartElement("titulo");
            writer.writeCharacters("Microservicios y Docker en Java");
            writer.writeEndElement();
            writer.writeCharacters("\n  ");

            writer.writeEndElement(); // </libro>
            writer.writeCharacters("\n");
            writer.writeEndElement(); // </catalogo>
            writer.writeEndDocument();

            writer.flush();
            writer.close();

            System.out.println("[+] Fichero XML generado con XMLStreamWriter: " + salidaStax.getAbsolutePath());

        } catch (Exception e) {
            System.err.println("[!] Error al escribir con StAX: " + e.getMessage());
        }
    }
}
