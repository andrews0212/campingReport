package org.example.camping2.memoria;


import jakarta.persistence.PersistenceException;
import org.example.camping2.dao.DAOGN;
import org.example.camping2.dto.Acompañante;
import org.example.camping2.dto.Cliente;
import org.example.camping2.dto.Recurso;
import org.example.camping2.dto.Reserva;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class Memoria<T, ID> {

    public DAOGN<T, ID> daogn;
    public Class<T> entityClass;
    public List<T> lista;

    public Memoria(Class<T> entityClass) {
        this.daogn = new DAOGN<T, ID>(entityClass);
        this.entityClass = entityClass;
        this.lista = daogn.findAll();
    }

    public void add(T objecto) {
        daogn.add(objecto);
        lista.add(objecto);
    }

    public boolean update(T objecto) {
        try {
            daogn.update(objecto); // Llamada al método de actualización en el DAO
            lista.remove(objecto); // Actualiza la lista local
            lista.add(objecto);
            return true; // La operación fue exitosa
        } catch (Exception e) {
            // Muestra el error en la consola o realiza un registro con un logger
            System.err.println("Error al actualizar el objeto: " + e.getMessage());
            return false; // La operación falló
        }
    }

    public boolean delete(ID id) {
        T objeto = findById(id);
        if (objeto != null) {
            daogn.delete(id);  // Eliminar el objeto desde la base de datos (o la fuente de datos correspondiente)
            lista.remove(objeto);  // Eliminar el objeto de la lista
            return true;  // Indicamos que la eliminación fue exitosa
        }
        return false;  // Si no se encuentra el objeto, devolvemos false
    }

    public T findById(ID id) {
        for (T objeto : lista) {
            if (entityClass.isAssignableFrom(Cliente.class)) {
                Cliente cliente = (Cliente) objeto;
                if (cliente.getId().equals(id)) {
                    return objeto;
                }
            } else if (entityClass.isAssignableFrom(Recurso.class)) {
                Recurso recurso = (Recurso) objeto;
                if (recurso.getId().equals(id)) {
                    return objeto;
                }

            } else if (entityClass.isAssignableFrom(Acompañante.class)) {
                Acompañante acompañante = (Acompañante) objeto;
                if (acompañante.getId().equals(id)) {
                    return objeto;
                }

            } else if (entityClass.isAssignableFrom(Reserva.class)) {
                Reserva reserva = (Reserva) objeto;
                if (reserva.getId().equals(id)) {
                    return objeto;
                }
            }
        }
        return null;
    }

    public List<T> findAll() {
        return lista;
    }


}
