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
            Member member = em.find(Member.class, 150L); // 원래 이름이 A인 데이터
            member.setName("ZZZZZ");

//            em.persist(member); -> 쓰면 안되고 쓸 필요 없다
            System.out.println("===============");
            // UPDATE 쿼리 날아가는 것 확인 할 수 있음

            tx.commit(); // DB에 쿼리가 날아가는 시점
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
