package br.com.crud.app.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "CRUD_APP_ROLE")
public class Role implements Serializable {

    private static final long serialVersionUID = 8095798867298707038L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ROLE", nullable = false, precision = 9, scale = 0)
    private Long id;

    @JsonIgnoreProperties(value = {"roles"})
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    @JsonInclude(Include.NON_NULL)
    private List<User> users;

    @Column(name = "CODE_ROLE", nullable = true, length = 255)
    @JsonInclude(Include.NON_NULL)
    private String code;

    @Column(name = "DESCRIPTION_ROLE", nullable = true, length = 255)
    @JsonInclude(Include.NON_NULL)
    private String description;

    @Column(name = "NAME_ROLE", nullable = true, length = 255)
    @JsonInclude(Include.NON_NULL)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(users, role.users) && Objects.equals(code, role.code) && Objects.equals(description, role.description) && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, users, code, description, name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", users=" + users +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
