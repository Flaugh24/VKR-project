package org.barmaley.vkr.dto;


public class TagDTO {
    private String id;
    private String fio;

    public TagDTO(String id, String tagName) {
        this.id = id;
        this.fio = tagName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

}
