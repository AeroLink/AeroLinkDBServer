/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interceptors;

import Synapse.R2SL;
import Synapse.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author BlackMoon
 */
public class aerolinkDB {

    PreparedStatement pst;

    JSONObject obj = new JSONObject();

    public JSONArray get(String sql) {
        try {
            this.pst = Session.INSTANCE.getConnection().prepareStatement(sql);

            System.out.println(Arrays.asList(RS2Obj.convert(this.pst.executeQuery())));

            return RS2Obj.convert(this.pst.executeQuery());

        } catch (SQLException ex) {
            Logger.getLogger(aerolinkDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public JSONArray get(String sql, String values) {
        try {

            this.pst = Session.INSTANCE.getConnection().prepareStatement(sql);
            ArrayList<String> list_values = new ArrayList();

            list_values.addAll(Arrays.asList(values.split(",")));
            System.out.println(sql);
            System.out.println(Arrays.asList(values.split(",")));

            for (int i = 1; i <= list_values.size(); i++) {
                System.out.println(list_values.get(i - 1));
                this.pst.setObject(i, list_values.get(i - 1));
            }

            JSONArray arr = RS2Obj.convert(this.pst.executeQuery());
            System.out.println(Arrays.asList(arr));

            return arr;

        } catch (SQLException ex) {
            Logger.getLogger(aerolinkDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public JSONObject insert(String sql, String values, Boolean returnID) {

        try {
            this.pst = returnID ? Session.INSTANCE.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) : Session.INSTANCE.getConnection().prepareStatement(sql);
            ArrayList<String> list_values = new ArrayList();

            list_values.addAll(Arrays.asList(values.split(",,")));
            System.out.println(values);

            for (int i = 1; i <= list_values.size(); i++) {
                Object nobj = String.valueOf(list_values.get(i - 1));
                System.out.println("Data -> " + nobj);
                this.pst.setObject(i, nobj);
            }

            if (this.pst.executeUpdate() != 0) {
                obj.put("success", true);
                if (returnID) {
                    obj.put("value", Integer.parseInt(((HashMap) R2SL.convert(this.pst.getGeneratedKeys()).get(0)).get("GENERATED_KEYS").toString()));
                }
                obj.put("message", "Successfully Inserted");
            }

        } catch (SQLException ex) {
            obj.put("success", false);
            obj.put("message", ex.getMessage());

        }

        return obj;
    }

    public JSONObject update(String sql, String values) {
        try {

            this.pst = Session.INSTANCE.getConnection().prepareStatement(sql);
            ArrayList<String> list_values = new ArrayList();

            list_values.addAll(Arrays.asList(values.split(",,")));
            System.out.println(sql);
            System.out.println(Arrays.asList(values.split(",,")));

            for (int i = 1; i <= list_values.size(); i++) {
                System.out.println(list_values.get(i - 1));
                this.pst.setObject(i, list_values.get(i - 1));
            }

            if (this.pst.executeUpdate() != 0) {
                obj.put("success", true);
                obj.put("message", "Successfully Update");
            }

        } catch (SQLException ex) {
            obj.put("success", false);
            obj.put("message", ex.getMessage());
        }

        return obj;
    }

    public JSONArray login(String username, String password) {
        Boolean passed = false;
        try {

            this.pst = Session.INSTANCE.getConnection().prepareStatement("SELECT * FROM aerolink.tbl_users WHERE username = ?");
            this.pst.setObject(1, username);

            List<HashMap> list = R2SL.convert(this.pst.executeQuery());

            if (!list.isEmpty()) {
                if (Synapse.Crypt.Decrypt(String.valueOf(list.get(0).get("password"))).equals(password)) {
                    passed = true;
                } else {
                    passed = false;
                }
            } else {
                passed = false;
            }

            return passed ? RS2Obj.convert(this.pst.executeQuery()) : new JSONArray();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return new JSONArray();
    }
}
