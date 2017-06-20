package org.barmaley.vkr.ldapAuth;//package org.barmaley.vkr.autentication;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.barmaley.vkr.autentication.CustomProvider;
import org.barmaley.vkr.autentication.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.io.*;
import java.util.List;
import java.util.logging.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.FileHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Abis {

    private static final HttpHost targetHost = new HttpHost(
            "ruslan.library.spbstu.ru", 443);
    private static final String targetURL = "https://ruslan.library.spbstu.ru:443/rrs-web/";
    private static final UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
            "flaugh", "4815162342");
    private static final RequestConfig globalConfig = RequestConfig.custom()
            .setCookieSpec(CookieSpecs.NETSCAPE).build();
    private static final CloseableHttpClient httpClient = HttpClients.custom()
            .setDefaultRequestConfig(globalConfig).build();
    private static final HttpClientContext localContext = HttpClientContext
            .create();

    // protected static Logger logger = Logger.getLogger("controller");


    protected static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("RESTTest");


    protected static Logger logger = Logger.getLogger("controller");


    public void testAuth() throws Exception{
        logger.debug("1");
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        logger.debug("2");
        credsProvider.setCredentials(new AuthScope(targetHost.getHostName(),
                targetHost.getPort()), creds);
        logger.debug("3");
        AuthCache authCache = new BasicAuthCache();
        logger.debug("4");
        AuthScheme basicAuth = new BasicScheme();
        logger.debug("5");
        authCache.put(targetHost, basicAuth);
        logger.debug("6");
        localContext.setCredentialsProvider(credsProvider);
        logger.debug("7");
        localContext.setAuthCache(authCache);
        logger.debug("8");
        HttpGet httpget = new HttpGet(targetURL + "auth");
        logger.debug("9");
        HttpResponse response = httpClient.execute(httpget, localContext);
        logger.debug("10");
        HttpEntity entity = response.getEntity();
        logger.debug("11");
        EntityUtils.consumeQuietly(entity);
        logger.debug("12");
        logger.info(String.valueOf((response.getStatusLine().getStatusCode())));
        assertTrue(response.getStatusLine().getStatusCode() >= 200
                && response.getStatusLine().getStatusCode() < 300);
       System.out.println("13");
    }

    public String searchRecordXML(boolean flag, String username) throws Exception  {
        System.out.println("1!!!!!");
        testAuth();
        HttpGet httpget = new HttpGet   (
                "https://ruslan.library.spbstu.ru/rrs-web/db/LUSR+IMOPUSER+STDUSER+KIUUSERS%20+KIUUSERS2?startRecord=1&maximumRecords=1&queryType=cql&query=(ruslan.100%20=%20%22"+username+"%22)%20or%20(ruslan.132%20=%20%22"+username+"%22)%20or%20(ruslan.254%20=%20%22"+username+"%22)&resultSetTTL=3600");
        HttpResponse response = httpClient.execute(httpget, localContext);

        assertTrue(response.getStatusLine().getStatusCode() >= 200
                && response.getStatusLine().getStatusCode() < 300);

        HttpEntity entity = response.getEntity();
        String entity1 = response.getEntity().toString();

//        FileHandler file;
//        try{
//            File file1 = new File("/home/impolun/github/LDAP-client/ABIS.txt");
//            if(file1.delete()){
//                logger.debug("КРУТО");
//            }else{
//                logger.debug("НЕт");
//            }
//            //if(file1.delete())
//            file = new FileHandler("/home/impolun/github/LDAP-client/ABIS.txt");
//
//            log.addHandler(file);
//            SimpleFormatter formatter = new SimpleFormatter();
//            file.setFormatter(formatter);
//            log.info(toString(entity));
//            //    log.info("Response:");
//
//            //   log.info(toString(entity));
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        ParsingABISXml parsing = new ParsingABISXml();
       List<String> list =  parsing.readAllABIS("/home/impolun/github/LDAP-client/ABIS.xml");
        logger.debug(list.size());
        logger.debug(list.get(30));
        logger.debug(list.get(4)+" "+list.get(5)+" "+list.get(6));
        EntityUtils.consumeQuietly(entity);
        assertEquals("application/sru+xml;charset=UTF-8", entity
                .getContentType().getValue());
    if(flag){
        return list.get(4)+" "+list.get(5)+" "+list.get(6);
    }else
        return list.get(30);
    }

    public static String toString(HttpEntity entity)
            throws UnsupportedEncodingException, IllegalStateException,
            IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(
                entity.getContent(), "UTF-8"));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null)
            result.append(line);

        return result.toString();
    }
    public static void write(String fileName, String text) {
        //Определяем файл
        File file = new File(fileName);

        try {
            //проверяем, что если файл не существует то создаем его
            if(!file.exists()){
                file.createNewFile();
            }

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                //Записываем текст у файл
                out.print(text);
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}

