package d.swan.backuplistcreator;

import java.io.File;

public class FileTypes {

    public String GetFileFormat(File file) {
        String result = "";
        String extension = "";
        String fileName = file.getName();

        // нахождение расширения файла
        int i = fileName.lastIndexOf('.');
        if (i > 0)
            extension = fileName.substring(i + 1);
        extension.toLowerCase();

        // если файл без расширения
        if (extension.equals(""))
            result = "File without extension";

        // если текстовый файл
        else if (extension.equals("txt"))
            result = "Text file";

        // если APK файл
        else if (extension.equals("apk"))
            result = "Android Package Archive";

        // если картинка
        else if (extension.equals("arw") || extension.equals("bmp")
                || extension.equals("cr2") || extension.equals("crw")
                || extension.equals("dng") || extension.equals("gif")
                || extension.equals("jpe") || extension.equals("jpeg")
                || extension.equals("jpg") || extension.equals("mrw")
                || extension.equals("nef") || extension.equals("orf")
                || extension.equals("pcx") || extension.equals("pef")
                || extension.equals("png") || extension.equals("psd")
                || extension.equals("raf") || extension.equals("rw2")
                || extension.equals("srf") || extension.equals("tga")
                || extension.equals("tif") || extension.equals("tiff")
                || extension.equals("wmf"))
            result = "Image file";

        // если аудио файл
        else if (extension.equals("aac") || extension.equals("ac3")
                || extension.equals("acs2") || extension.equals("aif")
                || extension.equals("aiff") || extension.equals("ape")
                || extension.equals("cda") || extension.equals("cue")
                || extension.equals("fla") || extension.equals("flac")
                || extension.equals("it") || extension.equals("kar")
                || extension.equals("m3u") || extension.equals("m3u8")
                || extension.equals("m4a") || extension.equals("mac")
                || extension.equals("mid") || extension.equals("midi")
                || extension.equals("mo3") || extension.equals("mod")
                || extension.equals("mp+") || extension.equals("mp1")
                || extension.equals("mp2") || extension.equals("mp3")
                || extension.equals("mpc") || extension.equals("mpp")
                || extension.equals("mtm") || extension.equals("oga")
                || extension.equals("ogg") || extension.equals("ofr")
                || extension.equals("ofs") || extension.equals("plc")
                || extension.equals("pls") || extension.equals("rmi")
                || extension.equals("s3m") || extension.equals("spx")
                || extension.equals("tta") || extension.equals("umx")
                || extension.equals("wav") || extension.equals("wma")
                || extension.equals("wv") || extension.equals("xm"))
            result = "Audio file";

        // если видео файл
        else if (extension.equals("avi") || extension.equals("avs")
                || extension.equals("ogm") || extension.equals("ogx")
                || extension.equals("ogv") || extension.equals("mpg")
                || extension.equals("mkv") || extension.equals("3gp")
                || extension.equals("mov") || extension.equals("asf")
                || extension.equals("divx") || extension.equals("m1v")
                || extension.equals("m2v") || extension.equals("mpe")
                || extension.equals("mpeg") || extension.equals("mpv")
                || extension.equals("m4v") || extension.equals("mp4")
                || extension.equals("mts") || extension.equals("tp")
                || extension.equals("qt") || extension.equals("flv")
                || extension.equals("f4v") || extension.equals("rm")
                || extension.equals("rmvb") || extension.equals("rv")
                || extension.equals("vob") || extension.equals("wm")
                || extension.equals("wmv") || extension.equals("vc1")
                || extension.equals("m2ts") || extension.equals("hdmov")
                || extension.equals("evo") || extension.equals("bik")
                || extension.equals("webm") || extension.equals("wtv")
                || extension.equals("dvr-ms") || extension.equals("h265")
                || extension.equals("265") || extension.equals("hevc")
                || extension.equals("h264") || extension.equals("264"))
            result = "Video file";

        // если Word документ
        else if (extension.equals("doc") || extension.equals("dot")
                || extension.equals("docx") || extension.equals("docm")
                || extension.equals("dotx") || extension.equals("dotm")
                || extension.equals("docb"))
            result = "Microsoft Word document";

        // если Excel документ
        else if (extension.equals("xls") || extension.equals("xlt")
                || extension.equals("xlm") || extension.equals("xlsx")
                || extension.equals("xlsm") || extension.equals("xltx")
                || extension.equals("xltm") || extension.equals("xlsb")
                || extension.equals("xla") || extension.equals("xlam")
                || extension.equals("xll") || extension.equals("xlw"))
            result = "Microsoft Excel document";

        // если документ призентации
        else if (extension.equals("ppt") || extension.equals("pot")
                || extension.equals("pps") || extension.equals("pptx")
                || extension.equals("pptm") || extension.equals("potx")
                || extension.equals("potm") || extension.equals("ppam")
                || extension.equals("ppsx") || extension.equals("ppsm")
                || extension.equals("sldx") || extension.equals("sldm"))
            result = "Microsoft PowerPoint document";

        // если Access файл
        else if (extension.equals("accdb") || extension.equals("accde")
                || extension.equals("accdt") || extension.equals("accdr"))
            result = "Microsoft Access document";

        // если архив
        else if (extension.equals("rar") || extension.equals("tar")
                || extension.equals("zip") || extension.equals("gzip")
                || extension.equals("cab") || extension.equals("uue")
                || extension.equals("arj") || extension.equals("bz2")
                || extension.equals("lzh") || extension.equals("jar")
                || extension.equals("ace") || extension.equals("iso")
                || extension.equals("7z") || extension.equals("z"))
            result = "Archive file";

        else
            result = (extension.toUpperCase() + " file");

        return result;
    }

    // метод определения, какую иконку присвоить файлу
    public int GetIcon(File file) {
        int icon = R.drawable.blank;
        if (GetFileFormat(file) == "Text file") icon = R.drawable.text;
        else if (GetFileFormat(file) == "Image file") icon = R.drawable.image;
        else if (GetFileFormat(file) == "Audio file") icon = R.drawable.music;
        else if (GetFileFormat(file) == "Video file") icon = R.drawable.video;
        else if (GetFileFormat(file) == "Android Package Archive") icon = R.drawable.apk;

        return icon;
    }

    // метод определения формата файла для вызова
    public String GetUriType(File file) {
        String result = "application/*";

        if (GetFileFormat(file) == "Text file") result = "text/*";
        else if (GetFileFormat(file) == "Image file") result = "image/*";
        else if (GetFileFormat(file) == "Audio file") result = "audio/*";
        else if (GetFileFormat(file) == "Video file") result = "video/*";
        else if (GetFileFormat(file) == "Archive file") result = "application/zip";
        else if (GetFileFormat(file) == "Android Package Archive") result = "application/vnd.android.package-archive";

        return result;
    }


}
