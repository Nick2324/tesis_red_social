package sportsallaround.utils;

import android.util.Log;

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

    public static Integer STATUS;

    private ServiceUtils(){}

    /*public static String invokeService(String parametros, String serviceURL, String method){
        String resultado = null;
        try {
            JSONObject parametrosJson = new JSONObject(parametros);
            resultado = invokeService(parametrosJson,serviceURL,method);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultado;

    }*/

    public static String invokeService(JSONObject parametros, String serviceURL, String method){

        Log.d("Nick:Servicio","Ejecutando servicio "+serviceURL);

        InputStream is;
        String retorno = "";

        try {
            URL completeUrl = null;

            //completeUrl = new URL(Constants.ROOT_URL + serviceURL);
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
            //if(parametros != null) {
            if(parametros != null && !method.equals("GET"))
                conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod(method.toUpperCase());
            conn.setDoInput(true);
            // Starts the query
            if(parametros != null && !method.equals("GET")) {
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                Log.d("Nick:JSONServ",parametros.toString());
                wr.write(parametros.toString());
                wr.flush();
            }
            conn.connect();
            STATUS = conn.getResponseCode();
            Log.d("Nick:Response", "Status: " + new Integer(conn.getResponseCode()).toString()+
                  "Servicio: " + serviceURL);
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
