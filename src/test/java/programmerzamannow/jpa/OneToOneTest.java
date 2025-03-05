package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Credential;
import programmerzamannow.jpa.entity.User;
import programmerzamannow.jpa.entity.Wallet;
import programmerzamannow.jpa.util.JpaUtil;

public class OneToOneTest {
    EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    @Test
    void insertCredentials() {
        entityTransaction.begin();

        Credential credential = new Credential();
        credential.setId("addien");
        credential.setEmail("adn@xmpl.com");
        credential.setPassword("123");

        entityManager.persist(credential);

        User user = new User();
        user.setId("addien");
        user.setName("Fauzan Addien");

        entityManager.persist(user);

        User user1 = entityManager.find(User.class, "addien");
        Assertions.assertNotNull(user1.getCredential());
        Assertions.assertEquals("adn@xmpl.com", user1.getCredential().getEmail());
        Assertions.assertEquals("123", user1.getCredential().getPassword());

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void findCredentials() {
        entityTransaction.begin();

        User user1 = entityManager.find(User.class, "addien");

        Assertions.assertNotNull(user1.getCredential());
        Assertions.assertEquals("adn@xmpl.com", user1.getCredential().getEmail());
        Assertions.assertEquals("123", user1.getCredential().getPassword());

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void saveWallet() {
        entityTransaction.begin();

        User user = entityManager.find(User.class, "addien");

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(1_000_000L);

        entityManager.persist(wallet);

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void getWallet() {
        entityTransaction.begin();

        User user = entityManager.find(User.class, "addien");

        Assertions.assertNotNull(user.getWallet());
        Assertions.assertEquals(1_000_000L, user.getWallet().getBalance());

        entityTransaction.commit();
        entityManager.close();
    }
}
