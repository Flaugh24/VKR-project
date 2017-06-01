package org.barmaley.vkr.controller;

import org.apache.log4j.Logger;
import org.barmaley.vkr.autentication.CustomUser;
import org.barmaley.vkr.domain.*;
import org.barmaley.vkr.dto.TicketEditDTO;
import org.barmaley.vkr.dto.TicketDTO;
import org.barmaley.vkr.service.*;
import org.barmaley.vkr.Tool.PermissionTool;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Permission;
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
        if (!tickets.isEmpty()) {
            for (Ticket ticket : tickets) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.y");
                TicketDTO ticketDTO = new TicketDTO();
                ticketDTO.setId(ticket.getId());
                try {
                    ticketDTO.setDateCreationStart(dateFormat.format(ticket.getDateCreationStart()));
                    ticketDTO.setDateCreationFinish(dateFormat.format(ticket.getDateCreationFinish()));
                    ticketDTO.setDateCheckCoordinatorStart(dateFormat.format(ticket.getDateCheckCoordinatorStart()));
                } catch (Exception e) {
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
                    logger.debug("getDepartment " + dto.getDepartment());
                    //----------------------------------------------------
                    dto.setDirection(educProgram.getDirection());
                    dto.setDirectionCode(educProgram.getDirectionCode());
//                    dto.setDegreeOfCurator(educProgram.getDegreeOfCurator());
//                    dto.setDegreeOfCuratorEng(educProgram.getDegreeOfCuratorEng());
//                    dto.setPosOfCurator(educProgram.getPosOfCurator());
//                    dto.setPosOfCuratorEng(educProgram.getPosOfCuratorEng());
                    //-----------------------------------------------------
                    //-----------------------------------------------------
                    educProgramsDTO.add(dto);
                } else if (result == null) {
                    EducProgram dto = new EducProgram();
                    dto.setId(educProgram.getId());
                    dto.setInstitute(educProgram.getInstitute());
                    dto.setDegree(educProgram.getDegree());
                    dto.setGroupNum(educProgram.getGroupNum());
                    dto.setDepartment(educProgram.getDepartment());
                    logger.debug("getDepartment " + dto.getDepartment());
                    //----------------------------------------------------
                    dto.setDirection(educProgram.getDirection());
                    dto.setDirectionCode(educProgram.getDirectionCode());
//                    dto.setDegreeOfCurator(educProgram.getDegreeOfCurator());
//                    dto.setDegreeOfCuratorEng(educProgram.getDegreeOfCuratorEng());
//                    dto.setPosOfCurator(educProgram.getPosOfCurator());
//                    dto.setPosOfCuratorEng(educProgram.getPosOfCuratorEng());
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
//            ticket.setDegreeOfCurator(educProgram.getDegreeOfCurator());
//            ticket.setDegreeOfCuratorEng(educProgram.getDegreeOfCuratorEng());
//            ticket.setPosOfCurator(educProgram.getPosOfCurator());
//            ticket.setPosOfCuratorEng(educProgram.getPosOfCuratorEng());
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

        if (principal.getId() == ticket.getUser().getId() || access) {
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
            dto.setFileRar(ticket.getFileRar());
            dto.setStatus(statusService.get(ticketService.getStatusId(ticket.getId())));
            dto.setDocumentTypeId(ticketService.getDocumentTypeId(ticket.getId()));
            dto.setTypeOfUseId(ticketService.getTypeOfUse(ticket.getId()));
            //----------------------------------------------------
            dto.setInstitute(ticket.getInstitute());
            String str = dto.getKeyWords();
            logger.debug(str);
            List list = new ArrayList();
            if (str != null) {
                for (String retval : str.split(", ")) {
                    logger.debug(retval);
                    list.add(retval);
                }
            }
            logger.debug(list);
            logger.debug(list.size());
            if (list.size() == 0) {
                dto.setWord1(null);
                dto.setWord2(null);
                dto.setWord3(null);
                dto.setWord4(null);
            }
            if (list.size() == 1) {
                dto.setWord1((String) list.get(0));
                dto.setWord2(null);
                dto.setWord3(null);
                dto.setWord4(null);
            }
            if (list.size() == 2) {
                dto.setWord1((String) list.get(0));
                dto.setWord2((String) list.get(1));
                dto.setWord3(null);
                dto.setWord4(null);
            }
            if (list.size() == 3) {
                dto.setWord1((String) list.get(0));
                dto.setWord2((String) list.get(1));
                dto.setWord3((String) list.get(2));
                dto.setWord4(null);
            }
            if (list.size() == 4) {
                dto.setWord1((String) list.get(0));
                logger.debug("1 " + dto.getWord1());
                dto.setWord2((String) list.get(1));
                logger.debug("2 " + dto.getWord2());
                dto.setWord3((String) list.get(2));
                logger.debug("3 " + dto.getWord3());
                dto.setWord4((String) list.get(3));
                logger.debug("4 " + dto.getWord4());
            }
            str = dto.getKeyWordsEng();
            logger.debug(str);
            list.clear();
            if (str != null) {
                for (String retval : str.split(", ")) {
                    logger.debug(retval);
                    list.add(retval);
                }
            }
            logger.debug(list);
            logger.debug(list.size());
            try {
                dto.setWord1Eng((String) list.get(0));
                dto.setWord2Eng((String) list.get(1));
                dto.setWord3Eng((String) list.get(2));
                dto.setWord4Eng((String) list.get(3));
            } catch (Exception e) {
                e.printStackTrace();
            }

            dto.setDirection(ticket.getDirection());
            dto.setDepartment(ticket.getDepartment());
            dto.setDirectionCode(ticket.getDirectionCode());
            dto.setGroupNum(ticket.getGroupNum());


//            dto.setDegreeOfCurator(ticket.getDegreeOfCurator());
//            dto.setDegreeOfCuratorEng(ticket.getDegreeOfCuratorEng());
//            dto.setPosOfCurator(ticket.getPosOfCurator());
//            dto.setPosOfCuratorEng(ticket.getPosOfCuratorEng());
            dto.setPlaceOfPublic(ticket.getPlaceOfPublic());
            dto.setPlaceOfPublicEng(ticket.getPlaceOfPublicEng());
            dto.setYearOfPublic(ticket.getYearOfPublic());
            dto.setDocumentTypeName(documentTypeService.get(ticketService.getDocumentTypeId(ticket.getId())).getName());
            dto.setDocumentTypeNameEng(documentTypeService.get(ticketService.getDocumentTypeId(ticket.getId())).getNameEng());
            dto.setSurFirstLastNameDir(ticket.getSurFirstLastNameDir());
            dto.setSflNMaster(ticket.getSflNMaster());
            dto.setSflNMasterEng(ticket.getSflNMasterEng());
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
                           @RequestParam(value = "button", required = false) String button) {
        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setAnnotationEng(dto.getAnnotationEng());
        ticket.setTitle(dto.getTitle());
        ticket.setTitleEng(dto.getTitleEng());
        String str = dto.getWord1() + ", " + dto.getWord2() + ", " + dto.getWord3() + ", " + dto.getWord4();
        ticket.setKeyWords(str);
        str = dto.getWord1Eng() + ", " + dto.getWord2Eng() + ", " + dto.getWord3Eng() + ", " + dto.getWord4Eng();
        ticket.setKeyWordsEng(str);
        ticket.setTypeOfUse(typeOfUseService.get(dto.getTypeOfUseId()));
        ticket.setStatus(statusService.get(1));
        //----------------------------------------------------
        ticket.setPlaceOfPublic(dto.getPlaceOfPublic());
        ticket.setPlaceOfPublicEng(dto.getPlaceOfPublicEng());
        ticket.setYearOfPublic(dto.getDateOfPublic());
        ticket.setSurFirstLastNameDir(dto.getSurFirstLastNameDir());
        ticket.setSflNMaster(dto.getSflNMaster());
        ticket.setSflNMasterEng(dto.getSflNMasterEng());
        ticket.setPosOfCurator(dto.getPosOfCurator());
        ticket.setDegreeOfCurator(dto.getDegreeOfCurator());
        //----------------------------------------------------
        if (button != null) {
            logger.debug("Status 2");
            ticket.setStatus(statusService.get(2));
            ticket.setDateCreationFinish(new Date());
        }

        ticketService.edit(ticket);

        return "redirect:/user";
    }

    @PostMapping("/ticket/deleterar")
    public String singleFileDeleterar(@RequestParam("uploadFile") MultipartFile file,
                                      @RequestParam("ticketId") String ticketId,
                                      @RequestParam("submit") String submit) {
        String fullPath, ROOT_FOLDERS;
        logger.debug("Upload PDF File");

        Ticket ticket = ticketService.get(ticketId);
        try {
            ROOT_FOLDERS = "D:\\src\\";
            byte[] bytes = file.getBytes();
            logger.debug(file.getOriginalFilename());
            if (submit.equals("Удалить PDF")) {
                logger.debug("SUBMIT= " + submit);
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

    @PostMapping("/ticket/delete")
    public String singleFileDelete(/*@RequestParam("uploadFile") MultipartFile file*/
                                   @RequestParam("ticketId") String ticketId,
                                   @RequestParam("submit") String submit) throws MalformedURLException {

        String ROOT_FOLDERS = "/home/impolun/data/";
        logger.debug("Upload PDF File");

        Ticket ticket = ticketService.get(ticketId);
        if (submit.equals("Удалить PDF")) {
            File file = new File(ticket.getFilePdf());
            if (file.delete()) {
                logger.debug(ticket.getFilePdf() + " файл удален");
            } else logger.debug("Файла" + ticket.getFilePdf() + " не обнаружено");
            ticket.setFilePdf(null);
            ticketService.editPdf(ticket);
        }
        if (submit.equals("Удалить архив")) {
            File file = new File(ticket.getFileRar());
            if (file.delete()) {
                logger.debug(ticket.getFileRar() + " файл удален");
            } else logger.debug("Файла" + ticket.getFileRar() + " не обнаружено");
            ticket.setFileRar(null);
            ticketService.editRar(ticket);
        }
        if (ticket.getDateCheckCoordinatorStart() == null) {
            return "redirect:/ticket/edit?ticketId=" + ticket.getId();
        } else {

        }
        return "redirect:/ticket/check?ticketId=" + ticket.getId();
    }


    public void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String filename = req.getParameter("filename");
        // File will be moved from location to target directory
        req.getPart("content").write("C:/data/" + filename + ".txt");
        if (req.getPart("content") != null) {
            PrintWriter out = resp.getWriter();
            out.write("<h3>File uploaded successfully</h3>");
        }
    }


    @PostMapping("/ticket/upload")
    public String singleFileUpload(@RequestParam("uploadFile") MultipartFile file,
                                   @RequestParam("ticketId") String ticketId,
                                   @RequestParam("submit") String submit) {

        logger.debug("Upload PDF File");

        String fullPath,

                ROOT_FOLDERS = "/home/impolun/data/",
                EXPDF = ".pdf",
                EXZIP = ".zip";

        Ticket ticket = ticketService.get(ticketId);

        try {

            byte[] bytes = file.getBytes();

            //Определяем расширешие файла(будет храниться в expansion)
            logger.debug("Original file name:" + file.getOriginalFilename());
            String expansion = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4, file.getOriginalFilename().length());
            logger.debug(expansion);

            if (submit.equals("Загрузить")) {
                if (expansion.equals(EXPDF)) {
                    fullPath = ROOT_FOLDERS + ticket.getId() + EXPDF;
                    logger.debug("Конечный путь файла pdf= " + fullPath);
                    Path path = Paths.get(fullPath);
                    Files.write(path, bytes);
                    ticket.setFilePdf(fullPath);
                    ticketService.editPdf(ticket);
                } else {
                    fullPath = ROOT_FOLDERS + ticket.getId() + EXZIP;
                    logger.debug("Конечный путь файла zip = " + fullPath);
                    Path path = Paths.get(fullPath);
                    Files.write(path, bytes);
                    ticket.setFileRar(fullPath);
                    ticketService.editRar(ticket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ticket.getDateCheckCoordinatorStart() == null) {
            return "redirect:/ticket/edit?ticketId=" + ticket.getId();
        } else {

        }
        return "redirect:/ticket/check?ticketId=" + ticket.getId();
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
