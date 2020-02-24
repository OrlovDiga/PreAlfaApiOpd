package app.domain;

import app.domain.secondRealization.MyEntity;

import javax.persistence.*;

@Entity
public class Photo implements MyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String path;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Actor actor;

    public Photo() {
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
