package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Category;
import programmerzamannow.jpa.util.JpaUtil;

import java.time.LocalDateTime;
import java.util.Calendar;

public class CategoryTest {

    EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    @Test
    void insertCategory() {
        entityTransaction.begin();

        Category category = new Category();
        category.setName("Food");
        category.setDescription("Diskon 60%");
        category.setCreatedAt(Calendar.getInstance());
        category.setUpdatedAt(LocalDateTime.now());

        entityManager.persist(category);

        entityTransaction.commit();

        entityManager.close();
    }

    @Test
    void insertListener() {
        entityTransaction.begin();

        Category category = new Category();
        category.setName("Contoh");
        category.setDescription("Contoh Desc");

        entityManager.persist(category);
        Assertions.assertNotNull(category.getUpdatedAt());

        entityTransaction.commit();
        entityManager.close();
    }
}
