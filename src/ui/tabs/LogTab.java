package ui.tabs;
import ui.ButtonNames;
import ui.LabApp;
import ui.labMapAndListWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

public class LogTab extends Tab{
    JButton viewLogButton = new JButton(ButtonNames.VIEWLOG.getValue());
    JButton clearLogButton = new JButton(ButtonNames.CLEARLOG.getValue());
    private JScrollPane logPane= new JScrollPane(new JTextArea());
    private JTextArea logText= new JTextArea();
    private JLabel logMessage= new JLabel();

    public LogTab(LabApp controller, labMapAndListWrapper m) {
        super(controller, m);
        updateCurrTime();

        setLayout(new GridLayout(4,1));
        placeGreeting();
        placeButtonsLabelPane();

        logText.setEditable(false);
        logText.setVisible(true);

    }

    //EFFECTS: creates greeting at top of console
    private void placeGreeting(){
        JLabel greeting = new JLabel(INIT_GREETING, JLabel.CENTER);
        this.add(greeting);
    }

    // //EFFECTS: places view report button, clear button, and log history pane to console
    private void placeButtonsLabelPane(){
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridLayout(3,3));

        setLogButton();
        setClearButton();

        buttonPane.add(viewLogButton);
        buttonPane.add(clearLogButton);
        buttonPane.add(logMessage);
        add(buttonPane,BorderLayout.CENTER);
        add(logPane,BorderLayout.PAGE_END);
    }

    //MODIFIES: this
    //EFFECTS: creates a view log button that prints app status when clicked
    private void setLogButton() {
        viewLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.VIEWLOG.getValue())) {
                    updateCurrTime();
                    String message = currTime;
                    logMessage.setText(message);
                    resetLog();
                    logPane.setViewportView(logText);
                }
            }
        });
    }

    //MODIFIES: labLogFile.txt
    //EFFECTS: creates a clear button that clears log history
    private void setClearButton(){
        clearLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if(buttonPressed.equals(ButtonNames.CLEARLOG.getValue())){
                    try {
                        handleClearLogHistory(LOGFILE);
                    } catch (FileNotFoundException e1) {
                        logNotFoundCatch();
                    }
                }
            }
        });
    }

    //MODIFIES: labLogFile.txt
    //EFFECTS: clears log history
    private void handleClearLogHistory(String LOGFILE) throws FileNotFoundException {
        new PrintWriter(LOGFILE).close();
        resetLog();
    }

    //MODIFIES: this
    //EFFECTS: sets the text of log pane to be the current log history
    private void resetLog() {
        try
        {
            FileReader reader = new FileReader( LOGFILE);
            BufferedReader br = new BufferedReader(reader);
            logText.read( br, null );
            br.close();
            logText.requestFocus();
        }
        catch(Exception e2) { System.out.println(e2); }
    }





}
