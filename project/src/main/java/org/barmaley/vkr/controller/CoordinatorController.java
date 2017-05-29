package org.barmaley.vkr.controller;


import org.apache.log4j.Logger;
import org.barmaley.vkr.autentication.CustomUser;
import org.barmaley.vkr.domain.*;
import org.barmaley.vkr.dto.LazyStudentsDTO;
import org.barmaley.vkr.dto.TicketEditDTO;
import org.barmaley.vkr.dto.TicketDTO;
import org.barmaley.vkr.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
        List<TicketDTO> ticketsNewDTOList = new ArrayList<>();

        List<Ticket> ticketsCheck = new ArrayList<>();
        List<TicketDTO> ticketsInCheckDTOList = new ArrayList<>();

        List<Ticket> ticketsReady = new ArrayList<>();
        List<TicketDTO> ticketsReadyDTOList = new ArrayList<>();

        List<LazyStudentsDTO> lazyStudentsDTOList = new ArrayList<>();

        Integer countTicketsNew;
        Integer countTicketsInCheck;
        Integer countTicketsReady;
        Integer countLazyStudents;

        if(!coordinatorRightsList.isEmpty()){
            for(CoordinatorRights coordinatorRights: coordinatorRightsList){
                List<Ticket> ticketsNewList = ticketService.getAllTicketForCoordinator(coordinatorRights.getGroupNum(), 2);
                List<Ticket> ticketsCheckList = ticketService.getAllTicketForCoordinator(coordinatorRights.getGroupNum(), 3);
                List<Ticket> ticketsReadyList = ticketService.getAllTicketForCoordinator(coordinatorRights.getGroupNum(), 4);
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
                ticketsNew.addAll(ticketsNewList);
                ticketsCheck.addAll(ticketsCheckList);
                ticketsReady.addAll(ticketsReadyList);
            }
        }
        for (Ticket ticket: ticketsNew){
            TicketDTO dto = new TicketDTO(ticket.getId(), ticket.getGroupNum(), ticket.getUser().getFirstName(),
                    ticket.getUser().getSecondName(), ticket.getUser().getSurname(), ticket.getTitle(), ticket.getDocumentType().getName(),
                    ticket.getTypeOfUse().getName(), ticket.getStatus().getName());
            ticketsNewDTOList.add(dto);
        }
        for (Ticket ticket: ticketsCheck){
            TicketDTO  dto = new TicketDTO(ticket.getId(), ticket.getGroupNum(), ticket.getUser().getFirstName(),
                    ticket.getUser().getSecondName(), ticket.getUser().getSurname(), ticket.getTitle(), ticket.getDocumentType().getName(),
                    ticket.getTypeOfUse().getName(), ticket.getStatus().getName());
            ticketsInCheckDTOList.add(dto);
        }
        for (Ticket ticket: ticketsReady){
            TicketDTO  dto = new TicketDTO(ticket.getId(), ticket.getGroupNum(), ticket.getUser().getFirstName(),
                    ticket.getUser().getSecondName(), ticket.getUser().getSurname(), ticket.getTitle(), ticket.getDocumentType().getName(),
                    ticket.getTypeOfUse().getName(), ticket.getStatus().getName());
            ticketsReadyDTOList.add(dto);
        }

        countTicketsNew = ticketsNewDTOList.size();
        countTicketsInCheck = ticketsInCheckDTOList.size();
        countLazyStudents = lazyStudentsDTOList.size();
        countTicketsReady = ticketsReadyDTOList.size();
        model.addAttribute("ticketsNew", ticketsNewDTOList);
        model.addAttribute("countTicketsNew", countTicketsNew);
        model.addAttribute("ticketsInCheck", ticketsInCheckDTOList);
        model.addAttribute("countTicketsInCheck", countTicketsInCheck);
        model.addAttribute("lazyStudents", lazyStudentsDTOList);
        model.addAttribute("countLazyStudents", countLazyStudents);
        model.addAttribute("ticketsReady", ticketsReadyDTOList);
        model.addAttribute("countTicketsReady", countTicketsReady);
        model.addAttribute("user", user);
        return ("coordinatorPage");
    }


    @GetMapping(value = "/ticket/check")
    public String getCheckTicket(@RequestParam(value = "ticketId") String ticketId,
                                 ModelMap model){
        CustomUser principal = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = usersService.getById(principal.getId());
        Set<CoordinatorRights> coordinatorRightsSet = user.getCoordinatorRights();
        Ticket ticket = ticketService.get(ticketId);
        TicketEditDTO dto = new TicketEditDTO();

        for(CoordinatorRights coordinatorRights: coordinatorRightsSet){
            if(coordinatorRights.getGroupNum().equals(ticket.getGroupNum())){
                ticket.setStatus(statusService.get(3));
                ticket.setDateCheckCoordinatorStart(new Date());
                ticketService.edit(ticket);
                List<TypeOfUse> typeOfUse = typeOfUseService.getAll();
                dto.setId(ticket.getId());
                dto.setAgreement(ticket.getAgreement());
                dto.setAnnotation(ticket.getAnnotation());
                dto.setAnnotationEng(ticket.getAnnotationEng());
                dto.setTitle(ticket.getTitle());
                dto.setTitleEng(ticket.getTitleEng());
                dto.setKeyWords(ticket.getKeyWords());
                dto.setKeyWordsEng(ticket.getKeyWordsEng());
                dto.setFilePdf(ticket.getFilePdf());
                dto.setStatus(statusService.get(ticketService.getStatusId(ticket.getId())));
                dto.setDocumentTypeId(ticketService.getDocumentTypeId(ticket.getId()));
                dto.setTypeOfUseId(ticketService.getTypeOfUse(ticket.getId()));
                //----------------------------------------------------
                dto.setInstitute(ticket.getInstitute());
                dto.setDirection(ticket.getDirection());
                dto.setGroupNum(ticket.getGroupNum());
                dto.setDirOfTrain(ticket.getDirOfTrain());
                dto.setCodeDirOfTrain(ticket.getCodeDirOfTrain());
                dto.setDegreeOfCurator(ticket.getDegreeOfCurator());
                dto.setDegreeOfCuratorEng(ticket.getDegreeOfCuratorEng());
                dto.setPosOfCurator(ticket.getPosOfCurator());
                dto.setPosOfCuratorEng(ticket.getPosOfCuratorEng());
                dto.setPlaceOfPublic(ticket.getPlaceOfPublic());
                dto.setPlaceOfPublicEng(ticket.getPlaceOfPublicEng());
                dto.setYearOfPublic(ticket.getYearOfPublic());
                dto.setDocumentTypeName(documentTypeService.get(ticketService.getDocumentTypeId(ticket.getId())).getName());
                dto.setDocumentTypeNameEng(documentTypeService.get(ticketService.getDocumentTypeId(ticket.getId())).getNameEng());
                dto.setSurFirstLastNameDir(ticket.getSurFirstLastNameDir());
                dto.setSflNMaster(ticket.getSflNMaster());
                dto.setSflNMasterEng(ticket.getSflNMasterEng());

                model.addAttribute("user", user);
                model.addAttribute("ticketAttribute", dto);
                model.addAttribute("typeOfUse", typeOfUse);

                return "checkPage";

            }
        }
        return "pnh";
    }

    @PostMapping(value = "ticket/check" )
    public String saveCheck(@ModelAttribute("ticketAttribute") TicketEditDTO dto,
                            @RequestParam(value = "button") String button){

        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setAnnotation(dto.getAnnotation());
        ticket.setAnnotationEng(dto.getAnnotationEng());
        ticket.setTitle(dto.getTitle());
        ticket.setTitleEng(dto.getTitleEng());
        ticket.setKeyWords(dto.getKeyWords());
        ticket.setKeyWordsEng(dto.getKeyWordsEng());
        ticket.setTypeOfUse(typeOfUseService.get(dto.getTypeOfUseId()));
        //----------------------------------------------------
        ticket.setPlaceOfPublic(dto.getPlaceOfPublic());
        ticket.setPlaceOfPublicEng(dto.getPlaceOfPublicEng());
        ticket.setYearOfPublic(dto.getYearOfPublic());
        ticket.setSurFirstLastNameDir(dto.getSurFirstLastNameDir());
        ticket.setSflNMaster(dto.getSflNMaster());
        ticket.setSflNMasterEng(dto.getSflNMasterEng());
        //----------------------------------------------------

        if(button.equals("save")){
            logger.debug("Status 3");
            ticket.setStatus(statusService.get(3));
        }
        if(button.equals("ready")){
            ticket.setStatus(statusService.get(4));
            ticket.setDateCheckCoordinatorFinish(new Date());
        }
        ticketService.edit(ticket);


        return "redirect:/coordinator";
    }


    @PostMapping(value = "/ticket/addLazy")
    public String getAddTicket(@RequestParam(value = "lazyStudentId") String username,
                               @RequestParam(value = "educId") Integer educId,
                               Model model){

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

    @PostMapping(value = "/readytickets")
    public String ticketsReady(@RequestAttribute("id") String id){
        logger.debug("asdasdasdasdas----------------------------------");
        logger.debug(id);

        return "redirect:/";
    }

}