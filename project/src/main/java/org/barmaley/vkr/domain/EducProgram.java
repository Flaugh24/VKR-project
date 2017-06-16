package org.barmaley.vkr.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "EDUC_PROGRAM")
public class EducProgram implements Serializable {

    private static final long serialVersionUID = 5924361831551833717L;

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "INSTITUTE")
    private String institute;

    @Column(name = "DEGREE")
    private String degree;

    @Column(name = "GROUP_NUM")
    private String groupNum;

    @Column(name = "DIRECTION")
    private String direction;

    @Column(name = "DIRECTION_CODE")
    private String directionCode;

    @Column(name = "DEPARTMENT")
    private String department;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID", nullable = false)
    private StudentCopy student;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirectionCode() {
        return directionCode;
    }

    public void setDirectionCode(String directionCode) {
        this.directionCode = directionCode;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public StudentCopy getStudent() {
        return student;
    }

    public void setStudent(StudentCopy student) {
        this.student = student;
    }
}
