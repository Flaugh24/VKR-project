package org.barmaley.vkr.controller;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.EmployeeCopy;
import org.barmaley.vkr.domain.Ticket;
import org.barmaley.vkr.domain.Users;
import org.barmaley.vkr.service.EmployeeCopyService;
import org.barmaley.vkr.service.TicketService;
import org.barmaley.vkr.service.UsersService;
import org.barmaley.vkr.tool.FileTool;
import org.barmaley.vkr.tool.PermissionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;


@Controller
public class MainController {

    private static Logger logger = Logger.getLogger(MainController.class.getName());

    @Resource(name = "ticketService")
    private TicketService ticketService;

    @Resource(name = "usersService")
    private UsersService usersService;

    @Resource(name = "employeeCopyService")
    private EmployeeCopyService employeeCopyService;

    @Autowired
    private PermissionTool permissionTool;

    @Autowired
    private FileTool fileTool;

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
        boolean change_config = permissionTool.checkPermission("PERM_CHANGE_CONFIG");
        if (add_ticket_for_educ_program) {
            logger.info("redirect to student");
            return "redirect:/student";
        } else if (check_tickets) {
            logger.info("redirect to coordinator");
            return "redirect:/coordinator";
        } else if (check_acts) {
            logger.info("redirect to bibliographer");
            return "redirect:/bibliographer";
        } else if (change_config) {
            logger.info("redirect to admin");
            return "redirect:/admin";
        } else {
            return "accessDenied";
        }
    }

    @PostMapping(value = "/profile")
    public String saveProfile(@ModelAttribute("user") Users user) {

        usersService.edit(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/getEmployee", method = RequestMethod.GET)
    public @ResponseBody
    List<EmployeeCopy> getCurator(@RequestParam String fullName) {

        logger.info("123123123");
        return (List<EmployeeCopy>) employeeCopyService.getEmployeeByFullName(fullName);

    }


    @PostMapping("/ticket/{id}/fileupload")
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