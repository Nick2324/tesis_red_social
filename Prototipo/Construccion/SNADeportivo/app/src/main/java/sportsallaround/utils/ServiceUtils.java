package sportsallaround.utils;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by razie on 10/05/2015.
 */
public class ServiceUtils {

    private ServiceUtils(){}

    public static String invokeService(JSONObject parametros, String serviceURL, String method){

        InputStream is;
        String retorno = "";

        try {
            URL completeUrl = null;
            if(parametros != null && method.equals("GET")){
                Iterator<String> it = parametros.keys();
                StringBuilder queryParams = new StringBuilder();
                String actualKey;
                while(it.hasNext()){
                    actualKey = it.next();
                    try {
                        queryParams.append("&").append(actualKey).append("=").append(parametros.get(actualKey));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                completeUrl = new URL(Constants.ROOT_URL + serviceURL + "?" + queryParams.substring(1));
            }
            else
                completeUrl = new URL(Constants.ROOT_URL + serviceURL);

            HttpURLConnection conn = (HttpURLConnection) completeUrl.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            if(parametros != null && !method.equals("GET"))
                conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod(method.toUpperCase());


            conn.setDoInput(true);

            // Starts the query
            if(parametros != null && !method.equals("GET")) {
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(parametros.toString());
                wr.flush();
            }
            conn.connect();

            is = conn.getInputStream();

            retorno = Utils.convertStreamToString(is);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retorno;
    }
}
