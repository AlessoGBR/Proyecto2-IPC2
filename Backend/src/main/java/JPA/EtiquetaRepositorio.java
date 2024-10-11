/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import Backend.Etiqueta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 *
 * @author alesso
 */
public class EtiquetaRepositorio {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Etiqueta> findAll() {
        TypedQuery<Etiqueta> query = entityManager.createQuery("SELECT * FROM Etiqueta", Etiqueta.class);
        List<Etiqueta> etiquetas = query.getResultList();

        for (Etiqueta etiqueta : etiquetas) {
            System.out.println("Etiqueta obtenida: " + etiqueta.getNombre());
        }

        return etiquetas;
    }
}
