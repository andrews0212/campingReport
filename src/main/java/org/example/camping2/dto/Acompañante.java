package org.example.camping2.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "`acompañante`")
public class Acompañante {
    @EmbeddedId
    private AcompañanteId id;

    @MapsId("idreserva")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDRESERVA", nullable = false)
    private org.example.camping2.dto.Reserva idreserva;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO", nullable = false, length = 50)
    private String apellido;

    @Column(name = "DNI", length = 9)
    private String dni;

    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    @Column(name = "TELEFONO", length = 9)
    private String telefono;

    public AcompañanteId getId() {
        return id;
    }

    public void setId(AcompañanteId id) {
        this.id = id;
    }

    public org.example.camping2.dto.Reserva getIdreserva() {
        return idreserva;
    }

    public void setIdreserva(org.example.camping2.dto.Reserva idreserva) {
        this.idreserva = idreserva;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}