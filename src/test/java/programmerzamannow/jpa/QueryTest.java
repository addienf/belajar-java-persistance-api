package programmerzamannow.jpa;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Brand;
import programmerzamannow.jpa.entity.Member;
import programmerzamannow.jpa.entity.Product;
import programmerzamannow.jpa.entity.User;

import java.util.List;

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
}
