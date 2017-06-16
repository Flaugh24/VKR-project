package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.barmaley.vkr.autentication.UserDAOImpl;
import org.barmaley.vkr.domain.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

//import javax.transaction.Transaction;

@Service("usersService")
@Transactional
public class UsersService {

    protected static Logger logger = Logger.getLogger("service");
    private final UserDAOImpl userDAO;
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Autowired
    public UsersService(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }

    public Users addUser(Users user) {

        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        session.flush();

        return user;
    }

    public void editUser(Users user) {

        Session session = sessionFactory.getCurrentSession();
        Users existingUser = session.get(Users.class, user.getId());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setSurnameEng(user.getSurnameEng());
        existingUser.setFirstNameEng(user.getFirstNameEng());
        existingUser.setSecondNameEng(user.getSecondNameEng());
        session.save(existingUser);

        userDAO.newAuthentication();

    }

    public Users getByExtId(String extId) {
        Session session = sessionFactory.getCurrentSession();
        logger.debug("Get user by extId");
        Query query = session.createQuery("FROM Users WHERE extId='" + extId + "'");
        return (Users) query.uniqueResult();
    }

    public Users getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        logger.debug("Get user by id");
        Query query = session.createQuery("FROM Users as U WHERE U.id=" + id);
        return (Users) query.uniqueResult();
    }

    //Метод для поиска людей, которые учатся в 1 институте
    public List getInstitute(String institute) {
        Session session = sessionFactory.getCurrentSession();
        logger.debug("Get users by institute");
        Query query = session.createQuery("FROM StudentCopy WHERE institute = :paramName");
        query.setParameter("paramName", institute);

        return query.list();
    }


    //Метод для поиска людей, которые учатся в 1 институте, в 1 кафедре, в 1 группе
    public List getInstituteAndDirection(String institute, String direction) {
        Session session = sessionFactory.getCurrentSession();
        logger.debug("Get users by institute");
        Query query = session.createQuery("FROM StudentCopy WHERE institute = :paramInstitute AND direction = :paramDirection");
        query.setParameter("paramInstitute", institute);
        query.setParameter("paramDirection", direction);

        return query.list();
    }

}