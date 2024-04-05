package hellojpa;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        //code
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Address("old1", "street", "10000"));
            member.getAddressHistory().add(new Address("old2", "street", "10000"));

            // 값 타입 컬렉션의 라이프사이클이 멤버에 의존
            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("========== START ==========");
            // 값 타입 컬렉션들은 지연 로딩
            Member findMember = em.find(Member.class, member.getId()); // Member에서만 조회 - 지연 로딩

            // homeCity -> newCity
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            // 값 타입 컬렉션 수정
            // 치킨 -> 한식
            // String 자체가 값타입. 업데이트라는게 없고 갈아끼워야함
            findMember.getFavoriteFoods().remove("치킨"); // DELETE query
            findMember.getFavoriteFoods().add("한식"); // INSERT query

            // old1 -> new1
            // remove시 equals로 비교해서 같은 인스턴스 지우기때문에
            // old1과 완전히 똑같은 인스턴스를 만들어주고 지운다
            // equlas와 hashCode가 제대로 구현되어있어야 한다
            findMember.getAddressHistory().remove(new Address("old1", "street", "10000"));
            // 현재 지우는 데이터에 걸려있는 member id 관련된 모든 데이터를 싹 다 지운다

            findMember.getAddressHistory().add(new Address("newCity1", "street", "10000"));
            // 컬렉션에 최종 남은 데이터들을 insert친다

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
