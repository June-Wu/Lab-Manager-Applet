package ui;

public enum ButtonNames {
    LOADLABS ("Load Labs"),
    SAVELABS ("Save Labs"),
    STARTLAB ("Start Lab"),
    ADDLAB("Add"),
    DELETELAB("Delete"),
    CLEARLOG ("Clear"),
    VIEWLOG("Lab Report");

    private final String name;

    ButtonNames(String name){
        this.name = name;
    }

    //EFFECTS: returns name value of this button
    public String getValue(){
        return name;
    }
}
