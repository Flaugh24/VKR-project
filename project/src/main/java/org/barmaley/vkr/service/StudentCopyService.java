package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.StudentCopy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SUN_SUN on 06.05.2017.
 */
@Service("studentCopyService")
@Transactional
public class StudentCopyService {
    protected static Logger logger = Logger.getLogger("service");

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    public StudentCopy get(String username) {
        logger.debug("Editing existing studentCopy");

        // Retrieve session from Hibernate
        // как всегда получаем сессию
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing person via id
        // получаем существующую персону по id
        StudentCopy studentCopy = (StudentCopy) session.get(StudentCopy.class, username);

        // Assign updated values to this person
        // обновляем значения
        // Save updates
        // сохраняем изменения
        return studentCopy;
    }

    public List<StudentCopy> getStudentByEducProgram(String groupNum){

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM StudentCopy as SC LEFT JOIN FETCH SC.educPrograms as EP where EP.groupNum = '"+groupNum +
                "' AND SC.username NOT IN( SELECT extId FROM Users) ORDER BY surname");

        return query.list();
    }

}

