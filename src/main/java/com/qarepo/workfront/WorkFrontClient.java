package com.qarepo.workfront;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.*;
import java.util.Map;

public class WorkFrontClient {
    private String jobID;
    private String jobName;
    private String wfBaseUrl = "https://workfront.com/attask/api/v9.0";
    private String creds = "username=&password=";
    private String boundary = Long.toHexString(System.currentTimeMillis());
    private String separator = "\r\n";

    public WorkFrontClient() {
    }

    public WorkFrontClient(String jobName) {
        this.jobName = jobName;
        JsonNode node = (JsonNode) getResponse("project/search", "name=" + jobName + "&name_Mod=contains&method=get");
        System.out.println(node.asText());
        if (!node.isEmpty()) {
            this.jobID = node.get(0).get("ID").asText();
        }
    }

    public String getJobID() {
        return this.jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    /**
     * HTTP GET to retrieve details from WorkFront project, task, or issue
     *
     * @param wfEndPoint      the type of object to query. Choices are project, task, or issue
     * @param queryParameters fields to query
     * @return json representation of response
     */
    public Object getResponse(String wfEndPoint, String queryParameters) {
        JsonNode node = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            URL url = new URL(wfBaseUrl + "/" + wfEndPoint + "/" + jobID + "?" + creds + "&" + queryParameters);
            node = mapper.readTree(url);
            if (!node.isEmpty()) {
                node = node.path("data");
            }
            return node;
        } catch (IOException e) {
            return "WorkFront Project Lookup Failed - " + e.getMessage();
        }
    }

    public JsonNode put(String wfEndPoint, String queryParameters) {
        HttpURLConnection conn = null;
        try {
            conn = openConnection(wfBaseUrl + "/" + wfEndPoint + "/" + jobID + "?" + creds + "&" + queryParameters);
            conn.setRequestMethod("PUT");
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(getConnectionResponse(conn));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return null;
    }

    private HttpURLConnection openConnection(String URL) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            return conn;
        } catch (IOException e) {

        }
        return null;
    }

    private void addFileToRequest(Writer writer, OutputStream outputStream, File file) throws IOException {
        writer.append("--" + boundary).append(separator);
        writer.append("Content-Disposition: form-data; name=\"uploadedFile\"; filename=\"" + file.getName() + "\"").append(separator);
        writer.append("Content-Type " + URLConnection.guessContentTypeFromName(file.getName())).append(separator);
        writer.append("Content-Transfer-Encoding: binary").append(separator);
        writer.append(separator).flush();
        FileInputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        inputStream.close();
        writer.append(separator).flush();
        writer.append("--").append(boundary).append("--").append(separator).flush();
    }

    public String uploadFile(File file) {
        HttpURLConnection conn = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            conn = openConnection(wfBaseUrl + "/upload?" + creds + "&method=post");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            OutputStream content = conn.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(content, "UTF-8"), true);
            addFileToRequest(writer, content, file);
            String handle = mapper.readTree(getConnectionResponse(conn)).path("data").get("handle").asText();
            content.close();
            conn.disconnect();
            return handle;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return null;
    }

    public void moveFileToProject(String query) {
        HttpURLConnection conn = null;
        try {
            conn = openConnection(wfBaseUrl + "/document?" + creds + "&method=post" + query);
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            OutputStream connOutputStream = conn.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(connOutputStream, "UTF-8"), true);
            writer.write(query);
            writer.flush();
            writer.close();
            getConnectionResponse(conn);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public String concatQueryParams(Map<String, Object> queryParams) {
        String query = "";
        try {
            for (String key : queryParams.keySet()) {
                if (queryParams.get(key) instanceof String[]) {
                    String[] value = (String[]) queryParams.get(key);
                    for (int i = 0; i < value.length; i++) {
                        String val = value[i];
                        query += "&" + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(val, "UTF-8");
                    }
                } else {
                    query += "&" + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(String.valueOf(queryParams.get(key)), "UTF-8");
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return query;
    }

    private String getConnectionResponse(HttpURLConnection conn) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            return responseBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}