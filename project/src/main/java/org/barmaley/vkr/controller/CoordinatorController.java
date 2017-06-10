package org.barmaley.vkr.controller;


import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.*;
import org.barmaley.vkr.dto.ActDTO;
import org.barmaley.vkr.dto.LazyStudentsDTO;
import org.barmaley.vkr.dto.TicketEditDTO;
import org.barmaley.vkr.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CoordinatorController {

    protected static Logger logger = Logger.getLogger("controller");

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

    @GetMapping(value = "/coordinator")
    public String getCoordinatorPage(ModelMap model) {

        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CoordinatorRights> coordinatorRightsList = coordinatorRightsService.getCoordinatorRights(user.getId());
        List<Ticket> ticketsNew = new ArrayList<>();
        List<Ticket> ticketsInCheck = new ArrayList<>();
        List<Ticket> ticketsReady = new ArrayList<>();
        List<LazyStudentsDTO> lazyStudentsDTOList = new ArrayList<>();
        List<Act> actList = actService.getAllActsByUserId(user.getId());

        int countTicketsNew;
        int countTicketsInCheck;
        int countTicketsReady;
        int countLazyStudents;
        int countActs;

        if (!coordinatorRightsList.isEmpty()) {
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

        return ("coordinatorPage");
    }


    @GetMapping(value = "/ticket/check")
    public String getCheckTicket(@RequestParam(value = "ticketId") String ticketId,
                                 ModelMap model) {

        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<CoordinatorRights> coordinatorRightsSet = user.getCoordinatorRights();
        Ticket ticket = ticketService.get(ticketId);
        TicketEditDTO dto = new TicketEditDTO();


        logger.debug(coordinatorRightsSet.size());

        CoordinatorRights coordinatorRights = coordinatorRightsSet.stream()
                .filter(x -> ticket.getGroupNum().equals(x.getGroupNum()))
                .findAny()
                .orElse(null);

        if (coordinatorRights != null && (ticket.getStatus().getId() != 1)) {
            if (ticket.getStatus().getId() == 2) {
                ticket.setStatus(statusService.get(3));
            }
                ticket.setDateCheckCoordinatorStart(new Date());
                ticketService.edit(ticket);
                List<TypeOfUse> typesOfUse = typeOfUseService.getAll();
                dto.setId(ticket.getId());
                dto.setLicenseNumber(ticket.getLicenseNumber());
                if (ticket.getLicenseDate() != null) {
                    SimpleDateFormat licenseDateFormat = new SimpleDateFormat("y-MM-dd");
                    dto.setLicenseDateDTO(licenseDateFormat.format(ticket.getLicenseDate()));
                }
                dto.setAnnotation(ticket.getAnnotation());
                dto.setAnnotationEng(ticket.getAnnotationEng());
                dto.setTitle(ticket.getTitle());
                dto.setTitleEng(ticket.getTitleEng());
                dto.setKeyWords(ticket.getKeyWords());
                dto.setKeyWordsEng(ticket.getKeyWordsEng());
                dto.setDepartment(ticket.getDepartment());
                dto.setDirectionCode(ticket.getDirectionCode());
                dto.setFilePdf(ticket.getFilePdf());
                dto.setFileZip(ticket.getFileZip());
                dto.setFilePdfSecret(ticket.getFilePdfSecret());
                dto.setFileZipSecret(ticket.getFileZipSecret());
                String str = dto.getKeyWords();
                List<String> list = new ArrayList<>();
                if (str != null) {
                    Collections.addAll(list, str.split(", "));
                }
                try {
                    dto.setWord1(list.get(0));
                    dto.setWord2(list.get(1));
                    dto.setWord3(list.get(2));
                    dto.setWord4(list.get(3));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                str = dto.getKeyWordsEng();
                list.clear();
                if (str != null) {
                    Collections.addAll(list, str.split(", "));
                }
                try {
                    dto.setWord1Eng(list.get(0));
                    dto.setWord2Eng(list.get(1));
                    dto.setWord3Eng(list.get(2));
                    dto.setWord4Eng(list.get(3));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dto.setKeyWordsEng(ticket.getKeyWordsEng());
                dto.setFilePdf(ticket.getFilePdf());
                dto.setFileZip(ticket.getFileZip());
                dto.setStatus(ticket.getStatus());
                dto.setTypeOfUse(ticket.getTypeOfUse());
                //----------------------------------------------------
                dto.setInstitute(ticket.getInstitute());
                dto.setDirection(ticket.getDirection());
                dto.setGroupNum(ticket.getGroupNum());
                dto.setPlaceOfPublic(ticket.getPlaceOfPublic());
                dto.setPlaceOfPublicEng(ticket.getPlaceOfPublicEng());
                dto.setYearOfPublic(ticket.getYearOfPublic());
                dto.setDocumentType(ticket.getDocumentType());
                dto.setHeadOfDepartment(ticket.getHeadOfDepartment());
                dto.setFullNameCurator(ticket.getFullNameCurator());
                dto.setFullNameCuratorEng(ticket.getFullNameCuratorEng());
                dto.setPosOfCurator(ticket.getPosOfCurator());
                dto.setPosOfCuratorEng(ticket.getPosOfCuratorEng());
                dto.setDegreeOfCurator(ticket.getDegreeOfCurator());
                dto.setDegreeOfCuratorEng(ticket.getDegreeOfCuratorEng());

                model.addAttribute("ticketAttribute", dto);
                model.addAttribute("typesOfUse", typesOfUse);

                return "checkPage";

        } else {
            return "pnh";
        }
    }

    @PostMapping(value = "/ticket/check")
    public String saveCheck(@ModelAttribute("ticketAttribute") TicketEditDTO dto,
                            @RequestParam(value = "button") String button) {
        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setLicenseNumber(dto.getLicenseNumber());
        if (!dto.getLicenseDateDTO().equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("y-MM-dd");
            try {
                ticket.setLicenseDate(dateFormat.parse(dto.getLicenseDateDTO()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        ticket.setAnnotation(dto.getAnnotation());
        ticket.setAnnotationEng(dto.getAnnotationEng());
        ticket.setTitle(dto.getTitle());
        ticket.setTitleEng(dto.getTitleEng());
        ticket.setKeyWords(dto.getKeyWords());
        ticket.setKeyWordsEng(dto.getKeyWordsEng());
        ticket.setTypeOfUse(typeOfUseService.get(dto.getTypeOfUse().getId()));
        List<String> wordsList = new ArrayList<>();
        String s = dto.getWord1();

        logger.debug(s.length());
        int j = s.length();
        logger.debug("1!= "+dto.getWord1().length());
        logger.debug("2!= "+dto.getWord2());
        logger.debug("3!= "+dto.getWord3());
        logger.debug("4!= "+dto.getWord4());
        if (dto.getWord1().length()!=0){logger.debug("Зашел 1!");wordsList.add(dto.getWord1());}
        if (dto.getWord2().length()!=0){logger.debug("Зашел 2!");wordsList.add(dto.getWord2());}
        if (dto.getWord3().length()!=0){logger.debug("Зашел 3!");wordsList.add(dto.getWord3());}
        if (dto.getWord4().length()!=0){logger.debug("Зашел 4!");wordsList.add(dto.getWord4());}
        logger.debug("worListSize= "+wordsList.size());
        for(int i=0; i<wordsList.size(); i++){
            logger.debug("i= "+i);
            if(i==wordsList.size()-1) {logger.debug("зашел в условие");wordsList.set(i,wordsList.get(i));}
            else{wordsList.set(i,wordsList.get(i)+", ");}
        }
        String str="";
        for (int i=0; i<wordsList.size();i++){
            str=str+wordsList.get(i);
        }
// String str = dto.getWord1() + ", " + dto.getWord2() + ", " + dto.getWord3() + ", " + dto.getWord4();
        ticket.setKeyWords(str);
        wordsList.clear();
        if (dto.getWord1Eng().length()!=0){wordsList.add(dto.getWord1Eng());}
        if (dto.getWord2Eng().length()!=0){wordsList.add(dto.getWord2Eng());}
        if (dto.getWord3Eng().length()!=0){wordsList.add(dto.getWord3Eng());}
        if (dto.getWord4Eng().length()!=0){wordsList.add(dto.getWord4Eng());}
        for(int i=0; i<wordsList.size(); i++){
            if(i==wordsList.size()-1) {wordsList.set(i,wordsList.get(i));}
            else{wordsList.set(i,wordsList.get(i)+", ");}
        }
        str="";
        for (int i=0; i<wordsList.size();i++){
            str=str+wordsList.get(i);
        }
        ticket.setKeyWordsEng(str);

        ticket.setKeyWordsEng(str);
        //----------------------------------------------------
        ticket.setPlaceOfPublic(dto.getPlaceOfPublic());
        ticket.setPlaceOfPublicEng(dto.getPlaceOfPublicEng());
        ticket.setYearOfPublic(dto.getYearOfPublic());
        ticket.setHeadOfDepartment(dto.getHeadOfDepartment());
        ticket.setFullNameCurator(dto.getFullNameCurator());
        ticket.setFullNameCuratorEng(dto.getFullNameCuratorEng());
        ticket.setPosOfCurator(dto.getPosOfCurator());
        ticket.setPosOfCuratorEng(dto.getPosOfCuratorEng());
        ticket.setDegreeOfCurator(dto.getDegreeOfCurator());
        ticket.setDegreeOfCuratorEng(dto.getDegreeOfCuratorEng());

        //----------------------------------------------------
        switch (button) {
            case "return":
                ticket.setStatus(statusService.get(5));
                ticket.setDateReturn(new Date());
                break;
            case "ready":
                ticket.setStatus(statusService.get(4));
                ticket.setDateCheckCoordinatorFinish(new Date());
                break;
            default:
                ticket.setStatus(statusService.get(3));
                break;
        }

        ticketService.edit(ticket);

        switch (button) {
            case "recordSheet":
                return "redirect:/downloadPDF1?ticketId=" + ticket.getId();
            case "licenseAgreement":
                return "redirect:/downloadPDF2?ticketId=" + ticket.getId();
        }

        return "redirect:/coordinator";
    }


    @PostMapping(value = "/ticket/addLazy")
    public String getAddTicket(@RequestParam(value = "lazyStudentId") String username,
                               @RequestParam(value = "educId") Integer educId,
                               Model model) {

        StudentCopy studentCopy = studentCopyService.get(username);
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
        ticket.setDirectionCode(educProgram.getDirectionCode());
        ticket.setInstitute(educProgram.getInstitute());
        ticket.setDepartment(educProgram.getDepartment());
        //-----------------------------------------------------------------
        ticketService.add(ticket);
        model.addAttribute("ticket", ticket);


        return "redirect:/ticket/check?ticketId=" + ticket.getId();
    }

    @GetMapping(value = "/act/add")
    public String createAct() {

        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeCopy coordinator = employeeCopyService.get(user.getExtId());

        Act act = new Act();
        act.setDateOfCreat(new Date());
        act.setStatus(statusActService.get(1));
        act.setInstitute(coordinator.getInstitute());
        act.setDepartment(coordinator.getDepartment());
        act.setPosition(coordinator.getPosition());
        act.setUser(user);
        act = actService.add(act);

        return "redirect:/act/edit?actId=" + act.getId();
    }

    @GetMapping(value = "/act/edit")
    public String getEditAct(@RequestParam(value = "actId") String actId, ModelMap model) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CoordinatorRights> coordinatorRightsList = coordinatorRightsService.getCoordinatorRights(user.getId());
        List<Ticket> tickets = new ArrayList<>();
        List<String> preCheckedVals = new ArrayList<>();
        ActDTO dto = new ActDTO();
        Act act = actService.get(actId);


        if (!coordinatorRightsList.isEmpty()) {
            for (CoordinatorRights coordinatorRights : coordinatorRightsList) {
                List<Ticket> ticketList = ticketService.getAllTicketForAct(coordinatorRights.getGroupNum(), 4, actId);
                tickets.addAll(ticketList);
            }
        }

        for (Ticket ticket : act.getTickets()) {
            preCheckedVals.add(ticket.getId());
        }

        dto.setId(act.getId());
        dto.setDateOfCreat(act.getDateOfCreat());
        dto.setDateOfAccept(act.getDateOfAccept());
        dto.setUser(act.getUser());
        dto.setPosition(act.getPosition());
        dto.setDepartment(act.getDepartment());
        dto.setInstitute(act.getInstitute());
        dto.setTickets(act.getTickets());
        dto.setTicketsId(preCheckedVals);

        model.addAttribute("act", dto);
        model.addAttribute("tickets", tickets);

        return "actPage";

    }

    @PostMapping(value = "/act/edit")
    public String postEditAct(ActDTO dto, @RequestParam(name = "button") String button) {

        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CoordinatorRights> coordinatorRightsList = coordinatorRightsService.getCoordinatorRights(user.getId());

        Act act = actService.get(dto.getId());
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

        if(button.equals("send")){
            act.setStatus(statusActService.get(2));
            actService.edit(act);
        }

        if (!coordinatorRightsList.isEmpty()) {
            for (CoordinatorRights coordinatorRights : coordinatorRightsList) {
                List<Ticket> ticketList = ticketService.getAllTicketForCoordinator(coordinatorRights.getGroupNum(), 6);
                otherTickets.addAll(ticketList);
            }
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