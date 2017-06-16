package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.Act;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("actService")
@Transactional
public class ActService {

    protected static Logger logger = Logger.getLogger("service");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;


    public List getAll() {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Act ORDER BY Id");

        return query.list();
    }

    public Act get(String id) {

        Session session = sessionFactory.getCurrentSession();

        return session.get(Act.class, id);
    }

    public List getAllActsByUserId(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        // Создаем запрос
        Query query = session.createQuery("FROM Act WHERE coordinator.id = " + userId + " ORDER BY Id");


        // Retrieve all
        // получаем всех
        return query.list();
    }

    public Act add(Act act) {
        Session session = sessionFactory.getCurrentSession();
        session.save(act);
        session.flush();

        return act;
    }

    public void delete(String id) {
        Session session = sessionFactory.getCurrentSession();

        Act act = session.get(Act.class, id);

        session.delete(act);
    }

    public void edit(Act act) {

        Session session = sessionFactory.getCurrentSession();

        Act existingAct = session.get(Act.class, act.getId());

        existingAct.setTickets(act.getTickets());
        existingAct.setDateOfAccept(act.getDateOfAccept());
        existingAct.setStatus(act.getStatus());
        session.save(existingAct);

        session.flush();
    }
}
