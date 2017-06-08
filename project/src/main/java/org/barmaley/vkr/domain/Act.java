package org.barmaley.vkr.domain;

import javax.persistence.*;
import java.util.Date;

import java.util.List;


@Entity
@Table(name = "ACT")
public class Act {

    @Id
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private Users user;
    @Column(name = "DATE_OF_СREATN")
    private Date dateOfCreat;
    @Column(name = "DATE_OF_ACCEPT")
    private Date dateOfAccept;
    @OneToMany(mappedBy = "act", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Ticket> tickets;


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

    public Date getDateOfCreat() {
        return dateOfCreat;
    }

    public void setDateOfCreat(Date dateOfCreat) {
        this.dateOfCreat = dateOfCreat;
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
}
