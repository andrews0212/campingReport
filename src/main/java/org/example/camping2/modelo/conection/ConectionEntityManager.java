package org.example.camping2.modelo.conection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utility class that provides a static method to obtain an {@link EntityManager} instance for interacting
 * with the database. It uses JPA (Jakarta Persistence API) and an {@link EntityManagerFactory} to create
 * the connection to the database.
 */
public class ConectionEntityManager {

    private static EntityManager em;
    private static EntityManagerFactory emf;

    /**
     * Retrieves an {@link EntityManager} instance to interact with the database.
     * The method initializes the {@link EntityManagerFactory} if it has not been initialized yet.
     *
     * @return The {@link EntityManager} instance for interacting with the database.
     */
    public static EntityManager getConectionEntityManager() {
        emf = Persistence.createEntityManagerFactory("miPersistencia");
        em = emf.createEntityManager();
        return em;
    }
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf; // donde emf es la instancia creada con Persistence.createEntityManagerFactory(...)
    }

}
