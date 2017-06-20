package org.barmaley.vkr.controller;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.EducProgram;
import org.barmaley.vkr.domain.Ticket;
import org.barmaley.vkr.domain.TypeOfUse;
import org.barmaley.vkr.domain.Users;
import org.barmaley.vkr.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class StudentController {

    protected static Logger logger = Logger.getLogger(StudentController.class.getName());

    @Resource(name = "ticketService")
    private TicketService ticketService;

    @Resource(name = "documentTypeService")
    private DocumentTypeService documentTypeService;

    @Resource(name = "statusTicketService")
    private StatusTicketService statusService;

    @Resource(name = "usersService")
    private UsersService usersService;

    @Resource(name = "typeOfUseService")
    private TypeOfUseService typeOfUseService;

    @Resource(name = "educProgramService")
    private EducProgramService educProgramService;


    @GetMapping(value = "/student")
    public String getStudentPage(ModelMap model) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<EducProgram> educPrograms = educProgramService.getAll(user.getExtId());
        List<EducProgram> educProgramsDTO = new ArrayList<>();
        List<Ticket> tickets = ticketService.getAllTicketsByUserId(user.getId());
        for (EducProgram educProgram : educPrograms) {
            if ((educProgram.getGroupNum().charAt(0) == '4' && educProgram.getDegree().equals("Бакалавр"))
                    || (educProgram.getGroupNum().charAt(0) == '6' && educProgram.getDegree().equals("Магистр"))
                    || (educProgram.getGroupNum().charAt(0) == '5' && educProgram.getDegree().equals("Специалист"))) {
                Ticket result = tickets.stream()// Преобразуем в поток
                        .filter(x -> educProgram.getGroupNum().equals(x.getGroupNum()))    // Фильтруем
                        .findAny()                                    // Если 'findAny', то возвращаем найденное
                        .orElse(null);
                if (tickets.isEmpty()) {
                    EducProgram dto = new EducProgram();
                    dto.setId(educProgram.getId());
                    dto.setInstitute(educProgram.getInstitute());
                    dto.setDegree(educProgram.getDegree());
                    dto.setGroupNum(educProgram.getGroupNum());
                    dto.setDepartment(educProgram.getDepartment());
                    dto.setDirection(educProgram.getDirection());
                    dto.setDirectionCode(educProgram.getDirectionCode());
                    educProgramsDTO.add(dto);
                } else if (result == null) {
                    EducProgram dto = new EducProgram();
                    dto.setId(educProgram.getId());
                    dto.setInstitute(educProgram.getInstitute());
                    dto.setDegree(educProgram.getDegree());
                    dto.setGroupNum(educProgram.getGroupNum());
                    dto.setDepartment(educProgram.getDepartment());
                    dto.setDirection(educProgram.getDirection());
                    dto.setDirectionCode(educProgram.getDirectionCode());
                    educProgramsDTO.add(dto);
                }
            }
        }

        model.addAttribute("educPrograms", educProgramsDTO);
        model.addAttribute("tickets", tickets);
        return ("studentPage");
    }

    @PostMapping(value = "/ticket/add", headers = "Content-Type=application/x-www-form-urlencoded")
    public String getAddTicket(@RequestParam(value = "userId") Integer userId,
                               @RequestParam(value = "educId") Integer educId,
                               Model model) {
        EducProgram educProgram = educProgramService.get(educId);
        List<Ticket> tickets = ticketService.getAllTicketsByUserId(userId);
        Ticket result = tickets.stream()// Преобразуем в поток
                .filter(x -> educProgram.getGroupNum().equals(x.getGroupNum()))    // Фильтруем
                .findAny()                                    // Если 'findAny', то возвращаем найденное
                .orElse(null);
        if (result == null) {

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
            ticket.setUser(usersService.getById(userId));
            ticket.setStatus(statusService.get(1));
            ticket.setGroupNum(educProgram.getGroupNum());
            ticket.setGroupNum(educProgram.getGroupNum());
            ticket.setInstitute(educProgram.getInstitute());
            ticket.setDepartment(educProgram.getDepartment());
            ticket.setDirection(educProgram.getDirection());
            ticket.setDirectionCode(educProgram.getDirectionCode());
            ticketService.add(ticket);
            model.addAttribute("ticket", ticket);


            return "redirect:/ticket/" + ticket.getId() + "edit";
        }
        return "redirect:/user";
    }

    @GetMapping(value = "/ticket/{id}/edit")
    public String getEditTicket(@PathVariable(value = "id") String ticketId,
                                Ticket ticket, ModelMap model) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ticket = ticketService.get(ticketId);

        if (user.getId().equals(ticket.getUser().getId())) {

            List<TypeOfUse> typesOfUse = typeOfUseService.getAll();
            List<String> words = new ArrayList<>();
            List<String> wordsEng = new ArrayList<>();
            Collections.addAll(words, ticket.getKeyWords().split(","));
            if (words.size() < 4) {
                while (words.size() != 4) {
                    words.add("-;");
                }
            }
            ticket.setKeyWords(String.join(",", words).replaceAll(";", ""));
            Collections.addAll(wordsEng, ticket.getKeyWordsEng().split(","));
            if (wordsEng.size() < 4) {
                while (wordsEng.size() != 4) {
                    wordsEng.add("-;");
                }
            }
            ticket.setKeyWordsEng(String.join(",", wordsEng).replaceAll(";", ""));

            model.addAttribute("typesOfUse", typesOfUse);
            model.addAttribute("ticketAttribute", ticket);
            return "editpage";
        } else {
            return "pnh";
        }
    }


    @PostMapping(value = "/ticket/{id}/edit")
    public String saveEdit(@PathVariable(value = "id") String ticketId,
                           @ModelAttribute("ticketAttribute") @Valid Ticket ticket, BindingResult bindingResult,
                           ModelMap model, @RequestParam(value = "button") String button) {

        if(bindingResult.hasErrors()){

            List<TypeOfUse> typesOfUse = typeOfUseService.getAll();

            model.addAttribute("typesOfUse", typesOfUse);
            model.addAttribute("ticketAttribute", ticket);
            return "editpage";
        }

        ticket.setKeyWords(ticket.getKeyWords().replaceAll("-,",""));
        ticket.setKeyWordsEng(ticket.getKeyWordsEng().replaceAll("-,",""));
        if (button.equals("save")) {
            ticket.setStatus(statusService.get(1));
        }
        if (button.equals("send")) {
            ticket.setStatus(statusService.get(2));
            ticket.setDateCreationFinish(new Date());
        }

        ticketService.edit(ticket);

        return "redirect:/user";
    }

    @ResponseBody
    @GetMapping("/pdfDocument")
    public String getPdf(@RequestParam(value = "ticketId") String ticketId) {
        Ticket ticket = ticketService.get(ticketId);
        return ticket.getFilePdf();
    }
}
