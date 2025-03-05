package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Department;
import programmerzamannow.jpa.entity.DepartmentId;
import programmerzamannow.jpa.util.JpaUtil;

public class DepartmentTest {

    EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    @Test
    void name() {
        entityTransaction.begin();

        DepartmentId id = new DepartmentId();
        id.setCompanyId("pzn");
        id.setDepartmentId("tech");

        Department department = new Department();
        department.setId(id);
        department.setName("Teknologi");

        entityManager.persist(department);

        Department departmentFromDB = entityManager.find(Department.class, id);
        Assertions.assertEquals(department.getName(), departmentFromDB.getName());

        entityTransaction.commit();
        entityManager.close();
    }
}
