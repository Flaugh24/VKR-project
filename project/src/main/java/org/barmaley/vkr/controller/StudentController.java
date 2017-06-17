package org.barmaley.vkr.controller;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.*;
import org.barmaley.vkr.dto.TicketEditDTO;
import org.barmaley.vkr.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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


    //------------------------------------------------------------------------
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
            //-----------------------------------------------------------------
            ticket.setGroupNum(educProgram.getGroupNum());
            ticket.setInstitute(educProgram.getInstitute());
            ticket.setDepartment(educProgram.getDepartment());
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
        Ticket ticket = ticketService.get(ticketId);
        TicketEditDTO dto = new TicketEditDTO();
        SimpleDateFormat dateFormat = new SimpleDateFormat("y");

        if (user.getId().equals(ticket.getUser().getId())) {
            List<TypeOfUse> typesOfUse = typeOfUseService.getAll();
            dto.setDateOfPublic(dateFormat.format(ticket.getDateCreationStart()));
            dto.setId(ticket.getId());
            dto.setLicenseNumber(ticket.getLicenseNumber());
            if (ticket.getLicenseDate() != null) {
                SimpleDateFormat licenseDateFormat = new SimpleDateFormat("y-MM-dd");
                dto.setLicenseDateDTO(licenseDateFormat.format(ticket.getLicenseDate()));
            }
            dto.setDocumentType(ticket.getDocumentType());
            dto.setAnnotation(ticket.getAnnotation());
            dto.setAnnotationEng(ticket.getAnnotationEng());
            dto.setTitle(ticket.getTitle());
            dto.setTitleEng(ticket.getTitleEng());
            dto.setKeyWords(ticket.getKeyWords());
            dto.setKeyWordsEng(ticket.getKeyWordsEng());
            dto.setFilePdf(ticket.getFilePdf());
            dto.setFileZip(ticket.getFileZip());
            dto.setFilePdfSecret(ticket.getFilePdfSecret());
            dto.setFileZipSecret(ticket.getFileZipSecret());
            dto.setStatus(ticket.getStatus());
            dto.setTypeOfUse(ticket.getTypeOfUse());
            //----------------------------------------------------
            dto.setInstitute(ticket.getInstitute());
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
            dto.setHeadOfDepartment(ticket.getHeadOfDepartment());
            dto.setFullNameCurator(ticket.getFullNameCurator());
            dto.setFullNameCuratorEng(ticket.getFullNameCuratorEng());
            dto.setPosOfCurator(ticket.getPosOfCurator());
            dto.setDegreeOfCurator(ticket.getDegreeOfCurator());
            //-----------------------------------------------------
            model.addAttribute("ticketAttribute", dto);
            model.addAttribute("typesOfUse", typesOfUse);
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
        List<String> wordsList = new ArrayList<>();
        String s = dto.getWord1();

        int j = s.length();
        if (dto.getWord1().length()!=0){wordsList.add(dto.getWord1());}
        if (dto.getWord2().length()!=0){wordsList.add(dto.getWord2());}
        if (dto.getWord3().length()!=0){wordsList.add(dto.getWord3());}
        if (dto.getWord4().length()!=0){wordsList.add(dto.getWord4());}
        for(int i=0; i<wordsList.size(); i++){
            if(i==wordsList.size()-1) {wordsList.set(i,wordsList.get(i));}
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

        ticket.setTypeOfUse(typeOfUseService.get(dto.getTypeOfUse().getId()));
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
