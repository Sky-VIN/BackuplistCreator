package d.swan.backuplistcreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CreateList {
    private File searchFolder, backupFolder;
    private int foldersCount = 0, filesCount = 0;

    public String Create(ArrayList<File> selectedFiles, File path, boolean subFolders) throws IOException {
        searchFolder = path;
        String log = "";

        // создание уникального имени прородительской папки
        String backupFolderName = path.getAbsolutePath() + "/## " + path.getName().toUpperCase()+ " BACKUP (" + new SimpleDateFormat("dd.MM.yyyy HH-mm-ss").format(System.currentTimeMillis()) + ") ##";
        backupFolder = new File(backupFolderName);

        if(backupFolder.mkdir()) { // если подродительская папка создана
            foldersCount++;

            // создание файла списка в подродительской папке
            File file = new File(backupFolder.getAbsolutePath() + "/" + path.getName() + ".txt");
            file.createNewFile();
            filesCount++;

            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), "cp1251"));

            // перечисляется прилетевший список
            for (File f : selectedFiles)
                // если попался файл, то добавляется в список
                if(!f.isDirectory()) out.append(f.getName() + "\n");
                // если папка и нужно искать в подпапках, вызывается поиск и создание списков в подпапках
                else if(subFolders && f.isDirectory()) createInSubFolders(f);

            out.close();

            // если файл списка пустой, то он удаляется
            if(file.length() == 0) {
                file.delete();
                filesCount--;
            }

            // если все ОК, лог тоже ОК
            log = "Created " + String.valueOf(filesCount) + " backup files\nin " + String.valueOf(foldersCount) + " folders";

        } else log = "Could not create parent folder!"; // если подродительская папка не создана, лог с ошибкой

        return log; // возвращается лог
    }

    // метод поиска и создания списков в подпапках
    private void createInSubFolders(File path) throws IOException {

        // создается имя папки
        String folderName = path.getAbsolutePath();

        // меняется путь с поискового на создаваемый
        folderName = folderName.replace(searchFolder.getAbsolutePath(), backupFolder.getAbsolutePath());

        // создается папка с новым путем
        File folder = new File(folderName);
        folder.mkdir();
        foldersCount++;

        // создание файла списка в этой папке
        File file = new File(folder.getAbsolutePath() + "/" + folder.getName() + ".txt");
        file.createNewFile();
        filesCount++;

        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), "cp1251"));

        // перечисляется список файлов в путе
        for (File f : path.listFiles())
            // если попался файл, то добавляется в список
            if(!f.isDirectory()) out.append(f.getName() + "\n");
            // если папка, вызывается этот же поиск и создание списков в подпапках (рекурсия)
            else createInSubFolders(f);

        out.close();

        // если файл списка пустой, то он удаляется
        if(file.length() == 0) {
            file.delete();
            filesCount--;
        }

        // если папка пустая, то он удаляется
        if(folder.listFiles().length == 0) {
            folder.delete();
            foldersCount--;
        }
    }
}
