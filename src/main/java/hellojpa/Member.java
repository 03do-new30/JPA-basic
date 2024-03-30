package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Member {

    @Id
    private Long id;

    // 객체에는 username, 컬럼에는 name 이라는 이름을 쓰고 싶을 때
    @Column(name = "name")
    private String username;

    private Integer age;

   @Enumerated(EnumType.STRING)
    private RoleType roleType;

   @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

   @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

   private LocalDate testLocalDate;
   private LocalDateTime testLocalDateTime;

   @Lob
    private String description;

   @Transient
   private int temp; // DB와 관계 없어. 매핑하지 않을래

   public Member() {}

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
