package com.lmpay.starter.helper;

import com.lmpay.starter.config.AppProperties;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class Helper {
    private static AppProperties appProperties;
    private static RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(Helper.class);

    public static void init(AppProperties appProps, RestTemplate restTemp) {
        appProperties = appProps;
        restTemplate = restTemp;
    }

    public static String httpWithCertificates(String url, String jsonResult) throws IOException {
        loadCertificate(false);

        // Create a connection
        URL urlCon = new URL(url);
        HttpURLConnection httpConnection = (HttpURLConnection) urlCon.openConnection();
        httpConnection.setDoOutput(true);
        httpConnection.setRequestMethod("POST");
        setHeaders(httpConnection);

        DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
        wr.write(jsonResult.getBytes());
        int responseCode = httpConnection.getResponseCode();

        // Creates a reader buffer
        BufferedReader bufferedReader;
        if (responseCode >= 200 && responseCode <= 299) {
            bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
        }

        // To receive the response
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line).append("\n");
        }
        bufferedReader.close();

        // Clear the SSL props
        loadCertificate(true);

        return content.toString();
    }

    public static Response httpBasicAuth(String jsonResult, String url, String method) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String credentials = addBasicAuthentication(appProperties.username, appProperties.password);
        Request request;
        if (method.equals("POST")) {
            request = new Request.Builder()
                    .url(url)
                    .header("Authorization", credentials)
                    .header("Content-Type", "application/json")
                    .post(RequestBody.create(jsonResult.getBytes()))
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .header("Authorization", credentials)
                    .header("Content-Type", "application/json")
                    .get()
                    .build();
        }

        return client.newCall(request).execute();
    }


    private static void loadCertificate(boolean isClear) {
        if (isClear) {
            // Clear the system properties
            System.clearProperty("javax.net.ssl.trustStore");
            System.clearProperty("javax.net.ssl.trustStorePassword");
            System.clearProperty("javax.net.ssl.keyStore");
            System.clearProperty("javax.net.ssl.keyStorePassword");
            System.clearProperty("java.protocol.handler.pkgs");
            System.clearProperty("javax.net.ssl.keyStoreType");
        } else {
            // Add SSL Certificates
            System.setProperty("javax.net.ssl.trustStore", appProperties.trustStore);
            System.setProperty("javax.net.ssl.trustStorePassword", appProperties.trustStorePassword);
            System.setProperty("javax.net.ssl.keyStore", appProperties.keyStore);
            System.setProperty("javax.net.ssl.keyStorePassword", appProperties.keyStorePassword);
            System.setProperty("java.protocol.handler.pkgs", appProperties.protocolHandlerPackages);
            System.setProperty("javax.net.ssl.keyStoreType", appProperties.keyStoreType);
        }
    }

    private static String addBasicAuthentication(String username, String password) {
        return Credentials.basic(username, password);
    }

    private static void setHeaders(HttpURLConnection httpConnection) {
        httpConnection.setRequestProperty("Content-Type", "application/json");
        httpConnection.setRequestProperty("Accept", "application/json");
        httpConnection.setRequestProperty("x-ibm-client-secret", appProperties.clientSecret);
        httpConnection.setRequestProperty("x-ibm-client-id", appProperties.clientId);
    }

    public static OkHttpClient getTrustAllCertsClient() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };

        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        OkHttpClient.Builder newBuilder = new OkHttpClient.Builder();
        newBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
        newBuilder.hostnameVerifier((hostname, session) -> true);
        return newBuilder.build();
    }

    public static String ClientResponse(String url) {
        try {
            OkHttpClient newClient = getTrustAllCertsClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = newClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return Objects.requireNonNull(response.body()).string();
            } else {
                log.error("Request failed with error code: " + response.code());
            }
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            log.error("Error Loading Url",e);
        }
        return null;
    }

}
