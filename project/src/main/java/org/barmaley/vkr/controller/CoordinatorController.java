package org.barmaley.vkr.controller;


import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.*;
import org.barmaley.vkr.dto.ActDTO;
import org.barmaley.vkr.dto.LazyStudentsDTO;
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
import java.util.*;

@Controller
public class CoordinatorController {

    protected static Logger logger = Logger.getLogger(CoordinatorController.class.getName());


    @Autowired
    @Qualifier("actFormValidator")
    private Validator validator;
    @Resource(name = "ticketService")
    private TicketService ticketService;
    @Resource(name = "actService")
    private ActService actService;
    @Resource(name = "usersService")
    private UsersService usersService;
    @Resource(name = "coordinatorRightsService")
    private CoordinatorRightsService coordinatorRightsService;
    @Resource(name = "studentCopyService")
    private StudentCopyService studentCopyService;
    @Resource(name = "employeeCopyService")
    private EmployeeCopyService employeeCopyService;
    @Resource(name = "educProgramService")
    private EducProgramService educProgramService;
    @Resource(name = "documentTypeService")
    private DocumentTypeService documentTypeService;
    @Resource(name = "statusTicketService")
    private StatusTicketService statusService;
    @Resource(name = "statusActService")
    private StatusActService statusActService;
    @Resource(name = "rolesService")
    private RolesService rolesService;
    @Resource(name = "typeOfUseService")
    private TypeOfUseService typeOfUseService;

    @InitBinder("actDto")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @GetMapping(value = "/coordinator")
    public String getCoordinatorPage(ModelMap model) {

        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CoordinatorRights> coordinatorRightsList = coordinatorRightsService.getCoordinatorRights(user.getId());
        List<Ticket> ticketsNew = new ArrayList<>();
        List<Ticket> ticketsInCheck = new ArrayList<>();
        List<Ticket> ticketsReady = new ArrayList<>();
        List<LazyStudentsDTO> lazyStudentsDTOList = new ArrayList<>();
        List<Act> actList = actService.getAllActsByUserId(1, user.getId());

        List<Act> actListReturn = actService.getAllActsByUserIdReturn(6, user.getId());
        int countTicketsNew;
        int countTicketsInCheck;
        int countTicketsReady;
        int countLazyStudents;
        int countActs;
        int countActsReturn;

        for (CoordinatorRights coordinatorRights : coordinatorRightsList) {
            List<Ticket> ticketsNewList = ticketService.getAllTicketForCoordinator(coordinatorRights.getGroupNum(), 2);
            List<Ticket> ticketsCheckList = ticketService.getAllTicketForCoordinator(coordinatorRights.getGroupNum(), 3);
            List<Ticket> ticketsReadyList = ticketService.getAllTicketForCoordinator(coordinatorRights.getGroupNum(), 4);
            List<StudentCopy> studentCopyList = studentCopyService.getStudentByEducProgram(coordinatorRights.getGroupNum());
            for (StudentCopy studentCopy : studentCopyList) {
                LazyStudentsDTO dto = new LazyStudentsDTO();
                dto.setStudentCopy(studentCopy);
                Set<EducProgram> educProgramSet = studentCopy.getEducPrograms();
                for (EducProgram educProgram : educProgramSet) {
                    if (educProgram.getGroupNum().equals(coordinatorRights.getGroupNum())) {
                        dto.setEducProgram(educProgram);
                    }
                    lazyStudentsDTOList.add(dto);
                }

                ticketsNew.addAll(ticketsNewList);
                ticketsInCheck.addAll(ticketsCheckList);
                ticketsReady.addAll(ticketsReadyList);
            }
        }

        countTicketsNew = ticketsNew.size();
        countTicketsInCheck = ticketsInCheck.size();
        countLazyStudents = lazyStudentsDTOList.size();
        countTicketsReady = ticketsReady.size();
        countActs = actList.size();
        countActsReturn = actListReturn.size();

        model.addAttribute("ticketsNew", ticketsNew);
        model.addAttribute("countTicketsNew", countTicketsNew);
        model.addAttribute("ticketsInCheck", ticketsInCheck);
        model.addAttribute("countTicketsInCheck", countTicketsInCheck);
        model.addAttribute("ticketsReady", ticketsReady);
        model.addAttribute("countTicketsReady", countTicketsReady);
        model.addAttribute("lazyStudents", lazyStudentsDTOList);
        model.addAttribute("countLazyStudents", countLazyStudents);
        model.addAttribute("acts", actList);
        model.addAttribute("countActs", countActs);
        model.addAttribute("countActsReturn", countActsReturn);

        return ("coordinatorPage");
    }

    @PostMapping(value = "/ticket/addLazy")
    public String getAddTicket(@RequestParam(value = "lazyStudentId") String extId,
                               @RequestParam(value = "educId") Integer educId,
                               Model model) {

        StudentCopy studentCopy = studentCopyService.get(extId);
        EducProgram educProgram = educProgramService.get(educId);
        Users user = new Users();
        Set<Roles> roles = new HashSet<>();

        roles.add(rolesService.getRole(1));
        user.setExtId(studentCopy.getUsername());
        user.setSurname(studentCopy.getSurname());
        user.setFirstName(studentCopy.getFirstName());
        user.setSecondName(studentCopy.getSecondName());
        user.setEnabled(true);
        user.setRoles(roles);
        user = usersService.add(user);

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
        ticket.setUser(usersService.get(user.getId()));
        ticket.setStatus(statusService.get(3));
        ticket.setTypeOfUse(typeOfUseService.get(1));
        ticket.setGroupNum(educProgram.getGroupNum());
        ticket.setDirection(educProgram.getDirection());
        ticket.setDirectionCode(educProgram.getDirectionCode());
        ticket.setInstitute(educProgram.getInstitute());
        ticket.setDepartment(educProgram.getDepartment());
        ticket.setKeyWords("#,");
        ticket.setKeyWordsEng("#,");
        ticket.setLicenseDate(new Date(0));

        ticketService.add(ticket);
        model.addAttribute("ticket", ticket);


        return "redirect:/ticket/" + ticket.getId() + "/check";
    }

    @GetMapping(value = "/act/add")
    public String createAct() {

        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeCopy coordinator = employeeCopyService.get(user.getExtId());

        Act act = new Act();
        act.setDateOfCreate(new Date());
        act.setStatus(statusActService.get(1));
        act.setInstitute(coordinator.getInstitute());
        act.setDepartment(coordinator.getDepartment());
        act.setPosition(coordinator.getPosition());
        act.setCoordinator(user);
        act = actService.add(act);

        return "redirect:/act/" + act.getId() + "/edit";
    }

    @GetMapping(value = "/act/{id}/edit")
    public String getEditAct(@PathVariable(value = "id") String actId, ModelMap model, ActDTO dto) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Act act = actService.get(actId);
        List<CoordinatorRights> coordinatorRightsList = user.getCoordinatorRights();
        List<Ticket> tickets = act.getTickets();
        List<String> preCheckedVals = new ArrayList<>();
        for (CoordinatorRights coordinatorRights : coordinatorRightsList) {
            List<Ticket> ticketList = ticketService.getAllTicketForCoordinator(coordinatorRights.getGroupNum(), 4);
            tickets.addAll(ticketList);
        }

        for (Ticket ticket : act.getTickets()) {
            preCheckedVals.add(ticket.getId());

        }

        dto.setAct(act);
        dto.setTicketsId(preCheckedVals);

        model.addAttribute("actDto", dto);
        model.addAttribute("tickets", tickets);

        return "editActPage";

    }

    @PostMapping(value = "/act/{id}/edit")
    public String postEditAct(@PathVariable(value = "id") String actId, @ModelAttribute("actDto") @Validated ActDTO dto,
                              BindingResult bindingResult, ModelMap model,
                              @RequestParam(name = "button") String button) {

        if (bindingResult.hasErrors()) {
            logger.info("error");
            return getEditAct(actId, model, dto);
        }


        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CoordinatorRights> coordinatorRightsList = coordinatorRightsService.getCoordinatorRights(user.getId());

        Act act = actService.get(dto.getAct().getId());
        List<String> ticketsId = dto.getTicketsId();
        List<Ticket> tickets = new ArrayList<>();
        List<Ticket> otherTickets = new ArrayList<>();

        for (String ticketId : ticketsId) {
            Ticket ticket = ticketService.get(ticketId);
            ticket.setAct(act);
            ticket.setStatus(statusService.get(6));
            ticketService.editAct(ticket);
            tickets.add(ticket);
        }

        if (button.equals("send")) {
            act.setStatus(statusActService.get(2));
            actService.edit(act);
        }

        for (CoordinatorRights coordinatorRights : coordinatorRightsList) {
            List<Ticket> ticketList = ticketService.getAllTicketForCoordinator(coordinatorRights.getGroupNum(), 6);
            otherTickets.addAll(ticketList);
        }

        for (Ticket otherTicket : otherTickets) {
            if (otherTicket.getAct().getId().equals(act.getId())) {

                boolean exist = false;

                for (Ticket ticket : tickets) {
                    if (otherTicket.getId().equals(ticket.getId())) {
                        exist = true;
                    }
                }

                if (!exist) {
                    otherTicket.setAct(null);
                    otherTicket.setStatus(statusService.get(4));
                    ticketService.editAct(otherTicket);
                }
            }
        }


        return "redirect:/";
    }

}