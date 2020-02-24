package app.repo;

import app.domain.Photo;

import java.util.List;

public interface PhotoRepo {
    public void save(Photo photo);
    public void delete(Photo photo);
    public void update(Photo photo);
    public List<Photo> findAll();
    public Photo findById(int photoId);
}
