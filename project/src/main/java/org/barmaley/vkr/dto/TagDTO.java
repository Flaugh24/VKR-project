package org.barmaley.vkr.dto;

/**
 * Created by gagarkin on 30.05.17.
 */
public class TagDTO {
    public String id;
    public String fio;

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

    public TagDTO(String id, String tagName) {
        this.id = id;
        this.fio = tagName;
    }

}
