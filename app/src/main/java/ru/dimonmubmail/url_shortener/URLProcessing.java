package ru.dimonmubmail.url_shortener;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dmitriy Mubarakshin on 20.11.2014.
 */
public class URLProcessing {
    static InputStream inputStream = null;
    static JSONObject jObject = null;
    static String json = "";
    static String link;
    public URLProcessing(String link) {
        this.link = link;
    }
    public MyObject getResponse() throws IOException,JSONException {
        MyObject myObject;
        String stringURL = "https://www.googleapis.com/urlshortener/v1/url?shortUrl=" + link + "&projection=FULL";
        URL url = new URL(stringURL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            myObject = readStream(in);
        } finally {
            urlConnection.disconnect();
        }
        return myObject;
    }

    private MyObject readStream(InputStream in) throws IOException, JSONException {
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        String response = sb.toString();
        MyObject myObject = new MyObjectParser().parse(response);
        return myObject;
    }

    public JSONObject getJSON(String address, String longURL) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(address);
            httpPost.setEntity(new StringEntity("{\"longUrl\":\"" + longURL + "\"}"));
            httpPost.setHeader("Content-Type", "application/json");
            HttpResponse hR = httpClient.execute(httpPost);
            HttpEntity hE = hR.getEntity();
            inputStream = hE.getContent();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line + "\n");
            }
            inputStream.close();
            json = sb.toString();
            Log.e("JSON", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result" + e.toString());
        }
        try {
            jObject = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data" + e.toString());
        }
        return jObject;
    }
}
