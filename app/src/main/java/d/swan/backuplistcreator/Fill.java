package d.swan.backuplistcreator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Fill {

    public ArrayList<Element> ListFill(File path) {
        ArrayList<Element> list = new ArrayList<>(); // сосновной список
        ArrayList<File> mainList = new ArrayList<>(); // список с файлами

        // если папка не корневая, создается элемент ".."
        if(path.getParent() != null)
            mainList.add(new File(".."));

        // если путь читабельный
        if(path.canRead()) {
            ArrayList<File> filesList = new ArrayList<>();

            // перечисление элементов в папке
            for(File file : path.listFiles()) {
                // если папка, добавляется в главный список
                if(file.isDirectory()) mainList.add(file);
                // если файл, добавляется в список файлов
                else filesList.add(file);
            }

            Collections.sort(mainList); // сортировка папок
            Collections.sort(filesList); // сортировка файлов
            mainList.addAll(filesList); // соединение списков
        }

        // перечисление всех элементов списка и присваивание значение каждому по его свойству
        for (File s : mainList) {
            int icon, check = R.drawable.checked;
            String type;
            if(s.isDirectory()) {
                if(s.getName().equals("..")) { // если имя папки ".."
                    icon = R.drawable.up;
                    type = "";
                    check = R.drawable.empty;
                }
                else { // если обычная папка
                    icon = R.drawable.folder;
                    type = "folder";
                }
            }
            else { // если файл, вызывается мктод распознавания и присваивания по типу
                type = new FileTypes().GetFileFormat(s);
                icon = new FileTypes().GetIcon(s);
            }

            // добавление отформатированного списка элементов
            list.add(new Element(icon, s, type, check));
        }

        // возврат списка элементов
        return list;
    }
}
