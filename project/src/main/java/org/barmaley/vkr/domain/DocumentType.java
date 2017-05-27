package org.barmaley.vkr.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Ivan on 02.04.2017.
 */
@Entity
@Table(name = "DOCUMENT_TYPE")
public class DocumentType implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NAME_ENG")
    private String nameEng;

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

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }
}
