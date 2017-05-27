package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.*;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by gagar on 16.05.2017.
 */

@Service("authenticationService")
@Transactional
public class AuthenticationService {

    protected static Logger logger = Logger.getLogger("controller");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public StudentCopy getStudentCopy(String name) {

        Session session = sessionFactory.getCurrentSession();

        StudentCopy studentCopy = (StudentCopy) session.get(StudentCopy.class, name);

        return studentCopy;
    }

    public EmployeeCopy getEmployeeCopy(String name) {

        Session session = sessionFactory.getCurrentSession();

        EmployeeCopy employeeCopy = (EmployeeCopy) session.get(EmployeeCopy.class, name);

        return employeeCopy;
    }

    public List<GrantedAuthority> getAuthorities(Integer userId){

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("select P.name FROM Permissions as P JOIN P.roles as R " +
                "                                             LEFT OUTER JOIN R.users as R" +
                "                                             WHERE R.id="+userId);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<String> permissions = query.list();
        for (String permission: permissions){
            grantedAuthorities.add(new SimpleGrantedAuthority(permission));
        }

        return grantedAuthorities;
    }


}
