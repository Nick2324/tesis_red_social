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
import java.util.List;
import java.util.Map;

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
            Log.d("NICK:URL", Constants.ROOT_URL + serviceURL);
            Log.d("Nick:Conexion","Intento abrir conexion****");
            HttpURLConnection conn = (HttpURLConnection) completeUrl.openConnection();
            Log.d("Conexion abierta","abro conexion");
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            if(parametros != null)
                conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod(method.toUpperCase());
            Log.d("Nick:headerA","**********************");
            for (Map.Entry<String, List<String>> header : conn.getHeaderFields().entrySet()) {
                Log.d("Nick:header",(header.getKey() + "=" + header.getValue()));
            }
            conn.setDoInput(true);
            for (Map.Entry<String, List<String>> header : conn.getHeaderFields().entrySet()) {
                Log.d("Nick:header",(header.getKey() + "=" + header.getValue()));
            }
            Log.d("Nick:headerD","**********************");
            // Starts the query
            if(parametros != null) {
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                Log.d("Nick:Status",new Integer(conn.getResponseCode()).toString());
                String parametrosString = parametros.toString();
                wr.write(parametros.toString());
                wr.flush();
            }
            conn.connect();

            is = conn.getInputStream();

            retorno = Utils.convertStreamToString(is);

        } catch (MalformedURLException e) {
            Log.w("MalformedURLException",e.getMessage());
        } catch (ProtocolException e) {
            Log.w("ProtocolException", e.getMessage());
        } catch (IOException e) {
            Log.w("IOException", e.getMessage());
        }
        return retorno;
    }
}
