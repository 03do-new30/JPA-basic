package hellojpa;

import jakarta.persistence.*;

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

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            em.persist(member2);

            em.flush();
            em.clear(); // 영속성 컨텍스트 깔끔하게

            Member m1 = em.find(Member.class, member1.getId()); // 영속성 컨텍스트에 올라감
            System.out.println("m1.getClass() = " + m1.getClass());

            Member reference = em.getReference(Member.class, member1.getId());
            // 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티 반환
            System.out.println("reference.getClass() = " + reference.getClass());

            System.out.println("JPA, 트랜잭션에서 a == a 보장: " + (m1 == reference));

            tx.commit(); // 커밋 시점에 INSERT (버퍼링 가능)
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 영속성 컨텍스트를 종료
        }
        emf.close();
    }
}
