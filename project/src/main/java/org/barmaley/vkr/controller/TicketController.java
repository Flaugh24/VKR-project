package org.barmaley.vkr.controller;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.EducProgram;
import org.barmaley.vkr.domain.Ticket;
import org.barmaley.vkr.domain.TypeOfUse;
import org.barmaley.vkr.domain.Users;
import org.barmaley.vkr.service.*;
import org.barmaley.vkr.tool.PermissionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by impolun on 26.06.17.
 */

@Controller
public class TicketController {

    private static Logger logger = Logger.getLogger(MainController.class.getName());

    @Resource(name = "ticketService")
    private TicketService ticketService;

    @Resource(name = "usersService")
    private UsersService usersService;

    @Resource(name = "typeOfUseService")
    private TypeOfUseService typeOfUseService;

    @Resource(name = "statusTicketService")
    private StatusTicketService statusService;

    @Resource(name = "educProgramService")
    private EducProgramService educProgramService;

    @Resource(name = "documentTypeService")
    private DocumentTypeService documentTypeService;

    @Autowired
    private PermissionTool permissionTool;

    @Autowired
    @Qualifier("ticketFormValidator")
    private Validator validator;

    @InitBinder("ticketAttribute")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @PostMapping(value = "/ticket/add", headers = "Content-Type=application/x-www-form-urlencoded")
    public String getAddTicket(@RequestParam(value = "educId") Integer educId,
                               Model model) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EducProgram educProgram = educProgramService.get(educId);
        Ticket ticket = new Ticket();
        String degree = educProgram.getDegree();

        switch (degree) {
            case "Бакалавр":
                ticket.setDocumentType(documentTypeService.get(1));
                break;
            case "Магистр":
                ticket.setDocumentType(documentTypeService.get(2));
                break;
            case "Специалист":
                ticket.setDocumentType(documentTypeService.get(3));
                break;
        }
        ticket.setDateCreationStart(new Date());
        ticket.setUser(user);
        ticket.setStatus(statusService.get(1));
        ticket.setGroupNum(educProgram.getGroupNum());
        ticket.setInstitute(educProgram.getInstitute());
        ticket.setDepartment(educProgram.getDepartment());
        ticket.setDirection(educProgram.getDirection());
        ticket.setDirectionCode(educProgram.getDirectionCode());
        ticket.setKeyWords("#,");
        ticket.setKeyWordsEng("#,");
        ticket.setYearOfPublic(new SimpleDateFormat("y").format(new Date()));
        ticketService.add(ticket);
        model.addAttribute("ticket", ticket);
        return "redirect:/ticket/" + ticket.getId() + "/edit";
    }

    @GetMapping(value = "/ticket/{id}")
    public String getTicket(@PathVariable(value = "id") String ticketId, ModelMap model) {

        boolean perm_check_tickets = permissionTool.checkPermission("PERM_CHECK_TICKETS");

        Ticket ticket = ticketService.get(ticketId);

        model.addAttribute("perm_check_tickets", perm_check_tickets);
        model.addAttribute("ticket", ticket);
        return "ticketPage";
    }



    @GetMapping(value = "/ticket/{id}/edit")
    public String getEditTicket(@PathVariable(value = "id") String ticketId,
                                ModelMap model) {
        Ticket ticket = ticketService.get(ticketId);
        boolean perm_check_tickets = permissionTool.checkPermission("PERM_CHECK_TICKETS");
        boolean perm_check_all_tickets = permissionTool.checkPermission("PERM_CHECK_ALL_TICKETS");
        boolean disabledEdit = true;
        boolean disabledCheck = true;
        if (perm_check_tickets && ticket.getStatus().getId() == 2) {
            ticket.setStatus(statusService.get(3));
            if (ticket.getDateCheckCoordinatorStart() != null) {
                ticket.setDateCheckCoordinatorStart(new Date());
            }
            ticketService.edit(ticket);
        }
        List<TypeOfUse> typesOfUse = typeOfUseService.getAll();
        List<String> words = new ArrayList<>();
        List<String> wordsEng = new ArrayList<>();
        Collections.addAll(words, ticket.getKeyWords().split(","));
        while (words.size() != 4) {
            words.add("#");
        }
        ticket.setKeyWords(String.join(",", words));
        Collections.addAll(wordsEng, ticket.getKeyWordsEng().split(","));
        while (wordsEng.size() != 4) {
            wordsEng.add("#");
        }
        ticket.setKeyWordsEng(String.join(",", wordsEng));


        if ((ticket.getStatus().getId() == 1 || ticket.getStatus().getId() == 5) && !perm_check_tickets) {
            disabledEdit = false;
        }
        if (perm_check_tickets || perm_check_all_tickets) {
            disabledCheck = false;
        }
        model.addAttribute("disabledCheck", disabledCheck);
        model.addAttribute("disabledEdit", disabledEdit);
        model.addAttribute("typesOfUse", typesOfUse);
        model.addAttribute("ticketAttribute", ticket);
        return "editpage";
    }


    @PostMapping(value = "/ticket/{id}/edit")
    public String saveEdit(@PathVariable("id") String ticketId,
                           @ModelAttribute("ticketAttribute") @Validated Ticket ticket, BindingResult bindingResult,
                           ModelMap model, @RequestParam(value = "button") String button) {

        if (!button.equals("save") && bindingResult.hasErrors()) {
            boolean perm_check_tickets = permissionTool.checkPermission("PERM_CHECK_TICKETS");
            boolean perm_check_all_tickets = permissionTool.checkPermission("PERM_CHECK_ALL_TICKETS");
            boolean disabledEdit = true;
            boolean disabledCheck = true;
            List<TypeOfUse> typesOfUse = typeOfUseService.getAll();

            if ((ticket.getStatus().getId() == 1 || ticket.getStatus().getId() == 5) && !perm_check_tickets) {
                disabledEdit = false;
            }
            if (perm_check_tickets || perm_check_all_tickets) {
                disabledCheck = false;
            }
            model.addAttribute("disabledCheck", disabledCheck);
            model.addAttribute("disabledEdit", disabledEdit);
            model.addAttribute("ticketAttribute", ticket);
            model.addAttribute("typesOfUse", typesOfUse);
            return "editpage";
        }
        ticket.setKeyWords(ticket.getKeyWords().replace("#,", ""));
        ticket.setKeyWordsEng(ticket.getKeyWordsEng().replace("#,", ""));

        switch (button) {
            case "send":
                ticket.setStatus(statusService.get(2));
                ticket.setDateCreationFinish(new Date());
                break;
            case "ready":
                ticket.setStatus(statusService.get(4));
                ticket.setDateCheckCoordinatorFinish(new Date());
                break;
            case "return":
                ticket.setStatus(statusService.get(5));
                ticket.setDateReturn(new Date());
                break;
            default:
                ticket.setStatus(statusService.get(ticket.getStatus().getId()));
        }

        ticketService.edit(ticket);

        return "redirect:/ticket/" + ticket.getId();
    }

    @GetMapping(value = "/ticket/{id}/pdf")
    public ResponseEntity getPdf(@PathVariable("id") String ticketId) {
        Ticket ticket = ticketService.get(ticketId);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(ticket.getFilePdf()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("Content-Disposition", "inline");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity(inputStreamResource, headers, HttpStatus.OK);
    }
}
