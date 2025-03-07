package programmerzamannow.jpa;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.*;

import java.util.List;
import java.util.Objects;

public class QueryTest {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BELAJAR");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    @Test
    void select() {
        TypedQuery<Brand> query = entityManager.createQuery("select b from Brand b", Brand.class);
        List<Brand> brands = query.getResultList();

        for (Brand brand : brands){
            System.out.println(brand.getName());
        }
    }

    @Test
    void where() {
        TypedQuery<Member> query = entityManager
                .createQuery("select m from Member m where m.name.firstName = :firstName"
                        + "and m.name.lastName = :lastName", Member.class);
        query.setParameter("firstName", "Addien");
        query.setParameter("lastName", "Fauzan");
        List<Member> members = query.getResultList();

        for (Member member : members){
            System.out.println(member.getName());
        }
    }

    @Test
    void join() {
        TypedQuery<Product> query = entityManager
                .createQuery("select p from Product p join p.brand b where b.name = :brand", Product.class);
        query.setParameter("brand", "Samsung");

        List<Product> products = query.getResultList();

        for (var product : products){
            System.out.println(product.getName());
        }
    }

    @Test
    void joinFetch() {
        TypedQuery<User> query = entityManager
                .createQuery("select u from User u join fetch u.likes p where p.name = :product", User.class);
        query.setParameter("product", "Samsung Galaxy 1");

        List<User> users = query.getResultList();

        for (var user : users){
            System.out.println(user.getName());
            for (var product : user.getLikes()){
                System.out.println(product.getName());
            }
        }
    }

    @Test
    void order() {
        TypedQuery<Brand> query = entityManager.createQuery("select b from Brand b order by b.name desc", Brand.class);
        List<Brand> brands = query.getResultList();

        for (Brand brand : brands){
            System.out.println(brand.getName());
        }
    }

    @Test
    void limitOffset() {
        TypedQuery<Brand> query = entityManager.createQuery("select b from Brand b order by b.name desc", Brand.class);
        query.setFirstResult(10);
        query.setMaxResults(10);

        List<Brand> brands = query.getResultList();

        for (Brand brand : brands){
            System.out.println(brand.getName());
        }
    }

    @Test
    void namedQuery() {
        TypedQuery<Brand> query = entityManager.createNamedQuery("Brand.findAllByName", Brand.class);
        query.setParameter("name", "Xiaomi");
        List<Brand> brands = query.getResultList();

        for (Brand brand : brands){
            System.out.println(brand.getName());
        }
    }

    @Test
    void someFields() {
        TypedQuery<Object[]> query = entityManager
                .createQuery("select b.id, b.name from Brand b where b.name = :name", Object[].class);
        query.setParameter("name", "Xiaomi");
        List<Object[]> objects = query.getResultList();

        for (Object[] object : objects){
            System.out.println(object[1]);
        }
    }

    @Test
    void constructorExpression() {
        TypedQuery<SimpleBrand> query = entityManager.createQuery(
           "select new programmerzamannow.jpa.entity.SimpleBrand(b.id, b.name)" +
                   "from Brand b where b.name = :name", SimpleBrand.class
        );

        query.setParameter("name", "Xiaomi");

        List<SimpleBrand> simpleBrands = query.getResultList();

        for (var simpleBrand : simpleBrands){
            System.out.println(simpleBrand.getName());
        }
    }

    @Test
    void aggregateFunction() {

        TypedQuery<Object[]> query = entityManager.createQuery(
                "select min(p.price), max(p.price), avg(p.price) from Product p", Object[].class
        );

        Object[] result = query.getSingleResult();

        System.out.println("Min " + result[0]);
        System.out.println("Max " + result[1]);
        System.out.println("Average " + result[2]);
    }

    @Test
    void groupByDanHaving() {

        TypedQuery<Object[]> query = entityManager.createQuery(
                "select min(p.price), max(p.price), avg(p.price) from Product p join p.brand b" +
                "", Object[].class
        );

        Object[] result = query.getSingleResult();

        System.out.println("Min " + result[0]);
        System.out.println("Max " + result[1]);
        System.out.println("Average " + result[2]);
    }
}
