package org.barmaley.vkr.dto;


public class EducProgramDTO {

    private Integer id;
    private String institute;
    private String degree;
    private String groupNum;
    private String direction;
    private String specialty;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String group) {
        this.groupNum = group;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
