package org.barmaley.vkr.service;

import org.apache.log4j.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.barmaley.vkr.domain.Ticket;
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

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    public Integer getCountTicketsWithStatus(Integer statusId){
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("COUNT FROM Ticket WHERE status.id="+statusId);

        return (Integer)query.uniqueResult();

    }


    public List<Ticket> getAll() {

        // Retrieve session from Hibernate
        // Получаем сессию
        Session session = sessionFactory.getCurrentSession();

        // Create a Hibernate query (HQL)
        // Создаем запрос
        Query query = session.createQuery("FROM  Ticket ORDER BY Id");

        // Retrieve all
        // получаем всех
        return  query.list();
    }

    public List<Ticket> getAllTicketForCoordinator(String groupNum, Integer statusId){

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM  Ticket WHERE groupNum = :groupNum AND status.id = :statusId" +
                    " ORDER BY Id"
        );
        query.setParameter("groupNum", groupNum);
        query.setParameter("statusId", statusId);

        return query.list();
    }

    public List<Ticket> getAllTicketsByUserId(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        // Создаем запрос
        Query query = session.createQuery("FROM  Ticket WHERE user.id = " + userId + " ORDER BY Id");


        // Retrieve all
        // получаем всех
        return  query.list();
    }

    /**
     * Retrieves a single person
     * Получение одной персоны
     */
    public Ticket get( String id ) {
        // Retrieve session from Hibernate
        // получаем сессию
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing person first
        // получаем персону по id
        Ticket ticket = (Ticket) session.get(Ticket.class, id);

        return ticket;
    }

    public Integer getStatusId(String id) {
/*        logger.debug("Deleting existing credit card");*/

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Delete reference to foreign key credit card first
        // We need a SQL query instead of HQL query here to access the third table
        Query query = session.createSQLQuery("SELECT STATUS FROM TICKET " +
                "WHERE ID='"+id+"'");

        return (Integer) query.uniqueResult();
    }

    public Integer getDocumentTypeId(String id) {
/*        logger.debug("Deleting existing credit card");*/

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Delete reference to foreign key credit card first
        // We need a SQL query instead of HQL query here to access the third table
        Query query = session.createSQLQuery("SELECT DOCUMENT_TYPE FROM TICKET " +
                "WHERE ID='"+id+"'");

        return (Integer) query.uniqueResult();
    }

    public Integer getTypeOfUse(String id) {
/*        logger.debug("Deleting existing credit card");*/

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Delete reference to foreign key credit card first
        // We need a SQL query instead of HQL query here to access the third table
        Query query = session.createSQLQuery("SELECT TYPE_OF_USE FROM TICKET " +
                "WHERE ID='"+id+"'");

        return (Integer) query.uniqueResult();
    }
    /**
     * Adds a new person
     *  Добавление персоны
     */

    public String add(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();
        session.save(ticket);
        session.flush();

        return ticket.getId();
    }

    public void delete(String id) {
        Session session = sessionFactory.getCurrentSession();

        Ticket ticket = (Ticket) session.get(Ticket.class, id);

        session.delete(ticket);
    }

    public void edit(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();

        Ticket existingTicket = (Ticket) session.get(Ticket.class, ticket.getId());

        existingTicket.setTitle(ticket.getTitle());
        existingTicket.setTitleEng(ticket.getTitleEng());
        existingTicket.setAnnotation(ticket.getAnnotation());
        existingTicket.setAnnotationEng(ticket.getAnnotationEng());
        existingTicket.setKeyWords(ticket.getKeyWords());
        existingTicket.setKeyWordsEng(ticket.getKeyWordsEng());
        existingTicket.setTypeOfUse(ticket.getTypeOfUse());
        existingTicket.setStatus(ticket.getStatus());
        if(ticket.getDateCreationFinish() != null){
            existingTicket.setDateCreationFinish(ticket.getDateCreationFinish());
        }
        if(ticket.getDateCheckCoordinatorStart() != null){
            existingTicket.setDateCheckCoordinatorStart(ticket.getDateCheckCoordinatorStart());
        }
        if(ticket.getDateCheckCoordinatorFinish() != null){
            existingTicket.setDateCheckCoordinatorFinish(ticket.getDateCheckCoordinatorFinish());
        }
        if(ticket.getDateReturn() != null){
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
        //-------------------------------------------------------------------
        session.save(existingTicket);
    }
    //---------------------------------------------------------
    public void editPdf(Ticket ticket) {

        Session session = sessionFactory.getCurrentSession();

        Ticket existingTicket = (Ticket) session.get(Ticket.class, ticket.getId());

        existingTicket.setFilePdf(ticket.getFilePdf());
        session.save(existingTicket);
    }

    public void editZip(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();

        Ticket existingTicket = (Ticket) session.get(Ticket.class, ticket.getId());
        existingTicket.setFileZip(ticket.getFileZip());
        session.save(existingTicket);
    }
}
