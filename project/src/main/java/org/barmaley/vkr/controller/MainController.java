package org.barmaley.vkr.controller;

import org.apache.log4j.Logger;
import org.barmaley.vkr.autentication.CustomUser;
import org.barmaley.vkr.domain.*;
import org.barmaley.vkr.service.*;
import org.barmaley.vkr.dto.TicketEditDTO;
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
import java.util.List;
import java.util.Set;


/**
 * Handles and retrieves person request
 * Управляет и возвращает запрос
 */
@Controller
public class MainController {

    protected static Logger logger = Logger.getLogger("controller");

    @Resource(name="ticketService")
    private TicketService ticketService;

    @Resource(name="statusService")
    private StatusService statusService;

    @Resource(name="documentTypeService")
    private DocumentTypeService documentTypeService;

    @Resource(name="typeOfUseService")
    private TypeOfUseService typeOfUseService;

    @Resource(name = "usersService")
    private UsersService usersService;

    @Resource(name = "permissionTool")
    private PermissionTool permissionTool;

    /**
     * Handles and retrieves all persons and show it in a JSP page
     * Получает всех персон и показывает их на jsp
     * @return the name of the JSP page
     */

    @GetMapping(value = ("/login"))
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
    }

    @GetMapping(value = {"/user", "/"})
    public String user(){
        logger.debug("MainController./user");
        CustomUser principal = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = usersService.getById(principal.getId());
        Set<Roles> roles = user.getRoles();
        Boolean add_tciket_for_educ_program = permissionTool.checkPermission("PERM_ADD_TCIKET_FOR_EDUC_PROGRAM");
        Boolean check_tickets = permissionTool.checkPermission("PERM_CHECK_TICKETS");
        if(add_tciket_for_educ_program == true){
            return "redirect:/student";
        } else if (check_tickets == true){
            return "redirect:/coordinator";
        }
        return null;
    }

    /*@RequestMapping(value = "/ticketscoordinator", method = RequestMethod.GET)
    public String getTIcketsCoordinatorLK(Model model){
        logger.debug("Received request to show all tickets");
        List<Ticket> tickets = ticketService.getAllCoordinatorTicket(2);
        List<TicketDTO> ticketDTO = new ArrayList<TicketDTO>();

        for(Ticket ticket: tickets)
        {
            TicketDTO dto = new TicketDTO();

            dto.setId(ticket.getByExtId());
            dto.setAgreement(ticket.getAgreement());
            dto.setAnnotation(ticket.getAnnotation());
            dto.setAnnotationEng(ticket.getAnnotationEng());
            dto.setTitle(ticket.getTitle());
            dto.setTitleEng(ticket.getTitleEng());
            dto.setKeyWords(ticket.getKeyWords());
            dto.setKeyWordsEng(ticket.getKeyWordsEng());
            dto.setStatus(statusService.getById(ticketService.getStatusId(ticket.getByExtId())));
            dto.setDocumentType(documentTypeService.getById(ticketService.getDocumentTypeId(ticket.getByExtId())));


            ticketDTO.add(dto);

        }
        // Attach persons to the Model
        // Прикрепляет персон к модели
        model.addAttribute("tickets", ticketDTO);
        return "coordinatorpage";
    }*/
    /*@RequestMapping(value = "/tickets", method = RequestMethod.GET)
    public String getTickets(Model model) {

        logger.debug("Received request to show all tickets");


        // Retrieve all persons by delegating the call to PersonService
        // Получает всех персон вызовом PersonService
        List<Ticket> tickets = ticketService.getAll();

        List<TicketDTO> ticketDTO = new ArrayList<TicketDTO>();

        for(Ticket ticket: tickets)
        {
            TicketDTO dto = new TicketDTO();

            dto.setId(ticket.getId());
            dto.setAgreement(ticket.getAgreement());
            dto.setAnnotation(ticket.getAnnotation());
            dto.setAnnotationEng(ticket.getAnnotationEng());
            dto.setTitle(ticket.getTitle());
            dto.setTitleEng(ticket.getTitleEng());
            dto.setKeyWords(ticket.getKeyWords());
            dto.setKeyWordsEng(ticket.getKeyWordsEng());
            dto.setStatus(statusService.get(ticketService.getStatusId(ticket.getId())));
            dto.setDocumentType(documentTypeService.get(ticketService.getDocumentTypeId(ticket.getId())));
            dto.setTypeOfUse(typeOfUseService.get(ticketService.getTypeOfUse(ticket.getId())));

            ticketDTO.add(dto);

        }
        // Attach persons to the Model
        // Прикрепляет персон к модели
        model.addAttribute("tickets", ticketDTO);


        // This will resolve to /WEB-INF/jsp/personspage.jsp
        // Перенаправляет на /WEB-INF/jsp/personspage.jsp
        return "ticketspage";
    }*/

    /**
     * Retrieves the add page
     * Возвращает страницу Добавления
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/tickets/uploadfiles", method = RequestMethod.GET)
    public String UploadFiles (){
        return "uploadfilespage";
    }

    @PostMapping("/tickets/upload") //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file)
    {
        Ticket ticket = new Ticket();
        String fullPath,ROOT_FOLDERS;
        logger.debug("Received request to add new ticket");

        try{
            ROOT_FOLDERS = "D:\\src\\";
            byte[] bytes = file.getBytes();


            logger.debug(file.getOriginalFilename());


            ticket.setId((ticketService.add(ticket))); //Создаем тикет
            fullPath = ROOT_FOLDERS + ticket.getId()+".pdf";
            Path path = Paths.get(fullPath);
            Files.write(path, bytes);
            ticket.setFilePdf(fullPath);
            ticketService.edit(ticket);
            logger.debug(ticket.getId());

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/vkr/main/tickets/edit?id="+ticket.getId();
    }

    @RequestMapping(value = "/tickets/add", method = RequestMethod.GET)
    public String getAdd(ModelMap model) {
        logger.debug("Received request to show add page");

        List<DocumentType> documentTypes = documentTypeService.getAll();

        TicketEditDTO dto = new TicketEditDTO();

        // Create new Person and add to model
        // This is the formBackingOBject
        // Создает новую персону и добавляет ее в модель
        // Это возвращающая форма

        //TicketDTO dto = new TicketDTO();

        model.addAttribute("ticketAttribute", dto);
        model.addAttribute("documentType", documentTypes);
        /*Map<String, Object> params = new HashMap<String, Object>();
        params.put("ticketAttribute", new Ticket());
        params.put("documentType", documentTypes);
        model.addAllAttributes(params);
*/


        // This will resolve to /WEB-INF/jsp/addpage.jsp
        // перенаправление на страницу Добавления
        return "addpage";
    }

    /**
     * Adds a new person by delegating the processing to PersonService.
     * Displays a confirmation JSP page
     * Добавляет новую персону через PersonService
     * Показывает подтверждающую JSP
     * @return  the name of the JSP page
     */
    @RequestMapping(value = "/tickets/saveTicket", method = RequestMethod.POST)
    public String save(@ModelAttribute("ticketAttribute") TicketEditDTO dto) {
        logger.debug("Received request to add new ticket");

        // The "personAttribute" model has been passed to the controller from the JSP
        // We use the name "personAttribute" because the JSP uses that name
        // Call PersonService to do the actual adding
        // модель "personAttribute" передана контроллеру из JSP
        // мы используем имя "personAttribute" потому что JSP  страница использует его
        // Вызов PersonService для совершения добавления
        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setAgreement(dto.getAgreement());
        ticket.setAnnotation(dto.getAnnotation());
        ticket.setAnnotationEng(dto.getAnnotationEng());
        ticket.setTitle(dto.getTitle());
        ticket.setTitleEng(dto.getTitleEng());
        ticket.setKeyWords(dto.getKeyWords());
        ticket.setKeyWordsEng(dto.getKeyWordsEng());

        ticketService.add(ticket);

        // This will resolve to /WEB-INF/jsp/addedpage.jsp
        return "redirect:/vkr/main/tickets";
    }

    @RequestMapping(value = "/tickets/postTicket", method = RequestMethod.POST)
    public String post(@ModelAttribute("ticketAttribute") TicketEditDTO dto) {
        logger.debug("Received request to add new ticket");

        // The "personAttribute" model has been passed to the controller from the JSP
        // We use the name "personAttribute" because the JSP uses that name
        // Call PersonService to do the actual adding
        // модель "personAttribute" передана контроллеру из JSP
        // мы используем имя "personAttribute" потому что JSP  страница использует его
        // Вызов PersonService для совершения добавления
        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setAgreement(dto.getAgreement());
        ticket.setAnnotation(dto.getAnnotation());
        ticket.setAnnotationEng(dto.getAnnotationEng());
        ticket.setTitle(dto.getTitle());
        ticket.setTitleEng(dto.getTitleEng());
        ticket.setKeyWords(dto.getKeyWords());
        ticket.setKeyWordsEng(dto.getKeyWordsEng());
        ticketService.add(ticket);

        // This will resolve to /WEB-INF/jsp/addedpage.jsp
        return "redirect:/vkr/main/tickets";
    }

    /**
     * Deletes an existing person by delegating the processing to PersonService.
     * Displays a confirmation JSP page
     *
     * @return  the name of the JSP page
     */
    @RequestMapping(value = "/tickets/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id", required=true) String id,
                         Model model) {

        logger.debug("Received request to delete existing ticket");

        // Call PersonService to do the actual deleting

        ticketService.delete(id);

        // Add id reference to Model
        model.addAttribute("id", id);

        // This will resolve to /WEB-INF/jsp/deletedpage.jsp
        return "deletedpage";
    }

    /**
     * Retrieves the edit page
     *
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/tickets/edit", method = RequestMethod.GET)
    public String getEdit(@RequestParam(value="id", required=true) String id,
                          ModelMap model) {
        logger.debug("Received request to show edit page");
        List<TypeOfUse> typeOfUse = typeOfUseService.getAll();
        List<DocumentType> documentTypes = documentTypeService.getAll();
        TicketEditDTO dto = new TicketEditDTO();
        Ticket ticket = ticketService.get(id);

        dto.setId(ticket.getId());
        dto.setAgreement(ticket.getAgreement());
        dto.setAnnotation(ticket.getAnnotation());
        dto.setAnnotationEng(ticket.getAnnotationEng());
        dto.setTitle(ticket.getTitle());
        dto.setTitleEng(ticket.getTitleEng());
        dto.setKeyWords(ticket.getKeyWords());
        dto.setKeyWordsEng(ticket.getKeyWordsEng());
        dto.setStatus(statusService.get(ticketService.getStatusId(ticket.getId())));
        dto.setDocumentTypeId(ticketService.getDocumentTypeId(ticket.getId()));
        dto.setTypeOfUseId(ticketService.getTypeOfUse(ticket.getId()));
        // Retrieve existing Person and add to model
        // This is the formBackingOBject
        model.addAttribute("ticketAttribute", dto);
        model.addAttribute("documentType", documentTypes);
        model.addAttribute("typeOfUse", typeOfUse);

        // This will resolve to /WEB-INF/jsp/editpage.jsp
        return "editpage";
    }

    /**
     * Edits an existing person by delegating the processing to PersonService.
     * Displays a confirmation JSP page
     *
     * @return  the name of the JSP page
     */
    @RequestMapping(value = "/tickets/edit", method = RequestMethod.POST)
    public String saveEdit(@ModelAttribute("ticketAttribute") TicketEditDTO dto)
                            {
        logger.debug("Received request to update ticket");

        // The "personAttribute" model has been passed to the controller from the JSP
        // We use the name "personAttribute" because the JSP uses that name

        // We manually assign the id because we disabled it in the JSP page
        // When a field is disabled it will not be included in the ModelAttribute

        logger.debug(dto.getDocumentTypeId());
        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setAgreement(dto.getAgreement());
        ticket.setAnnotation(dto.getAnnotation());
        ticket.setAnnotationEng(dto.getAnnotationEng());
        ticket.setTitle(dto.getTitle());
        ticket.setTitleEng(dto.getTitleEng());
        ticket.setKeyWords(dto.getKeyWords());
        ticket.setKeyWordsEng(dto.getKeyWordsEng());
        ticket.setDocumentType(documentTypeService.get(dto.getDocumentTypeId()));
        ticket.setTypeOfUse(typeOfUseService.get(dto.getTypeOfUseId()));
        logger.debug(ticket.getDocumentType().getId());

        // Delegate to PersonService for editing

        ticketService.edit(ticket);

        // This will resolve to /WEB-INF/jsp/editedpage.jsp
        return "redirect:/vkr/main/tickets";
    }

}