package org.barmaley.vkr.dto;

import org.barmaley.vkr.domain.Act;

import java.util.List;

public class ActDTO extends Act {

    private List<String> ticketsId;

    public List<String> getTicketsId() {
        return ticketsId;
    }

    public void setTicketsId(List<String> ticketsId) {
        this.ticketsId = ticketsId;
    }

}
