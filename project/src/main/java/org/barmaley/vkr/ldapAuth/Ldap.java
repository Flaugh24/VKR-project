package org.barmaley.vkr.ldapAuth;

/**
 * Created by impolun on 17.06.17.
 */
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
    public Ldap(String username, String password) throws AuthenticationException, NamingException{
        ldapConnector(username+"@FLIB", password);
    }

    static DirContext ldapContext;
    public static void ldapConnector (String username, String password) throws AuthenticationException, NamingException{


        System.out.println("Début du test Active Directory");

        Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
        ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//ldapEnv.put(Context.PROVIDER_URL, "ldap://societe.fr:389");
        ldapEnv.put(Context.PROVIDER_URL, "ldap://194.85.183.70:389");
        ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
//ldapEnv.put(Context.SECURITY_PRINCIPAL, "cn=administrateur,cn=users,dc=societe,dc=fr");
        ldapEnv.put(Context.SECURITY_PRINCIPAL, username);
        ldapEnv.put(Context.SECURITY_CREDENTIALS, password);
//ldapEnv.put(Context.SECURITY_PROTOCOL, "ssl");
//ldapEnv.put(Context.SECURITY_PROTOCOL, "simple");
        ldapContext = new InitialDirContext(ldapEnv);

// Create the search controls
        SearchControls searchCtls = new SearchControls();

//Specify the attributes to return
        String returnedAtts[]={"sn","givenName", "samAccountName"};
        searchCtls.setReturningAttributes(returnedAtts);

//Specify the search scope
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

//specify the LDAP search filter
        String searchFilter = "(&(objectClass=user))";

//Specify the Base for the search
        String searchBase = "OU=Users,OU=ruslan,DC=FLIB";
//initialize counter to total the results
        int totalResults = 0;

// Search for objects using the filter
        NamingEnumeration<SearchResult> answer = ldapContext.search(searchBase, searchFilter, searchCtls);

//Loop through the search results
        while (answer.hasMoreElements())
        {
            SearchResult sr = (SearchResult)answer.next();

            totalResults++;

            System.out.println("»>" + sr.getName());
            Attributes attrs = sr.getAttributes();
            System.out.println("»»»" + attrs.get("samAccountName") + attrs.get("givenName"));
        }

        System.out.println("Total results: " + totalResults);
        ldapContext.close();

    }
}

