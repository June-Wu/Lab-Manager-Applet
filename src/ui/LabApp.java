package ui;

import ui.tabs.LabsTab;
import ui.tabs.LogTab;
import ui.tabs.MainTab;

import javax.swing.*;

public class LabApp extends JFrame {
    private static final int MAIN_TAB_INDEX = 0;
    private static final int LABS_TAB_INDEX = 1;
    private static final int LOG_TAB_INDEX = 2;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private JTabbedPane sidebar;

    public static void main(String[] args) {
        new LabApp();
    }

    private LabApp() {
        super("Lab Manager Applet");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.TOP);

        loadTabs();
        add(sidebar);
        setVisible(true);
    }

  //adds tabs to console frame
    private void loadTabs() {
        labMapAndListWrapper m = new labMapAndListWrapper();
        JPanel mainTab = new MainTab(this, m);
        JPanel labsTab = new LabsTab(this, m);
        JPanel logTab = new LogTab(this, m);

        sidebar.add(mainTab, MAIN_TAB_INDEX);
        sidebar.setTitleAt(MAIN_TAB_INDEX, "Main");
        sidebar.add(labsTab, LABS_TAB_INDEX);
        sidebar.setTitleAt(LABS_TAB_INDEX, "Labs");
        sidebar.add(logTab, LOG_TAB_INDEX);
        sidebar.setTitleAt(LOG_TAB_INDEX, "Report");
    }

}



