package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Usuario;
import org.example.util.JPAUtil;

import java.util.List;

public class UsuarioRepositoryImpl implements UsuarioRepository {

    @Override
    public void salvar(Usuario usuario) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(usuario);

            em.getTransaction().commit();

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    @Override
    public List<Usuario> listarTodos() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "FROM Usuario", Usuario.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    @Override
    public Usuario buscarPorId(long id) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Usuario.class, id);

        } finally {
            em.close();
        }
    }

    @Override
    public void atualizar(Usuario usuario) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            em.merge(usuario);

            em.getTransaction().commit();

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    @Override
    public boolean remover(long id) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            Usuario usuario = em.find(Usuario.class, id);

            if (usuario == null) {
                return false;
            }

            em.getTransaction().begin();

            em.remove(usuario);

            em.getTransaction().commit();

            return true;

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }
}