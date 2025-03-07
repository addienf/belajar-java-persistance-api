package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Brand;
import programmerzamannow.jpa.util.JpaUtil;

import java.time.LocalDateTime;

public class ManagedEntityTest {
    EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    @Test
    void manageEntityTest() {
        entityTransaction.begin();

        Brand brand = new Brand();
        brand.setId("apple");
        brand.setName("Apple");
        entityManager.persist(brand);

        brand.setDescription("Apple Internasional");

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void detachEntityTest() {
        entityTransaction.begin();

        Brand brand = entityManager.find(Brand.class, "apple");
        entityManager.detach(brand);
        brand.setName("Apple Indonesia");

        entityTransaction.commit();
        entityManager.close();
    }
}
