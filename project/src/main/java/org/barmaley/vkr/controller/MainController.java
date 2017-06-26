package org.barmaley.vkr.controller;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.EmployeeCopy;
import org.barmaley.vkr.domain.Users;
import org.barmaley.vkr.service.EmployeeCopyService;
import org.barmaley.vkr.service.UsersService;
import org.barmaley.vkr.tool.PermissionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class MainController {

    private static Logger logger = Logger.getLogger(MainController.class.getName());

    @Resource(name = "usersService")
    private UsersService usersService;

    @Resource(name = "permissionTool")
    private PermissionTool permissionTool;

    @Resource(name = "employeeCopyService")
    private EmployeeCopyService employeeCopyService;

    @Autowired
    @Qualifier("ticketFormValidator")
    private Validator validator;

    @InitBinder("ticketAttribute")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    // Получение пользователя в контексте Security
    public Users getPrincipal() {

        return (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // Отправка сообщений в форму аутентификации
    @GetMapping(value = ("/login"))
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Неверный логин и пароль.");
        }

        if (logout != null) {
            model.addAttribute("message", "Успешный выход из системы.");
        }

        return "login";
    }

    // Авторизация пользователя
    @GetMapping(value = {"/user", "/"})
    public String user() {
        boolean add_ticket_for_educ_program = permissionTool.checkPermission("PERM_ADD_TCIKET_FOR_EDUC_PROGRAM");
        boolean check_tickets = permissionTool.checkPermission("PERM_CHECK_TICKETS");
        boolean check_acts = permissionTool.checkPermission("PERM_CHECK_ACTS");
        if (add_ticket_for_educ_program) {
            logger.info("redirect to student");
            return "redirect:/student";
        } else if (check_tickets) {
            logger.info("redirect to coordinator");
            return "redirect:/coordinator";
        } else if (check_acts) {
            logger.info("redirect to bibliographer");
            return "redirect:/bibliographer";
        }
        return "pnh";
    }

    // Редактирование профиля пользователя
    @PostMapping(value = "/user/profile")
    public String saveProfile(@ModelAttribute("user") Users user) {

        usersService.editUser(user);
        return "redirect:/user";
    }

    // Для живого поиска
    @RequestMapping(value = "/getTags", method = RequestMethod.GET)
    public @ResponseBody
    List<EmployeeCopy> getTags(@RequestParam String tagName) {

        return (List<EmployeeCopy>) employeeCopyService.getEmployeeByFIO(tagName);

    }
}