package org.barmaley.vkr.controller;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.EducProgram;
import org.barmaley.vkr.domain.Ticket;
import org.barmaley.vkr.domain.Users;
import org.barmaley.vkr.service.EducProgramService;
import org.barmaley.vkr.service.TicketService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {

    protected static Logger logger = Logger.getLogger(StudentController.class.getName());

    @Resource(name = "ticketService")
    private TicketService ticketService;

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
}
