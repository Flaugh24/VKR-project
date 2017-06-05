package org.barmaley.vkr.dto;

import org.barmaley.vkr.domain.Act;

import java.util.List;

public class ActDTO extends Act {

    private List<String> ticketsId;
    private String dateOfCreatDTO;
    private String dateOfAcceptDTO;

    public List<String> getTicketsId() {
        return ticketsId;
    }

    public void setTicketsId(List<String> ticketsId) {
        this.ticketsId = ticketsId;
    }

    public String getDateOfCreatDTO() {
        return dateOfCreatDTO;
    }

    public void setDateOfCreatDTO(String dateOfCreatDTO) {
        this.dateOfCreatDTO = dateOfCreatDTO;
    }

    public String getDateOfAcceptDTO() {
        return dateOfAcceptDTO;
    }

    public void setDateOfAcceptDTO(String dateOfAcceptDTO) {
        this.dateOfAcceptDTO = dateOfAcceptDTO;
    }
}
