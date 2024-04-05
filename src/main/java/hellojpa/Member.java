package hellojpa;

import jakarta.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Embedded
    private Period workPeriod;

    @Embedded
    private Address homeAddress;

    // 한 엔티티에서 같은 임베디드 타입을 사용하면 컬럼명 중복 에러 -> @AttributeOverrides, @AttributeOverride로 해결
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "city", column = @Column(name = "work_city")),
                            @AttributeOverride(name = "street", column = @Column(name = "work_street")),
                            @AttributeOverride(name = "zipcode", column = @Column(name = "work_zipcode"))})
    private Address workAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
}