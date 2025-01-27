package org.example.camping2.conection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConectionEntityManager {

    private static EntityManager em;
    private static EntityManagerFactory emf;

    public static EntityManager getConectionEntityManager() {
        emf = Persistence.createEntityManagerFactory("miPersistencia");
        em = emf.createEntityManager();
        return em;
    }

}
