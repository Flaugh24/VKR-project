package org.barmaley.vkr.autentication;

import org.apache.log4j.Logger;

import org.barmaley.vkr.domain.*;
import org.barmaley.vkr.service.AuthenticationService;
import org.barmaley.vkr.service.EducProgramService;
import org.barmaley.vkr.service.RolesService;
import org.barmaley.vkr.service.UsersService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;


@Transactional
@Repository
public class UserDAOImpl {

    private static  Logger logger = Logger.getLogger(UserDAOImpl.class.getName());
    @Resource(name = "authenticationService")
    private AuthenticationService authenticationService;
    @Resource(name = "usersService")
    private UsersService usersService;
    @Resource(name = "rolesService")
    private RolesService rolesService;

    @Resource(name = "educProgramService")
    private EducProgramService educProgramService;


    public CustomUser loadUserByUsername(final String username) {

        CustomUser customUser = new CustomUser();
        Users user = usersService.getByUsername(username);
        if (user != null) {
            customUser.setId(user.getId());
            customUser.setUsername(user.getUsername());
            customUser.setPassword(user.getPassword());
            customUser.setExtId(user.getExtId());
            customUser.setOrigin(user.getOrigin());
            customUser.setFirstName(user.getFirstName());
            customUser.setSecondName(user.getSecondName());
            customUser.setSurname(user.getSurname());
            customUser.setFirstNameEng(user.getFirstNameEng());
            customUser.setSecondNameEng(user.getSecondNameEng());
            customUser.setSurnameEng(user.getSurnameEng());
            customUser.setEmail(user.getEmail());
            customUser.setPhoneNumber(user.getPhoneNumber());
            customUser.setCoordinatorRights(user.getCoordinatorRights());
            customUser.setRoles(user.getRoles());
            customUser.setAuthorities(authenticationService.getAuthorities(user.getId()));
            return customUser;
        }
        return null;
    }


    public CustomUser loadStudentByUsername(final String username, final int educId) {
        EducProgram educProgram=  educProgramService.get(educId);

        CustomUser customUser = new CustomUser();
        StudentCopy studentCopy = educProgram.getStudent();
        if (studentCopy != null) {
            Users user = new Users();
            Set<Roles> roles = new HashSet<>();
            if (studentCopy != null) {
                roles.add(rolesService.getRole(1));
                user.setExtId(studentCopy.getUsername());
                user.setUsername(username);
                user.setPassword(studentCopy.getPassword());
                user.setSurname(studentCopy.getSurname());
                user.setFirstName(studentCopy.getFirstName());
                user.setSecondName(studentCopy.getSecondName());
                user.setEnabled(true);
                user.setRoles(roles);
                user.setOrigin("StudentCopy");
                user = usersService.addUser(user);
            }
            customUser.setId(user.getId());
            customUser.setExtId(user.getExtId());
            customUser.setUsername(user.getUsername());
            customUser.setPassword(user.getPassword());
            customUser.setExtId(user.getExtId());
            customUser.setOrigin(user.getOrigin());
            customUser.setFirstName(user.getFirstName());
            customUser.setSecondName(user.getSecondName());
            customUser.setSurname(user.getSurname());
            customUser.setFirstNameEng(user.getFirstNameEng());
            customUser.setSecondNameEng(user.getSecondNameEng());
            customUser.setSurnameEng(user.getSurnameEng());
            customUser.setEmail(user.getEmail());
            customUser.setPhoneNumber(user.getPhoneNumber());
            customUser.setCoordinatorRights(user.getCoordinatorRights());
            customUser.setRoles(user.getRoles());
            customUser.setAuthorities(authenticationService.getAuthorities(user.getId()));
            return customUser;
        }
        return null;
    }


    public CustomUser loadEmployeeByUsername(final String username, final String fullname) {

        CustomUser customUser = new CustomUser();
        EmployeeCopy employeeCopy = authenticationService.getEmployeeByFIO(fullname);
        if (employeeCopy != null) {
            Users user = new Users();
            Set<Roles> roles = new HashSet<>();
            if (employeeCopy != null) {
                roles.add(rolesService.getRole(2));
                user.setExtId(employeeCopy.getUsername());
                user.setPassword(employeeCopy.getPassword());
                user.setSurname(employeeCopy.getSurname());
                user.setFirstName(employeeCopy.getFirstName());
                user.setSecondName(employeeCopy.getSecondName());
                user.setUsername(username);
                user.setEnabled(true);
                user.setRoles(roles);
                user.setOrigin("EmployeeCopy");
                user = usersService.addUser(user);
                }
            customUser.setId(user.getId());
            customUser.setUsername(user.getExtId());
            customUser.setPassword(user.getPassword());
            customUser.setExtId(user.getExtId());
            customUser.setUsername(user.getUsername());
            customUser.setOrigin(user.getOrigin());
            customUser.setFirstName(user.getFirstName());
            customUser.setSecondName(user.getSecondName());
            customUser.setSurname(user.getSurname());
            customUser.setFirstNameEng(user.getFirstNameEng());
            customUser.setSecondNameEng(user.getSecondNameEng());
            customUser.setSurnameEng(user.getSurnameEng());
            customUser.setEmail(user.getEmail());
            customUser.setPhoneNumber(user.getPhoneNumber());
            customUser.setCoordinatorRights(user.getCoordinatorRights());
            customUser.setRoles(user.getRoles());
            customUser.setAuthorities(authenticationService.getAuthorities(user.getId()));
            return customUser;
        }
        return null;
    }


    public Authentication newAuthentication() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = usersService.getById(customUser.getId());
        customUser.setId(user.getId());
        customUser.setUsername(user.getExtId());
        customUser.setExtId(user.getExtId());
        customUser.setOrigin(user.getOrigin());
        customUser.setFirstName(user.getFirstName());
        customUser.setSecondName(user.getSecondName());
        customUser.setSurname(user.getSurname());
        customUser.setFirstNameEng(user.getFirstNameEng());
        customUser.setSecondNameEng(user.getSecondNameEng());
        customUser.setSurnameEng(user.getSurnameEng());
        customUser.setEmail(user.getEmail());
        customUser.setPhoneNumber(user.getPhoneNumber());
        customUser.setCoordinatorRights(user.getCoordinatorRights());
        customUser.setRoles(user.getRoles());
        customUser.setAuthorities(authenticationService.getAuthorities(user.getId()));

        return new UsernamePasswordAuthenticationToken(customUser, auth.getCredentials(), customUser.getAuthorities());

    }
}
