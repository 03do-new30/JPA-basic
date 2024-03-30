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
            Member member = new Member();
            member.setUsername("C");

            System.out.println("================");
            em.persist(member); // IDENTITY 전략 -> persist시점에 INSERT쿼리 날아감 (PK값이 INSERT 후에 얻어지기때문)
            System.out.println("member.getId() = " + member.getId());
            System.out.println("================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 영속성 컨텍스트를 종료
        }
        emf.close();
    }
}
