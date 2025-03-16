package org.example.camping2.dto;

import jakarta.persistence.*;

/**
 * Entity representing a user in the camping system.
 * This class holds information about a user, including their personal details,
 * email, nickname, state, and password.
 * The entity is mapped to the `usuario` table in the database.
 *
 * @author Andrews Dos Ramos
 * @version 1.0.0
 * @since 31/01/2025
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    /**
     * The unique identifier of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    /**
     * The first name of the user.
     */
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    /**
     * The last name of the user.
     */
    @Column(name = "APELLIDO", nullable = false, length = 50)
    private String apellido;

    /**
     * The email address of the user.
     */
    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    /**
     * The nickname of the user.
     */
    @Column(name = "NICKNAME", nullable = false, length = 50)
    private String nickname;

    /**
     * The state or status of the user (e.g., "active", "inactive").
     */
    @Lob
    @Column(name = "ESTADO")
    private String estado;

    /**
     * The password of the user, typically stored in a hashed format.
     */
    @Column(name = "`CONTRASEÑA`", nullable = false, length = 60)
    private String contraseña;

    public Usuario() {
    }

    public Usuario(String nickname, String nombre, String apellido, String email, String estado, String contraseña) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.nickname = nickname;
        this.estado = estado;
        this.contraseña = contraseña;
    }

    /**
     * Returns the unique identifier of the user.
     *
     * @return the user ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id the user ID to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the first name of the user.
     *
     * @return the user's first name.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the first name of the user.
     *
     * @param nombre the first name to set.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the user's last name.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Sets the last name of the user.
     *
     * @param apellido the last name to set.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Returns the email address of the user.
     *
     * @return the user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the nickname of the user.
     *
     * @return the user's nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the nickname of the user.
     *
     * @param nickname the nickname to set.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Returns the state or status of the user.
     *
     * @return the user's state.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the state or status of the user.
     *
     * @param estado the state to set.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Returns the password of the user.
     *
     * @return the user's password.
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Sets the password of the user.
     *
     * @param contraseña the password to set.
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
