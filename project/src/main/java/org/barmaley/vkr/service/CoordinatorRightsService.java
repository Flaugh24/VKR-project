package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.CoordinatorRights;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by gagar on 18.05.2017.
 */

@Service("coordinatorRightsService")
@Transactional
public class CoordinatorRightsService {

    protected static Logger logger = Logger.getLogger("controller");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public List getCoordinatorRights(Integer coordinatorId) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM CoordinatorRights WHERE coordinator.id = :coordinatorId ORDER BY groupNum");
        query.setParameter("coordinatorId", coordinatorId);

        logger.debug("Coordinator Rights");

        return query.list();

    }

    public CoordinatorRights add(CoordinatorRights coordinatorRights) {
        Session session = sessionFactory.getCurrentSession();
        session.save(coordinatorRights);
        session.flush();

        return coordinatorRights;
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();

        CoordinatorRights coordinatorRights = session.get(CoordinatorRights.class, id);

        session.delete(coordinatorRights);
    }

}
