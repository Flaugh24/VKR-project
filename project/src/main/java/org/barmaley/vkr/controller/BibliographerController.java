package org.barmaley.vkr.controller;


import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.Act;
import org.barmaley.vkr.domain.Ticket;
import org.barmaley.vkr.domain.Users;
import org.barmaley.vkr.dto.ActDTO;
import org.barmaley.vkr.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Controller
public class BibliographerController {

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

    @GetMapping(value = "/bibliographer")
    public String getBibliographerPage(ModelMap model){

        List<Act> actList = actService.getAllActForCoordinator(2);
        List<Act> actListReadyConvert = actService.getAllActForCoordinator(3);
        List<Act> actListReadyLibrary = actService.getAllActForCoordinator(4);
        model.addAttribute("actList", actList);
        model.addAttribute("actListReadyConvert", actListReadyConvert);
        model.addAttribute("actListReadyLibrary", actListReadyLibrary);
        return "bibliographerPage";
    }

    @GetMapping(value = "/act/check")
    public String getCheckAct(@RequestParam(value = "actId") String actId, ModelMap model){

        Act act = actService.get(actId);

        ActDTO dto = new ActDTO();
        List<String> preCheckedVals = new ArrayList<>();

        for (Ticket ticket : act.getTickets()) {
            preCheckedVals.add(ticket.getId());
            ticket.setStatus(statusService.get(7));
            ticketService.edit(ticket);
        }

        act.setStatus(statusActService.get(3));
        act.setBibliographer((Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        actService.edit(act);


        dto.setAct(act);
        dto.setTicketsId(preCheckedVals);

        model.addAttribute("dto", dto);

        return "checkActPage";
    }

    @PostMapping(value = "/act/check")
    public String postEditAct(ActDTO dto, @RequestParam(name = "button") String button) {
        Act act = actService.get(dto.getId());
        if (button.equals("return")){
            act.setStatus(statusActService.get(3));
            actService.edit(act);

        }

        return "redirect:/bibliographer";
    }

}
