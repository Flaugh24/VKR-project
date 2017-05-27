package org.barmaley.vkr.domain;

import javax.persistence.*;

/**
 * Created by Flugh on 16.04.2017.
 */

@Entity
@Table(name = "TYPE_OF_USE")
public class TypeOfUse {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "NAME")
    private String name;

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
}
