package org.barmaley.vkr.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by gagar on 28.04.2017.
 */

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

    @Column(name = "SPECIALTY")
    private String specialty;

    //----------------------------------------------------
    @Column(name = "DIR_OF_TRAIN")
    private String dirOfTrain;

    @Column(name = "CODE_DIR_OF_TRAIN")
    private String codeDirOfTrain;

    @Column(name = "DEGREE_OF_CURATOR")
    private String degreeOfCurator;

    @Column(name = "DEGREE_OF_CURATOR_ENG")
    private String degreeOfCuratorEng;

    @Column(name = "POS_OF_CURATOR")
    private String posOfCurator;

    @Column(name = "POS_OF_CURATOR_ENG")
    private String posOfCuratorEng;
    //----------------------------------------------------

    @ManyToMany(mappedBy = "educPrograms")
    private Set<StudentCopy> studentCopies;

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

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Set<StudentCopy> getStudentCopies() {
        return studentCopies;
    }

    public void setStudentCopies(Set<StudentCopy> studentCopies) {
        this.studentCopies = studentCopies;
    }

    public String getDirOfTrain() {
        return dirOfTrain;
    }

    public void setDirOfTrain(String dirOfTrain) {
        this.dirOfTrain = dirOfTrain;
    }

    public String getCodeDirOfTrain() {
        return codeDirOfTrain;
    }

    public void setCodeDirOfTrain(String codeDirOfTrain) {
        this.codeDirOfTrain = codeDirOfTrain;
    }

    public String getDegreeOfCurator() {
        return degreeOfCurator;
    }

    public void setDegreeOfCurator(String degreeOfCurator) {
        this.degreeOfCurator = degreeOfCurator;
    }

    public String getDegreeOfCuratorEng() {
        return degreeOfCuratorEng;
    }

    public void setDegreeOfCuratorEng(String degreeOfCuratorEng) {
        this.degreeOfCuratorEng = degreeOfCuratorEng;
    }

    public String getPosOfCurator() {
        return posOfCurator;
    }

    public void setPosOfCurator(String posOfCurator) {
        this.posOfCurator = posOfCurator;
    }

    public String getPosOfCuratorEng() {
        return posOfCuratorEng;
    }

    public void setPosOfCuratorEng(String posOfCuratorEng) {
        this.posOfCuratorEng = posOfCuratorEng;
    }
}
