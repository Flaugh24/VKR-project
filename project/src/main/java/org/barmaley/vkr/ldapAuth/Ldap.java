package org.barmaley.vkr.ldapAuth;

/**
 * Created by impolun on 17.06.17.
 */
import org.apache.log4j.Logger;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class Ldap
{
    protected static Logger logger = Logger.getLogger(Ldap.class);

    public Ldap(String username, String password) throws AuthenticationException, NamingException{
        ldapConnector(username+"@FLIB", password);
    }

    static DirContext ldapContext;
    public static void ldapConnector (String username, String password) throws AuthenticationException, NamingException{


        Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
        ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        ldapEnv.put(Context.PROVIDER_URL, "ldap://194.85.183.70:389");
        ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
        ldapEnv.put(Context.SECURITY_PRINCIPAL, username);
        ldapEnv.put(Context.SECURITY_CREDENTIALS, password);
        ldapContext = new InitialDirContext(ldapEnv);
        logger.info("ldap success");
        ldapContext.close();
    }
}

