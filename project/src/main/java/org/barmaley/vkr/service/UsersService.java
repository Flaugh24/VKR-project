package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.StudentCopy;
import org.barmaley.vkr.domain.Ticket;
import org.barmaley.vkr.domain.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
//import javax.transaction.Transaction;
import java.util.List;

/**
 * Created by gagar on 28.04.2017.
 */
@Service("usersService")
@Transactional
public class UsersService {

    protected static Logger logger = Logger.getLogger("service");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public void addUser(Users user){

        Session session = sessionFactory.getCurrentSession();
        logger.debug("session" + Integer.toHexString(System.identityHashCode(session)) + "---" + session.hashCode());
        session.save(user);
        session.flush();
    }

    public void editUser(Users user){

        Session session = sessionFactory.getCurrentSession();
        Users existingUser = (Users) session.get(Users.class, user.getId());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setSurnameEng(user.getSurnameEng());
        existingUser.setFirstNameEng(user.getFirstNameEng());
        existingUser.setSecondNameEng(user.getSecondNameEng());
        session.save(existingUser);
    }

    public Users getByExtId(String extId) {
        Session session = sessionFactory.getCurrentSession();
        logger.debug("Get user by extId");
        Query query = session.createQuery("FROM Users as U WHERE U.extId='"+extId+"'");
        return (Users) query.uniqueResult();
    }

    public Users getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        logger.debug("Get user by id");
        Query query = session.createQuery("FROM Users as U WHERE U.id="+id);
        return (Users) query.uniqueResult();
    }

    //Метод для поиска людей, которые учатся в 1 институте
    public List<Users> getInstitute(String institute){
        Session session = sessionFactory.getCurrentSession();
        logger.debug("Get users by institute");
        Query query = session.createQuery("FROM StudentCopy WHERE institute = :paramName");
        query.setParameter("paramName", institute);

        return query.list();
    }


    //Метод для поиска людей, которые учатся в 1 институте, в 1 кафедре, в 1 группе
    public List<StudentCopy> getInstituteAndDirection(String institute, String direction){
        Session session = sessionFactory.getCurrentSession();
        logger.debug("Get users by institute");
        Query query = session.createQuery("FROM StudentCopy WHERE institute = :paramInstitute AND direction = :paramDirection");
        query.setParameter("paramInstitute", institute);
        query.setParameter("paramDirection", direction);

        return query.list();
    }

}