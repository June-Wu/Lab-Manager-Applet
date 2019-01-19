package ui.tabs;

import model.labActivity;
import ui.ButtonNames;
import ui.LabApp;
import ui.labMapAndListWrapper;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class LabsTab extends Tab implements ListSelectionListener{
    private JList jListLabs = new JList(listModelLabs);
    private JScrollPane labsScrollPane = new JScrollPane(jListLabs);
    private static final String INIT_GREETING = "Available Labs";
    private static JButton addButton = new JButton(ButtonNames.ADDLAB.getValue());
    private static JButton startButton = new JButton(ButtonNames.STARTLAB.getValue());
    private static JButton deleteButton = new JButton(ButtonNames.DELETELAB.getValue());
    private JTextField labInputField =new JTextField();
    private boolean alreadyEnabled=false;


    public LabsTab(LabApp controller, labMapAndListWrapper m) {
        super(controller, m);
        setLayout(new GridLayout(4,1));
        placeGreeting();
        placeButtonsAndTextPanes();
    }

    //EFFECTS: creates greeting at top of console
    private void placeGreeting(){
        JLabel greeting = new JLabel(INIT_GREETING, JLabel.CENTER);
        this.add(greeting);
    }

    //EFFECTS: places add, start, delete buttons and pane of labs on console
    private void placeButtonsAndTextPanes(){
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridLayout(3,2));

        jListLabs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListLabs.addListSelectionListener(this);
        jListLabs.addMouseListener(mouseListener);

        setAddButton();
        setDeleteButton();
        setStartButton();
        setlabInputField();

        startButton.setEnabled(false);
        addButton.setEnabled(false);
        deleteButton.setEnabled(false);

        buttonPane.add(labInputField);
        buttonPane.add(addButton);
        buttonPane.add(startButton);
        buttonPane.add(deleteButton);


        add(buttonPane,BorderLayout.CENTER);
        add(labsScrollPane,BorderLayout.PAGE_END);

    }


    //MODIFIES: this
    //EFFECTS: creates add button that adds a new lab when valid lab details are entered
    private void setAddButton(){
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equalsIgnoreCase(ButtonNames.ADDLAB.getValue())){
                    String name = labInputField.getText();
                    if(name.equalsIgnoreCase("")){
                        labInputField.requestFocusInWindow();
                        labInputField.selectAll();
                        return;
                    }
                    int index = jListLabs.getSelectedIndex();
                    if(index==-1){
                        index=0;
                    }else{
                        index++;
                    }
                    handleAddLab(name);
                    labInputField.requestFocusInWindow();
                    labInputField.setText("");
                    jListLabs.setSelectedIndex(index);
                    jListLabs.ensureIndexIsVisible(index);
                }
            }
        });
    }


    //MODIFIES: this
    //EFFECTS: creates a start button that logs lab activity when valid observation is entered for the selected lab
    private void setStartButton() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if(buttonPressed.equals(ButtonNames.STARTLAB.getValue())){
                    try {
                        handleStartLab();
                    } catch (IOException e1) {
                        ioExceptionCatch();
                    }

                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: creates a delete button that deletes the selected lab
    private void setDeleteButton(){
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if(buttonPressed.equalsIgnoreCase(ButtonNames.DELETELAB.getValue())){
                    handleDeleteLab();
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: create text field where users enter lab names to add
    private void setlabInputField(){
        labInputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableButton();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                handleEmptyTextField(e);
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                if(!handleEmptyTextField(e)){
                    enableButton();
                }
            }
        });

    }

    //MODIFIES: this
    //EFFECTS: adds a new lab if user inputs valid lab details
    private void handleAddLab(String nameEntry) {
        if ((nameEntry!= null)){
            if(alreadyInList(nameEntry)){
                alreadyExistsExceptionCatch(nameEntry);
                return;
            }
            String newName = capitalizeAndTrimEnds(nameEntry);
            String newProcedure = setLabProcedure();
            String newResult = setLabResult();
            String newType = setLabType();
            if(newName!=null &&newProcedure!=null&&newType!=null) {
                addNewLabOfCorrectType(newName, newProcedure, newResult, newType);
                displayLabDetails(newName, newProcedure, newResult, newType);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: adds new lab activity if user inputs valid observation for the selected lab
    private void handleStartLab() throws IOException {
        String name = capitalizeAndTrimEnds((String) jListLabs.getSelectedValue());

            warning(myMap.get(name));
            labActivity labActivity = new labActivity();
            labActivity.setDoLab(name);
            String observation = setObservation(name);
            if(observation!=null){
                labActivity.setObservation(observation);
                JOptionPane.showMessageDialog(controller,
                        "Your observation: " + observation
                                + "\nExpected result: " + myMap.get(name).getLabResult());
                labLog.add(labActivity);
                System.out.println(labLog);
                handleSaveLogs(labActivity, LOGFILE);
            }
    }

    //MODIFIES: labLogFile.txt
    //EFFECTS: saves lab activity to labLogFile
    private void handleSaveLogs(labActivity labEntry, String labLogFile) throws IOException {
        PrintWriter writer = new PrintWriter(new FileOutputStream(new File(labLogFile), true));
        updateCurrTime();
        writer.println("Time: " +currTime
                +"\nLab: " +labEntry.getDoLab()
                +"\nObservation: " +labEntry.getObservation());
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: deletes the selected lab
    private void handleDeleteLab(){
        int index = jListLabs.getSelectedIndex();
        String labKey = (String) jListLabs.getSelectedValue();
        myMap.remove(labKey);
        listModelLabs.remove(index);
        int size = listModelLabs.getSize();
        if (size == 0) {
            deleteButton.setEnabled(false);
        }
        jListLabs.setSelectedIndex(index);
        jListLabs.ensureIndexIsVisible(index);
    }


    //EFFECTS: returns true if the lab name already exists
    private boolean alreadyInList(String name){
        name= capitalizeAndTrimEnds(name);
        return myMap.containsKey(name);
    }


    private String setLabProcedure() {
        String procedure = JOptionPane.showInputDialog(controller, "Enter lab procedure");
            if (procedure.length()==0) {
                setLabProcedure();
            }
        return procedure;
    }

    //EFFECTS: returns user input for lab observation
    private String setObservation(String name) {
        String observation = JOptionPane.showInputDialog(controller,"Please perform " + name
                + "\nProcedure: " + myMap.get(name).getLabProcedure()+"\nWhat is your result?");
        if(observation.length()==0){
            setObservation(name);
        }
        return observation;
    }


    //EFFECTS: returns user input for lab result
    private String setLabResult() {
        String result = JOptionPane.showInputDialog(controller, "Enter expected result");
        if (result.length() == 0) {
            setLabResult();
        }
        return result;
    }

    //EFFECTS: returns user input for lab type
    private String setLabType() {
        int type=JOptionPane.showConfirmDialog(
                controller,
                "Is this lab dangerous?",
                "Lab Type",
                JOptionPane.YES_NO_OPTION);
        if(type==0){
            return "Dangerous";
        } else if(type==1){
            return "Safe";
        }
        return null;
    }


    //EFFECTS: enables delete and start buttons when a lab is selected
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(e.getValueIsAdjusting()==false){
            if(jListLabs.getSelectedIndex()==-1){
                deleteButton.setEnabled(false);
                startButton.setEnabled(false);
            }else{
                deleteButton.setEnabled(true);
                startButton.setEnabled(true);
            }
        }
    }


    //EFFECTS: displays lab details when lab entry is double clicked
    MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            JList list = (JList) e.getSource();
            if (e.getClickCount() == 2) {
                int index = list.locationToIndex(e.getPoint());
                if(index>=0){
                displayLabDetails(myMap.get(listModelLabs.get(index)).getLabName(),
                        myMap.get(listModelLabs.get(index)).getLabProcedure(),
                        myMap.get(listModelLabs.get(index)).getLabResult(),
                        myMap.get(listModelLabs.get(index)).getLabType());
                }
            }
        }
    };

    //EFFECTS: enables add button when there is text in the text field
    private boolean handleEmptyTextField(DocumentEvent e){
        if(e.getDocument().getLength()<=0){
            addButton.setEnabled(false);
            alreadyEnabled=false;
            return true;
        }
        return false;
    }

    //EFFECTS: enables button if not
    private void enableButton(){
        if(!alreadyEnabled){
            addButton.setEnabled(true);
        }
    }
}
