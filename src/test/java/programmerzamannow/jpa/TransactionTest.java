package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

public class TransactionTest {
    @Test
    void transaction() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BELAJAR");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            //Do Something

            entityTransaction.commit();
        } catch (Throwable throwable){
            entityTransaction.rollback();
        }

        entityManager.close();
        entityManagerFactory.close();
    }
}
