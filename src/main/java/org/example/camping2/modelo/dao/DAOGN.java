package org.example.camping2.modelo.dao;

import jakarta.persistence.Cache;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import org.example.camping2.modelo.conection.ConectionEntityManager;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class DAOGN<T, ID> {

    public EntityManager entityManager;
    public Class<T> entityClass;

    public DAOGN(Class<T> entityClass) {
        entityManager = ConectionEntityManager.getConectionEntityManager();
        this.entityClass = entityClass;
    }

    public boolean add(T objecto) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(objecto);
            entityManager.getTransaction().commit();
            return true;
        } catch (ConstraintViolationException cve) {
            entityManager.getTransaction().rollback();
            System.err.println("Violación de restricción: " + cve.getMessage());
            return false;
        } catch (PersistenceException pe) {
            entityManager.getTransaction().rollback();
            System.err.println("Error de persistencia: " + pe.getMessage());
            return false;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Error inesperado: " + e.getMessage());
            return false;
        }
    }

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

    public T findById(ID id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll() {
        return entityManager.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
    }

    /**
     * Limpia la caché de primer nivel (EntityManager) y segundo nivel (EntityManagerFactory).
     */
    public void limpiarCache() {
        // Limpiar primer nivel (memoria del EntityManager actual)
        entityManager.clear();

        // Limpiar segundo nivel (si está habilitado)
        EntityManagerFactory emf = ConectionEntityManager.getEntityManagerFactory();
        Cache cache = emf.getCache();
        if (cache != null) {
            cache.evictAll();
            System.out.println("Caché limpiada exitosamente (primer y segundo nivel).");
        } else {
            System.out.println("No se encontró caché de segundo nivel.");
        }
    }
}
