package org.barmaley.vkr.service;


import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.StatusAct;
import org.barmaley.vkr.domain.StatusTicket;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("statusActService")
@Transactional
public class StatusActService {

    protected static Logger logger = Logger.getLogger("service");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;


    public List<StatusTicket> getAll() {
        logger.debug("Retrieving all persons");

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM  StatusAct");

        // Retrieve all
        return query.list();
    }

    public StatusAct get(Integer id) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing person
        StatusAct status = session.get(StatusAct.class, id);

        return status;
    }
}


