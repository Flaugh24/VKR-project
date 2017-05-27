package org.barmaley.vkr.domain;

import org.springframework.scheduling.annotation.EnableScheduling;

import javax.persistence.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by SUN_SUN on 05.05.2017.
 */
@Entity
@Table(name="STUDENT_COPY")
@EnableScheduling
public class StudentCopy implements Serializable {

    @Id
    @Column(name ="USERNAME")
    private String username;

    @Column(name ="PASSWORD")
    private String password;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "SECOND_NAME")
    private String secondName;

    @ManyToMany
    @JoinTable(name = "STUDENT_EDUC_PROGRAMS", joinColumns = @JoinColumn(name = "STUDENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "EDUC_PROGRAM_ID"))
    private Set<EducProgram> educPrograms;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Set<EducProgram> getEducPrograms() {
        return educPrograms;
    }

    public void setEducPrograms(Set<EducProgram> educPrograms) {
        this.educPrograms = educPrograms;
    }

    /*
            _____________________________________________________________________
            */
    public ArrayList<String> parseTxtFile3() throws IOException
    {
        String pathTheFile ="D:\\src\\std.txt";
        String line = "";
        ArrayList<String> list = new ArrayList<String>();
        System.out.println("Размер list до заполнения: "+list.size());
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathTheFile),"windows-1251"));
        while (line != null)
        {
            line = br.readLine();
            if (line==null)
            {
                continue;
            }
            line = line.replace(" ","");
            line = line.replace("|"," ");
            String tmp[] = line.split(" ");
            for(int i=0; i<tmp.length; i++)
            {
                list.add(tmp[i]);
            }
        }
        return list;
    }
}
