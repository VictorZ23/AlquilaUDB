package com.alquilaudb.jpa;

import com.alquilaudb.model.Resena;
import com.alquilaudb.model.Vivienda;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

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

    // BUSCAR VIVIENDAS CON FILTROS
    public List<Vivienda> buscar(
            String texto,
            String tipo,
            boolean soloDisponibles) {

        EntityManager em = emf.createEntityManager();

        try {

            boolean tieneTexto =
                    texto != null && !texto.trim().isEmpty();

            boolean tieneTipo =
                    tipo != null && !tipo.trim().isEmpty();

            StringBuilder jpql = new StringBuilder(
                    "SELECT v FROM Vivienda v WHERE 1 = 1"
            );

            if (tieneTexto) {
                jpql.append(
                        " AND (LOWER(v.titulo) LIKE :texto "
                        + "OR LOWER(v.zona) LIKE :texto)"
                );
            }

            if (tieneTipo) {
                jpql.append(" AND v.tipo = :tipo");
            }

            if (soloDisponibles) {
                jpql.append(" AND v.disponible = true");
            }

            jpql.append(" ORDER BY v.id");

            TypedQuery<Vivienda> consulta =
                    em.createQuery(
                            jpql.toString(),
                            Vivienda.class
                    );

            if (tieneTexto) {
                consulta.setParameter(
                        "texto",
                        "%" + texto.trim().toLowerCase(Locale.ROOT) + "%"
                );
            }

            if (tieneTipo) {
                consulta.setParameter(
                        "tipo",
                        tipo.trim()
                );
            }

            return consulta.getResultList();

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

            Vivienda vivienda = em.find(
                    Vivienda.class,
                    id
            );

            if (vivienda == null) {
                throw new IllegalArgumentException(
                        "La vivienda seleccionada ya no existe."
                );
            }

            // VERIFICAR SI EXISTEN RESEÑAS ASOCIADAS
            Long cantidadResenas = em.createQuery(
                    "SELECT COUNT(r) FROM Resena r "
                    + "WHERE r.vivienda.id = :viviendaId",
                    Long.class
            )
            .setParameter("viviendaId", id)
            .getSingleResult();

            if (cantidadResenas > 0) {
                throw new IllegalStateException(
                        "No se puede eliminar esta vivienda "
                        + "porque tiene reseñas asociadas."
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

    // CONSULTAR RESEÑAS DE UNA VIVIENDA
    public List<Resena> listarResenas(int viviendaId) {

        EntityManager em = emf.createEntityManager();

        try {

            return em.createQuery(
                    "SELECT r FROM Resena r "
                    + "WHERE r.vivienda.id = :viviendaId "
                    + "ORDER BY r.fecha DESC, r.id DESC",
                    Resena.class
            )
            .setParameter("viviendaId", viviendaId)
            .getResultList();

        } finally {
            em.close();
        }
    }

    // REGISTRAR UNA RESEÑA
    public void guardarResena(int viviendaId, Resena resena) {

        if (resena == null) {
            throw new IllegalArgumentException(
                    "La reseña no puede ser nula."
            );
        }

        if (resena.getAutor() == null
                || resena.getAutor().trim().length() < 2
                || resena.getAutor().trim().length() > 100) {

            throw new IllegalArgumentException(
                    "El autor debe tener entre 2 y 100 caracteres."
            );
        }

        if (resena.getCalificacion() < 1
                || resena.getCalificacion() > 5) {

            throw new IllegalArgumentException(
                    "La calificación debe estar entre 1 y 5."
            );
        }

        if (resena.getComentario() == null
                || resena.getComentario().trim().length() < 10
                || resena.getComentario().trim().length() > 1000) {

            throw new IllegalArgumentException(
                    "El comentario debe tener entre 10 y 1000 caracteres."
            );
        }

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();

            Vivienda vivienda = em.find(
                    Vivienda.class,
                    viviendaId
            );

            if (vivienda == null) {
                throw new IllegalArgumentException(
                        "No existe la vivienda indicada."
                );
            }

            resena.setAutor(resena.getAutor().trim());

            resena.setComentario(
                    resena.getComentario().trim()
            );

            resena.setVivienda(vivienda);

            em.persist(resena);

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

    // LIBERAR LOS RECURSOS DE JPA
    @PreDestroy
    public void cerrar() {

        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}