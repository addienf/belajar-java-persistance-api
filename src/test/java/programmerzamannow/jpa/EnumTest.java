package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Customer;
import programmerzamannow.jpa.entity.CustomerType;
import programmerzamannow.jpa.util.JpaUtil;

public class EnumTest {
    EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    @Test
    void insertEnumTest() {
        entityTransaction.begin();

        Customer customer = new Customer();
        customer.setId("1");
        customer.setName("Fauzan");
        customer.setPrimaryEmail("contoh@example.com");
        customer.setAge((byte) 24);
        customer.setMarriage(true);
        customer.setType(CustomerType.PREMIUM);

        entityManager.persist(customer);

        entityTransaction.commit();
        entityManagerFactory.close();
        entityManager.close();
    }
}
