package org.barmaley.vkr.dto;



/**
 * Created by SUN_SUN on 06.05.2017.
 */
public class StudentCopyDTO {


    private Integer id;

    private String surname;

    private String groups;

    private String institute;

    private String direction;

    public Integer getId() {
        return id;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
