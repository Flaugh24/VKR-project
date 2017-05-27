package org.barmaley.vkr.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Flugh on 22.04.2017.
 */

@Entity
@Table(name = "ROLES")
public class Roles implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<Users> users;

    @ManyToMany
    @JoinTable(name = "ROLE_PERMISSIONS", joinColumns = @JoinColumn(name = "ROLES_ID"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSIONS_ID"))
    private Set<Permissions> permissions;

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

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permissions> permissions) {
        this.permissions = permissions;
    }
}
