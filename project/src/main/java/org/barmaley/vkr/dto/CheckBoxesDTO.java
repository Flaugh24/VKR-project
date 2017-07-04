package org.barmaley.vkr.dto;

import java.util.List;

public class CheckBoxesDTO {
    private int pathVariable;
    private String name;
    private List<Integer> checkedValsInt;
    private List<String> checkedValsStr;

    public int getPathVariable() {
        return pathVariable;
    }

    public void setPathVariable(int pathVariable) {
        this.pathVariable = pathVariable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getCheckedValsInt() {
        return checkedValsInt;
    }

    public void setCheckedValsInt(List<Integer> checkedValsInt) {
        this.checkedValsInt = checkedValsInt;
    }

    public List<String> getCheckedValsStr() {
        return checkedValsStr;
    }

    public void setCheckedValsStr(List<String> checkedValsStr) {
        this.checkedValsStr = checkedValsStr;
    }
}
