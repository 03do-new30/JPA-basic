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

            Member member = new Member();
            member.setUsername("hello");

            em.persist(member);

            em.flush();
            em.clear(); // 영속성 컨텍스트 깔끔하게

//            Member findMember = em.find(Member.class, member.getId());
            Member findMember = em.getReference(Member.class, member.getId()); // 호출 시점에는 쿼리하지 않음
            System.out.println("findMember = " + findMember.getClass()); // Member$HibernateProxy
            System.out.println("findMember.getId() = " + findMember.getId()); // 이미 getId를 알기에 쿼리하지 않음
            // 값이 실제 사용되는 시점에 쿼리함 !!
            System.out.println("findMember.getUsername() = " + findMember.getUsername());

            tx.commit(); // 커밋 시점에 INSERT (버퍼링 가능)
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 영속성 컨텍스트를 종료
        }
        emf.close();
    }
}
