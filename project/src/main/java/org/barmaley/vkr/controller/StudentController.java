package org.barmaley.vkr.controller;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.*;
import org.barmaley.vkr.dto.TicketDTO;
import org.barmaley.vkr.dto.TicketEditDTO;
import org.barmaley.vkr.service.*;
import org.barmaley.vkr.tool.PermissionTool;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class StudentController {

    protected static Logger logger = Logger.getLogger("controller");


    @Resource(name = "studentCopyService")
    private StudentCopyService studentCopyService;

    @Resource(name = "ticketService")
    private TicketService ticketService;

    @Resource(name = "documentTypeService")
    private DocumentTypeService documentTypeService;

    @Resource(name = "statusService")
    private StatusService statusService;

    @Resource(name = "usersService")
    private UsersService usersService;

    @Resource(name = "typeOfUseService")
    private TypeOfUseService typeOfUseService;

    @Resource(name = "educProgramService")
    private EducProgramService educProgramService;

    @Resource(name = "permissionTool")
    private PermissionTool permissionTool;


    //------------------------------------------------------------------------
    @GetMapping(value = "/student")
    public String getStudentPage(ModelMap model) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<EducProgram> educPrograms = educProgramService.getAll(user.getExtId());
        List<EducProgram> educProgramsDTO = new ArrayList<>();
        List<Ticket> tickets = ticketService.getAllTicketsByUserId(user.getId());
        List<TicketDTO> ticketsDTO = new ArrayList<>();
        Boolean perm_add_fio_eng = permissionTool.checkPermission("PERM_ADD_FIO_ENG");
        if (!tickets.isEmpty()) {
            for (Ticket ticket : tickets) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.y");
                TicketDTO ticketDTO = new TicketDTO();
                ticketDTO.setId(ticket.getId());
                try {
                    ticketDTO.setDateCreationStart(dateFormat.format(ticket.getDateCreationStart()));
                    ticketDTO.setDateCreationFinish(dateFormat.format(ticket.getDateCreationFinish()));
                    ticketDTO.setDateCheckCoordinatorStart(dateFormat.format(ticket.getDateCheckCoordinatorStart()));
                    ticketDTO.setDateReturn(dateFormat.format(ticket.getDateReturn()));
                    ticketDTO.setDateCheckCoordinatorFinish(dateFormat.format(ticket.getDateCheckCoordinatorFinish()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ticketsDTO.add(ticketDTO);
            }
        }
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
                    //----------------------------------------------------
                    dto.setDirection(educProgram.getDirection());
                    dto.setDirectionCode(educProgram.getDirectionCode());
                    //-----------------------------------------------------
                    educProgramsDTO.add(dto);
                } else if (result == null) {
                    EducProgram dto = new EducProgram();
                    dto.setId(educProgram.getId());
                    dto.setInstitute(educProgram.getInstitute());
                    dto.setDegree(educProgram.getDegree());
                    dto.setGroupNum(educProgram.getGroupNum());
                    dto.setDepartment(educProgram.getDepartment());
                    //----------------------------------------------------
                    dto.setDirection(educProgram.getDirection());
                    dto.setDirectionCode(educProgram.getDirectionCode());
                    //----------------------------------------------------
                    educProgramsDTO.add(dto);
                }
            }
        }

        model.addAttribute("educPrograms", educProgramsDTO);
        model.addAttribute("user", user);
        model.addAttribute("ticketsDTO", ticketsDTO);
        model.addAttribute("perm_add_fio_eng", perm_add_fio_eng);
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
            ticket.setUser(usersService.getById(userId));
            ticket.setStatus(statusService.get(1));
            ticket.setGroupNum(educProgram.getGroupNum());
            //-----------------------------------------------------------------
            ticket.setGroupNum(educProgram.getGroupNum());
            ticket.setInstitute(educProgram.getInstitute());
            ticket.setDepartment(educProgram.getDepartment());
            logger.debug("getDepartment " + ticket.getDepartment());
            ticket.setDirection(educProgram.getDirection());
            ticket.setDirectionCode(educProgram.getDirectionCode());
            //-----------------------------------------------------------------
            ticketService.add(ticket);
            model.addAttribute("ticket", ticket);


            return "redirect:/ticket/edit?ticketId=" + ticket.getId();
        }
        return "redirect:/user";
    }

    @GetMapping(value = "/ticket/edit")
    public String getEditTicket(@RequestParam(value = "ticketId") String ticketId,
                                ModelMap model) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean access = false;
        Set<CoordinatorRights> coordinatorRightsSet = user.getCoordinatorRights();
        Ticket ticket = ticketService.get(ticketId);
        TicketEditDTO dto = new TicketEditDTO();
        SimpleDateFormat dateFormat = new SimpleDateFormat("y");
        logger.debug("dateofmpublic= " + dto.getDateOfPublic());

        for (CoordinatorRights coordinatorRights : coordinatorRightsSet) {
            if (coordinatorRights.getGroupNum().equals(ticket.getGroupNum())) {
                access = true;
                ticket.setStatus(statusService.get(3));
                ticket.setDateCheckCoordinatorStart(new Date());
                ticketService.edit(ticket);
            }
        }

        if (user.getId().equals(ticket.getUser().getId()) || access) {
            List<TypeOfUse> typeOfUse = typeOfUseService.getAll();
            dto.setDateOfPublic(dateFormat.format(ticket.getDateCreationStart()));
            dto.setId(ticket.getId());
            dto.setAgreement(ticket.getAgreement());
            dto.setAnnotation(ticket.getAnnotation());
            dto.setAnnotationEng(ticket.getAnnotationEng());
            dto.setTitle(ticket.getTitle());
            dto.setTitleEng(ticket.getTitleEng());
            dto.setKeyWords(ticket.getKeyWords());
            dto.setKeyWordsEng(ticket.getKeyWordsEng());
            dto.setFilePdf(ticket.getFilePdf());
            dto.setFileZip(ticket.getFileZip());
            dto.setStatus(statusService.get(ticketService.getStatusId(ticket.getId())));
            dto.setDocumentTypeId(ticketService.getDocumentTypeId(ticket.getId()));
            dto.setTypeOfUseId(ticketService.getTypeOfUse(ticket.getId()));
            //----------------------------------------------------
            dto.setInstitute(ticket.getInstitute());
            String str = dto.getKeyWords();
            logger.debug(str);
            List<String> list = new ArrayList<>();
            if (str != null) {
                for (String retval : str.split(", ")) {
                    logger.debug(retval);
                    list.add(retval);
                }
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

            dto.setDirection(ticket.getDirection());
            dto.setDepartment(ticket.getDepartment());
            dto.setDirectionCode(ticket.getDirectionCode());
            dto.setGroupNum(ticket.getGroupNum());
            dto.setDegreeOfCurator(ticket.getDegreeOfCurator());
            dto.setDegreeOfCuratorEng(ticket.getDegreeOfCuratorEng());
            dto.setPosOfCurator(ticket.getPosOfCurator());
            dto.setPosOfCuratorEng(ticket.getPosOfCuratorEng());
            dto.setPlaceOfPublic(ticket.getPlaceOfPublic());
            dto.setPlaceOfPublicEng(ticket.getPlaceOfPublicEng());
            dto.setYearOfPublic(ticket.getYearOfPublic());
            dto.setDocumentTypeName(documentTypeService.get(ticketService.getDocumentTypeId(ticket.getId())).getName());
            dto.setDocumentTypeNameEng(documentTypeService.get(ticketService.getDocumentTypeId(ticket.getId())).getNameEng());
            dto.setHeadOfDepartment(ticket.getHeadOfDepartment());
            dto.setFullNameCurator(ticket.getFullNameCurator());
            dto.setFullNameCuratorEng(ticket.getFullNameCuratorEng());
            dto.setPosOfCurator(ticket.getPosOfCurator());
            dto.setDegreeOfCurator(ticket.getDegreeOfCurator());
            //-----------------------------------------------------
            model.addAttribute("user", user);
            model.addAttribute("ticketAttribute", dto);
            model.addAttribute("typeOfUse", typeOfUse);
            return "editpage";
        } else {
            return "pnh";
        }
    }


    @PostMapping(value = "/ticket/edit")
    public String saveEdit(@ModelAttribute("ticketAttribute") TicketEditDTO dto,
                           @RequestParam(value = "button") String button) {
        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setAnnotation(dto.getAnnotation());
        ticket.setAnnotationEng(dto.getAnnotationEng());
        ticket.setTitle(dto.getTitle());
        ticket.setTitleEng(dto.getTitleEng());
        String str = dto.getWord1() + ", " + dto.getWord2() + ", " + dto.getWord3() + ", " + dto.getWord4();
        ticket.setKeyWords(str);
        str = dto.getWord1Eng() + ", " + dto.getWord2Eng() + ", " + dto.getWord3Eng() + ", " + dto.getWord4Eng();
        ticket.setKeyWordsEng(str);
        ticket.setTypeOfUse(typeOfUseService.get(dto.getTypeOfUseId()));
        //----------------------------------------------------
        ticket.setPlaceOfPublic(dto.getPlaceOfPublic());
        ticket.setPlaceOfPublicEng(dto.getPlaceOfPublicEng());
        ticket.setYearOfPublic(dto.getDateOfPublic());
        ticket.setHeadOfDepartment(dto.getHeadOfDepartment());
        ticket.setFullNameCurator(dto.getFullNameCurator());
        ticket.setFullNameCuratorEng(dto.getFullNameCuratorEng());
        ticket.setPosOfCurator(dto.getPosOfCurator());
        ticket.setPosOfCuratorEng(dto.getPosOfCuratorEng());
        ticket.setDegreeOfCurator(dto.getDegreeOfCurator());
        ticket.setDegreeOfCuratorEng(dto.getDegreeOfCuratorEng());

        //----------------------------------------------------
        logger.debug("button: " + button);
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
