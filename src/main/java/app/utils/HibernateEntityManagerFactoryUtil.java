package app.utils;

import app.domain.Actor;
import app.domain.Photo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateEntityManagerFactoryUtil {
    private static EntityManagerFactory entityManagerFactory;

    private HibernateEntityManagerFactoryUtil() {
    }

    public static EntityManagerFactory getManagerFactory() {
        if (entityManagerFactory == null) {
            try {
                entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }

        return entityManagerFactory;
    }
}
