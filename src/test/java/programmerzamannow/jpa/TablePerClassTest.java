package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Transaction;
import programmerzamannow.jpa.entity.TransactionCredit;
import programmerzamannow.jpa.entity.TransactionDebit;

import java.time.LocalDateTime;

public class TablePerClassTest {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BELAJAR");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    @Test
    void insertTransaction() {
        entityTransaction.begin();

        Transaction transaction = new Transaction();
        transaction.setId("t1");
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setBalance(1_000_000L);
        entityManager.persist(transaction);

        TransactionDebit transactionDebit = new TransactionDebit();
        transactionDebit.setId("t2");
        transactionDebit.setCreatedAt(LocalDateTime.now());
        transactionDebit.setBalance(2_000_000L);
        transactionDebit.setDebitAmount(1_000_000L);
        entityManager.persist(transactionDebit);

        TransactionCredit transactionCredit = new TransactionCredit();
        transactionCredit.setId("t3");
        transactionCredit.setCreatedAt(LocalDateTime.now());
        transactionCredit.setBalance(1_000_000L);
        transactionCredit.setCreditAmount(1_000_000L);
        entityManager.persist(transactionCredit);

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void findChild() {
        entityTransaction.begin();

        TransactionCredit transactionCredit = entityManager.find(TransactionCredit.class, "t3");
        Assertions.assertNotNull(transactionCredit);
    }

    @Test
    void findParent() {
        entityTransaction.begin();

        Transaction transaction = entityManager.find(Transaction.class, "t1");
        Assertions.assertNotNull(transaction);
    }
}
