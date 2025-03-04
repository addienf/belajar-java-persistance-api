package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Customer;
import programmerzamannow.jpa.util.JpaUtil;

public class DataTypeTest {
    EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    @Test
    void insert2() {
        entityTransaction.begin();

        Customer customer = new Customer();
        customer.setId("2");
        customer.setName("Fauzan");
        customer.setPrimaryEmail("example2@contoh.com");
        customer.setAge((byte) 24);
        customer.setMarriage(true);

        entityManager.persist(customer);

        entityTransaction.commit();
        entityManagerFactory.close();
        entityManager.close();
    }
}
