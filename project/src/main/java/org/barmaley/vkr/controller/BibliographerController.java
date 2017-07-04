package org.barmaley.vkr.controller;


import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.Act;
import org.barmaley.vkr.domain.CoordinatorRights;
import org.barmaley.vkr.domain.Ticket;
import org.barmaley.vkr.domain.Users;
import org.barmaley.vkr.dto.ActDTO;
import org.barmaley.vkr.dto.LazyStudentsDTO;
import org.barmaley.vkr.generator.MyFileCopyVisitor;
import org.barmaley.vkr.generator.TicketPathGenerator;
import org.barmaley.vkr.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //Список актов, присланных от всех координаторов, но не просмотренными библиографами
        List<Act> actAllCoordinators = actService.getAllCoordinators(2);
        //Список актов, просмотренные библиографом
        List<Act> actListGet = actService.getAllActForCoordinator(user.getId(),3);
        //Список актов, готовые к конвертации
        List<Act> actListReadyConvert = actService.getAllActForCoordinator(user.getId(),4);
        //Список актов, готовых к передаче в ИБК
        List<Act> actListReadyLibrary = actService.getAllActForCoordinator(user.getId(),5);

        int countActsNew=actAllCoordinators.size();
        int countActsInCheck=actListGet.size();
        int countActsConvert=actListReadyConvert.size();
        int countActsLibrary=actListReadyLibrary.size();

        model.addAttribute("countActsNew",countActsNew);
        model.addAttribute("countActsInCheck",countActsInCheck);
        model.addAttribute("countActsConvert",countActsConvert);
        model.addAttribute("countActsLibrary",countActsLibrary);
        model.addAttribute("actAllCoordinators", actAllCoordinators);
        model.addAttribute("actListGet",actListGet);
        model.addAttribute("actListReadyConvert", actListReadyConvert);
        model.addAttribute("actListReadyLibrary", actListReadyLibrary);

        return "bibliographerPage";
    }

    @GetMapping(value = "/act/check")
    public String getCheckAct(@RequestParam(value = "actId") String actId, ModelMap model){

        Act act = actService.get(actId);
        if (act.getBibliographer()==null) {
            Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            act.setBibliographer(user);
            actService.edit(act);
        }

        ActDTO dto = new ActDTO();
        List<String> preCheckedVals = new ArrayList<>();

        for (Ticket ticket : act.getTickets()) {
            preCheckedVals.add(ticket.getId());
            ticket.setStatus(statusService.get(7));
            ticketService.edit(ticket);
        }
        if(act.getStatus().getId()==4||act.getStatus().getId()==5||act.getStatus().getId()==6) {

        }else
        {
            act.setStatus(statusActService.get(3));
        }
        act.setBibliographer((Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        actService.edit(act);


        dto.setAct(act);
        dto.setTicketsId(preCheckedVals);

        model.addAttribute("dto", dto);

        return "checkActPage";
    }

    @PostMapping(value = "/act/check")
    public String postEditAct(ActDTO dto, @RequestParam(name = "button") String button) throws IOException {
        Act act = actService.get(dto.getAct().getId());
        if (button.equals("return")){
            act.setStatus(statusActService.get(6));
            actService.edit(act);

        }else if(button.equals("accept")){
            act.setStatus(statusActService.get(4));
            actService.edit(act);
        }else if(button.equals("convert")){
            act.setStatus(statusActService.get(5));
            actService.edit(act);
        }
        else if(button.equals("readyIBK")){
            File file = new File("/home/impolun/finishpath/"+act.getId());
            file.mkdir();
            File pathSource = new File("/home/impolun/data/public/"+act.getId());
            File pathDestination = new File("/home/impolun/finishpath/"+act.getId());
            TicketPathGenerator ticketPathGenerator = new TicketPathGenerator();
            ticketPathGenerator.copyDirectory(pathSource,pathDestination);
            //Files.walkFileTree(pathSource, new MyFileCopyVisitor(pathSource,pathDestination));
            act.setStatus(statusActService.get(7));
            actService.edit(act);
        }

        return "redirect:/bibliographer";
    }

}
