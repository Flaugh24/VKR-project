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

@Service("ticketService")
@Transactional
public class TicketService {

    protected static Logger logger = Logger.getLogger(TicketService.class);

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;


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

    //Запрос заявки по id
    public Ticket get(String id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Ticket.class, id);
    }

    //Добавление новой заявки
    public String add(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();
        session.save(ticket);
        session.flush();
        return ticket.getId();
    }

    //Редактирование заявки
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
        existingTicket.setPlaceOfPublic(ticket.getPlaceOfPublic());
        existingTicket.setPlaceOfPublicEng(ticket.getPlaceOfPublicEng());
        existingTicket.setYearOfPublic(ticket.getYearOfPublic());
        existingTicket.setHeadOfDepartment(ticket.getHeadOfDepartment());
        existingTicket.setCuratorId(ticket.getCuratorId());
        existingTicket.setFullNameCurator(ticket.getFullNameCurator());
        existingTicket.setFullNameCuratorEng(ticket.getFullNameCuratorEng());
        existingTicket.setPosOfCurator(ticket.getPosOfCurator());
        existingTicket.setPosOfCuratorEng(ticket.getPosOfCuratorEng());
        existingTicket.setDegreeOfCurator(ticket.getDegreeOfCurator());
        existingTicket.setDegreeOfCuratorEng(ticket.getDegreeOfCuratorEng());
        if (ticket.getAct() != null) {
            existingTicket.setAct(ticket.getAct());
        }
        session.save(existingTicket);
        session.flush();
    }

    //Изменение ссылок на файлы pdf
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


    //Изменение ссылок на файлы zip
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

    //Изменение акта
    public void editAct(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();

        Ticket existingTicket = session.get(Ticket.class, ticket.getId());

        existingTicket.setAct(ticket.getAct());
        existingTicket.setStatus(ticket.getStatus());
        session.save(existingTicket);
    }
}
