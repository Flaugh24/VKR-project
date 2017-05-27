package org.barmaley.vkr.dto;

public class TicketDTO {
    private String id;
    private String groupNum;
    private String firstName;
    private String secondName;
    private String surname;
    private String title;
    private String documentType;
    private String typeOfUse;
    private String status;
    private String dateCreationStart;
    private String dateCreationFinish;
    private String dateCheckCoordinatorStart;
    private String getDateCheckCoordinatorFinish;

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getTypeOfUse() {
        return typeOfUse;
    }

    public void setTypeOfUse(String typeOfUse) {
        this.typeOfUse = typeOfUse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateCreationStart() {
        return dateCreationStart;
    }

    public void setDateCreationStart(String dateCreationStart) {
        this.dateCreationStart = dateCreationStart;
    }

    public String getDateCreationFinish() {
        return dateCreationFinish;
    }

    public void setDateCreationFinish(String dateCreationFinish) {
        this.dateCreationFinish = dateCreationFinish;
    }

    public String getDateCheckCoordinatorStart() {
        return dateCheckCoordinatorStart;
    }

    public void setDateCheckCoordinatorStart(String dateCheckCoordinatorStart) {
        this.dateCheckCoordinatorStart = dateCheckCoordinatorStart;
    }

    public String getGetDateCheckCoordinatorFinish() {
        return getDateCheckCoordinatorFinish;
    }

    public void setGetDateCheckCoordinatorFinish(String getDateCheckCoordinatorFinish) {
        this.getDateCheckCoordinatorFinish = getDateCheckCoordinatorFinish;
    }

    public TicketDTO(String id, String groupNum, String firstName, String secondName, String surname, String title, String documentType, String typeOfUse, String status) {
        this.id = id;
        this.groupNum = groupNum;
        this.firstName = firstName;
        this.secondName = secondName;
        this.surname = surname;
        this.title = title;
        this.documentType = documentType;
        this.typeOfUse = typeOfUse;
        this.status = status;
    }

    public TicketDTO() {

    }
}
