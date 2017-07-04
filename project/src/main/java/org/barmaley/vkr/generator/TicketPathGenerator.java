package org.barmaley.vkr.generator;

import org.barmaley.vkr.domain.Ticket;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by impolun on 23.06.17.
 */
public class TicketPathGenerator extends SimpleFileVisitor {
    private static final int BUFFER_SIZE = 1024;

    public void getGenerator(boolean flag,String ROOT_ACT, Ticket ticket) {
        try {
            if (flag) {

            } else {
                File f = new File(ROOT_ACT + "/pdf");
                f.mkdir();
                Path pathSource = Paths.get(ticket.getFilePdf());
                Path pathDestination = Paths.get(ROOT_ACT + "/pdf/" + ticket.getId() + ".pdf");
                Files.move(pathSource, pathDestination, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Source file moved pdf successfully");
                f = new File(ROOT_ACT + "/zip");
                f.mkdir();
                pathSource = Paths.get(ticket.getFileZip());
                pathDestination = Paths.get(ROOT_ACT + "/zip/" + ticket.getId() + ".zip");
                Files.move(pathSource, pathDestination, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Source file moved zip successfully");
            }    } catch(IOException e){
                e.printStackTrace();
            }
    }
    public void copyDirectory(File sourceLocation , File targetLocation)
            throws IOException {

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
              }
            } else {

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }}

