package app.services;

import app.domain.Actor;
import app.repo.impl.ActorRepoImpl;

import java.util.Date;
import java.util.List;

public enum  ActorService {
    INSTANCE;

    private ActorRepoImpl actorRepo = new ActorRepoImpl();

    private ActorService() {
    }

    public void saveActor(Actor actor) {
        actor.setLastUpdate(new Date());
        actorRepo.save(actor);
    }

    public void updateActor(Actor actor) {
        actor.setLastUpdate(new Date());
        actorRepo.update(actor);
    }

    public void deleteActor(Actor actor) {
        actorRepo.delete(actor);
    }

    public List<Actor> findAllActors() {
        return actorRepo.findAll();
    }

    public Actor findActorById(int id) {
        return actorRepo.findById(id);
    }
}
