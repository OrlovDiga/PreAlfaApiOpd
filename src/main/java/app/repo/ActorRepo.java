package app.repo;

import app.domain.Actor;
import app.domain.Photo;

import java.util.List;

public interface ActorRepo {

    public Actor findById(int id);
    public List<Actor> findAll();
    public void save(Actor actor);
    public void update(Actor actor);
    public void delete(Actor actor);



}
