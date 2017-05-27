package org.barmaley.vkr.dto;

import org.barmaley.vkr.domain.EducProgram;
import org.barmaley.vkr.domain.StudentCopy;

/**
 * Created by gagarkin on 25.05.17.
 */
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
