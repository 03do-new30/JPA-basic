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

            Team team1 = new Team();
            team1.setName("teamA");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team1);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(team2);
            em.persist(member2);

            em.flush();
            em.clear();

//            Member m = em.find(Member.class, member1.getId());

            // N + 1 PROBLEM
            // 즉시 로딩 시, JPQL 쓰면 쿼리가 여러번 나간다
            // 1. SQL로 변환(SELECT * FROM MEMBER)해서, MEMBER 테이블을 쭉 가져온다
            // 2. 어라? MEMBER 가져왔더니 Team이 즉시로딩으로 되어있네? 이를 위한 쿼리가 별도로 나감
            // (SELECT * FROM TEAM WHERE TEAM_ID = team1의 아이디)
            // (SELECT * FROM TEAM WHERE TEAM_ID = team2의 아이디) ...
            List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();


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
