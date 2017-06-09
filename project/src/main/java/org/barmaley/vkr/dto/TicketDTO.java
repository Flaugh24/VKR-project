package org.barmaley.vkr.dto;

import org.barmaley.vkr.domain.Ticket;

public class TicketDTO extends Ticket {
    private String dateCreationStartDTO;
    private String dateCreationFinishDTO;
    private String dateCheckCoordinatorStartDTO;
    private String dateCheckCoordinatorFinishDTO;
    private String dateReturnDTO;

    public TicketDTO() {

    }

    public String getDateCreationStartDTO() {
        return dateCreationStartDTO;
    }

    public void setDateCreationStartDTO(String dateCreationStartDTO) {
        this.dateCreationStartDTO = dateCreationStartDTO;
    }

    public String getDateCreationFinishDTO() {
        return dateCreationFinishDTO;
    }

    public void setDateCreationFinishDTO(String dateCreationFinishDTO) {
        this.dateCreationFinishDTO = dateCreationFinishDTO;
    }

    public String getDateCheckCoordinatorStartDTO() {
        return dateCheckCoordinatorStartDTO;
    }

    public void setDateCheckCoordinatorStartDTO(String dateCheckCoordinatorStartDTO) {
        this.dateCheckCoordinatorStartDTO = dateCheckCoordinatorStartDTO;
    }

    public String getDateCheckCoordinatorFinishDTO() {
        return dateCheckCoordinatorFinishDTO;
    }

    public void setDateCheckCoordinatorFinishDTO(String dateCheckCoordinatorFinishDTO) {
        this.dateCheckCoordinatorFinishDTO = dateCheckCoordinatorFinishDTO;
    }

    public String getDateReturnDTO() {
        return dateReturnDTO;
    }

    public void setDateReturnDTO(String dateReturnDTO) {
        this.dateReturnDTO = dateReturnDTO;
    }
}
