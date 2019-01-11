/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Synapse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;

/**
 *
 * @author BlackMoon
 */
public class HttpClient {

    String res;
    HttpURLConnection connection;
    OutputStreamWriter request = null;
    URL url = null;
    private static String response = null;
    String jsonBuild = null;

    public static String post(String json) {

        try {

            String url = "http://127.0.0.1:5000/AeroLinkServer-war/api";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setUseCaches(false);

            // declare json as data
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            System.err.println(json);
            out.write(json);
            out.flush();
            out.close();

            //JOptionPane.showMessageDialog(null, json);
            int responseCode = con.getResponseCode();

            String line;

            InputStreamReader isr = new InputStreamReader(con.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                //JOptionPane.showMessageDialog(null, line);
                response = line;

            }

            return response;

            //System.out.println(res);
        } catch (Exception ex) {

            //handle exception here
            //JOptionPane.showMessageDialog(null, ex,"Access denied",JOptionPane.ERROR_MESSAGE);
            //preloader.setVisible(false);
            return ex.getMessage();

        }
    }

}
