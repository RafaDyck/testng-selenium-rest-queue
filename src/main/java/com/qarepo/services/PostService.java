package com.qarepo.services;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostService implements Serializable {
    private static final Logger LOGGER = LogManager.getLogger(PostService.class);
    private static StringWriter sw = new StringWriter();

    public String executeBannerPost(final String msg, final String endpoint) {
        URL url;
        HttpURLConnection conn = null;
        StringBuffer response = null;
        String responseString = null;
        try {
            url = new URL(endpoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            DataOutputStream fos = new DataOutputStream(conn.getOutputStream());
            LOGGER.info("REQUEST MSG=" +msg);
            fos.writeBytes(msg);
            fos.flush();
            fos.close();

            InputStream is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            responseString = response.toString();
            return responseString;
        } catch (IOException e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.log(Level.ERROR, "Exception: " + sw.toString());
            responseString = sw.toString();
            return responseString;
        }
    }
}
