package org.barmaley.vkr.controller;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.EducProgram;
import org.barmaley.vkr.domain.Ticket;
import org.barmaley.vkr.domain.TypeOfUse;
import org.barmaley.vkr.domain.Users;
import org.barmaley.vkr.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("ticketFormValidator")
    private Validator validator;

    @InitBinder("ticketAttribute")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    // Редактирование задачи студента
    @GetMapping(value = "/ticket/{ticketId}/edit")
    public String getEditTicket(@PathVariable(value = "ticketId") String ticketId,
                                ModelMap model) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ticket ticket = ticketService.get(ticketId);

        if (user.getId().equals(ticket.getUser().getId())) {

            List<TypeOfUse> typesOfUse = typeOfUseService.getAll();
            List<String> words = new ArrayList<>();
            List<String> wordsEng = new ArrayList<>();
            Collections.addAll(words, ticket.getKeyWords().split(","));
            while (words.size() != 4) {
                words.add("-;");
            }
            ticket.setKeyWords(String.join(",", words).replaceAll(";", ""));
            Collections.addAll(wordsEng, ticket.getKeyWordsEng().split(","));
            while (wordsEng.size() != 4) {
                wordsEng.add("-;");
            }
            ticket.setKeyWordsEng(String.join(",", wordsEng).replaceAll(";", ""));

            model.addAttribute("typesOfUse", typesOfUse);
            model.addAttribute("ticketAttribute", ticket);
            return "editpage";
        } else {
            return "pnh";
        }
    }
    // Сохранение задачи студента
    @PostMapping(value = "/ticket/{ticketId}/edit")
    public String saveEdit(@PathVariable("ticketId") String ticketId,
                           @ModelAttribute("ticketAttribute") @Validated Ticket ticket, BindingResult bindingResult,
                           ModelMap model, @RequestParam(value = "button") String button) {
        if (button.equals("send") && bindingResult.hasErrors()) {
            logger.info(bindingResult.getAllErrors());
            List<TypeOfUse> typesOfUse = typeOfUseService.getAll();
            model.addAttribute("ticketAttribute", ticket);
            model.addAttribute("typesOfUse", typesOfUse);
            return "editpage";
        }
        ticket.setKeyWords(ticket.getKeyWords().replaceAll("-,",""));
        ticket.setKeyWordsEng(ticket.getKeyWordsEng().replaceAll("-,",""));
        if (button.equals("save")) {
            ticket.setStatus(statusService.get(1));
            logger.debug("Заявка "+statusService.get(1));
        }
        if (button.equals("send")) {
            ticket.setStatus(statusService.get(2));
            logger.debug("Заявка "+statusService.get(2));

            ticket.setDateCreationFinish(new Date());
        }

        ticketService.edit(ticket);

        return "redirect:/user";
    }
    // Добавление задачи
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
            logger.debug("заявка "+statusService.get(1));
            ticket.setGroupNum(educProgram.getGroupNum());
            ticket.setGroupNum(educProgram.getGroupNum());
            ticket.setInstitute(educProgram.getInstitute());
            ticket.setDepartment(educProgram.getDepartment());
            ticket.setDirection(educProgram.getDirection());
            ticket.setDirectionCode(educProgram.getDirectionCode());
            ticket.setKeyWords("-,");
            ticket.setKeyWordsEng("-,");
            ticket.setLicenseDate(new Date(0));
            ticketService.add(ticket);
            model.addAttribute("ticket", ticket);


            return "redirect:/ticket/" + ticket.getId() + "/edit";
        }
        return "redirect:/user";
    }
}
