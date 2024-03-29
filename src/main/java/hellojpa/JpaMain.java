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
            // 첫번째 조회할 때는 DB에서 SELECT해오는 쿼리가 나와야 함
            Member findMember1 = em.find(Member.class, 101L);
            // 두번째 조회할 때는 캐시에 있는 것을 가져오므로 쿼리가 나오면 안됨
            Member findMember2 = em.find(Member.class, 101L);

            // 영속 엔티티의 동일성 보장
            System.out.println("result = " + (findMember1 == findMember2));

            tx.commit(); // DB에 쿼리가 날아가는 시점
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
