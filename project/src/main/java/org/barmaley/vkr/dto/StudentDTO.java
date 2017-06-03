package org.barmaley.vkr.dto;


public class StudentDTO {

    private String firstName;
    private String secondName;
    private String surname;

    public StudentDTO(String firstName, String secondName, String surname) {
        this.firstName = firstName;
        this.secondName = secondName;
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

    public void setSecondName(String lastName) {
        this.secondName = lastName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
