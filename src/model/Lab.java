package model;



import java.util.*;

public class Lab {
    private String labName;
    private String labProcedure;
    private String labResult;
    private String labType;



    public Lab(String labName, String labProcedure, String labResult, String labType) {
        this.labName=labName;
        this.labProcedure=labProcedure;
        this.labResult=labResult;
        this.labType=labType;
    }

    // getters
    public String getLabName(){return labName;}
    public String getLabProcedure(){return labProcedure;}
    public String getLabResult(){return labResult;}
    public String getLabType(){return labType;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lab)) return false;
        Lab lab = (Lab) o;
        return Objects.equals(getLabName(), lab.getLabName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getLabName());
    }

}
