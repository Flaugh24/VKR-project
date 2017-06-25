package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.EducProgram;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("educProgramService")
@Transactional
public class EducProgramService {

    protected static Logger logger = Logger.getLogger("service");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public List<EducProgram> getAll(String extId) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM EducProgram WHERE student = '" + extId + "'");

        return query.list();
    }

    public EducProgram get(Integer id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(EducProgram.class, id);
    }


    public EducProgram getByGroupNum(String groupNum) {
        Session session = sessionFactory.getCurrentSession();


        Query query = session.createQuery("FROM EducProgram AS EP WHERE groupNum= '" + groupNum + "'");

        return (EducProgram) query.uniqueResult();
    }

    public List getGroups(String department) {

        Session session = sessionFactory.getCurrentSession();


        Query query = session.createQuery(" SELECT DISTINCT groupNum FROM EducProgram WHERE department = :department");
        query.setParameter("department", department);

        return query.list();
    }
}
