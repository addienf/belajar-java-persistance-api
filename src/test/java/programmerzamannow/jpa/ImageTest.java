package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Image;
import programmerzamannow.jpa.util.JpaUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageTest {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BELAJAR");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    @Test
    void insertImage() throws IOException {

        entityTransaction.begin();

        Image image = new Image();
        image.setName("Contoh Image");
        image.setDescription("Contoh Desc Image");

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("images/sample.png");
        if (inputStream == null) {
            throw new IllegalArgumentException("❌ File tidak ditemukan di classpath!");
        }

        byte[] bytes = inputStream.readAllBytes();
//        byte[] bytes = Files.readAllBytes(Path.of(getClass().getResource("images/sample.png").getPath()));
        image.setImage(bytes);

        entityManager.persist(image);

        entityTransaction.commit();
        entityManager.close();
    }
}
