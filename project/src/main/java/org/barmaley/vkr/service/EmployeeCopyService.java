package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.EmployeeCopy;
import org.barmaley.vkr.domain.StudentCopy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by gagarkin on 30.05.17.
 */
@Service("employeeCopyService")
@Transactional
public class EmployeeCopyService {
    protected static Logger logger = Logger.getLogger("service");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public List<EmployeeCopy> getEmployeeByFIO(String like){

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM EmployeeCopy where concat(surname,' ', firstName,' ', secondName) like :fioLike" );

        session.flush();
        query.setParameter("fioLike", "%"+like+"%");

        return query.list();
    }
}