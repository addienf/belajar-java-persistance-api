package programmerzamannow.jpa;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Brand;
import programmerzamannow.jpa.entity.Product;
import programmerzamannow.jpa.entity.SimpleBrand;
import programmerzamannow.jpa.util.JpaUtil;

import java.util.List;

public class CriteriaTest {
    EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    @Test
    void createCriteria() {
        entityTransaction.begin();

        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> root = criteriaQuery.from(Brand.class);
        criteriaQuery.select(root);

        TypedQuery<Brand> query = entityManager.createQuery(criteriaQuery);
        List<Brand> brands = query.getResultList();

        for (var brand : brands){
            System.out.println(brand.getId());
        }
    }

    @Test
    void nonEntityCreate() {
        CriteriaQuery<Object[]> criteria = criteriaBuilder.createQuery(Object[].class);
        Root<Brand> root = criteria.from(Brand.class);
        criteria.select(criteriaBuilder.array(root.get("id"), root.get("name")));

        TypedQuery<Object[]> query = entityManager.createQuery(criteria);
        List<Object[]> objects = query.getResultList();

        for (var object : objects){
            System.out.println("Id : " + object[0]);
            System.out.println("Name : " + object[1]);
        }
    }

    @Test
    void constructExpression() {
        CriteriaQuery<SimpleBrand> criteria = criteriaBuilder.createQuery(SimpleBrand.class);
        Root<Brand> root = criteria.from(Brand.class);
        criteria.select(criteriaBuilder.construct(SimpleBrand.class, root.get("id"), root.get("name")));

        TypedQuery<SimpleBrand> query = entityManager.createQuery(criteria);
        List<SimpleBrand> objects = query.getResultList();

        for (var object : objects){
            System.out.println(object.getId() + " : " + object.getName());
        }
    }

    @Test
    void whereCriteria() {
        entityTransaction.begin();

        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> root = criteriaQuery.from(Brand.class);

        criteriaQuery.where(
            criteriaBuilder.equal(root.get("name"), "Xiaomi"),
            criteriaBuilder.isNotNull(root.get("createdAt"))
        );

        TypedQuery<Brand> query = entityManager.createQuery(criteriaQuery);
        List<Brand> brands = query.getResultList();

        for (var brand : brands){
            System.out.println(brand.getId());
        }
    }

    @Test
    void whereOrCriteria() {
        entityTransaction.begin();

        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> root = criteriaQuery.from(Brand.class);

        criteriaQuery.where(
                criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("name"), "Xiaomi"),
                        criteriaBuilder.equal(root.get("name"), "Samsung")
                )
        );

        TypedQuery<Brand> query = entityManager.createQuery(criteriaQuery);
        List<Brand> brands = query.getResultList();

        for (var brand : brands){
            System.out.println(brand.getId());
        }
    }

    @Test
    void joinCriteria() {
        entityTransaction.begin();

        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        Join<Product, Brand> brandRoot = productRoot.join("brand");

        criteriaQuery.select(productRoot);
        criteriaQuery.where(criteriaBuilder.equal(brandRoot.get("name"), "Samsung"));

        TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
        List<Product> products = query.getResultList();

        for (var brand : products){
            System.out.println(brand.getName());
        }
    }

    @Test
    void criteriaParameter() {
        entityTransaction.begin();

        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        Join<Product, Brand> brandRoot = productRoot.join("brand");

        ParameterExpression<String> nameParameter = criteriaBuilder.parameter(String.class);

        criteriaQuery.select(productRoot);
        criteriaQuery.where(criteriaBuilder.equal(brandRoot.get("name"), nameParameter));

        TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(nameParameter, "Samsung");

        List<Product> products = query.getResultList();
        for (var brand : products){
            System.out.println(brand.getName());
        }
    }

    @Test
    void aggregateCriteria() {
        CriteriaQuery<Object[]> criteria = criteriaBuilder.createQuery(Object[].class);
        Root<Product> productRoot = criteria.from(Product.class);
        Join<Product, Brand> brandRoot = productRoot.join("brand");

        criteria.select(
                criteriaBuilder.array(
                        brandRoot.get("id"), criteriaBuilder.min(productRoot.get("price")),
                        criteriaBuilder.max(productRoot.get("price")), criteriaBuilder.avg(productRoot.get("price"))
                )
        );

        criteria.groupBy(brandRoot.get("id"));
        criteria.having(criteriaBuilder.greaterThan(criteriaBuilder.min(productRoot.get("price")), 500_000L));

        TypedQuery<Object[]> query = entityManager.createQuery(criteria);
        List<Object[]> objects = query.getResultList();

        for (var object : objects){
            System.out.println("Brand : " + object[0]);
            System.out.println("Min : " +object[1]);
            System.out.println("Max : " +object[2]);
            System.out.println("Average : " +object[3]);
        }
    }

    @Test
    void criteriaUpdate() {
        CriteriaUpdate<Brand> criteria = criteriaBuilder.createCriteriaUpdate(Brand.class);
        Root<Brand> brandRoot = criteria.from(Brand.class);

        criteria.set(brandRoot.get("name"), "Apple Update");
        criteria.where(criteriaBuilder.equal(brandRoot.get("id"), "apple"));

        Query query = entityManager.createQuery(criteria);
        int impactedRecords = query.executeUpdate();
        System.out.println("Success Update" + impactedRecords + " records");
    }
}
