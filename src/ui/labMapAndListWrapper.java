package ui;

import model.Lab;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class labMapAndListWrapper {
    private Map<String, Lab> labMap = new HashMap<>();
    private DefaultListModel listModelLabs =new DefaultListModel();
    public DefaultListModel getListModelLabs() {
        return listModelLabs;
    }
    public Map<String, Lab> getLabMap() {
        return labMap;
    }
}
