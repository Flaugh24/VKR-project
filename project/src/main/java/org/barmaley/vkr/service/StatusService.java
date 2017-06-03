package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.Status;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Service for processing Persons
 *
 * @author Krams at
 */
@Service("statusService")
@Transactional
public class StatusService {

    protected static Logger logger = Logger.getLogger("service");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Retrieves all persons
     *
     * @return a list of persons
     */
    public List<Status> getAll() {
        logger.debug("Retrieving all persons");

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM  Status");

        // Retrieve all
        return query.list();
    }

    /**
     * Retrieves a single person
     *
     * @param id
     */
    public Status get(Integer id) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing person
        Status status = session.get(Status.class, id);

        return status;
    }

    /**
     * Adds a new person
     */
    public void add(Status status) {
        logger.debug("Adding new person");

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Persists to db
        session.save(status);
    }

    /**
     * Deletes an existing person
     *
     * @param id the id of the existing person
     */
    public void delete(Integer id) {
        logger.debug("Deleting existing person");

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing person
        Status status = session.get(Status.class, id);

        // Delete
        session.delete(status);
    }

}

