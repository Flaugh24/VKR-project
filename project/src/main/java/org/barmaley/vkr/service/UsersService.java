package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.barmaley.vkr.autentication.UserDAOImpl;
import org.barmaley.vkr.domain.Ticket;
import org.barmaley.vkr.domain.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("usersService")
@Transactional
public class UsersService {

    private final Logger logger = Logger.getLogger(UsersService.class);

    private final UserDAOImpl userDAO;

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Autowired
    public UsersService(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }

    //Запрос пользователя по id
    public Users get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Users.class, id);
    }

    //Запрос пользователя по Username
    public Users getByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Users WHERE username= :username ");
        query.setParameter("username", username);
        return (Users) query.uniqueResult();
    }

    //Добавление нового пользователя
    public Users add(Users user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        session.flush();
        return user;
    }

    //Редактирование пользователя
    public void edit(Users user) {

        Session session = sessionFactory.getCurrentSession();
        Users existingUser = session.get(Users.class, user.getId());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setSurnameEng(user.getSurnameEng());
        existingUser.setFirstNameEng(user.getFirstNameEng());
        existingUser.setSecondNameEng(user.getSecondNameEng());
        session.save(existingUser);

        //Обновление Authentication
        userDAO.newAuthentication();

    }
}