package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Member;
import programmerzamannow.jpa.entity.Name;
import programmerzamannow.jpa.util.JpaUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class MemberTest {
    EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    @Test
    void insertMember() {
        entityTransaction.begin();

        Member member = new Member();
        member.setEmail("test1@gmail.com");

        Name name = new Name();
        name.setTitle("Mr");
        name.setFirstName("First");
        name.setMiddleName("Middle");
        name.setLastName("Last");

        member.setName(name);

        entityManager.persist(member);

        entityTransaction.commit();

        entityManager.close();;
    }

    @Test
    void insertJoinTest() {
        entityTransaction.begin();

        Name name = new Name();
        name.setTitle("Mr");
        name.setFirstName("Fauzan");
        name.setMiddleName("Addien");
        name.setLastName("A");

        Member member = new Member();
        member.setName(name);
        member.setEmail("test1@gmail.com");

        member.setHobbies(new ArrayList<>());
        member.getHobbies().add("Coding");
        member.getHobbies().add("Gaming");

        entityManager.persist(member);
        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void update() {
        entityTransaction.begin();

        Member member = entityManager.find(Member.class, 2);
        member.getHobbies().add("Traveling");



        entityManager.merge(member);
        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void insertMap() {
        entityTransaction.begin();

        Name name = new Name();
        name.setTitle("Mr");
        name.setFirstName("Addien");
        name.setMiddleName("Fauzan");
        name.setLastName("A");

        Member member = new Member();
        member.setName(name);
        member.setEmail("test2@gmail.com");

        member.setSkills(new HashMap<>());
        member.getSkills().put("Java", 90);
        member.getSkills().put("Golang", 90);
        member.getSkills().put("PHP", 85);

        entityManager.persist(member);
        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void findMemberTransient() {
        entityTransaction.begin();

        Member member = entityManager.find(Member.class, 1);
        Assertions.assertEquals("Mr. First Middle Last", member.getFullname());

        entityTransaction.commit();
        entityManager.close();
    }
}
