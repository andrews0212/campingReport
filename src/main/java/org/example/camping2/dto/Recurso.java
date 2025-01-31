package org.example.camping2.dto;

import jakarta.persistence.*;

/**
 * Entity representing a resource in the camping system.
 * This class holds information about a resource, such as its name, type, capacity, price,
 * minimum number of people, and status. The entity is mapped to the `recurso` table in the database.
 *
 * @author Andrews Dos Ramos
 * @version 1.0.0
 * @since 31/01/2025
 */
@Entity
@Table(name = "recurso")
public class Recurso {

    /**
     * The unique identifier of the resource.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRECURSO", nullable = false)
    private Integer id;

    /**
     * The name of the resource.
     */
    @Column(name = "NOMBRE", nullable = false, length = 25)
    private String nombre;

    /**
     * The type of the resource (e.g., "bungalow", "barbecue").
     */
    @Lob
    @Column(name = "TIPO", nullable = false)
    private String tipo;

    /**
     * The capacity of the resource (e.g., number of people it can accommodate).
     */
    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    /**
     * The price of the resource.
     */
    @Column(name = "PRECIO", nullable = false)
    private Integer precio;

    /**
     * The minimum number of people required to book the resource.
     */
    @Column(name = "MINIMO_PERSONAS", nullable = false)
    private Integer minimoPersonas;

    /**
     * The status of the resource (e.g., "available", "unavailable").
     */
    @Lob
    @Column(name = "ESTADO", nullable = false)
    private String estado;

    /**
     * Returns the unique identifier of the resource.
     *
     * @return the resource ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the resource.
     *
     * @param id the resource ID to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the name of the resource.
     *
     * @return the name of the resource.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the name of the resource.
     *
     * @param nombre the name to set for the resource.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Returns the type of the resource.
     *
     * @return the type of the resource.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the type of the resource.
     *
     * @param tipo the type to set for the resource.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Returns the capacity of the resource.
     *
     * @return the capacity of the resource.
     */
    public Integer getCapacidad() {
        return capacidad;
    }

    /**
     * Sets the capacity of the resource.
     *
     * @param capacidad the capacity to set for the resource.
     */
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * Returns the price of the resource.
     *
     * @return the price of the resource.
     */
    public Integer getPrecio() {
        return precio;
    }

    /**
     * Sets the price of the resource.
     *
     * @param precio the price to set for the resource.
     */
    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    /**
     * Returns the minimum number of people required to book the resource.
     *
     * @return the minimum number of people.
     */
    public Integer getMinimoPersonas() {
        return minimoPersonas;
    }

    /**
     * Sets the minimum number of people required to book the resource.
     *
     * @param minimoPersonas the minimum number of people to set.
     */
    public void setMinimoPersonas(Integer minimoPersonas) {
        this.minimoPersonas = minimoPersonas;
    }

    /**
     * Returns the status of the resource.
     *
     * @return the status of the resource.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the status of the resource.
     *
     * @param estado the status to set for the resource.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
