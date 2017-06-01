package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.Permissions;
import org.barmaley.vkr.domain.Roles;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by impolun on 31.05.17.
 */
@Service("permissionsService")
@Transactional
public class PermissionsService {
    protected static Logger logger = Logger.getLogger("service");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public Permissions getPermission(Integer id) {
        logger.debug("The permissions id");
        Session session = sessionFactory.getCurrentSession();
        Permissions permission = (Permissions) session.get(Permissions.class, id);

        return permission;
    }

    public List<Permissions> getAll() {
        logger.debug("Retrieving all roles");

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM  Permissions");

        // Retrieve all
        return  query.list();
    }
}
