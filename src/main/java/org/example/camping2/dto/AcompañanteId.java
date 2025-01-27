package org.example.camping2.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class AcompañanteId implements java.io.Serializable {
    private static final long serialVersionUID = 5038473308994968916L;
    @Column(name = "`IDACOMPAÑANTE`", nullable = false)
    private Integer idacompañante;

    @Column(name = "IDCLIENTE", nullable = false)
    private Integer idcliente;

    @Column(name = "IDRESERVA", nullable = false)
    private Integer idreserva;

    public Integer getIdacompañante() {
        return idacompañante;
    }

    public void setIdacompañante(Integer idacompañante) {
        this.idacompañante = idacompañante;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public Integer getIdreserva() {
        return idreserva;
    }

    public void setIdreserva(Integer idreserva) {
        this.idreserva = idreserva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AcompañanteId entity = (AcompañanteId) o;
        return Objects.equals(this.idreserva, entity.idreserva) &&
                Objects.equals(this.idacompañante, entity.idacompañante) &&
                Objects.equals(this.idcliente, entity.idcliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idreserva, idacompañante, idcliente);
    }

}