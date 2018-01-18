package inteligenty_zamek.app_ik.API;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class HTTPRequestAPI extends
        AsyncTask<Void, Void, String> {




    String urlString;
    HashMap DataToSend;
    String methodName;
    Object presenter;

    public HTTPRequestAPI(Object presenter,String url,String methodName,HashMap DataToSend) {


// Install the all-trusting trust manager

        this.urlString=url;
        this.DataToSend=DataToSend;
        this.methodName=methodName;
        this.presenter=presenter;
    }


    @Override
    protected String doInBackground(Void... params) {
        String response = "";
        try {
            response = performPostCall(urlString, DataToSend);
        } catch (Exception e) {
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {

        java.lang.reflect.Method method;
        Log.i("HHHH","8");
        Log.i("HHHH",response);
        try {
            method = presenter.getClass().getMethod(methodName, String.class);
            method.invoke(presenter, response);

        }catch(Exception ex){
        }
    }

    public String performPostCall(String requestURL,
                                  HashMap<String, String> postDataParams) {
        Log.i("HHHH",requestURL);
        URL url;
        String response = "";
        try {
            url=new URL(requestURL.replace("http:","https:") );
            HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new NullX509TrustManager()}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
            javax.net.ssl.HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();

            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();

          //  int responseCode = conn.getResponseCode();


                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));

                while ((line = br.readLine()) != null) {
                    response += line;
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;


        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}

