package org.barmaley.vkr.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "ACT")
public class Act {

    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "DATE_OF_СREATN")
    private Date dateOfCreati;
    @Column(name = "DATE_OF_ACCEPT")
    private Date dateOfAccept;
    @OneToMany(mappedBy = "act", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Ticket> tickets;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateOfCreati() {
        return dateOfCreati;
    }

    public void setDateOfCreati(Date dateOfCreati) {
        this.dateOfCreati = dateOfCreati;
    }

    public Date getDateOfAccept() {
        return dateOfAccept;
    }

    public void setDateOfAccept(Date dateOfAccept) {
        this.dateOfAccept = dateOfAccept;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
