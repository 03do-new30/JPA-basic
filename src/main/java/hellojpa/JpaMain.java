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

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Address("old1", "street", "10000"));
            member.getAddressHistory().add(new Address("old2", "street", "10000"));

            // 값 타입 컬렉션의 라이프사이클이 멤버에 의존
            em.persist(member);

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
