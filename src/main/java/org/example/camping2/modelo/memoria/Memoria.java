package org.example.camping2.modelo.memoria;


import org.example.camping2.modelo.dao.DAOGN;
import org.example.camping2.modelo.dto.Acompanante;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.dto.Reserva;

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

    public boolean add(T objecto) {
        boolean validar = daogn.add(objecto);
        if (validar){
            lista.add(objecto);
            return validar;
        }
        return validar;

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

            } else if (entityClass.isAssignableFrom(Acompanante.class)) {
                Acompanante acompañante = (Acompanante) objeto;
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
        lista = daogn.findAll();
        return lista;
    }


}
