package org.barmaley.vkr.Tool;

import org.apache.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gagar on 21.05.2017.
 */

@Service("permissionTool")
public class PermissionTool {

    protected static Logger logger = Logger.getLogger("controller");

    public Boolean checkPermission(String permission) {

        Boolean existence;

        List<SimpleGrantedAuthority> permissions = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        logger.debug(permissions);
        SimpleGrantedAuthority result = permissions.stream()// Преобразуем в поток
                .filter(x -> x.toString().equals(permission))    // Фильтруем
                .findAny()                                    // Если 'findAny', то возвращаем найденное
                .orElse(null);
        if (result != null) {
            existence = true;
        } else {
            existence = false;
        }
        return existence;
    }
}
