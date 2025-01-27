package org.example.camping2.dto;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRESERVA", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDCLIENTE")
    private Cliente idcliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDRECURSO")
    private Recurso idrecurso;

    @Column(name = "FECHA_INICIO")
    private LocalDate fechaInicio;

    @Column(name = "FECHA_FIN")
    private LocalDate fechaFin;

    @Lob
    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "PRECIO_TOTAL", nullable = false)
    private Integer precioTotal;

    @Column(name = "NUMERO_PERSONAS", nullable = false)
    private Integer numeroPersonas;

    @OneToMany(mappedBy = "idreserva")
    private Set<Acompañante> acompañantes = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Cliente idcliente) {
        this.idcliente = idcliente;
    }

    public Recurso getIdrecurso() {
        return idrecurso;
    }

    public void setIdrecurso(Recurso idrecurso) {
        this.idrecurso = idrecurso;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Integer precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Integer getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(Integer numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public Set<Acompañante> getAcompañantes() {
        return acompañantes;
    }

    public void setAcompañantes(Set<Acompañante> acompañantes) {
        this.acompañantes = acompañantes;
    }

}