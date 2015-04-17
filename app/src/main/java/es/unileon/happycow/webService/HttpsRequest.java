/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unileon.happycow.webService;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author dorian
 */
public class HttpsRequest {
    
    public static String request(String path, List<Parameter> parameters) {
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };

        SSLContext sc=null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HttpsRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(HttpsRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        if(path.startsWith("/")){
            path=path.substring(1);
        }
        
        URL url=null;
        try {
            url = new URL("https://happycow.no-ip.org/"+path);
        } catch (MalformedURLException ex) {
            Logger.getLogger(HttpsRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        StringBuilder parametersUrl=new StringBuilder();
        for (Iterator<Parameter> iterator = parameters.iterator(); iterator.hasNext();) {
            Parameter next = iterator.next();
            parametersUrl.append(next.key);
            parametersUrl.append("=");
            parametersUrl.append(next.value);
            parametersUrl.append("&");
        }
        String urlParameters = parametersUrl.toString();
        byte[] postData = urlParameters.getBytes(Charset.forName("UTF-8"));
        int postDataLength = postData.length;

        //URLConnection con = url.openConnection();
        HttpURLConnection cox=null;
        try {
            cox = (HttpURLConnection) url.openConnection();
        } catch (IOException ex) {
            Logger.getLogger(HttpsRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        cox.setDoOutput(true);
        cox.setDoInput(true);
        cox.setInstanceFollowRedirects(false);
        try {
            cox.setRequestMethod("POST");
        } catch (ProtocolException ex) {
            Logger.getLogger(HttpsRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        cox.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        cox.setRequestProperty("charset", "utf-8");
        cox.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        cox.setUseCaches(false);
        try (DataOutputStream wr = new DataOutputStream(cox.getOutputStream())) {
            wr.write(postData);
        } catch (IOException ex) {
            Logger.getLogger(HttpsRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Reader reader=null;
        try {
            reader = new InputStreamReader(cox.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(HttpsRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        StringBuilder result=new StringBuilder();
        while (true) {
            int ch=0;
            try {
                ch = reader.read();
            } catch (IOException ex) {
                Logger.getLogger(HttpsRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (ch == -1) {
                break;
            }
            result.append((char)ch);
            //System.out.print((char) ch);
        }
        return result.toString();
    }
}

