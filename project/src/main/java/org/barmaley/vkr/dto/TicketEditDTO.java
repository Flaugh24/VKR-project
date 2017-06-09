package org.barmaley.vkr.dto;
import org.barmaley.vkr.domain.Ticket;

public class TicketEditDTO extends Ticket{
    private String licenseDateDTO;
    private String dateOfPublic;
    //----------------------------------------------------
    private String word1, word2, word3, word4, word1Eng, word2Eng, word3Eng, word4Eng;

    //----------------------------------------------------


    public String getLicenseDateDTO() {
        return licenseDateDTO;
    }

    public void setLicenseDateDTO(String licenseDateDTO) {
        this.licenseDateDTO = licenseDateDTO;
    }

    public String getWord1() {
        return word1;
    }

    public void setWord1(String word1) {
        this.word1 = word1;
    }

    public String getWord2() {
        return word2;
    }

    public void setWord2(String word2) {
        this.word2 = word2;
    }

    public String getWord3() {
        return word3;
    }

    public void setWord3(String word3) {
        this.word3 = word3;
    }

    public String getWord4() {
        return word4;
    }

    public void setWord4(String word4) {
        this.word4 = word4;
    }

    public String getWord1Eng() {
        return word1Eng;
    }

    public void setWord1Eng(String word1Eng) {
        this.word1Eng = word1Eng;
    }

    public String getWord2Eng() {
        return word2Eng;
    }

    public void setWord2Eng(String word2Eng) {
        this.word2Eng = word2Eng;
    }

    public String getWord3Eng() {
        return word3Eng;
    }

    public void setWord3Eng(String word3Eng) {
        this.word3Eng = word3Eng;
    }

    public String getWord4Eng() {
        return word4Eng;
    }

    public void setWord4Eng(String word4Eng) {
        this.word4Eng = word4Eng;
    }

    public String getDateOfPublic() {
        return dateOfPublic;
    }

    public void setDateOfPublic(String dateOfPublic) {
        this.dateOfPublic = dateOfPublic;
    }
}
