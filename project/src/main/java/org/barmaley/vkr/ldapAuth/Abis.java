package org.barmaley.vkr.ldapAuth;

//import jdk.internal.org.xml.sax.InputSource;
//import jdk.internal.org.xml.sax.InputSource;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.*;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Abis {

    private static final Logger log = Logger.getLogger("RESTTest");
    private static XPath xpath = XPathFactory.newInstance().newXPath();
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

    public void testAuth() throws Exception {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(targetHost.getHostName(),
                targetHost.getPort()), creds);

        AuthCache authCache = new BasicAuthCache();
        AuthScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);

        localContext.setCredentialsProvider(credsProvider);
        localContext.setAuthCache(authCache);

        HttpGet httpget = new HttpGet(targetURL + "auth");
        HttpResponse response = httpClient.execute(httpget, localContext);
        HttpEntity entity = response.getEntity();
        EntityUtils.consumeQuietly(entity);
        log.info(String.valueOf((response.getStatusLine().getStatusCode())));
        assertTrue(response.getStatusLine().getStatusCode() >= 200
                && response.getStatusLine().getStatusCode() < 300);

    }

    public String searchRecordXML(boolean flag, String username) throws Exception  {
        testAuth();
        HttpGet httpget = new HttpGet   (
                "https://ruslan.library.spbstu.ru/rrs-web/db/LUSR+IMOPUSER+STDUSER+KIUUSERS%20+KIUUSERS2?startRecord=1&maximumRecords=1&queryType=cql&query=(ruslan.100%20=%20%22"+username+"%22)%20or%20(ruslan.132%20=%20%22"+username+"%22)%20or%20(ruslan.254%20=%20%22"+username+"%22)&resultSetTTL=3600");
       // httpget.setHeader("Accept", "application/json"); //Для формата json
        HttpResponse response = httpClient.execute(httpget, localContext);
        assertTrue(response.getStatusLine().getStatusCode() >= 200
                && response.getStatusLine().getStatusCode() < 300);
        HttpEntity entity = response.getEntity();
        String x = toString(entity);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(x));
        Document doc = builder.parse(is);
        EntityUtils.consumeQuietly(entity);
        assertEquals("application/sru+xml;charset=UTF-8", entity
                .getContentType().getValue());
        log.info(getTagByTagValue(doc,"101")+" "+getTagByTagValue(doc,"102")+" "+getTagByTagValue(doc,"103"));
        log.info(getTagByTagValue(doc,"252"));
        if(flag){
            return getTagByTagValue(doc,"101")+" "+getTagByTagValue(doc,"102")+" "+getTagByTagValue(doc,"103");
        }else
            return getTagByTagValue(doc,"252");
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

    public static String getTagByTagValue(Document doc, String tagValue) {
        try {
            Node n;
            XPathExpression expr =  xpath.compile("//record/tag[@tagValue='" + tagValue + "']");
            n = (Node) expr.evaluate(doc.getDocumentElement(),
                    XPathConstants.NODE);
            if (n != null) {
                return n.getTextContent();
            }
        } catch (XPathExpressionException e) {
            log.info("Error in XPath expression: " + e.getMessage());
        }

        return null;
    }
}