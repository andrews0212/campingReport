package org.example.camping2.modelo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * Composite primary key for the `Acompañante` entity.
 * This class represents a unique identifier for a companion in the reservation system,
 * composed of the companion ID, client ID, and reservation ID.
 * It implements `Serializable` to be used as an embedded primary key in the `Acompañante` entity.
 *
 * @author Andrews Dos Ramos
 * @version 1.0.0
 * @since 31/01/2025
 */
@Embeddable
public class AcompañanteId implements java.io.Serializable {

    /**
     * The unique identifier of the companion.
     */
    @Column(name = "`IDACOMPAÑANTE`", nullable = false)
    private Integer idacompañante;


    /**
     * The unique identifier of the reservation.
     */
    @Column(name = "IDRESERVA", nullable = false)
    private Integer idreserva;

    /**
     * Returns the ID of the companion.
     *
     * @return the unique identifier of the companion.
     */
    public Integer getIdacompañante() {
        return idacompañante;
    }

    /**
     * Sets the ID of the companion.
     *
     * @param idacompañante the unique identifier to set for the companion.
     */
    public void setIdacompañante(Integer idacompañante) {
        this.idacompañante = idacompañante;
    }

    /**
     * Returns the ID of the client.
     *
     * @return the unique identifier of the client.
     */

    /**
     * Returns the ID of the reservation.
     *
     * @return the unique identifier of the reservation.
     */
    public Integer getIdreserva() {
        return idreserva;
    }

    /**
     * Sets the ID of the reservation.
     *
     * @param idreserva the unique identifier to set for the reservation.
     */
    public void setIdreserva(Integer idreserva) {
        this.idreserva = idreserva;
    }

    /**
     * Compares this object to the specified object for equality.
     * The comparison is based on the companion ID, client ID, and reservation ID.
     *
     * @param o the object to compare with.
     * @return {@code true} if the two objects are equal; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AcompañanteId entity = (AcompañanteId) o;
        return Objects.equals(this.idreserva, entity.idreserva) &&
                Objects.equals(this.idacompañante, entity.idacompañante);
    }

    /**
     * Returns the hash code for this object.
     * The hash code is generated based on the companion ID, client ID, and reservation ID.
     *
     * @return the hash code for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idreserva, idacompañante);
    }
}
