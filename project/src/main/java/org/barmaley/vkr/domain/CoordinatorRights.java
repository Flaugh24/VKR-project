package org.barmaley.vkr.domain;

import javax.persistence.*;

@Entity
@Table(name = "COORDINATOR_RIGHTS")
public class CoordinatorRights {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "COORDINATOR_ID", nullable = false)
    private Users coordinator;

    @Column(name = "GROUP_NUM")
    private String groupNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(Users coordinator) {
        this.coordinator = coordinator;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }
}
