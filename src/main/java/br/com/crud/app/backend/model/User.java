package br.com.crud.app.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "CRUD_APP_USER")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER", nullable = false)
    private Long id;

    // Owner for Relationship between User and Role (JoinColumn)
    @JsonIgnoreProperties(value = "users", allowSetters = true)
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name = "CRUD_APP_REL_USER_ROLE",
            joinColumns = { @JoinColumn(name = "ID_USER") },
            inverseJoinColumns = { @JoinColumn(name = "ID_ROLE") })
    private List<Role> roles;

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, mappedBy = "user", optional = true)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private Person person;

    @Column(name = "USERNAME_USER", nullable = false, length = 255)
    private String username;

    @Column(name = "PASSWORD_USER", nullable = false)
    private String password;

    @Column(name = "LOGIN_COUNT_USER", nullable = false)
    private Integer loginCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(roles, user.roles) && Objects.equals(person, user.person) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(loginCount, user.loginCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roles, person, username, password, loginCount);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", roles=" + roles +
                ", person=" + person +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", loginCount=" + loginCount +
                '}';
    }
}
