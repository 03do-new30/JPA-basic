package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        //code
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 저장
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team); // 연관관계의 주인에 값을 입력
            em.persist(member);

            team.getMembers().add(member); // 순수한 객체 관계를 고려하면 항상 양쪽 다 값을 입력해야 한다

//            em.flush();
//            em.clear();

            Team findTeam = em.find(Team.class, team.getId()); // flush, clear 하지 않으면 현재 1차 캐시에만 올라가있는 값 (순수한 객체 상태)
            List<Member> members = findTeam.getMembers(); // flush, clear 하지 않으면 members의 값이 없을 것임
            System.out.println("====================");
            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }
            System.out.println("====================");

            tx.commit(); // 커밋 시점에 INSERT (버퍼링 가능)
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 영속성 컨텍스트를 종료
        }
        emf.close();
    }
}
