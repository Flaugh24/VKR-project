package org.barmaley.vkr.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "ACT")
public class Act {

    @Id
    @GenericGenerator(name = "sequence_act_id",
            strategy = "org.barmaley.vkr.generator.ActIdGenerator")
    @GeneratedValue(generator = "sequence_act_id")
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "STATUS", nullable = false)
    private StatusAct status;

    @ManyToOne
    @JoinColumn(name = "COORDINATOR_ID", nullable = false)
    private Users coordinator;


    @ManyToOne
    @JoinColumn(name = "BIBLIOGRAPHER_ID")
    private Users bibliographer;

    @Column(name = "DATE_OF_СREATE")
    private Date dateOfCreate;

    @Column(name = "DATE_OF_ACCEPT")
    private Date dateOfAccept;

    @OneToMany(mappedBy = "act", fetch = FetchType.EAGER)
    private List<Ticket> tickets;

    @Column(name = "INSTITUTE")
    private String institute;

    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "POSITION")
    private String position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StatusAct getStatus() {
        return status;
    }

    public void setStatus(StatusAct status) {
        this.status = status;
    }

    public Users getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(Users coordinator) {
        this.coordinator = coordinator;
    }

    public Users getBibliographer() {
        return bibliographer;
    }

    public void setBibliographer(Users bibliographer) {
        this.bibliographer = bibliographer;
    }

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public Date getDateOfAccept() {
        return dateOfAccept;
    }

    public void setDateOfAccept(Date dateOfAccept) {
        this.dateOfAccept = dateOfAccept;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
