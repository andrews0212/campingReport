package org.example.camping2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.example.camping2.conection.ConectionEntityManager;
import org.example.camping2.dto.Cliente;
import org.hibernate.exception.ConstraintViolationException;

import java.time.LocalDate;
import java.util.List;

/**
 * Generic DAO (Data Access Object) class that provides common database operations (add, update, delete, find)
 * for any entity type. It uses JPA (Jakarta Persistence API) and an {@link EntityManager} to interact with the database.
 *
 * @param <T>  The type of entity the DAO will manage.
 * @param <ID> The type of the entity's identifier (ID).
 */
public class DAOGN<T, ID> {

    public EntityManager entityManager;
    public Class<T> entityClass;

    /**
     * Constructor to initialize the DAO with the entity class and set up the {@link EntityManager}.
     *
     * @param entityClass The class type of the entity to be managed by this DAO.
     */
    public DAOGN(Class<T> entityClass) {
        entityManager = ConectionEntityManager.getConectionEntityManager();
        this.entityClass = entityClass;
    }

    /**
     * Adds a new entity to the database.
     *
     * @param objecto The entity to be added.
     */
    public void add(T objecto) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(objecto);
            entityManager.getTransaction().commit();
        } catch (ConstraintViolationException cve) {
            entityManager.getTransaction().rollback();
            System.err.println("Violación de restricción: " + cve.getMessage());
            throw cve;
        } catch (PersistenceException pe) {
            entityManager.getTransaction().rollback();
            System.err.println("Error de persistencia: " + pe.getMessage());
            throw pe;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Error inesperado: " + e.getMessage());
            throw new RuntimeException("Ocurrió un error al agregar el objeto", e);
        }
    }

    /**
     * Updates an existing entity in the database.
     *
     * @param objecto The entity to be updated.
     * @return true if the update was successful, false otherwise.
     */
    public boolean update(T objecto) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(objecto);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Error al actualizar el objeto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes an entity from the database by its ID.
     *
     * @param id The ID of the entity to be deleted.
     */
    public void delete(ID id) {
        try {
            entityManager.getTransaction().begin();
            T objeto = entityManager.find(entityClass, id);
            entityManager.remove(objeto);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    /**
     * Finds an entity by its ID.
     *
     * @param id The ID of the entity to find.
     * @return The entity with the specified ID, or null if not found.
     */
    public T findById(ID id) {
        return entityManager.find(entityClass, id);
    }

    /**
     * Finds all entities of the specified type in the database.
     *
     * @return A list of all entities of the specified type.
     */
    public List<T> findAll() {
        return entityManager.createQuery("from " + entityClass.getSimpleName()).getResultList();
    }

    /**
     * Main method to demonstrate the usage of the generic DAO.
     * It retrieves all clients and adds a new one.
     *
     * @param args The command-line arguments (not used).
     */
    public static void main(String[] args) {
        DAOGN<Cliente, Integer> daoGN = new DAOGN<>(Cliente.class);
        daoGN.findAll().forEach(System.out::println);
        daoGN.add(new Cliente("Andrews", "Dos Ramos", "19234567A", "andrewsdosramos@gmail.com", "634127185", LocalDate.now(), "ACTIVO", ""));
    }
}
