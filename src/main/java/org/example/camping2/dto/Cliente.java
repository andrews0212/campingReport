package org.example.camping2.dto;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCLIENTE", nullable = false)
    private Integer id;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO", nullable = false, length = 50)
    private String apellido;

    @Column(name = "DNI", nullable = false, length = 9)
    private String dni;

    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    @Column(name = "TELEFONO", length = 9)
    private String telefono;

    @Column(name = "FECHA_NACIMIENTO")
    private LocalDate fechaNacimiento;

    @Lob
    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @Column(name = "COMENTARIOS", length = 300)
    private String comentarios;

    @OneToMany(mappedBy = "idcliente")
    private Set<org.example.camping2.dto.Reserva> reservas = new LinkedHashSet<>();

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

    public Cliente() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Set<org.example.camping2.dto.Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<org.example.camping2.dto.Reserva> reservas) {
        this.reservas = reservas;
    }

}