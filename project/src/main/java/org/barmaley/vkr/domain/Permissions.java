package org.barmaley.vkr.domain;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "PERMISSIONS")
public class Permissions {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Set<Roles> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
}
