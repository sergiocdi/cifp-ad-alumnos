package es.cifpaviles.dam2.ad.ficheros.serializacion;

import java.io.Serial;
import java.io.Serializable;

/**
 * UT01 - Manejo de Ficheros
 * Clase modelo para serialización:
 * - Implementa Serializable.
 * - Define serialVersionUID explícito.
 * - Incluye campo transient (password) que se excluye de la persistencia binaria.
 */
public class Contacto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String email;
    private double salario;

    // Campo marcado como transient: NO se guarda en disco durante la serialización
    private transient String tokenSeguridad;

    public Contacto() {
    }

    public Contacto(int id, String nombre, String email, double salario, String tokenSeguridad) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.salario = salario;
        this.tokenSeguridad = tokenSeguridad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public double getSalario() {
        return salario;
    }

    public String getTokenSeguridad() {
        return tokenSeguridad;
    }

    @Override
    public String toString() {
        return String.format("Contacto{id=%d, nombre='%s', email='%s', salario=%.2f, tokenSeguridad='%s'}",
                id, nombre, email, salario, tokenSeguridad);
    }
}
