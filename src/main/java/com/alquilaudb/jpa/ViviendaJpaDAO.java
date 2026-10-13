package com.alquilaudb.jpa;

import com.alquilaudb.model.Vivienda;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@ApplicationScoped
public class ViviendaJpaDAO {

    private EntityManagerFactory emf;

    @PostConstruct
    public void iniciar() {
        emf = Persistence.createEntityManagerFactory("AlquilaUDBPU");
    }

    // CONSULTAR TODAS LAS VIVIENDAS
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

    // BUSCAR UNA VIVIENDA POR ID
    public Vivienda buscarPorId(int id) {

        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Vivienda.class, id);

        } finally {
            em.close();
        }
    }

    // REGISTRAR UNA VIVIENDA
    public void guardar(Vivienda vivienda) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            em.persist(vivienda);

            tx.commit();

        } catch (RuntimeException e) {

            if (tx.isActive()) {
                tx.rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    // ACTUALIZAR UNA VIVIENDA
    public void actualizar(Vivienda vivienda) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Vivienda existente = em.find(
                    Vivienda.class,
                    vivienda.getId()
            );

            if (existente == null) {
                throw new IllegalArgumentException(
                        "La vivienda que se intenta actualizar no existe."
                );
            }

            em.merge(vivienda);

            tx.commit();

        } catch (RuntimeException e) {

            if (tx.isActive()) {
                tx.rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    // ELIMINAR UNA VIVIENDA
    public void eliminar(int id) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Vivienda vivienda = em.find(Vivienda.class, id);

            if (vivienda == null) {
                throw new IllegalArgumentException(
                        "No existe una vivienda con el ID: " + id
                );
            }

            em.remove(vivienda);

            tx.commit();

        } catch (RuntimeException e) {

            if (tx.isActive()) {
                tx.rollback();
            }

            throw e;

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
