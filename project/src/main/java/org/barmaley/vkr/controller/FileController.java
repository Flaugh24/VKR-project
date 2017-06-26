package org.barmaley.vkr.controller;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.Ticket;
import org.barmaley.vkr.service.*;
import org.barmaley.vkr.tool.PermissionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileController {

    private static Logger logger = Logger.getLogger(MainController.class.getName());

    @Resource(name = "ticketService")
    private TicketService ticketService;

    @Resource(name = "permissionTool")
    private PermissionTool permissionTool;

    @Autowired
    @Qualifier("ticketFormValidator")
    private Validator validator;

    @InitBinder("ticketAttribute")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }
    // Сохранение файлов на сервер
    @PostMapping("/ticket/fileupload")
    public String singleFileUpload(@RequestParam("uploadFile") MultipartFile file,
                                   @RequestParam("ticketId") String ticketId,
                                   @RequestParam("submit") String submit,
                                   @RequestParam(value = "tradeSecret", required = false) String tradeSecret) {



        String fullPath,
                ROOT_FOLDERS = "/home/impolun/data/public/",
                ROOT_FOLDERS_TRADE_SECRET="/home/impolun/data/secret/",
                EXPDF = ".pdf",
                EXZIP = ".zip";
        Ticket ticket = ticketService.get(ticketId);

        try {

            byte[] bytes = file.getBytes();

            //Определяем расширешие файла(будет храниться в expansion)
            String expansion = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4, file.getOriginalFilename().length());
            if (submit.equals("Загрузить")) {
                if (expansion.equals(EXPDF)) {
                    if (tradeSecret==null) {

                        fullPath = ROOT_FOLDERS + ticket.getId() + EXPDF;
                        Path path = Paths.get(fullPath);
                        Files.write(path, bytes);
                        ticket.setFilePdf(fullPath);

                    }
                    else{
                        fullPath = ROOT_FOLDERS_TRADE_SECRET + ticket.getId() + EXPDF;
                        Path path = Paths.get(fullPath);
                        Files.write(path, bytes);
                        ticket.setFilePdfSecret(fullPath);
                    }


                    ticketService.editPdf(ticket);
                } else {
                    if (tradeSecret==null) {
                        fullPath = ROOT_FOLDERS + ticket.getId() + EXZIP;
                        Path path = Paths.get(fullPath);
                        Files.write(path, bytes);
                        ticket.setFileZip(fullPath);
                    }
                    else{
                        fullPath = ROOT_FOLDERS_TRADE_SECRET + ticket.getId() + EXZIP;
                        Path path = Paths.get(fullPath);
                        Files.write(path, bytes);
                        ticket.setFileZipSecret(fullPath);
                    }
                    ticketService.editZip(ticket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Boolean check_tickets = permissionTool.checkPermission("PERM_CHECK_TICKETS");

        if (check_tickets) {
            return "redirect:/ticket/check?ticketId=" + ticket.getId();
        } else {
            return "redirect:/ticket/edit?ticketId=" + ticket.getId();
        }
    }
    //Удаление файла из сервера
    @PostMapping("/ticket/filedelete")
    public String singleFileDelete(/*@RequestParam("uploadFile") MultipartFile file*/
                                   @RequestParam("ticketId") String ticketId,
                                   @RequestParam("submit") String submit) throws MalformedURLException {

        Ticket ticket = ticketService.get(ticketId);
        if (submit.equals("Удалить PDF")) {
            File file;
            if(ticket.getFilePdf()!=null){
                file = new File(ticket.getFilePdf());
                ticket.setFilePdf(null);
            }
            else{
                file = new File(ticket.getFilePdfSecret());
                ticket.setFilePdfSecret(null);
            }
            if (file.delete()) {
            } else logger.debug("Файла" + ticket.getFilePdf() + " не обнаружено");
            ticketService.editPdf(ticket);
        }
        if (submit.equals("Удалить архив")) {
            logger.debug("УДАЛЕНИЕ АРХИВА");
            File file;
            if(ticket.getFileZip()!=null){
                file = new File(ticket.getFileZip());
                ticket.setFileZip(null);
            }
            else{
                file = new File(ticket.getFileZipSecret());
                ticket.setFileZipSecret(null);
            }
            if (file.delete()) {
                logger.debug(ticket.getFileZip() + " файл удален");
            } else logger.debug("Файла" + ticket.getFileZip() + " не обнаружено");
            ticketService.editZip(ticket);
        }

        Boolean check_tickets = permissionTool.checkPermission("PERM_CHECK_TICKETS");

        if (check_tickets) {
            return "redirect:/ticket/check?ticketId=" + ticket.getId();
        } else {
            return "redirect:/ticket/edit?ticketId=" + ticket.getId();
        }
    }
    // Генерация pdf документа
    @ResponseBody
    @GetMapping("/pdfDocument")
    public String getPdf(@RequestParam(value = "ticketId") String ticketId) {
        Ticket ticket = ticketService.get(ticketId);
        return ticket.getFilePdf();
    }

}
