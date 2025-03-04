package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Customer;
import programmerzamannow.jpa.util.JpaUtil;

public class CRUDTest {
    EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    @Test
    void insert() {
        entityTransaction.begin();

        Customer customer = new Customer();
        customer.setId("1");
        customer.setName("Addien");

        entityManager.persist(customer);

        entityTransaction.commit();
        entityManagerFactory.close();
        entityManager.close();
    }

    @Test
    void find() {
        entityTransaction.begin();

        Customer customer = entityManager.find(Customer.class, "1");
        Assertions.assertEquals("1", customer.getId());
        Assertions.assertEquals("Addien", customer.getName());

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void update() {
        entityTransaction.begin();

        Customer customer = entityManager.find(Customer.class, "1");
        customer.setName("Fauzan Addien");
        entityManager.merge(customer);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void remove() {
        entityTransaction.begin();

        Customer customer = entityManager.find(Customer.class, "1");
        entityManager.remove(customer);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
