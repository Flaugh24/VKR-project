package org.barmaley.vkr.controller;


import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.Act;
import org.barmaley.vkr.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
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

        List<Act> actList = actService.getAll();

        model.addAttribute("actList", actList);
        return "bibliographerPage";
    }
}
