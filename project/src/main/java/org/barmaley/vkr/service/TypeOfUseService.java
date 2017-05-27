package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.barmaley.vkr.domain.TypeOfUse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("typeOfUseService")
@Transactional
public class TypeOfUseService {

    protected static Logger logger = Logger.getLogger("service");

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Retrieves all persons
     *
     * @return a list of persons
     */
    public List<TypeOfUse> getAll() {
        logger.debug("Retrieving all persons");

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM  TypeOfUse");

        // Retrieve all
        return  query.list();
    }

    /**
     * Retrieves a single person
     * @param id
     */
    public TypeOfUse get(Integer id ) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing person
        TypeOfUse typeOfUse = (TypeOfUse) session.get(TypeOfUse.class, id);

        return typeOfUse;
    }

    /**
     * Adds a new person
     */
    public void add(TypeOfUse typeOfUse) {
        logger.debug("Adding new person");

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Persists to db
        session.save(typeOfUse);
    }

    /**
     * Deletes an existing person
     * @param id the id of the existing person
     */
    public void delete(Integer id) {
        logger.debug("Deleting existing person");

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing person
        TypeOfUse typeOfUse = (TypeOfUse) session.get(TypeOfUse.class, id);

        // Delete
        session.delete(typeOfUse);
    }

}

