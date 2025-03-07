package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.*;
import programmerzamannow.jpa.util.JpaUtil;

public class InheritanceTest {
    EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    @Test
    void singleTableInsert() {
        entityTransaction.begin();

        Employee employee = new Employee();
        employee.setId("rina");
        employee.setName("Rina Wati");
        entityManager.persist(employee);

        Manager manager = new Manager();
        manager.setId("joko");
        manager.setName("Joko Widoddow");
        manager.setTotalEmployee(10);
        entityManager.persist(manager);

        VicePresident vp = new VicePresident();
        vp.setId("budi");
        vp.setName("Budi Nugraha");
        vp.setTotalManager(5);
        entityManager.persist(vp);

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void singleTableFind() {
        entityTransaction.begin();

        Employee employee = entityManager.find(Employee.class, "budi");

        VicePresident vicePresident = (VicePresident) employee;
    }

    @Test
    void joinTableInsert() {

        entityTransaction.begin();

        PaymentGopay paymentGopay = new PaymentGopay();
        paymentGopay.setId("gopay1");
        paymentGopay.setGopayId("082116672456");
        paymentGopay.setAmount(100_000L);
        entityManager.persist(paymentGopay);

        PaymentCreditCard paymentCreditCard = new PaymentCreditCard();
        paymentCreditCard.setId("cc1");
        paymentCreditCard.setMaskedCard("4555-5555");
        paymentCreditCard.setAmount(500_000L);
        paymentCreditCard.setBank("BCA");
        entityManager.persist(paymentCreditCard);

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void joinTableFind() {

        entityTransaction.begin();

        Payment payment = entityManager.find(Payment.class, "gopay1");
        PaymentGopay gopay = (PaymentGopay) payment;

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void joinTableFindParent() {

        entityTransaction.begin();

        Payment gopay = entityManager.find(Payment.class, "gopay1");

        entityTransaction.commit();
        entityManager.close();
    }
}
