package org.barmaley.vkr.security;

import org.barmaley.vkr.domain.CoordinatorRights;
import org.barmaley.vkr.domain.Ticket;
import org.barmaley.vkr.domain.Users;
import org.barmaley.vkr.service.TicketService;
import org.barmaley.vkr.tool.PermissionTool;
import org.springframework.security.core.Authentication;

import javax.annotation.Resource;
import java.util.Set;

public class WebSecurity {

    @Resource(name = "ticketService")
    private TicketService ticketService;

    @Resource(name = "permissionTool")
    private PermissionTool permissionTool;

    public boolean checkTicketId(Authentication authentication, String id) {
        Users user = (Users) authentication.getPrincipal();
        Ticket ticket = ticketService.get(id);
        Set<CoordinatorRights> coordinatorRightsSet = user.getCoordinatorRights();
        CoordinatorRights coordinatorRights = coordinatorRightsSet.stream()
                .filter(x -> ticket.getGroupNum().equals(x.getGroupNum()))
                .findAny()
                .orElse(null);

        boolean perm_check_all_tickets = permissionTool.checkPermission("PERM_CHECK_ALL_TICKETS");

        if (ticket.getUser().getId().equals(user.getId())) {
            return true;
        } else if (coordinatorRights != null) {
            return true;
        } else if (perm_check_all_tickets) {
            return true;
        } else {
            return false;
        }
    }
}