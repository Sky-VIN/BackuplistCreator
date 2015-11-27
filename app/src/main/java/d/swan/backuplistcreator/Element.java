package d.swan.backuplistcreator;

import java.io.File;

public class Element {
    File name;
    String type;
    int icon, check;

    Element(int icon, File name, String type, int check) {
        this.icon = icon;
        this.name = name;
        this.type = type;
        this.check = check;
    }
}
