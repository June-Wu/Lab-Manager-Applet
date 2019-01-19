package ui.tabs;

import model.Lab;
import model.labActivity;
import ui.LabApp;
import ui.labMapAndListWrapper;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class Tab extends JPanel {
    protected LabApp controller;
    protected static final String LOGFILE = "labLogFile.txt";
    protected static final String LABFILE = "labFile.txt";
    protected String INIT_GREETING = "Lab Activity Report";

    protected ArrayList<labActivity> labLog = new ArrayList<>();
    protected Map<String, Lab> myMap;
    protected DefaultListModel listModelLabs ;

    protected String currTime;
    protected SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
    protected Date currDate= Calendar.getInstance().getTime();


    //REQUIRES: LabApp controller that holds this tab
    public Tab(LabApp controller, labMapAndListWrapper wrapper){
        this.controller = controller;
        this.myMap = wrapper.getLabMap();
        this.listModelLabs=wrapper.getListModelLabs();
    }

    //EFFECTS: creates and returns row with button included
    protected JPanel formatButtonRow(JButton b){
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);
        return p;
    }

    //MODIFIES: this
    //EFFECTS: if lab doesn't already exist, creates lab with specified name, procedure, result, type,and return true;
    //         otherwise return false
    protected boolean addNewLabOfCorrectType(String name, String procedure, String result, String type){

        name = capitalizeAndTrimEnds(name);
        if(myMap.containsKey(name)){
            return false;
        }
        Lab labToAdd = new Lab(name, procedure, result, type);
        addElementToListModelSorted(name);
        myMap.put(name,labToAdd);
        return true;
    }

    //MODIFIES: this
    //EFFECTS: inserts lab by alphabetical order
    private void addElementToListModelSorted(String newLabName){
        int insertIndex=0;
        for(int i=0; i<listModelLabs.size();i++){
            String nameFromList = myMap.get(listModelLabs.get(i)).getLabName();
            if(newLabName.compareToIgnoreCase(nameFromList)<0){
                break;
            }
            insertIndex++;
        }
        listModelLabs.add(insertIndex,newLabName);
    }


    //MODIFIES: this
    //EFFECTS: updates current time in format HH:mm:ss
    protected void updateCurrTime(){
        currDate = Calendar.getInstance().getTime();
        currTime = timeFormat.format(currDate);
    }

    //EFFECTS: display warning dialogue for dangerous lab
    protected void warning(Lab lab) {
        if(lab.getLabType().equals("Dangerous")){
            JOptionPane.showMessageDialog(controller,
                    "Be careful! This lab is dangerous!",
                    "Danger Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    //EFFECTS: display dialogue with lab details
    protected void displayLabDetails(String name, String procedure, String result, String type){
        JOptionPane.showMessageDialog(controller,
                "Lab: "+name+
                        "\nProcedure: "+procedure+
                        "\nResult: "+result+
                        "\nType: "+type,
                "Lab Details",
                JOptionPane.PLAIN_MESSAGE);
    }

    //EFFECTS: display error dialogue when log file is not found
    protected void logNotFoundCatch() {
        JOptionPane.showMessageDialog(controller,
                "Log file not found",
                "Not Found Exception",
                JOptionPane.ERROR_MESSAGE);
    }

    //EFFECTS: display error dialogue for IOException
    protected void ioExceptionCatch() {
        JOptionPane.showMessageDialog(controller,
                "IOException",
                "IOException",
                JOptionPane.ERROR_MESSAGE);
    }

    //EFFECTS: display error dialogue when trying to add a lab with same name as an existing lab
    protected void alreadyExistsExceptionCatch(String name){
        JOptionPane.showMessageDialog(controller,
                "This name is Taken: "+name,
                "Already Exists Exception",
                JOptionPane.ERROR_MESSAGE);
    }

    ////EFFECTS: helps read part of line from file
    protected static ArrayList<String> splitOnTripleBackSlash(String line){
        String[] splits = line.split("///");
        return new ArrayList<>(Arrays.asList(splits));
    }

    //EFFECTS: capitalize and remove white spaces and quotation marks around str
    protected static String capitalizeAndTrimEnds(String str) {
        str = str.toUpperCase();
        str = str.trim();
        str = str.replaceAll("\"|\'", "");
        return str;
    }


}
