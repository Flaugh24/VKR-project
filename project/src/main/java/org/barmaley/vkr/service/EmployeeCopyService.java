package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.EmployeeCopy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("employeeCopyService")
@Transactional
public class EmployeeCopyService {
    protected static Logger logger = Logger.getLogger("service");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;


    public EmployeeCopy get(String username) {

        Session session = sessionFactory.getCurrentSession();

        return session.get(EmployeeCopy.class, username);
    }


    public List getEmployeeByFullName(String fullName) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM EmployeeCopy where concat(surname,' ', firstName,' ', secondName) like :fullName");

        session.flush();
        query.setParameter("fullName", "%" + fullName + "%");

        return query.list();
    }
}