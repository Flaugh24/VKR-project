package org.barmaley.vkr.controller;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.*;
import org.barmaley.vkr.dto.CheckBoxesDTO;
import org.barmaley.vkr.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


//Контроллер для назначения прав координаторам и назначение координаторам групп
@Controller
public class AdminController {


    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Resource(name = "coordinatorRightsService")
    private CoordinatorRightsService coordinatorRightsService;

    @Resource(name = "usersService")
    private UsersService usersService;

    @Resource(name = "employeeCopyService")
    private EmployeeCopyService employeeCopyService;

    @Resource(name = "educProgramService")
    private EducProgramService educProgramService;

    @Resource(name = "rolesService")
    private RolesService rolesService;

    @Resource(name = "permissionsService")
    private PermissionsService permissionsService;

    @GetMapping(value = "/admin")
    public String getCoordinatorPage(ModelMap model) {
        return ("adminPage");
    }

    @GetMapping(value = "/admin/permissions/{id}")
    public String getChangePermissions(@PathVariable("id") int roleId, ModelMap model) {

        CheckBoxesDTO dto = new CheckBoxesDTO();
        Roles role = rolesService.getRole(roleId);

        dto.setName(role.getName());
        List<Permissions> permissionsList = permissionsService.getAll();
        List<Permissions> permissions = role.getPermissions();
        List<Integer> preCheckedVals = new ArrayList<>();
        for (Permissions permission : permissions) {
            preCheckedVals.add(permission.getId());
        }

        dto.setCheckedValsInt(preCheckedVals);
        dto.setPathVariable(roleId);

        model.addAttribute("permissionsAll", permissionsList);
        model.addAttribute("dto", dto);

        return "permissionsEditPage";
    }

    @PostMapping(value = "/admin/permissions/{id}")
    public String postChangePermissions(@PathVariable("id") int roleId, CheckBoxesDTO dto) {

        Roles role = rolesService.getRole(roleId);

        List<Integer> preCheckedVals = dto.getCheckedValsInt();

        List<Permissions> permissionsList = new ArrayList<>();

        for (int i : preCheckedVals) {
            Permissions permission = permissionsService.getPermission(i);
            permissionsList.add(permission);
        }
        role.setPermissions(permissionsList);

        rolesService.edit(role);

        return "redirect:/";
    }

    @PostMapping(value = "/admin/groups", headers = "Content-Type=application/x-www-form-urlencoded")
    public String getCoordinatorRightsChange(@RequestParam(value = "coordinatorId") String coordiantorId,
                                             Model model) {
        logger.info(coordiantorId);
        EmployeeCopy employeeCopy = employeeCopyService.get(coordiantorId);
        Users user = usersService.getByExtId(coordiantorId);

        if (user == null) {
            user = new Users();
            Set<Roles> roles = new HashSet<>();
            roles.add(rolesService.getRole(2));
            user.setExtId(employeeCopy.getUsername());
            user.setPassword(employeeCopy.getPassword());
            user.setSurname(employeeCopy.getSurname());
            user.setFirstName(employeeCopy.getFirstName());
            user.setSecondName(employeeCopy.getSecondName());
            user.setEnabled(true);
            user.setRoles(roles);
            user.setOrigin("EmployeeCopy");
            user = usersService.add(user);
        }
        List<String> coordinatorRightsList = new ArrayList<>();
        CheckBoxesDTO dto = new CheckBoxesDTO();
        for (CoordinatorRights coordinatorRight : user.getCoordinatorRights()) {
            coordinatorRightsList.add(coordinatorRight.getGroupNum());
        }
        dto.setCheckedValsStr(coordinatorRightsList);
        dto.setPathVariable(user.getId());
        List<String> groupsNum = educProgramService.getGroups(employeeCopy.getDepartment());

        model.addAttribute("dto", dto);
        model.addAttribute("groupsNum", groupsNum);
        return "changeGroups";
    }

    @PostMapping(value = "/admin/groups/{id}")
    public String postCoordinatorRightsChange(@PathVariable("id") int coordiantorId,
                                              CheckBoxesDTO dto, Model model) {

        Users user = usersService.get(coordiantorId);
        List<String> groupsNum = dto.getCheckedValsStr();
        List<CoordinatorRights> coordinatorRightsList = user.getCoordinatorRights();
        for (String groupNum : groupsNum) {
            CoordinatorRights rights = coordinatorRightsList.stream()// Преобразуем в поток
                    .filter(x -> groupNum.equals(x.getGroupNum()))    // Фильтруем
                    .findAny()                                    // Если 'findAny', то возвращаем найденное
                    .orElse(null);
            if (rights == null) {
                CoordinatorRights coordinatorRights = new CoordinatorRights();
                coordinatorRights.setGroupNum(groupNum);
                coordinatorRights.setCoordinator(user);
                coordinatorRightsService.add(coordinatorRights);
            }
        }
        for (CoordinatorRights coordinatorRights : coordinatorRightsList) {
            if (!dto.getCheckedValsStr().contains(coordinatorRights.getGroupNum())) {
                coordinatorRightsService.delete(coordinatorRights.getId());
            }
        }
        return "redirect:/";
    }
}
