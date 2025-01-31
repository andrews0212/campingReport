package org.example.camping2.dto;

import jakarta.persistence.*;

/**
 * Entity representing a companion (Acompañante) in a reservation system.
 * This class holds details about a companion accompanying a guest on a reservation.
 * It includes personal information like name, surname, DNI, email, and phone number.
 * The entity is mapped to the `acompañante` table in the database.
 *
 * @author Andrews Dos Ramos
 * @version 1.0.0
 * @since 31/01/2025
 */
@Entity
@Table(name = "`acompañante`")
public class Acompañante {

    /**
     * Composite primary key consisting of companion, reservation, and client IDs.
     */
    @EmbeddedId
    private AcompañanteId id;

    /**
     * The reservation associated with the companion.
     */
    @MapsId("idreserva")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDRESERVA", nullable = false)
    private Reserva idreserva;

    /**
     * The first name of the companion.
     */
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    /**
     * The last name of the companion.
     */
    @Column(name = "APELLIDO", nullable = false, length = 50)
    private String apellido;

    /**
     * The DNI (ID number) of the companion.
     */
    @Column(name = "DNI", length = 9)
    private String dni;

    /**
     * The email address of the companion.
     */
    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    /**
     * The phone number of the companion.
     */
    @Column(name = "TELEFONO", length = 9)
    private String telefono;

    /**
     * Returns the ID of the companion, which includes the relationship between the client ID, reservation ID, and companion ID.
     *
     * @return the ID of the companion.
     */
    public AcompañanteId getId() {
        return id;
    }

    /**
     * Sets the ID for the companion.
     *
     * @param id the ID to set, representing the relationship between client, reservation, and companion.
     */
    public void setId(AcompañanteId id) {
        this.id = id;
    }

    /**
     * Returns the reservation associated with the companion.
     *
     * @return the reservation associated with the companion.
     */
    public Reserva getIdreserva() {
        return idreserva;
    }

    /**
     * Sets the reservation for the companion.
     *
     * @param idreserva the reservation to set for the companion.
     */
    public void setIdreserva(Reserva idreserva) {
        this.idreserva = idreserva;
    }

    /**
     * Returns the first name of the companion.
     *
     * @return the first name of the companion.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the first name of the companion.
     *
     * @param nombre the first name of the companion.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Returns the last name of the companion.
     *
     * @return the last name of the companion.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Sets the last name of the companion.
     *
     * @param apellido the last name of the companion.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Returns the DNI (ID number) of the companion.
     *
     * @return the DNI of the companion.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Sets the DNI (ID number) of the companion.
     *
     * @param dni the DNI to set for the companion.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Returns the email address of the companion.
     *
     * @return the email address of the companion.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the companion.
     *
     * @param email the email address to set for the companion.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of the companion.
     *
     * @return the phone number of the companion.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Sets the phone number of the companion.
     *
     * @param telefono the phone number to set for the companion.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
