package es.cifpaviles.dam2.ad.ficheros.serializacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * UT01 - Manejo de Ficheros
 * Demostración de Serialización de Objetos en Java:
 * - ObjectOutputStream (serialización) y ObjectInputStream (deserialización).
 * - Guardado de colecciones enteras (List<Contacto>) en un único flujo.
 * - Comprobación del efecto de campos transient (se restauran como null).
 */
public class SerializacionDemo {

    public static void main(String[] args) {
        System.out.println("=== DEMO: Serialización de Objetos (ObjectOutputStream / ObjectInputStream) ===\n");

        File carpeta = new File("output/temp_io");
        carpeta.mkdirs();
        File archivoObjetos = new File(carpeta, "contactos.ser");

        // 1. Crear lista de objetos
        List<Contacto> listaOriginal = new ArrayList<>();
        listaOriginal.add(new Contacto(1, "Ana Morales", "ana@cifpaviles.es", 29500.0, "TOKEN_SECRETO_9823"));
        listaOriginal.add(new Contacto(2, "David Suárez", "david@cifpaviles.es", 34000.0, "TOKEN_SECRETO_1124"));
        listaOriginal.add(new Contacto(3, "Carmen Vega", "carmen@cifpaviles.es", 31200.0, "TOKEN_SECRETO_5531"));

        // 2. Serializar la colección completa a disco
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoObjetos))) {
            oos.writeObject(listaOriginal);
            System.out.println("[+] Lista de " + listaOriginal.size() + " contactos serializada en: " + 
                               archivoObjetos.getAbsolutePath() + " (" + archivoObjetos.length() + " bytes)");
        } catch (IOException e) {
            System.err.println("[!] Error al serializar objetos: " + e.getMessage());
            return;
        }

        // 3. Deserializar desde disco y recuperar el estado de los objetos en memoria
        System.out.println("\n--- Recuperando objetos serializados desde disco ---");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoObjetos))) {

            @SuppressWarnings("unchecked")
            List<Contacto> listaRecuperada = (List<Contacto>) ois.readObject();

            for (Contacto c : listaRecuperada) {
                System.out.println(" -> " + c);
            }

            System.out.println("\n[!] OBSERVA EL CAMPO 'tokenSeguridad':");
            System.out.println("    En la lista original tenía valor, pero al estar marcado como 'transient'");
            System.out.println("    la JVM no lo escribió en disco y al deserializar vale: " + listaRecuperada.get(0).getTokenSeguridad());

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[!] Error al deserializar objetos: " + e.getMessage());
        }
    }
}
