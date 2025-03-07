package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Product;
import programmerzamannow.jpa.entity.User;
import programmerzamannow.jpa.util.JpaUtil;

import java.util.HashSet;

public class ManyToManyTest {

    EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    @Test
    void insertUser() {
        entityTransaction.begin();

        User user = entityManager.find(User.class, "addien");
        user.setLikes(new HashSet<>());
        user.getLikes().add(entityManager.find(Product.class, "p1"));
        user.getLikes().add(entityManager.find(Product.class, "p2"));

        entityManager.persist(user);

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void find() {
        User user = entityManager.find(User.class, "addien");
        user.getLikes().forEach(product -> {
            System.out.println(product.getName());
        });
    }
}
