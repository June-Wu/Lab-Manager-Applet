
package model;

public class labActivity {
    private String doLab = "";
    private String observation = "";


    //MODIFIES: this
    //EFFECTS: sets labEntry doLab
    public void setDoLab(String doLab) {
        this.doLab = doLab;
    }

    //MODIFIES: this
    //EFFECTS: sets labEntry observation
    public void setObservation(String observation) {
        this.observation = observation;
    }



    //EFFECTS: returns a list of labs performed and their observed results
    public String toString(){
        return "(LAB: "+ doLab +", OBSERVATION: " + observation +")";
    }

    //getters
    public String getDoLab(){return doLab;}
    public String getObservation(){return observation;}
}
