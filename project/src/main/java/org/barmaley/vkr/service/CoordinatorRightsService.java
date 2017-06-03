package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
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

}
