package org.barmaley.vkr.controller;


import org.apache.log4j.Logger;
import org.barmaley.vkr.autentication.CustomUser;
import org.barmaley.vkr.domain.*;
import org.barmaley.vkr.dto.LazyStudentsDTO;
import org.barmaley.vkr.dto.TicketDTO;
import org.barmaley.vkr.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.*;

@Controller
public class CoordinatorController {

    protected static Logger logger = Logger.getLogger("controller");

    @Resource(name = "ticketService")
    private TicketService ticketService;

    @Resource(name = "usersService")
    private UsersService usersService;

    @Resource(name = "coordinatorRightsService")
    private CoordinatorRightsService coordinatorRightsService;

    @Resource(name = "studentCopyService")
    private StudentCopyService studentCopyService;

    @Resource(name = "educProgramService")
    private EducProgramService educProgramService;

    @Resource(name = "documentTypeService")
    private DocumentTypeService documentTypeService;

    @Resource(name = "statusService")
    private StatusService statusService;

    @Resource(name = "rolesService")
    private RolesService rolesService;

    @Resource(name = "typeOfUseService")
    private TypeOfUseService typeOfUseService;

    @GetMapping(value = "/coordinator")
    public String getCoordinatorPage(ModelMap model) {

        CustomUser principal = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = usersService.getById(principal.getId());
        List<CoordinatorRights> coordinatorRightsList = coordinatorRightsService.getCoordinatorRights(principal.getId());
        List<Ticket> ticketsNew = new ArrayList<>();
        List<TicketDTO> ticketNewDTOList = new ArrayList<>();
        List<Ticket> ticketsCheck = new ArrayList<>();
        List<TicketDTO> ticketInCheckDTOList = new ArrayList<>();
        List<LazyStudentsDTO> lazyStudentsDTOList = new ArrayList<>();

        Integer countTicketsNew = null;
        Integer countTicketsInCheck = null;
        Integer countLazyStudents = null;
        if(!coordinatorRightsList.isEmpty()){
            for(CoordinatorRights coordinatorRights: coordinatorRightsList){
                List<Ticket> newTicketList = ticketService.getAllTicketForCoordinator(coordinatorRights.getGroupNum(), 2);
                List<Ticket> ticketsCheckList = ticketService.getAllTicketForCoordinator(coordinatorRights.getGroupNum(), 3);
                List<StudentCopy> studentCopyList = studentCopyService.getStudentByEducProgram(coordinatorRights.getGroupNum());
                for(StudentCopy studentCopy: studentCopyList){
                    LazyStudentsDTO dto = new LazyStudentsDTO();
                    dto.setStudentCopy(studentCopy);
                    Set<EducProgram> educProgramSet = studentCopy.getEducPrograms();
                    for(EducProgram educProgram: educProgramSet){
                        if(educProgram.getGroupNum().equals(coordinatorRights.getGroupNum())){
                            dto.setEducProgram(educProgram);
                        }
                    }
                    lazyStudentsDTOList.add(dto);

                }
                ticketsNew.addAll(newTicketList);
                ticketsCheck.addAll(ticketsCheckList);
            }
        }
        for (Ticket ticket: ticketsNew){
            TicketDTO dto = new TicketDTO(ticket.getId(), ticket.getGroupNum(), ticket.getUser().getFirstName(),
                    ticket.getUser().getSecondName(), ticket.getUser().getSurname(), ticket.getTitle(), ticket.getDocumentType().getName(),
                    ticket.getTypeOfUse().getName(), ticket.getStatus().getName());
            ticketNewDTOList.add(dto);
        }
        for (Ticket ticket: ticketsCheck){
            TicketDTO  dto = new TicketDTO(ticket.getId(), ticket.getGroupNum(), ticket.getUser().getFirstName(),
                    ticket.getUser().getSecondName(), ticket.getUser().getSurname(), ticket.getTitle(), ticket.getDocumentType().getName(),
                    ticket.getTypeOfUse().getName(), ticket.getStatus().getName());
            ticketInCheckDTOList.add(dto);
        }

        countTicketsNew = ticketNewDTOList.size();
        countTicketsInCheck = ticketInCheckDTOList.size();
        countLazyStudents = lazyStudentsDTOList.size();

        model.addAttribute("ticketsNew", ticketNewDTOList);
        model.addAttribute("countTicketsNew", countTicketsNew);
        model.addAttribute("ticketsInCheck", ticketInCheckDTOList);
        model.addAttribute("countTicketsInCheck", countTicketsInCheck);
        model.addAttribute("lazyStudents", lazyStudentsDTOList);
        model.addAttribute("countLazyStudents", countLazyStudents);
        model.addAttribute("user", user);
        return ("coordinatorPage");
    }

    @PostMapping(value = "/ticket/addLazy")
    public String getAddTicket(@RequestParam(value = "lazyStudentId") String username,
                               @RequestParam(value = "educId") Integer educId,
                               Model model){

        logger.debug("Add lazy -----------------------------------");
        StudentCopy studentCopy = studentCopyService.get(username);
        EducProgram educProgram = educProgramService.get(educId);
        Users user = new Users();
        Set<Roles> roles= new HashSet<>();

        roles.add(rolesService.getRole(1));
        user.setExtId(studentCopy.getUsername());
        user.setSurname(studentCopy.getSurname());
        user.setFirstName(studentCopy.getFirstName());
        user.setSecondName(studentCopy.getSecondName());
        user.setEnabled(true);
        user.setRoles(roles);
        usersService.addUser(user);
        user = usersService.getByExtId(username);

        Ticket ticket = new Ticket();
        String degree = educProgram.getDegree();
        logger.debug(degree);
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
        ticket.setUser(usersService.getById(user.getId()));
        ticket.setStatus(statusService.get(3));
        ticket.setTypeOfUse(typeOfUseService.get(1));
        ticket.setGroupNum(educProgram.getGroupNum());
        //-----------------------------------------------------------------
        ticket.setGroupNum(educProgram.getGroupNum());
        ticket.setDirection(educProgram.getDirection());
        ticket.setInstitute(educProgram.getInstitute());
        ticket.setSpecialty(educProgram.getSpecialty());
        ticket.setDirOfTrain(educProgram.getDirOfTrain());
        ticket.setCodeDirOfTrain(educProgram.getCodeDirOfTrain());
        ticket.setDegreeOfCurator(educProgram.getDegreeOfCurator());
        ticket.setDegreeOfCuratorEng(educProgram.getDegreeOfCuratorEng());
        ticket.setPosOfCurator(educProgram.getPosOfCurator());
        ticket.setPosOfCuratorEng(educProgram.getPosOfCuratorEng());
        //-----------------------------------------------------------------
        ticketService.add(ticket);
        model.addAttribute("ticket", ticket);


        return "redirect:/ticket/edit?ticketId=" + ticket.getId();
    }
}