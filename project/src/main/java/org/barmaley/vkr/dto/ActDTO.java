package org.barmaley.vkr.dto;

import org.barmaley.vkr.domain.Act;

import java.util.List;

public class ActDTO {

    private Act act;

    private List<String> ticketsId;

    public Act getAct() {
        return act;
    }

    public void setAct(Act act) {
        this.act = act;
    }

    public List<String> getTicketsId() {
        return ticketsId;
    }

    public void setTicketsId(List<String> ticketsId) {
        this.ticketsId = ticketsId;
    }

}
