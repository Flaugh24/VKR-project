package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.barmaley.vkr.domain.Roles;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("rolesService")
@Transactional
public class RolesService {

    protected static Logger logger = Logger.getLogger("service");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public Roles getRole(Integer id) {
        Session session = sessionFactory.getCurrentSession();

        Roles role = (Roles) session.get(Roles.class, id);
        logger.debug(role.getName());


        return role;
    }
}
