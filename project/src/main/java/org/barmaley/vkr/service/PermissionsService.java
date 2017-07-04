package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.Permissions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("permissionsService")
@Transactional
public class PermissionsService {
    protected static Logger logger = Logger.getLogger("service");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public Permissions getPermission(Integer id) {
        logger.debug("The permissions id");
        Session session = sessionFactory.getCurrentSession();

        return session.get(Permissions.class, id);
    }

    public List getAll() {
        logger.debug("Retrieving all roles");

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM Permissions ORDER BY name");

        // Retrieve all
        return query.list();
    }
}
