package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Brand;
import programmerzamannow.jpa.entity.Product;
import programmerzamannow.jpa.util.JpaUtil;

public class OneToManyTest {
    EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    @Test
    void insertBrandAndProduct() {

        entityTransaction.begin();

        Brand brand = new Brand();
        brand.setId("samsung");
        brand.setName("Samsung");
        entityManager.persist(brand);

        Product product1 = new Product();
        product1.setId("p1");
        product1.setName("Samsung Galaxy 1");
        product1.setBrand(brand);
        product1.setPrice(1_000_000L);
        entityManager.persist(product1);

        Product product2 = new Product();
        product2.setId("p2");
        product2.setName("Samsung Galaxy 2");
        product2.setBrand(brand);
        product2.setPrice(2_000_000L);
        entityManager.persist(product2);

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void find() {
        Brand brand = entityManager.find(Brand.class, "samsung");
        Assertions.assertEquals(2, brand.getProduct().size());

        brand.getProduct().forEach(product -> System.out.println(product.getName()));
    }
}
