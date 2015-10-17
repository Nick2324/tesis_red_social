package sportsallaround.utils.generales;

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

    private ServiceUtils(){}

    private static String invokeServiceAbstracto(JSONObject parametros,
                                                 String serviceURL,
                                                 String method,
                                                 boolean conBody){

        Log.d("Nick:Servicio",method+":"+serviceURL);

        InputStream is;
        String retorno = "";

        try {
            URL completeUrl = null;

            if(parametros != null && method.equals("GET") && !conBody){
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

            if(!method.equals("GET") || conBody){
                if(parametros != null) {
                    conn.setRequestProperty("Content-Type", "application/json");
                    Log.d("Nick:getContent", "OK");
                }
            }

            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod(method.toUpperCase());
            conn.setDoInput(true);
            // Starts the query
            if(!method.equals("GET") || conBody) {
                if(parametros != null ) {
                    conn.setDoOutput(true);
                    Log.d("Nick:setParam", "OK");
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    Log.d("Nick:JSONParam", parametros.toString());
                    wr.write(parametros.toString());
                    wr.flush();
                }
            }
            conn.connect();
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

    public static String invokeServiceBody(JSONObject parametros,
                                           String serviceURL,
                                           String method){

        return invokeServiceAbstracto(parametros,
                serviceURL,
                method,
                true);

    }

    public static String invokeService(JSONObject parametros, String serviceURL, String method){

        return invokeServiceAbstracto(parametros,
                                      serviceURL,
                                      method,
                                      false);

    }
}
