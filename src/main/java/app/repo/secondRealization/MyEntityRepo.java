package app.repo.secondRealization;

import app.domain.secondRealization.MyEntity;

import java.util.List;

public interface MyEntityRepo <T extends MyEntity> {
    public void save(T entity);
    public void update(T entity);
    public void delete(T entity);
    public T findById(int entityId);
    public List<T> findAll();
}
