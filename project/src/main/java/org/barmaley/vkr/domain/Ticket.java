package org.barmaley.vkr.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TICKET")
public class Ticket implements Serializable {

    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GenericGenerator(name = "sequence_ticket_id",
            strategy = "org.barmaley.vkr.generator.TicketIdGenerator")
    @GeneratedValue(generator = "sequence_ticket_id")
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private Users user;

    @Column(name = "LICENSE_NUMBER")
    private String licenseNumber;

    @Column(name = "LICENSE_DATE")
    private Date licenseDate;

    @Column(name = "GROUP_NUM")
    private String groupNum;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TITLE_ENG")
    private String titleEng;

    @Column(name = "ANNOTATION", length = 1024)
    private String annotation;

    @Column(name = "ANNOTATION_ENG", length = 1024)
    private String annotationEng;

    @Column(name = "KEY_WORDS")
    private String keyWords;

    @Column(name = "KEY_WORDS_ENG")
    private String keyWordsEng;

    @ManyToOne
    @JoinColumn(name = "STATUS", nullable = false)
    private StatusTicket status;

    @ManyToOne
    @JoinColumn(name = "DOCUMENT_TYPE")
    private DocumentType documentType;

    @ManyToOne
    @JoinColumn(name = "TYPE_OF_USE")
    private TypeOfUse typeOfUse;

    @Column(name = "FILE_PDF")
    private String filePdf;

    @Column(name = "FILE_ZIP")
    private String fileZip;

    @Column(name = "DATE_CREATION_START")
    private Date dateCreationStart;

    @Column(name = "DATE_CREATION_FINISH")
    private Date dateCreationFinish;

    @Column(name = "DATE_CHECK_COORDINATOR_START")
    private Date dateCheckCoordinatorStart;

    @Column(name = "DATE_CHECK_COORDINATOR_FINISH")
    private Date dateCheckCoordinatorFinish;

    @Column(name = "DATE_RETURN")
    private Date dateReturn;
    //----------------------------------------------------
    @Column(name = "INSTITUTE")
    private String institute;

    @Column(name = "DIRECTION")
    private String direction;

    @Column(name = "DIRECTION_CODE")
    private String directionCode;

    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "PLACE_OF_PUBLIC")
    private String placeOfPublic;

    @Column(name = "PLACE_OF_PUBLIC_ENG")
    private String placeOfPublicEng;

    @Column(name = "YEAR_OF_PUBLIC")
    private String yearOfPublic;

    @Column(name = "HEAD_OF_DEPARTMENT")
    private String headOfDepartment;

    @Column(name = "FULL_NAME_CURATOR")
    private String fullNameCurator;

    @Column(name = "FULL_NAME_CURATOR_ENG")
    private String fullNameCuratorEng;

    @Column(name = "POSITION_OF_CURATOR")
    private String posOfCurator;

    @Column(name = "POSITION_OF_CURATOR_ENG")
    private String posOfCuratorEng;

    @Column(name = "DEGREE_OF_CURATOR")
    private String degreeOfCurator;

    @Column(name = "DEGREE_OF_CURATOR_ENG")
    private String degreeOfCuratorEng;

    @ManyToOne
    @JoinColumn(name = "ACT")
    private Act act;
    //----------------------------------------------------

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Date getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(Date licenseDate) {
        this.licenseDate = licenseDate;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEng() {
        return titleEng;
    }

    public void setTitleEng(String titleEng) {
        this.titleEng = titleEng;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getAnnotationEng() {
        return annotationEng;
    }

    public void setAnnotationEng(String annotationEng) {
        this.annotationEng = annotationEng;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getKeyWordsEng() {
        return keyWordsEng;
    }

    public void setKeyWordsEng(String keyWordsEng) {
        this.keyWordsEng = keyWordsEng;
    }

    public StatusTicket getStatus() {
        return status;
    }

    public void setStatus(StatusTicket status) {
        this.status = status;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getFilePdf() {
        return filePdf;
    }

    public void setFilePdf(String filePdf) {
        this.filePdf = filePdf;
    }

    public String getFileZip() {
        return fileZip;
    }

    public void setFileZip(String fileZip) {
        this.fileZip = fileZip;
    }

    public TypeOfUse getTypeOfUse() {
        return typeOfUse;
    }

    public void setTypeOfUse(TypeOfUse typeOfUse) {
        this.typeOfUse = typeOfUse;
    }

    public Date getDateCreationStart() {
        return dateCreationStart;
    }

    public void setDateCreationStart(Date dateCreationStart) {
        this.dateCreationStart = dateCreationStart;
    }

    public Date getDateCreationFinish() {
        return dateCreationFinish;
    }

    public void setDateCreationFinish(Date dateCreationFinish) {
        this.dateCreationFinish = dateCreationFinish;
    }

    public Date getDateCheckCoordinatorStart() {
        return dateCheckCoordinatorStart;
    }

    public void setDateCheckCoordinatorStart(Date dateCheckCoordinatorStart) {
        this.dateCheckCoordinatorStart = dateCheckCoordinatorStart;
    }

    public Date getDateCheckCoordinatorFinish() {
        return dateCheckCoordinatorFinish;
    }

    public void setDateCheckCoordinatorFinish(Date dateCheckCoordinatorFinish) {
        this.dateCheckCoordinatorFinish = dateCheckCoordinatorFinish;
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

    public String getPlaceOfPublic() {
        return placeOfPublic;
    }

    public void setPlaceOfPublic(String placeOfPublic) {
        this.placeOfPublic = placeOfPublic;
    }

    public String getPlaceOfPublicEng() {
        return placeOfPublicEng;
    }

    public void setPlaceOfPublicEng(String placeOfPublicEng) {
        this.placeOfPublicEng = placeOfPublicEng;
    }

    public String getYearOfPublic() {
        return yearOfPublic;
    }

    public void setYearOfPublic(String yearOfPublic) {
        this.yearOfPublic = yearOfPublic;
    }

    public String getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(String headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    public String getFullNameCurator() {
        return fullNameCurator;
    }

    public void setFullNameCurator(String fullNameCurator) {
        this.fullNameCurator = fullNameCurator;
    }

    public String getFullNameCuratorEng() {
        return fullNameCuratorEng;
    }

    public void setFullNameCuratorEng(String fullNameCuratorEng) {
        this.fullNameCuratorEng = fullNameCuratorEng;
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

    public Act getAct() {
        return act;
    }

    public void setAct(Act act) {
        this.act = act;
    }

    public Date getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }
}

