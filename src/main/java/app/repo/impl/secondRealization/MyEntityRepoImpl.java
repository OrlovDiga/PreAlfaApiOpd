package app.repo.impl.secondRealization;

import app.domain.secondRealization.MyEntity;
import app.repo.secondRealization.MyEntityRepo;
import app.utils.HibernateEntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

public class MyEntityRepoImpl<T extends MyEntity> implements MyEntityRepo<T> {

    private EntityManager manager = HibernateEntityManagerFactoryUtil.getManagerFactory().createEntityManager();
    private Class<T> clazz;


    public Class<T> getClazz() {
        return clazz;
    }


    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void save(T entity) {
        manager.getTransaction().begin();
        manager.persist(entity);
        manager.getTransaction().commit();
    }


    @Override
    public void update(T entity) {
        manager.getTransaction().begin();
        manager.merge(entity);
        manager.getTransaction().commit();
    }


    @Override
    public void delete(T entity) {
        manager.getTransaction().begin();
        manager.remove(entity);
        manager.getTransaction().commit();
    }


    @Override
    public T findById(int entityId) {
        manager.getTransaction().begin();
        T result = manager.find(clazz, entityId);

        if (result == null) {
            throw new EntityNotFoundException("Can't find" + clazz + "Actor for ID" + entityId);
        }

        manager.getTransaction().commit();

        return result;
    }


    @Override
    public List<T> findAll() {
        manager.getTransaction().begin();
        List<T> result = manager.createQuery("FROM " + clazz).getResultList();
        manager.getTransaction().commit();

        return result;
    }
}
