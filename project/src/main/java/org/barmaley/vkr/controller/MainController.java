package org.barmaley.vkr.controller;

import org.apache.log4j.Logger;
import org.barmaley.vkr.autentication.CustomUser;
import org.barmaley.vkr.domain.*;
import org.barmaley.vkr.dto.TagDTO;
import org.barmaley.vkr.service.*;
import org.barmaley.vkr.dto.TicketEditDTO;
import org.barmaley.vkr.Tool.PermissionTool;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Handles and retrieves person request
 * Управляет и возвращает запрос
 */
@Controller
public class MainController {

    protected static Logger logger = Logger.getLogger("controller");

    @Resource(name="ticketService")
    private TicketService ticketService;

    @Resource(name="statusService")
    private StatusService statusService;

    @Resource(name="documentTypeService")
    private DocumentTypeService documentTypeService;

    @Resource(name="typeOfUseService")
    private TypeOfUseService typeOfUseService;

    @Resource(name = "usersService")
    private UsersService usersService;

    @Resource(name = "permissionTool")
    private PermissionTool permissionTool;


    @Resource(name = "employeeCopyService")
    private EmployeeCopyService employeeCopyService;


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
    public String user(){
        logger.debug("MainController./user");
        Boolean add_ticket_for_educ_program = permissionTool.checkPermission("PERM_ADD_TCIKET_FOR_EDUC_PROGRAM");
        Boolean check_tickets = permissionTool.checkPermission("PERM_CHECK_TICKETS");
        if(add_ticket_for_educ_program == true){
            return "redirect:/student";
        } else if (check_tickets == true){
            return "redirect:/coordinator";
        }
        return "pnh";
    }

    @PostMapping(value = "/user/profile")
    public String saveProfile(@ModelAttribute("user") Users user){

        logger.debug("edit profile");
        usersService.editUser(user);
        return "redirect:/user";
    }

    @RequestMapping(value = "/getTags", method = RequestMethod.GET)
    public @ResponseBody
    List<EmployeeCopy> getTags(@RequestParam String tagName) {
        logger.debug("get tags");
        return simulateSearchResult(tagName);

    }

    private List<EmployeeCopy> simulateSearchResult(String tagName) {

        List<EmployeeCopy> result = employeeCopyService.getEmployeeByFIO(tagName);

        return result;
    }

    @PostMapping("/ticket/fileupload")
    public String singleFileUpload(@RequestParam("uploadFile") MultipartFile file,
                                   @RequestParam("ticketId") String ticketId,
                                   @RequestParam("submit") String submit) {

        logger.debug("Upload PDF File");

        String fullPath,
                ROOT_FOLDERS="/home/gagarkin/tmp/",
                EXPDF = ".pdf",
                EXZIP = ".zip";
        Ticket ticket = ticketService.get(ticketId);

        try{

            byte[] bytes = file.getBytes();

            //Определяем расширешие файла(будет храниться в expansion)
            logger.debug("Original file name:"+file.getOriginalFilename());
            String expansion = file.getOriginalFilename().substring(file.getOriginalFilename().length()-4,file.getOriginalFilename().length());
            logger.debug(expansion);

            if(submit.equals("Загрузить"))
            {
                if(expansion.equals(EXPDF)) {
                    fullPath = ROOT_FOLDERS + ticket.getId() + EXPDF;
                    logger.debug("Конечный путь файла pdf= "+fullPath);
                    Path path = Paths.get(fullPath);
                    Files.write(path, bytes);
                    ticket.setFilePdf(fullPath);
                    ticketService.editPdf(ticket);
                }
                else {
                    fullPath = ROOT_FOLDERS + ticket.getId() + EXZIP;
                    logger.debug("Конечный путь файла zip = "+fullPath);
                    Path path = Paths.get(fullPath);
                    Files.write(path, bytes);
                    ticket.setFileZip(fullPath);
                    ticketService.editZip(ticket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Boolean check_tickets = permissionTool.checkPermission("PERM_CHECK_TICKETS");

        if(check_tickets){
            return "redirect:/ticket/check?ticketId=" + ticket.getId();
        }else{
            return "redirect:/ticket/edit?ticketId=" + ticket.getId();
        }
    }

    @PostMapping("/ticket/filedelete")
    public String singleFileDelete(/*@RequestParam("uploadFile") MultipartFile file*/
                                   @RequestParam("ticketId") String ticketId,
                                   @RequestParam("submit") String submit) throws MalformedURLException {
        String ROOT_FOLDERS="/home/gagarkin/tmp/";
        logger.debug("Upload PDF File");

        Ticket ticket = ticketService.get(ticketId);
        if(submit.equals("Удалить PDF")){
            File file = new File(ticket.getFilePdf());
            if(file.delete()){
                logger.debug(ticket.getFilePdf()+" файл удален");
            }else logger.debug("Файла"+ticket.getFilePdf()+" не обнаружено");
            ticket.setFilePdf(null);
            ticketService.editPdf(ticket);
        }
        if(submit.equals("Удалить архив")){
            File file = new File(ticket.getFileZip());
            if(file.delete()){
                logger.debug(ticket.getFileZip()+" файл удален");
            }else logger.debug("Файла"+ticket.getFileZip()+" не обнаружено");
            ticket.setFileZip(null);
            ticketService.editZip(ticket);
        }

        Boolean check_tickets = permissionTool.checkPermission("PERM_CHECK_TICKETS");

        if(check_tickets){
            return "redirect:/ticket/check?ticketId=" + ticket.getId();
        }else{
            return "redirect:/ticket/edit?ticketId=" + ticket.getId();
        }
    }
}