package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.Ticket;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Service for processing Persons
 * Сервис для класса Person
 */
@Service("ticketService")
@Transactional
public class TicketService {

    protected static Logger logger = Logger.getLogger("service");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public Integer getCountTicketsWithStatus(Integer statusId) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("COUNT FROM Ticket WHERE status.id=" + statusId);

        return (Integer) query.uniqueResult();

    }


    public List getAll() {

        // Retrieve session from Hibernate
        // Получаем сессию
        Session session = sessionFactory.getCurrentSession();

        // Create a Hibernate query (HQL)
        // Создаем запрос
        Query query = session.createQuery("FROM  Ticket ORDER BY Id");

        // Retrieve all
        // получаем всех
        return query.list();
    }

    public List getAllTicketForCoordinator(String groupNum, Integer statusId) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM  Ticket WHERE groupNum = :groupNum AND status.id = :statusId" +
                " ORDER BY Id"
        );
        query.setParameter("groupNum", groupNum);
        query.setParameter("statusId", statusId);

        return query.list();
    }

    public List getAllTicketForAct(String groupNum, Integer statusId, String actId) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Ticket WHERE (groupNum = :groupNum AND status.id = :statusId) OR (groupNum = :groupNum AND act.id = :actId)" +
                " ORDER BY Id"
        );
        query.setParameter("groupNum", groupNum);
        query.setParameter("statusId", statusId);
        query.setParameter("actId", actId);

        return query.list();
    }

    public List getAllTicketsByUserId(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        // Создаем запрос
        Query query = session.createQuery("FROM  Ticket WHERE user.id = " + userId + " ORDER BY Id");


        // Retrieve all
        // получаем всех
        return query.list();
    }

    /**
     * Retrieves a single person
     * Получение одной персоны
     */
    public Ticket get(String id) {
        // Retrieve session from Hibernate
        // получаем сессию
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing person first
        // получаем персону по id

        return session.get(Ticket.class, id);
    }

    public Integer getStatusId(String id) {
/*        logger.debug("Deleting existing credit card");*/

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Delete reference to foreign key credit card first
        // We need a SQL query instead of HQL query here to access the third table
        Query query = session.createSQLQuery("SELECT STATUS FROM TICKET " +
                "WHERE ID='" + id + "'");

        return (Integer) query.uniqueResult();
    }

    public Integer getDocumentTypeId(String id) {
/*        logger.debug("Deleting existing credit card");*/

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Delete reference to foreign key credit card first
        // We need a SQL query instead of HQL query here to access the third table
        Query query = session.createSQLQuery("SELECT DOCUMENT_TYPE FROM TICKET " +
                "WHERE ID='" + id + "'");

        return (Integer) query.uniqueResult();
    }

    public Integer getTypeOfUse(String id) {
/*        logger.debug("Deleting existing credit card");*/

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Delete reference to foreign key credit card first
        // We need a SQL query instead of HQL query here to access the third table
        Query query = session.createSQLQuery("SELECT TYPE_OF_USE FROM TICKET " +
                "WHERE ID='" + id + "'");

        return (Integer) query.uniqueResult();
    }

    /**
     * Adds a new person
     * Добавление персоны
     */

    public String add(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();
        session.save(ticket);
        session.flush();

        return ticket.getId();
    }

    public void delete(String id) {
        Session session = sessionFactory.getCurrentSession();

        Ticket ticket = session.get(Ticket.class, id);

        session.delete(ticket);
    }

    public void edit(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();

        Ticket existingTicket = session.get(Ticket.class, ticket.getId());

        existingTicket.setLicenseNumber(ticket.getLicenseNumber());
        existingTicket.setLicenseDate(ticket.getLicenseDate());
        existingTicket.setTitle(ticket.getTitle());
        existingTicket.setTitleEng(ticket.getTitleEng());
        existingTicket.setAnnotation(ticket.getAnnotation());
        existingTicket.setAnnotationEng(ticket.getAnnotationEng());
        existingTicket.setKeyWords(ticket.getKeyWords());
        existingTicket.setKeyWordsEng(ticket.getKeyWordsEng());
        existingTicket.setTypeOfUse(ticket.getTypeOfUse());
        if (ticket.getStatus() != null){
            existingTicket.setStatus(ticket.getStatus());
        }
        if (ticket.getDateCreationFinish() != null) {
            existingTicket.setDateCreationFinish(ticket.getDateCreationFinish());
        }
        if (ticket.getDateCheckCoordinatorStart() != null) {
            existingTicket.setDateCheckCoordinatorStart(ticket.getDateCheckCoordinatorStart());
        }
        if (ticket.getDateCheckCoordinatorFinish() != null) {
            existingTicket.setDateCheckCoordinatorFinish(ticket.getDateCheckCoordinatorFinish());
        }
        if (ticket.getDateReturn() != null) {
            existingTicket.setDateReturn(ticket.getDateReturn());
        }
        //-------------------------------------------------------------------
        existingTicket.setPlaceOfPublic(ticket.getPlaceOfPublic());
        existingTicket.setPlaceOfPublicEng(ticket.getPlaceOfPublicEng());
        existingTicket.setYearOfPublic(ticket.getYearOfPublic());
        existingTicket.setHeadOfDepartment(ticket.getHeadOfDepartment());
        existingTicket.setFullNameCurator(ticket.getFullNameCurator());
        existingTicket.setFullNameCuratorEng(ticket.getFullNameCuratorEng());
        existingTicket.setPosOfCurator(ticket.getPosOfCurator());
        existingTicket.setPosOfCuratorEng(ticket.getPosOfCuratorEng());
        existingTicket.setDegreeOfCurator(ticket.getDegreeOfCurator());
        existingTicket.setDegreeOfCuratorEng(ticket.getDegreeOfCuratorEng());
        if (ticket.getAct() != null) {
            existingTicket.setAct(ticket.getAct());
        }
        //-------------------------------------------------------------------
        session.save(existingTicket);
        session.flush();
    }

    //---------------------------------------------------------
    public void editPdf(Ticket ticket, boolean secret) {

        Session session = sessionFactory.getCurrentSession();
        Ticket existingTicket = session.get(Ticket.class, ticket.getId());
        if (!secret) {
            existingTicket.setFilePdf(ticket.getFilePdf());
        } else {
            existingTicket.setFilePdfSecret(ticket.getFilePdfSecret());
        }
        session.save(existingTicket);
    }

    public void editZip(Ticket ticket, boolean secret) {
        Session session = sessionFactory.getCurrentSession();
        Ticket existingTicket = session.get(Ticket.class, ticket.getId());
        if (!secret) {
            existingTicket.setFileZip(ticket.getFileZip());
        } else {
            existingTicket.setFileZipSecret(ticket.getFileZipSecret());
        }
        session.save(existingTicket);
    }
    public void editAct(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();

        Ticket existingTicket = session.get(Ticket.class, ticket.getId());

        existingTicket.setAct(ticket.getAct());
        existingTicket.setStatus(ticket.getStatus());
        session.save(existingTicket);
    }
}
