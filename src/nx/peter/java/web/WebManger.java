package nx.peter.java.web;

import nx.peter.java.json.writer.JsonWriter;
import nx.peter.java.storage.FileManager;
import nx.peter.java.util.Util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class WebManger {

    public <O> O getUrlModel(CharSequence url, Class<O> clazz) {
        return new JsonWriter().getContext().fromJson(getUrlData(url, "GET"), false, clazz);
    }

    public static String getUrlData(CharSequence url, CharSequence requestMethod) {
        InputStream input;
        String data = "";
        HttpURLConnection conn = openHttpURLConnection(url);
        try {
            if (conn != null) {
                conn.setRequestMethod(requestMethod.toString());
                conn.setDoInput(true);
                conn.connect();
            }
            input = conn != null ? conn.getInputStream() : null;
            if (input != null)
                data = Util.toString(FileManager.readLines(input));
        } catch (IOException ignored) {} finally {
            if (conn != null)
                conn.disconnect();
        }
        return data;
    }

    public static BufferedReader getBufferedReader(CharSequence url, CharSequence requestMethod) {
        InputStreamReader reader = getInputStreamReader(url, requestMethod);
        return reader != null ? new BufferedReader(reader) : null;
    }

    public static BufferedWriter getBufferedWriter(CharSequence url) {
        OutputStreamWriter writer = getOutputStreamWriter(url);
        return writer != null ? new BufferedWriter(writer) : null;
    }

    public static InputStream getInputStream(CharSequence url, CharSequence requestMethod) {
        InputStream input = null;
        HttpURLConnection conn = openHttpURLConnection(url);
        try {
            if (conn != null) {
                conn.setRequestMethod(requestMethod.toString());
                conn.setDoInput(true);
                conn.connect();
            }
            input = conn != null ? conn.getInputStream() : null;
        } catch (IOException ignored) {}
        return input;
    }

    public static InputStreamReader getInputStreamReader(CharSequence url, CharSequence requestMethod) {
        InputStream input = getInputStream(url, requestMethod);
        return input != null ? new InputStreamReader(input) : null;
    }

    public static OutputStreamWriter getOutputStreamWriter(CharSequence url) {
        try {
            URLConnection conn = openConnection(url);
            if (conn != null)
                conn.setDoOutput(true);
            return conn != null ? new OutputStreamWriter(conn.getOutputStream()) : null;
        } catch (IOException e) {
            return null;
        }
    }

    public static URLConnection openConnection(CharSequence url) {
        try {
            return url != null ? new URL(url.toString()).openConnection() : null;
        } catch (IOException e) {
            return null;
        }
    }

    public static HttpURLConnection openHttpURLConnection(CharSequence url) {
        URLConnection conn = openConnection(url);
        return conn != null && url.toString().trim().startsWith("http") ? (HttpURLConnection) conn : null;
    }
}
