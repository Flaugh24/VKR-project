package org.barmaley.vkr.generator;

import org.barmaley.vkr.domain.Ticket;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by impolun on 23.06.17.
 */
public class TicketPathGenerator {

    public void getGenerator(boolean flag,String ROOT_ACT, Ticket ticket) {
        try {
            if (flag) {

            } else {
                File f = new File(ROOT_ACT + "/pdf");
                f.mkdir();
                Path pathSource = Paths.get(ticket.getFilePdf());
                Path pathDestination = Paths.get(ROOT_ACT + "/pdf/" + ticket.getId() + ".pdf");
                Files.copy(pathSource, pathDestination, StandardCopyOption.REPLACE_EXISTING);
                Files.deleteIfExists(pathSource);
                System.out.println("Source file copied pdf successfully");
                f = new File(ROOT_ACT + "/zip");
                f.mkdir();
                pathSource = Paths.get(ticket.getFileZip());
                pathDestination = Paths.get(ROOT_ACT + "/zip/" + ticket.getId() + ".zip");
                Files.copy(pathSource, pathDestination, StandardCopyOption.REPLACE_EXISTING);
                Files.deleteIfExists(pathSource);
                System.out.println("Source file copied zip successfully");
            }    } catch(IOException e){
                e.printStackTrace();
            }
    }

    private Path source, destination;

    public TicketPathGenerator(Path s, Path d) {
        source = s;
        destination = d;
    }
    public TicketPathGenerator(){}

    public FileVisitResult visitFile(Path path,
                                     BasicFileAttributes fileAttributes) {
        Path newd = destination.resolve(source.relativize(path));
        try {
            Files.copy(path, newd, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult preVisitDirectory(Path path,
                                             BasicFileAttributes fileAttributes) {
        Path newd = destination.resolve(source.relativize(path));
        try {
            Files.copy(path, newd, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileVisitResult.CONTINUE;
    }
}

