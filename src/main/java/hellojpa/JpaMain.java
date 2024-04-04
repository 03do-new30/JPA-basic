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
            System.out.println("before  findMember = " + findMember.getClass()); // Member$HibernateProxy
            // 프록시 객체 초기화
            System.out.println("findMember.getUsername() = " + findMember.getUsername());
            // 프록시 객체가 실제 엔티티로 바뀌는 것은 아님
            System.out.println("after   findMember = " + findMember.getClass()); // Member$HibernateProxy

            tx.commit(); // 커밋 시점에 INSERT (버퍼링 가능)
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 영속성 컨텍스트를 종료
        }
        emf.close();
    }
}
