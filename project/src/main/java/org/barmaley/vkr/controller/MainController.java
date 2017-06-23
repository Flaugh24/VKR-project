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

    private static Logger logger = Logger.getLogger(MainController.class.getName());

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

    @GetMapping(value = "/403")
    public String accessDenied() {

        return "403";
    }

    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Неправильный логин или пароль");
        }

        if (logout != null) {
            model.addAttribute("message", "Вы успешно вышли из системы");
        }

        return "login";
    }

    @GetMapping(value = "/")
    public String user() {
        logger.debug("MainController./");
        boolean add_ticket_for_educ_program = permissionTool.checkPermission("PERM_ADD_TCIKET_FOR_EDUC_PROGRAM");
        boolean check_tickets = permissionTool.checkPermission("PERM_CHECK_TICKETS");
        boolean check_acts = permissionTool.checkPermission("PERM_CHECK_ACTS");
        if (add_ticket_for_educ_program) {
            logger.info("redirect to student");
            return "redirect:/student";
        } else if (check_tickets) {
            logger.info("redirect to coordinator");
            return "redirect:/coordinator";
        } else if (check_acts) {
            logger.info("redirect to bibliographer");
            return "redirect:/bibliographer";
        } else {
            return "accessDenied";
        }
    }

    @PostMapping(value = "/profile")
    public String saveProfile(@ModelAttribute("user") Users user) {

        usersService.editUser(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/getTags", method = RequestMethod.GET)
    public @ResponseBody
    List<EmployeeCopy> getTags(@RequestParam String tagName) {

        return (List<EmployeeCopy>) employeeCopyService.getEmployeeByFIO(tagName);

    }


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
}