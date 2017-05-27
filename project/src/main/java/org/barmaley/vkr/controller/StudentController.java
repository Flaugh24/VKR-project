package org.barmaley.vkr.controller;

import org.apache.log4j.Logger;
import org.barmaley.vkr.autentication.CustomUser;
import org.barmaley.vkr.domain.*;
import org.barmaley.vkr.dto.TicketEditDTO;
import org.barmaley.vkr.dto.TicketDTO;
import org.barmaley.vkr.service.*;
import org.barmaley.vkr.Tool.PermissionTool;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        CustomUser principal = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = usersService.getById(principal.getId());
        List<EducProgram> educPrograms = educProgramService.getAll(user.getExtId());
        List<EducProgram> educProgramsDTO = new ArrayList<EducProgram>();
        List<Ticket> tickets = ticketService.getAllTicketsByUserId(principal.getId());
        List<TicketDTO> ticketsDTO = new ArrayList<>();
        Boolean perm_add_fio_eng = permissionTool.checkPermission("PERM_ADD_FIO_ENG");
        if(!tickets.isEmpty()){
            for (Ticket ticket: tickets){
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.y");
                TicketDTO ticketDTO = new TicketDTO();
                ticketDTO.setId(ticket.getId());
                try{
                    ticketDTO.setDateCreationStart(dateFormat.format(ticket.getDateCreationStart()));
                    ticketDTO.setDateCreationFinish(dateFormat.format(ticket.getDateCreationFinish()));
                    ticketDTO.setDateCheckCoordinatorStart(dateFormat.format(ticket.getDateCheckCoordinatorStart()));
                }
                catch (Exception e){}
                ticketsDTO.add(ticketDTO);
            }
        }
        for (EducProgram educProgram : educPrograms) {
            if ((educProgram.getGroupNum().charAt(0) == '4' && educProgram.getDegree().equals("Бакалавр"))
                    || (educProgram.getGroupNum().charAt(0) == '6' && educProgram.getDegree().equals("Магистр"))
                    || (educProgram.getGroupNum().charAt(0) == '5' && educProgram.getDegree().equals("Специалист"))) {
                Ticket result = tickets.stream()// Преобразуем в поток
                        .filter(x -> educProgram.getGroupNum().equals(x.getGroupNum()))	// Фильтруем
                        .findAny()									// Если 'findAny', то возвращаем найденное
                        .orElse(null);
                if (tickets.isEmpty()){
                    EducProgram dto = new EducProgram();
                    dto.setId(educProgram.getId());
                    dto.setInstitute(educProgram.getInstitute());
                    dto.setDegree(educProgram.getDegree());
                    dto.setGroupNum(educProgram.getGroupNum());
                    dto.setDirection(educProgram.getDirection());
                    dto.setSpecialty(educProgram.getSpecialty());
                    //----------------------------------------------------
                    dto.setDirOfTrain(educProgram.getDirOfTrain());
                    dto.setCodeDirOfTrain(educProgram.getCodeDirOfTrain());
                    dto.setDegreeOfCurator(educProgram.getDegreeOfCurator());
                    dto.setDegreeOfCuratorEng(educProgram.getDegreeOfCuratorEng());
                    dto.setPosOfCurator(educProgram.getPosOfCurator());
                    dto.setPosOfCuratorEng(educProgram.getPosOfCuratorEng());
                    //-----------------------------------------------------
                    educProgramsDTO.add(dto);
                }
                else if (result == null) {
                    EducProgram dto = new EducProgram();
                    dto.setId(educProgram.getId());
                    dto.setInstitute(educProgram.getInstitute());
                    dto.setDegree(educProgram.getDegree());
                    dto.setGroupNum(educProgram.getGroupNum());
                    dto.setDirection(educProgram.getDirection());
                    dto.setSpecialty(educProgram.getSpecialty());
                    //----------------------------------------------------
                    dto.setDirOfTrain(educProgram.getDirOfTrain());
                    dto.setCodeDirOfTrain(educProgram.getCodeDirOfTrain());
                    dto.setDegreeOfCurator(educProgram.getDegreeOfCurator());
                    dto.setDegreeOfCuratorEng(educProgram.getDegreeOfCuratorEng());
                    dto.setPosOfCurator(educProgram.getPosOfCurator());
                    dto.setPosOfCuratorEng(educProgram.getPosOfCuratorEng());
                    //----------------------------------------------------
                    educProgramsDTO.add(dto);

                    result = null;
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
            .filter(x -> educProgram.getGroupNum().equals(x.getGroupNum()))	// Фильтруем
            .findAny()									// Если 'findAny', то возвращаем найденное
            .orElse(null);
        if(result == null) {

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
        return "redirect:/user";
    }

    @GetMapping(value = "/ticket/edit")
    public String getEditTicket(@RequestParam(value = "ticketId") String ticketId,
                                ModelMap model) {
        CustomUser principal = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = usersService.getById(principal.getId());
        Set<Roles> roles = user.getRoles();
        Boolean access = false;
        Set<CoordinatorRights> coordinatorRightsSet = user.getCoordinatorRights();
        Ticket ticket = ticketService.get(ticketId);
        TicketEditDTO dto = new TicketEditDTO();

        for(CoordinatorRights coordinatorRights: coordinatorRightsSet){
            if(coordinatorRights.getGroupNum().equals(ticket.getGroupNum())){
                access = true;
                ticket.setStatus(statusService.get(3));
                ticket.setDateCheckCoordinatorStart(new Date());
                ticketService.edit(ticket);
            }
        }

        if (principal.getId() == ticket.getUser().getId() || access) {
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
        ticket.setKeyWords(dto.getKeyWords());
        ticket.setKeyWordsEng(dto.getKeyWordsEng());
        ticket.setTypeOfUse(typeOfUseService.get(dto.getTypeOfUseId()));
        ticket.setStatus(statusService.get(1));
        //----------------------------------------------------
        ticket.setPlaceOfPublic(dto.getPlaceOfPublic());
        ticket.setPlaceOfPublicEng(dto.getPlaceOfPublicEng());
        ticket.setYearOfPublic(dto.getYearOfPublic());
        ticket.setSurFirstLastNameDir(dto.getSurFirstLastNameDir());
        ticket.setSflNMaster(dto.getSflNMaster());
        ticket.setSflNMasterEng(dto.getSflNMasterEng());
        //----------------------------------------------------

        if(button.equals("Отправить на проверку")){
            logger.debug("Status 2");
            ticket.setStatus(statusService.get(2));
            ticket.setDateCreationFinish(new Date());
        }
        ticketService.edit(ticket);

        return "redirect:/user";
    }

    @PostMapping("/ticket/deleterar")
    public String singleFileDeleterar(@RequestParam("filePdf") MultipartFile file,
                                   @RequestParam("ticketId") String ticketId,
                                   @RequestParam("submit") String submit) {
        String fullPath, ROOT_FOLDERS;
        logger.debug("Upload PDF File");

        Ticket ticket = ticketService.get(ticketId);
        try{
            ROOT_FOLDERS = "D:\\src\\";
            byte[] bytes = file.getBytes();
            logger.debug(file.getOriginalFilename());
            if(submit.equals("Удалить PDF"))
            {
                logger.debug("SUBMIT= "+submit);
                fullPath = ROOT_FOLDERS + ticket.getId() + ".pdf";

                Path path = Paths.get(fullPath);
                logger.debug("Path delete!");
                Files.delete(path);

                ticket.setFilePdf(null);
                ticketService.editPdf(ticket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "redirect:/ticket/edit?ticketId=" + ticket.getId();
    }

    @PostMapping("/ticket/deletepdf")
    public String singleFileDelete(@RequestParam("filePdf") MultipartFile file,
                                   @RequestParam("ticketId") String ticketId,
                                   @RequestParam("submit") String submit) {
        String fullPath, ROOT_FOLDERS;
        logger.debug("Upload PDF File");

        Ticket ticket = ticketService.get(ticketId);
        try{
            ROOT_FOLDERS = "D:\\src\\";
            byte[] bytes = file.getBytes();
            logger.debug(file.getOriginalFilename());
            if(submit.equals("Удалить PDF"))
            {
                logger.debug("SUBMIT= "+submit);
                fullPath = ROOT_FOLDERS + ticket.getId() + ".pdf";

                Path path = Paths.get(fullPath);
                logger.debug("Path delete!");
                Files.delete(path);

                ticket.setFilePdf(null);
                ticketService.editPdf(ticket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "redirect:/ticket/edit?ticketId=" + ticket.getId();
    }

    @PostMapping("/ticket/upload")
    public String singleFileUpload(@RequestParam("filePdf") MultipartFile file,
                                   @RequestParam("ticketId") String ticketId,
                                   @RequestParam("submit") String submit) {
        String fullPath, ROOT_FOLDERS;
        logger.debug("Upload PDF File");

        Ticket ticket = ticketService.get(ticketId);
        try{
            ROOT_FOLDERS = "D:\\src\\";
            byte[] bytes = file.getBytes();
            logger.debug(file.getOriginalFilename());
            if(submit.equals("Загрузить PDF"))
            {
                logger.debug("SUBMIT= "+submit);
                fullPath = ROOT_FOLDERS + ticket.getId() + ".pdf";

                Path path = Paths.get(fullPath);

                if (Files.exists(path)){
                    logger.debug("Path delete!");
                    Files.delete(path);
                }
                logger.debug("Path update!");
                Files.write(path, bytes);
                ticket.setFilePdf(fullPath);
                ticketService.editPdf(ticket);

            }
            else
            {
                logger.debug("SUBMIT= "+submit);
                fullPath = ROOT_FOLDERS + ticket.getId() + ".rar";
                Path path = Paths.get(fullPath);
                Files.write(path, bytes);
                ticket.setFileRar(fullPath);
                ticketService.editRar(ticket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "redirect:/ticket/edit?ticketId=" + ticket.getId();
    }

    @PostMapping(value = "/user/profile")
    public String saveProfile(@ModelAttribute("user") Users user){

        logger.debug("edit profile");
        usersService.editUser(user);
        return "redirect:/user";
    }

    @ResponseBody
    @GetMapping("/pdfDocument")
    public String getPdf(@RequestParam(value = "ticketId") String ticketId){
        Ticket ticket = ticketService.get(ticketId);
        return ticket.getFilePdf();
    }
}
