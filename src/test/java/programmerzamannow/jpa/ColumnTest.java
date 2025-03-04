package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Customer;
import programmerzamannow.jpa.util.JpaUtil;

public class ColumnTest {
    EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    @Test
    void column() {
        entityTransaction.begin();

        Customer customer = new Customer();
        customer.setId("1");
        customer.setName("Addien");
        customer.setPrimaryEmail("contoh@example.com");

        entityManager.persist(customer);
        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();

    }
}
