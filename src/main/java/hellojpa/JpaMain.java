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

            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember.getClass() = " + refMember.getClass()); // Proxy

            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember.getClass() = " + findMember.getClass()); // 헉 얘가 Member가 아니라 Proxy네!

            // 한번 Proxy로 조회되면 em.find에서 Proxy를 반환한다
            // 한 컨텍스트 내에서 == 비교 시 같음을 보장하기 위해서
            System.out.println("(findMember == refMember) = " + (findMember == refMember));

            tx.commit(); // 커밋 시점에 INSERT (버퍼링 가능)
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 영속성 컨텍스트를 종료
        }
        emf.close();
    }
}
