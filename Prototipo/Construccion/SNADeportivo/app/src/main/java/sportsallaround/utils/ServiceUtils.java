package sportsallaround.utils;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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

            completeUrl = new URL(Constants.ROOT_URL + serviceURL);
            HttpURLConnection conn = (HttpURLConnection) completeUrl.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            if(parametros != null) {
                conn.setRequestProperty("Content-Type", "application/json");
            }
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod(method.toUpperCase());
            conn.setDoInput(true);
            // Starts the query
            if(parametros != null) {
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                Log.d("Nick:JSON",parametros.toString());
                wr.write(parametros.toString());
                wr.flush();
            }
            conn.connect();

            Log.d("Nick:Status", new Integer(conn.getResponseCode()).toString());
            is = conn.getInputStream();

            retorno = Utils.convertStreamToString(is);

        } catch (MalformedURLException e) {
            Log.w("SNA:MalformedURLExc",e.getMessage());
        } catch (ProtocolException e) {
            Log.w("SNA:ProtocolException", e.getMessage());
        } catch (IOException e) {
            Log.w("SNA:IOException",e.getMessage());
        }

        return retorno;
    }
}
