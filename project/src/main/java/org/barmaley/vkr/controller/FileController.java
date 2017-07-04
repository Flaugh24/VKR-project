package org.barmaley.vkr.controller;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.Ticket;
import org.barmaley.vkr.service.*;
import org.barmaley.vkr.tool.FileTool;
import org.barmaley.vkr.tool.PermissionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileController {

    private static Logger logger = Logger.getLogger(MainController.class.getName());

    @Resource(name = "ticketService")
    private TicketService ticketService;

    @Autowired
    private FileTool fileTool;

    @PostMapping("/ticket/{id}/file/upload")
    public String singleFileUpload(@PathVariable("id") String ticketId,
                                   @RequestParam("uploadFile") MultipartFile file,
                                   @RequestParam("submit") String submit,
                                   @RequestParam(value = "tradeSecret", required = false) boolean tradeSecret,
                                   ModelMap model) {

        if (!fileTool.checkFile(file)) {
            Ticket ticket = ticketService.get(ticketId);

            model.addAttribute("ticketAttribute", ticket);

            return "editpage";
        }
        Ticket ticket = ticketService.get(ticketId);
        try {
            String path = fileTool.store(file, ticketId, tradeSecret);
            String extension = fileTool.getFileExtension(file);
            boolean secret = false;
            if (extension.equals("pdf")) {
                if (!tradeSecret) {
                    ticket.setFilePdf(path);
                } else {
                    secret = true;
                    ticket.setFilePdfSecret(path);
                }
                ticketService.editPdf(ticket, secret);
            } else {
                if (!tradeSecret) {
                    ticket.setFileZip(path);
                } else {
                    secret = true;
                    ticket.setFileZipSecret(path);
                }
                ticketService.editZip(ticket, secret);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/ticket/" + ticket.getId() + "/edit";
    }

    @GetMapping("/ticket/{id}/file/delete")
    public String singleFileDelete(@PathVariable("id") String ticketId,
                                   @RequestParam("type") String type) throws MalformedURLException {

        logger.info(type);

        Ticket ticket = ticketService.get(ticketId);
        String path = null;
        boolean secret = false;
        switch (type) {
            case "pdf":
                path = ticket.getFilePdf();
                ticket.setFilePdf(null);
                ticketService.editPdf(ticket, secret);
                break;
            case "pdfSecret":
                path = ticket.getFilePdfSecret();
                ticket.setFilePdfSecret(null);
                secret = true;
                ticketService.editPdf(ticket, secret);
                break;
            case "zip":
                path = ticket.getFileZip();
                ticket.setFileZip(null);
                ticketService.editZip(ticket, secret);
                break;
            case "zipSecret":
                path = ticket.getFileZipSecret();
                ticket.setFilePdfSecret(null);
                secret = true;
                ticketService.editZip(ticket, secret);
                break;
        }

        fileTool.delete(path);

        return "redirect:/ticket/" + ticket.getId() + "/edit";

    }

}
