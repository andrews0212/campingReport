package org.example.camping2.dto;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entity representing a client in the camping system.
 * This class holds the personal information of a client such as name, surname, DNI, email,
 * phone number, birth date, status, and comments. It also includes a set of associated reservations.
 * The entity is mapped to the `cliente` table in the database.
 *
 * @author Andrews Dos Ramos
 * @version 1.0.0
 * @since 31/01/2025
 */
@Entity
@Table(name = "cliente")
public class Cliente {

    /**
     * The unique identifier of the client.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCLIENTE", nullable = false)
    private Integer id;

    /**
     * The first name of the client.
     */
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    /**
     * The last name of the client.
     */
    @Column(name = "APELLIDO", nullable = false, length = 50)
    private String apellido;

    /**
     * The DNI (ID number) of the client.
     */
    @Column(name = "DNI", nullable = false, length = 9)
    private String dni;

    /**
     * The email address of the client.
     */
    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    /**
     * The phone number of the client.
     */
    @Column(name = "TELEFONO", length = 9)
    private String telefono;

    /**
     * The birth date of the client.
     */
    @Column(name = "FECHA_NACIMIENTO")
    private LocalDate fechaNacimiento;

    /**
     * The status of the client (e.g., active, inactive).
     */
    @Lob
    @Column(name = "ESTADO", nullable = false)
    private String estado;

    /**
     * Comments associated with the client.
     */
    @Column(name = "COMENTARIOS", length = 300)
    private String comentarios;

    /**
     * The set of reservations associated with the client.
     */
    @OneToMany(mappedBy = "idcliente")
    private Set<org.example.camping2.dto.Reserva> reservas = new LinkedHashSet<>();

    /**
     * Constructor to create a client with specified details.
     *
     * @param nombre the first name of the client.
     * @param apellido the last name of the client.
     * @param dni the DNI of the client.
     * @param email the email of the client.
     * @param telefono the phone number of the client.
     * @param fechaNacimiento the birth date of the client.
     * @param estado the status of the client.
     * @param comentarios additional comments for the client.
     */
    public Cliente(String nombre, String apellido, String dni, String email, String telefono, LocalDate fechaNacimiento, String estado, String comentarios) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
        this.comentarios = comentarios;
    }

    /**
     * Default constructor for the `Cliente` entity.
     */
    public Cliente() {

    }

    /**
     * Returns the unique identifier of the client.
     *
     * @return the client ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the client.
     *
     * @param id the client ID to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the first name of the client.
     *
     * @return the first name of the client.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the first name of the client.
     *
     * @param nombre the first name to set.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Returns the last name of the client.
     *
     * @return the last name of the client.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Sets the last name of the client.
     *
     * @param apellido the last name to set.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Returns the DNI (ID number) of the client.
     *
     * @return the DNI of the client.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Sets the DNI (ID number) of the client.
     *
     * @param dni the DNI to set for the client.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Returns the email address of the client.
     *
     * @return the email of the client.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the client.
     *
     * @param email the email to set for the client.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of the client.
     *
     * @return the phone number of the client.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Sets the phone number of the client.
     *
     * @param telefono the phone number to set for the client.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Returns the birth date of the client.
     *
     * @return the birth date of the client.
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Sets the birth date of the client.
     *
     * @param fechaNacimiento the birth date to set for the client.
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Returns the status of the client.
     *
     * @return the status of the client.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the status of the client.
     *
     * @param estado the status to set for the client.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Returns the comments associated with the client.
     *
     * @return the comments of the client.
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * Sets the comments for the client.
     *
     * @param comentarios the comments to set for the client.
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * Returns the set of reservations associated with the client.
     *
     * @return the set of reservations for the client.
     */
    public Set<org.example.camping2.dto.Reserva> getReservas() {
        return reservas;
    }

    /**
     * Sets the set of reservations associated with the client.
     *
     * @param reservas the set of reservations to set for the client.
     */
    public void setReservas(Set<org.example.camping2.dto.Reserva> reservas) {
        this.reservas = reservas;
    }
}
