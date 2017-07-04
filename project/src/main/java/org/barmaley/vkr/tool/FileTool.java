package org.barmaley.vkr.tool;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileTool {


    private final Logger logger = Logger.getLogger(FileTool.class);
    private final String ROOT_FOLDERS = "/home/gagarkin/data/public/";
    private final String ROOT_FOLDERS_TRADE_SECRET = "/home/gagarkin/data/secret/";
    private final String EXPDF = "pdf";
    private final String EXZIP = "zip";


    //валидация файла
    public boolean checkFile(MultipartFile file) {
        if (!file.isEmpty()) {
            String ext = getFileExtension(file);
            if (ext.equals(EXPDF) || ext.equals(EXZIP)) {
                return true;
            }
        }
        return false;
    }

    //сохранение файла
    public String store(MultipartFile file, String ticketId, boolean secret) throws IOException {
        String extension = getFileExtension(file);
        String fileName = ticketId + "." + extension;
        String folders;
        if (!secret) {
            Path path = Paths.get(ROOT_FOLDERS + fileName);
            Files.copy(file.getInputStream(), path);
            folders = path.toString();
        } else {
            Path path = Paths.get(ROOT_FOLDERS_TRADE_SECRET + fileName);
            Files.copy(file.getInputStream(), path);
            folders = path.toString();
        }
        return folders;
    }

    //удаление файла
    public void delete(String path) {
        File file = new File(path);
        file.delete();
    }

    //метод определения расширения файла
    public String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        // если в имени файла есть точка и она не является первым символом в названии файла
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        // в противном случае возвращаем заглушку, то есть расширение не найдено
        else return "";
    }
}
