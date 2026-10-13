package com.alquilaudb.jpa;

import com.alquilaudb.model.Vivienda;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class ViviendaJpaDAO {

    private EntityManagerFactory emf;

    @PostConstruct
    public void iniciar() {
        emf = Persistence.createEntityManagerFactory("AlquilaUDBPU");
    }

    public List<Vivienda> listar() {

        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT v FROM Vivienda v ORDER BY v.id",
                    Vivienda.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    @PreDestroy
    public void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}

