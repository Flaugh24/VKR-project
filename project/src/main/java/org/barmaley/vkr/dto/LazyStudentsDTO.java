package org.barmaley.vkr.dto;

import org.barmaley.vkr.domain.EducProgram;
import org.barmaley.vkr.domain.StudentCopy;

public class LazyStudentsDTO {

    private StudentCopy studentCopy;
    private EducProgram educProgram;

    public StudentCopy getStudentCopy() {
        return studentCopy;
    }

    public void setStudentCopy(StudentCopy studentCopy) {
        this.studentCopy = studentCopy;
    }

    public EducProgram getEducProgram() {
        return educProgram;
    }

    public void setEducProgram(EducProgram educProgram) {
        this.educProgram = educProgram;
    }
}
