package org.example.camping2.modelo.dto;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entity representing a reservation in the camping system.
 * This class holds information about a reservation, including details about the client,
 * resource, dates, total price, number of people, and accompanying guests.
 * The entity is mapped to the `reserva` table in the database.
 *
 * @author Andrews Dos Ramos
 * @version 1.0.0
 * @since 31/01/2025
 */
@Entity
@Table(name = "reserva")
public class Reserva {

    /**
     * The unique identifier of the reservation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRESERVA", nullable = false)
    private Integer id;

    /**
     * The client associated with the reservation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDCLIENTE")
    private Cliente idcliente;

    /**
     * The resource associated with the reservation (e.g., bungalow, barbecue).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDRECURSO")
    private Recurso idrecurso;

    /**
     * The start date of the reservation.
     */
    @Column(name = "FECHA_INICIO")
    private LocalDate fechaInicio;

    /**
     * The end date of the reservation.
     */
    @Column(name = "FECHA_FIN")
    private LocalDate fechaFin;

    /**
     * The status of the reservation (e.g., "confirmed", "canceled").
     */
    @Lob
    @Column(name = "ESTADO")
    private String estado;

    /**
     * The total price of the reservation.
     */
    @Column(name = "PRECIO_TOTAL", nullable = false)
    private Integer precioTotal;

    /**
     * The number of people for the reservation.
     */
    @Column(name = "NUMERO_PERSONAS", nullable = false)
    private Integer numeroPersonas;

    /**
     * The list of accompanying guests for the reservation.
     */
    @OneToMany(mappedBy = "idreserva")
    private Set<Acompanante> acompañantes = new LinkedHashSet<>();

    public Reserva(Cliente idcliente, Recurso idrecurso, LocalDate fechaInicio, LocalDate fechaFin, String estado, Integer precioTotal, Integer numeroPersonas) {
        this.idcliente = idcliente;
        this.idrecurso = idrecurso;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.precioTotal = precioTotal;
        this.numeroPersonas = numeroPersonas;
    }

    public Reserva() {

    }

    /**
     * Returns the unique identifier of the reservation.
     *
     * @return the reservation ID.
     */

    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the reservation.
     *
     * @param id the reservation ID to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the client associated with the reservation.
     *
     * @return the client of the reservation.
     */
    public Cliente getIdcliente() {
        return idcliente;
    }

    /**
     * Sets the client for the reservation.
     *
     * @param idcliente the client to set.
     */
    public void setIdcliente(Cliente idcliente) {
        this.idcliente = idcliente;
    }

    /**
     * Returns the resource associated with the reservation.
     *
     * @return the resource of the reservation.
     */
    public Recurso getIdrecurso() {
        return idrecurso;
    }

    /**
     * Sets the resource for the reservation.
     *
     * @param idrecurso the resource to set.
     */
    public void setIdrecurso(Recurso idrecurso) {
        this.idrecurso = idrecurso;
    }

    /**
     * Returns the start date of the reservation.
     *
     * @return the start date.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Sets the start date of the reservation.
     *
     * @param fechaInicio the start date to set.
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Returns the end date of the reservation.
     *
     * @return the end date.
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * Sets the end date of the reservation.
     *
     * @param fechaFin the end date to set.
     */
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Returns the status of the reservation.
     *
     * @return the status of the reservation.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the status of the reservation.
     *
     * @param estado the status to set.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Returns the total price of the reservation.
     *
     * @return the total price.
     */
    public Integer getPrecioTotal() {
        return precioTotal;
    }

    /**
     * Sets the total price of the reservation.
     *
     * @param precioTotal the total price to set.
     */
    public void setPrecioTotal(Integer precioTotal) {
        this.precioTotal = precioTotal;
    }

    /**
     * Returns the number of people for the reservation.
     *
     * @return the number of people.
     */
    public Integer getNumeroPersonas() {
        return numeroPersonas;
    }

    /**
     * Sets the number of people for the reservation.
     *
     * @param numeroPersonas the number of people to set.
     */
    public void setNumeroPersonas(Integer numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    /**
     * Returns the list of accompanying guests for the reservation.
     *
     * @return the accompanying guests.
     */
    public Set<Acompanante> getAcompañantes() {
        return acompañantes;
    }

    /**
     * Sets the list of accompanying guests for the reservation.
     *
     * @param acompañantes the guests to set.
     */
    public void setAcompañantes(Set<Acompanante> acompañantes) {
        this.acompañantes = acompañantes;
    }
}
