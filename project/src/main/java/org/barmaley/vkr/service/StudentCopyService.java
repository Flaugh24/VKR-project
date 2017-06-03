package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.StudentCopy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("studentCopyService")
@Transactional
public class StudentCopyService {
    protected static Logger logger = Logger.getLogger("service");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public StudentCopy get(String username) {
        logger.debug("Editing existing studentCopy");

        Session session = sessionFactory.getCurrentSession();

        return session.get(StudentCopy.class, username);
    }

    public List getStudentByEducProgram(String groupNum) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM StudentCopy as SC LEFT JOIN FETCH SC.educPrograms as EP where EP.groupNum = '" + groupNum +
                "' AND SC.username NOT IN( SELECT extId FROM Users) ORDER BY surname");

        return query.list();
    }

}

