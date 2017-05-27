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


    public List<Ticket> getCoordinatorForStudents(Integer id){
        Session session = sessionFactory.getCurrentSession();
        logger.debug("Get users by institute");
        Query query = session.createQuery("FROM Ticket WHERE status.id="+id);

        return query.list();
    }
    /**
     * Retrieves all persons
     * Получает лист всех персон
     * @return a list of persons
     */
    public List<Ticket> getAll() {
        logger.debug("Retrieving all persons");

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
        logger.debug("Retrieving all persons");

        // Retrieve session from Hibernate
        // Получаем сессию
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
        logger.debug("Adding new ticket");

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        logger.debug("add ticket" + session.hashCode());

        // Retrieve existing person via id

        // Add person to credit card
        // Persists to db
        session.save(ticket);
        session.flush();

        return ticket.getId();
    }

    /**
     * Deletes an existing person
     * Удаление существующей персоны
     * @param id the id of the existing person
     */
    public void delete(String id) {
        logger.debug("Deleting existing person");

        // Retrieve session from Hibernate
        // получаем сессию
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing person first
        // получаем существующую персону
        Ticket ticket = (Ticket) session.get(Ticket.class, id);

        // Delete
        // удаляем
        session.delete(ticket);
    }

    /**
     * Edits an existing person
     * Правка персоны
     */
    public void edit(Ticket ticket) {
        logger.debug("Editing existing ticket") ;
        logger.debug("Editing existing ticket: "+ticket.getId());
        // Retrieve session from Hibernate
        // как всегда получаем сессию
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing person via id
        // получаем существующую персону по id
        Ticket existingTicket = (Ticket) session.get(Ticket.class, ticket.getId());
       // Ticket existingTicket = (Ticket) session.get(Ticket.class, "V17-1");
        // Assign updated values to this person
        // обновляем значения
        logger.debug(ticket.getStatus().getName());
        existingTicket.setTitle(ticket.getTitle());
        existingTicket.setTitleEng(ticket.getTitleEng());
        existingTicket.setAnnotation(ticket.getAnnotation());
        existingTicket.setAnnotationEng(ticket.getAnnotationEng());
        existingTicket.setKeyWords(ticket.getKeyWords());
        existingTicket.setKeyWordsEng(ticket.getKeyWordsEng());
        //existingTicket.setFilePdf(ticket.getFilePdf());
        existingTicket.setTypeOfUse(ticket.getTypeOfUse());
        existingTicket.setStatus(ticket.getStatus());
        existingTicket.setDateCreationFinish(ticket.getDateCreationFinish());
        existingTicket.setDateCheckCoordinatorStart(ticket.getDateCheckCoordinatorStart());
        //-------------------------------------------------------------------
        existingTicket.setPlaceOfPublic(ticket.getPlaceOfPublic());
        existingTicket.setPlaceOfPublicEng(ticket.getPlaceOfPublicEng());
        existingTicket.setYearOfPublic(ticket.getYearOfPublic());
        existingTicket.setSurFirstLastNameDir(ticket.getSurFirstLastNameDir());
        existingTicket.setSflNMaster(ticket.getSflNMaster());
        existingTicket.setSflNMasterEng(ticket.getSflNMasterEng());
        //-------------------------------------------------------------------
        // Save updates
        // сохраняем изменения
        session.save(existingTicket);
    }
    //---------------------------------------------------------
    public void editPdf(Ticket ticket) {
        logger.debug("Editing existing ticket");

        // Retrieve session from Hibernate
        // как всегда получаем сессию
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing person via id
        // получаем существующую персону по id
        Ticket existingTicket = (Ticket) session.get(Ticket.class, ticket.getId());

        // Assign updated values to this person
        // обновляем значения

        existingTicket.setFilePdf(ticket.getFilePdf());

        // Save updates
        // сохраняем изменения
        session.save(existingTicket);
    }

    public void editRar(Ticket ticket) {
        logger.debug("Editing existing ticket");

        // Retrieve session from Hibernate
        // как всегда получаем сессию
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing person via id
        // получаем существующую персону по id
        Ticket existingTicket = (Ticket) session.get(Ticket.class, ticket.getId());

        // Assign updated values to this person
        // обновляем значения

        //existingTicket.setFilePdf(ticket.getFilePdf());
        existingTicket.setFileRar(ticket.getFileRar());

        // Save updates
        // сохраняем изменения
        session.save(existingTicket);
    }
//---------------------------------------------------------
}
