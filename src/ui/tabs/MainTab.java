package ui.tabs;
import model.Lab;
import ui.ButtonNames;
import ui.LabApp;
import ui.labMapAndListWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainTab extends Tab{
    private static final String INIT_GREETING = "Welcome to Lab App!";


    public MainTab(LabApp controller, labMapAndListWrapper m) {
        super(controller, m);

        setLayout(new GridLayout(4,1));

        placeGreeting();
        placeLoadButton();
        placeSaveButton();
    }

    //EFFECTS: creates greeting at top of console
    private void placeGreeting(){
        JLabel greeting = new JLabel(INIT_GREETING, JLabel.CENTER);
        this.add(greeting);
    }

    //MODIFIES: this
    //EFFECTS: creates load button on console that loads labs
    private void placeLoadButton() {
        JButton loadButton = new JButton(ButtonNames.LOADLABS.getValue());
        JPanel buttonRow = formatButtonRow(loadButton);

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if(buttonPressed.equals(ButtonNames.LOADLABS.getValue())){
                    try {
                        handleLoadLabs();
                    } catch (IOException e1) {
                        ioExceptionCatch();
                    }
                }
            }
        });
        this.add(buttonRow);
    }

    //MODIFIES: labFile.txt
    //EFFECTS: creates save button on console that saves labs
    private void placeSaveButton() {
        JButton saveButton = new JButton(ButtonNames.SAVELABS.getValue());

        JPanel buttonRow = formatButtonRow(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if(buttonPressed.equals(ButtonNames.SAVELABS.getValue())){
                    try {
                        handleSaveLabs();
                    } catch (IOException e1) {
                        ioExceptionCatch();
                    }
                }
            }
        });
        this.add(buttonRow);
    }

    //MODIFIES: this
    //EFFECTS: loads labs from file
    protected void handleLoadLabs() throws IOException {
        String labsLoaded="";
        List<String> lines = Files.readAllLines(Paths.get(LABFILE));
        for (String line:lines){
            ArrayList<String> partsOfLine = splitOnTripleBackSlash(line);
            if(partsOfLine.get(0)==null||partsOfLine.get(1)==null||partsOfLine.get(2)==null||partsOfLine.get(3)==null){
                return;
            }
            if(addNewLabOfCorrectType(partsOfLine.get(0), partsOfLine.get(1), partsOfLine.get(2),partsOfLine.get(3))){
                labsLoaded="\n"+partsOfLine.get(0)+labsLoaded;
            }
        }
        if(labsLoaded.length()>0) {
            JOptionPane.showMessageDialog(controller,
                    "Labs loaded: " + labsLoaded,
                    "Load Labs",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    //MODIFIES: labFile.txt
    //EFFECTS: saves labs to file
    protected void handleSaveLabs() throws IOException {
        String labsSaved="";
        PrintWriter writer = new PrintWriter(LABFILE, "UTF-8");
        for (Lab lab : myMap.values()) {
            writer.println(lab.getLabName() + "///" + lab.getLabProcedure() + "///" + lab.getLabResult() + "///" + lab.getLabType()
            );
            labsSaved="\n"+lab.getLabName()+labsSaved;
        }
        if(labsSaved.length()>0){
            JOptionPane.showMessageDialog(controller,
                    "Labs Saved: "+labsSaved,
                    "Save Labs",
                    JOptionPane.PLAIN_MESSAGE);
        }
        writer.close();
    }


}
