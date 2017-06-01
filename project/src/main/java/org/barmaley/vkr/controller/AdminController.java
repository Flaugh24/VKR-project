package org.barmaley.vkr.controller;

import org.barmaley.vkr.autentication.CustomUser;
import org.barmaley.vkr.domain.*;
import org.barmaley.vkr.dto.LazyStudentsDTO;
import org.barmaley.vkr.dto.TicketDTO;
import org.barmaley.vkr.service.CoordinatorRightsService;
import org.barmaley.vkr.service.PermissionsService;
import org.barmaley.vkr.service.RolesService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by impolun on 31.05.17.
 */
//Контроллер для назначения прав координаторам и назначение координаторам групп
@Controller
public class AdminController {


    @Resource(name = "coordinatorRightsService")
    private CoordinatorRightsService coordinatorRightsService;

    @Resource(name = "rolesService")
    private RolesService rolesService;

    @Resource(name = "permissionsService")
    private PermissionsService permissionsService;

    @PostMapping(value = "/admin")
    public String getCoordinatorPage(@RequestParam(value = "rolesId") Integer rolesId, ModelMap model) {


        List<Permissions> listPermission = permissionsService.getAll();
        model.addAttribute("listPermission"+listPermission);
//        /*test*/
//        Roles role = rolesService.getRole(2);
//
//        Set<Permissions> setPermissions = new HashSet<>();
//
//        Permissions perm1 = permissionsService.getPermission(1);
//        Permissions perm2 = permissionsService.getPermission(2);
//        Permissions perm3 = permissionsService.getPermission(3);
//
//        setPermissions.add(perm1);
//        setPermissions.add(perm2);
//        setPermissions.add(perm3);
//
//        role.setPermissions(setPermissions);
//        rolesService.edit(role);
//        /*test*/

        return ("adminPage");
    }

//    public String submitForm(ModelMap model) {
//        Roles roles = rolesService.getRole(rolesId);
//
//        model.addAttribute("member", member);
//
//        return "successMember";
//
//    }


}
