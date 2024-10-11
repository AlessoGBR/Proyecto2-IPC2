/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServiciosRest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author alesso
 */
public class EtiquetaService {

    @PersistenceContext
    private EntityManager entityManager;

     public List<Etiqueta> obtenerTodasLasEtiquetas() {
        String jpql = "SELECT e FROM Etiqueta e";
        TypedQuery<Etiqueta> query = entityManager.createQuery(jpql, Etiqueta.class);
        return query.getResultList();
    }
}
