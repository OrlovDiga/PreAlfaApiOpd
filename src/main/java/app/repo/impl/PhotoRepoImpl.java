package app.repo.impl;

import app.domain.Photo;
import app.repo.PhotoRepo;
import app.utils.HibernateEntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

public class PhotoRepoImpl implements PhotoRepo {

    @Override
    public void save(Photo photo) {
        EntityManager manager = HibernateEntityManagerFactoryUtil.getManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        manager.persist(photo);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void delete(Photo photo) {
        EntityManager manager = HibernateEntityManagerFactoryUtil.getManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        manager.remove(photo);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void update(Photo photo) {
        EntityManager manager = HibernateEntityManagerFactoryUtil.getManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        manager.merge(photo);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public List<Photo> findAll() {
        EntityManager manager = HibernateEntityManagerFactoryUtil.getManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        List<Photo> result = manager.createQuery("FROM photo", Photo.class).getResultList();
        manager.getTransaction().commit();
        manager.close();
        return result;
    }

    @Override
    public Photo findById(int photoId) {
        EntityManager manager = HibernateEntityManagerFactoryUtil.getManagerFactory().createEntityManager();
        Photo photo = manager.find(Photo.class, photoId);

        if (photo == null) {
            throw new EntityNotFoundException("Can't find Photo for ID" + photoId);
        }

        return photo;
    }
}
