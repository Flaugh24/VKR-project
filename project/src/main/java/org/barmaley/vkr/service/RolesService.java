package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.barmaley.vkr.domain.Roles;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


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
    public List<Roles> getAll() {
        logger.debug("Retrieving all roles");

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM  Roles");

        // Retrieve all
        return  query.list();
    }

    public void add(Roles role) {
        Session session = sessionFactory.getCurrentSession();
        session.save(role);
        session.flush();
    }

    public void edit(Roles role) {
        logger.debug("edit Role");
        Session session = sessionFactory.getCurrentSession();

        Roles existingRole = (Roles) session.get(Roles.class, role.getId());

        existingRole.setPermissions(role.getPermissions());
        //-------------------------------------------------------------------
        session.save(existingRole);
    }
}
