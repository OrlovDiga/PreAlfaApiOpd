package app.repo.impl;

import app.domain.Actor;
import app.domain.Photo;
import app.repo.ActorRepo;
import app.utils.HibernateEntityManagerFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

public class ActorRepoImpl implements ActorRepo {

    @Override
    public Actor findById(int id) {
        Actor actor = HibernateEntityManagerFactoryUtil.getManagerFactory().createEntityManager().find(Actor.class, id);

        if (actor == null) {
            throw new EntityNotFoundException("Can't find Actor for ID" + id);
        }

        return actor;
    }

    @Override
    public List<Actor> findAll() {
        EntityManager manager = HibernateEntityManagerFactoryUtil.getManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        List<Actor> result = manager.createQuery("FROM actor", Actor.class).getResultList();
        manager.getTransaction().commit();
        manager.close();
        return result;
    }

    @Override
    public void save(Actor actor) {
        EntityManager manager = HibernateEntityManagerFactoryUtil.getManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        manager.persist(actor);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void update(Actor actor) {
        EntityManager manager = HibernateEntityManagerFactoryUtil.getManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        manager.merge(actor);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void delete(Actor actor) {
        EntityManager manager = HibernateEntityManagerFactoryUtil.getManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        manager.remove(actor);
        manager.getTransaction().commit();
        manager.close();

    }
}
