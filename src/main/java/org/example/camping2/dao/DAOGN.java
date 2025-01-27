package org.example.camping2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.example.camping2.conection.ConectionEntityManager;
import org.example.camping2.dto.Cliente;
import org.hibernate.exception.ConstraintViolationException;


import java.time.LocalDate;
import java.util.List;

public class DAOGN<T,ID> {

    public EntityManager entityManager;
    public Class<T> entityClass;

    public DAOGN(Class<T> entityClass) {
        entityManager = ConectionEntityManager.getConectionEntityManager();
        this.entityClass = entityClass;
    }
    public void add(T objecto) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(objecto);
            entityManager.getTransaction().commit();
        } catch (ConstraintViolationException cve) {
            entityManager.getTransaction().rollback();
            System.err.println("Violación de restricción: " + cve.getMessage());
            throw cve; // Opcional: vuelve a lanzar para manejarlo en un nivel superior
        } catch (PersistenceException pe) {
            entityManager.getTransaction().rollback();
            System.err.println("Error de persistencia: " + pe.getMessage());
            throw pe; // Opcional: vuelve a lanzar para manejarlo en un nivel superior
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Error inesperado: " + e.getMessage());
            throw new RuntimeException("Ocurrió un error al agregar el objeto", e);
        }
    }


    public boolean update(T objecto) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(objecto); // Actualiza el objeto en la base de datos
            entityManager.getTransaction().commit();
            return true; // Indica que la operación fue exitosa
        } catch (Exception e) {
            entityManager.getTransaction().rollback(); // Realiza rollback en caso de error
            System.err.println("Error al actualizar el objeto: " + e.getMessage());
            return false; // Indica que la operación falló
        }
    }

    public void delete(ID id){
     try{
         entityManager.getTransaction().begin();
        T objeto = entityManager.find(entityClass, id);
        entityManager.remove(objeto);
        entityManager.getTransaction().commit();
    } catch (Exception e) {
        entityManager.getTransaction().rollback();
        throw e;
    }
    }

    public T findById(ID id){
        return entityManager.find(entityClass, id);
    }
    public List<T> findAll(){
        return entityManager.createQuery("from " + entityClass.getSimpleName()).getResultList();
    }

    public static void main(String[] args) {
        DAOGN<Cliente, Integer> daoGN = new DAOGN<>(Cliente.class);
        daoGN.findAll().forEach(System.out::println);
        daoGN.add(new Cliente("Andrews", "Dos Ramos","19234567A", "andrewsdosramos@gmail.com", "634127185", LocalDate.now(), "ACTIVO",""));
    }
}
