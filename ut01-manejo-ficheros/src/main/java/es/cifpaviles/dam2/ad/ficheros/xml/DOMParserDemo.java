package es.cifpaviles.dam2.ad.ficheros.xml;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * UT01 - Manejo de Ficheros
 * Demostración de Procesamiento XML mediante DOM (Document Object Model):
 * - Carga completa del árbol XML en memoria.
 * - Recorrido de nodos hijos y extracción de atributos y valores de texto.
 * - Modificación de elementos en memoria y volcado a disco con Transformer.
 */
public class DOMParserDemo {

    public static void main(String[] args) {
        System.out.println("=== DEMO: Parseo XML con DOM (DocumentBuilder / Document) ===\n");

        File archivoXml = new File("ut01-manejo-ficheros/src/main/resources/catalogo.xml");
        if (!archivoXml.exists()) {
            // Si se ejecuta desde la carpeta del subproyecto
            archivoXml = new File("src/main/resources/catalogo.xml");
        }

        try {
            // 1. Crear el analizador DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 2. Parsear el archivo XML a un árbol Document en memoria
            Document doc = builder.parse(archivoXml);
            doc.getDocumentElement().normalize();

            Element raiz = doc.getDocumentElement();
            System.out.println("[+] Elemento raíz: <" + raiz.getNodeName() + ">");

            // 3. Obtener todos los elementos <libro>
            NodeList listaLibros = doc.getElementsByTagName("libro");
            System.out.println("[+] Total de libros encontrados: " + listaLibros.getLength() + "\n");

            for (int i = 0; i < listaLibros.getLength(); i++) {
                Node nodo = listaLibros.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoLibro = (Element) nodo;

                    String id = elementoLibro.getAttribute("id");
                    String categoria = elementoLibro.getAttribute("categoria");
                    String titulo = elementoLibro.getElementsByTagName("titulo").item(0).getTextContent();
                    String autor = elementoLibro.getElementsByTagName("autor").item(0).getTextContent();
                    String anio = elementoLibro.getElementsByTagName("anio").item(0).getTextContent();
                    
                    Element precioElement = (Element) elementoLibro.getElementsByTagName("precio").item(0);
                    String precio = precioElement.getTextContent();
                    String moneda = precioElement.getAttribute("moneda");

                    System.out.printf("  [%s] '%s' por %s (%s) -> %s %s [Cat: %s]%n",
                            id, titulo, autor, anio, precio, moneda, categoria);
                }
            }

            // 4. Modificar un dato en memoria y guardar una copia con Transformer
            File carpetaSalida = new File("output/temp_xml");
            carpetaSalida.mkdirs();
            File archivoModificado = new File(carpetaSalida, "catalogo_rebajado.xml");

            // Aplicamos un descuento al primer libro
            Element primerPrecio = (Element) doc.getElementsByTagName("precio").item(0);
            primerPrecio.setTextContent("29.99");

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(archivoModificado);
            transformer.transform(source, result);

            System.out.println("\n[+] XML modificado guardado en: " + archivoModificado.getAbsolutePath());

        } catch (Exception e) {
            System.err.println("[!] Error al parsear XML con DOM: " + e.getMessage());
        }
    }
}
