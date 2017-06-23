package org.barmaley.vkr.tool;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;


public class FileTool {

    final String ROOT_FOLDERS = "/home/gagarkin/data/public/";
    final String ROOT_FOLDERS_TRADE_SECRET = "/home/gagarkin/data/secret/";
    final String EXPDF = ".pdf";
    final String EXZIP = ".zip";

    public void upload(MultipartFile file, String name) {


    }

    public void delete(String path) {
        File file = new File(path);
        file.delete();
    }
}
