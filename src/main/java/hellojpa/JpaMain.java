package hellojpa;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        //code
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Address address = new Address("city", "street", "1000");

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setHomeAddress(address);

            em.persist(member1);

            // Address가 불변객체이면 member1의 주소는 어떻게 수정해?
            // 이렇게 ^^
            Address newAddress = new Address("NewCity", address.getStreet(), address.getZipcode());
            member1.setHomeAddress(newAddress);

            tx.commit(); // 커밋 시점에 INSERT (버퍼링 가능)
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close(); // 영속성 컨텍스트를 종료
        }
        emf.close();
    }
}
