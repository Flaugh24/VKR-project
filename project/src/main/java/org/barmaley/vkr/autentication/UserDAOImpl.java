package org.barmaley.vkr.autentication;

import org.apache.log4j.Logger;

import org.barmaley.vkr.domain.*;
import org.barmaley.vkr.service.*;
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

    @Resource(name = "studentCopyService")
    private StudentCopyService studentCopyService;

    @Resource(name = "employeeCopyService")
    private EmployeeCopyService employeeCopyService;


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
       StudentCopy studentCopy = new StudentCopy();
        if(educId==0){
           studentCopy =studentCopyService.get(username);
       }else{
           EducProgram educProgram=  educProgramService.get(educId);
           studentCopy = educProgram.getStudent();
       }

        CustomUser customUser = new CustomUser();

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

        EmployeeCopy employeeCopy = new EmployeeCopy();
        if (fullname.equals("ИИИ")){
            employeeCopy = employeeCopyService.get(username);
        }else{
            employeeCopy = authenticationService.getEmployeeByFIO(fullname);
        }
        CustomUser customUser = new CustomUser();

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
