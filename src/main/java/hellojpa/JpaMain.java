package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        //code
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 영속
            Member member = em.find(Member.class, 150L);
            member.setName("AAAAA");

            // 준영속
            // JPA에서 관리하지 않음, 영속성 컨텍스트에서 관리하지 않음
            // memeber 관련 모든 것이 영속성 컨텍스트에서 빠짐
            // update 쿼리가 나가지 않을 것
            em.detach(member); // member 엔티티만 준영속 상태로 전환

            em.clear(); // 영속성 컨텍스트를 완전히 초기화
            // 1차 캐시에 없으므로 똑같은 멤버를 조회해도 다시 select 쿼리 나감
            Member member2 = em.find(Member.class, 150L);

            System.out.println("==========");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 영속성 컨텍스트를 종료
        }
        emf.close();
    }
}
