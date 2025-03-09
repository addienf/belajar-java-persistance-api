package programmerzamannow.jpa;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.util.JpaUtil;

public class NonQueryTest {
    EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    @Test
    void nonQuery() {
        entityTransaction.begin();

        Query query = entityManager.createQuery("update Brand b set b.name = :name, b.description = :description where b.id = :id");
        query.setParameter("name", "Samsung Updated");
        query.setParameter("description", "Samsung Global");
        query.setParameter("id", "samsung");
        int impactedRecord = query.executeUpdate();
        System.out.println("Success Updated" + impactedRecord + " records");

        entityTransaction.commit();
        entityManager.close();
    }
}
