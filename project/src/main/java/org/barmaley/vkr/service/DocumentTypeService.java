package org.barmaley.vkr.service;

import java.util.List;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.DocumentType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for processing Persons
 *
 * @author Krams at
 */
@Service("documentTypeService")
@Transactional
public class DocumentTypeService {

    protected static Logger logger = Logger.getLogger("service");

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Retrieves all persons
     *
     * @return a list of persons
     */
    public List<DocumentType> getAll() {
        logger.debug("Retrieving all persons");

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM  DocumentType");

        // Retrieve all
        return  query.list();
    }

    /**
     * Retrieves a single person
     * @param id
     */
    public DocumentType get(Integer id ) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing person
        DocumentType document = (DocumentType) session.get(DocumentType.class, id);

        return document;
    }

    /**
     * Adds a new person
     */
    public void add(DocumentType document) {
        logger.debug("Adding new person");

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Persists to db
        session.save(document);
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
        DocumentType document = (DocumentType) session.get(DocumentType.class, id);

        // Delete
        session.delete(document);
    }

}