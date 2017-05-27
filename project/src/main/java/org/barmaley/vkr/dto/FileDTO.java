package org.barmaley.vkr.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by gagar on 22.05.2017.
 */
public class FileDTO {
    private String ticketId;
    private MultipartFile file;
    private String action;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
