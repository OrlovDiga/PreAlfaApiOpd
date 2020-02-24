package app.services;

import app.domain.Photo;
import app.repo.impl.PhotoRepoImpl;

import java.util.List;

public enum  PhotoService {
    INSTANCE;

    private PhotoRepoImpl photoRepo = new PhotoRepoImpl();

    private PhotoService() {
    }

    public void savePhoto(Photo photo) {
        photoRepo.save(photo);
    }

    public void updatePhoto(Photo photo) {
        photoRepo.update(photo);
    }

    public void deletePhoto(Photo photo) {
        photoRepo.delete(photo);
    }

    public List<Photo> findAllPhoto() {
        return photoRepo.findAll();
    }

    public Photo findPhotoById(int id) {
        return photoRepo.findById(id);
    }
}
