package org.barmaley.vkr.service;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.EmployeeCopy;
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

@Service("authenticationService")
@Transactional
public class AuthenticationService {

    protected static Logger logger = Logger.getLogger(AuthenticationService.class);

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public EmployeeCopy getEmployeeByFIO(String like) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM EmployeeCopy where concat(surname,' ', firstName,' ', secondName) like :fioLike");

        session.flush();
        query.setParameter("fioLike", "%" + like + "%");

        return (EmployeeCopy) query.uniqueResult();
    }

    public List<GrantedAuthority> getAuthorities(Integer userId) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT P.name FROM Permissions AS P JOIN P.roles AS R " +
                "                                             LEFT OUTER JOIN R.users AS U" +
                "                                             WHERE U.id=" + userId);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<String> permissions = query.list();
        for (String permission : permissions) {
            grantedAuthorities.add(new SimpleGrantedAuthority(permission));
        }

        return grantedAuthorities;
    }
}
