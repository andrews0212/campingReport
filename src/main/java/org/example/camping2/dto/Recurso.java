package org.example.camping2.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "recurso")
public class Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRECURSO", nullable = false)
    private Integer id;

    @Column(name = "NOMBRE", nullable = false, length = 25)
    private String nombre;

    @Lob
    @Column(name = "TIPO", nullable = false)
    private String tipo;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    @Column(name = "PRECIO", nullable = false)
    private Integer precio;

    @Column(name = "MINIMO_PERSONAS", nullable = false)
    private Integer minimoPersonas;

    @Lob
    @Column(name = "ESTADO", nullable = false)
    private String estado;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getMinimoPersonas() {
        return minimoPersonas;
    }

    public void setMinimoPersonas(Integer minimoPersonas) {
        this.minimoPersonas = minimoPersonas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}