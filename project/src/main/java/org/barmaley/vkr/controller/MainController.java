package org.barmaley.vkr.controller;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.EmployeeCopy;
import org.barmaley.vkr.domain.Ticket;
import org.barmaley.vkr.domain.Users;
import org.barmaley.vkr.service.EmployeeCopyService;
import org.barmaley.vkr.service.TicketService;
import org.barmaley.vkr.service.UsersService;
import org.barmaley.vkr.tool.PermissionTool;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
public class MainController {

    protected static Logger logger = Logger.getLogger("controller");

    @Resource(name = "ticketService")
    private TicketService ticketService;

    @Resource(name = "usersService")
    private UsersService usersService;

    @Resource(name = "permissionTool")
    private PermissionTool permissionTool;


    @Resource(name = "employeeCopyService")
    private EmployeeCopyService employeeCopyService;


    public Users getPrincipal() {

        return (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    @GetMapping(value = ("/login"))
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
    }

    @GetMapping(value = {"/user", "/"})
    public String user() {
        logger.debug("MainController./user");
        boolean add_ticket_for_educ_program = permissionTool.checkPermission("PERM_ADD_TCIKET_FOR_EDUC_PROGRAM");
        boolean check_tickets = permissionTool.checkPermission("PERM_CHECK_TICKETS");
        boolean check_acts = permissionTool.checkPermission("PERM_CHECK_ACTS");
        if (add_ticket_for_educ_program) {
            return "redirect:/student";
        } else if (check_tickets) {
            return "redirect:/coordinator";
        } else if (check_acts) {
            return "redirect:/bibliographer";
        }
        return "pnh";
    }

    @PostMapping(value = "/user/profile")
    public String saveProfile(@ModelAttribute("user") Users user) {

        logger.debug("edit profile");
        usersService.editUser(user);
        return "redirect:/user";
    }

    @RequestMapping(value = "/getTags", method = RequestMethod.GET)
    public @ResponseBody
    List<EmployeeCopy> getTags(@RequestParam String tagName) {
        logger.debug("get tags");

        return (List<EmployeeCopy>) employeeCopyService.getEmployeeByFIO(tagName);

    }


    @PostMapping("/ticket/fileupload")
    public String singleFileUpload(@RequestParam("uploadFile") MultipartFile file,
                                   @RequestParam("ticketId") String ticketId,
                                   @RequestParam("submit") String submit,
                                   @RequestParam(value = "tradeSecret", required = false) String tradeSecret) {

        logger.debug("tradeSecret= "+tradeSecret);
        logger.debug("Upload Files");


        String fullPath,
                ROOT_FOLDERS = "/home/impolun/data/",
                ROOT_FOLDERS_TRADE_SECRET="/home/impolun/datasecret/",
                EXPDF = ".pdf",
                EXZIP = ".zip";
        Ticket ticket = ticketService.get(ticketId);

        try {

            byte[] bytes = file.getBytes();

            //Определяем расширешие файла(будет храниться в expansion)
            logger.debug("Original file name:" + file.getOriginalFilename());
            String expansion = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4, file.getOriginalFilename().length());
            logger.debug(expansion);
            if (submit.equals("Загрузить")) {
                logger.debug("1");
                if (expansion.equals(EXPDF)) {
                    logger.debug("2");

                    if (tradeSecret==null) {
                        logger.debug("3");

                        fullPath = ROOT_FOLDERS + ticket.getId() + EXPDF;
                        logger.debug("Конечный путь файла pdf= " + fullPath);
                        Path path = Paths.get(fullPath);
                        Files.write(path, bytes);
                        ticket.setFilePdf(fullPath);
                        logger.debug("4");

                    }
                    else{
                        logger.debug("5");

                        fullPath = ROOT_FOLDERS_TRADE_SECRET + ticket.getId() + EXPDF;
                        logger.debug("Конечный путь файла pdf= " + fullPath);
                        Path path = Paths.get(fullPath);
                        Files.write(path, bytes);
                        ticket.setFilePdfSecret(fullPath);
                        logger.debug("6");

                    }


                    ticketService.editPdf(ticket);
                } else {
                    if (tradeSecret==null) {
                        logger.debug("3");

                        fullPath = ROOT_FOLDERS + ticket.getId() + EXZIP;
                        logger.debug("Конечный путь файла pdf= " + fullPath);
                        Path path = Paths.get(fullPath);
                        Files.write(path, bytes);
                        ticket.setFileZip(fullPath);
                        logger.debug("4");

                    }
                    else{
                        logger.debug("5");

                        fullPath = ROOT_FOLDERS_TRADE_SECRET + ticket.getId() + EXZIP;
                        logger.debug("Конечный путь файла pdf= " + fullPath);
                        Path path = Paths.get(fullPath);
                        Files.write(path, bytes);
                        ticket.setFileZipSecret(fullPath);
                        logger.debug("6");

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

    @PostMapping("/ticket/filedelete")
    public String singleFileDelete(/*@RequestParam("uploadFile") MultipartFile file*/
                                   @RequestParam("ticketId") String ticketId,
                                   @RequestParam("submit") String submit) throws MalformedURLException {
        String ROOT_FOLDERS = "/home/impolun/data/";
        logger.debug("Upload PDF File");

        Ticket ticket = ticketService.get(ticketId);
        if (submit.equals("Удалить PDF")) {
            logger.debug("УДАЛЕНИЕ PDF");
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
                logger.debug(ticket.getFilePdf() + " файл удален");
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
}